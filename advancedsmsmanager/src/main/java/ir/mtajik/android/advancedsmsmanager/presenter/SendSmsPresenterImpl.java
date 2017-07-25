package ir.mtajik.android.advancedsmsmanager.presenter;

import android.content.Context;
import android.os.Build;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ir.mtajik.android.advancedsmsmanager.model.MySmsManager;
import ir.mtajik.android.advancedsmsmanager.model.SendSmsModel;
import ir.mtajik.android.advancedsmsmanager.view.SendSmsView;


public class SendSmsPresenterImpl implements SendSmsPresenter {

    private static ArrayList<Integer> cariersICC = new ArrayList<Integer>();
    private static ArrayList<String> cariersNAME = new ArrayList<String>();
    private static List<SubscriptionInfo> subInfoList = new ArrayList<>();
    static private int mySmsId;
    private SendSmsView view;
    private Context context;
    private String body;
    private SendSmsModel model;
    private MySmsManager.SMSManagerCallBack callBack;

    public SendSmsPresenterImpl(SendSmsModel model, Context context) {
        this.context = context;
        this.model = model;
    }

    @Override
    public void closeDialog() {

        if (view != null) {
            view.endView();
        }

    }

    @Override
    public void startWiringUp(String message, String body, MySmsManager
            .SMSManagerCallBack
            callback) {

        this.callBack = callback;
        this.body = body;
        Random rand = new Random();
        this.mySmsId = rand.nextInt(3000 - 1) + 1; //random id between 1 and 3000

        if (view != null) {
            view.renderView(context, message);
        }


    }

    @Override
    public void prepareSendSms() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {

            SubscriptionManager mSubscriptionManager = SubscriptionManager.from(context);
            subInfoList = mSubscriptionManager.getActiveSubscriptionInfoList();
            if (subInfoList != null && subInfoList.size() > 1) {
                cariersICC.add(0, subInfoList.get(0).getSubscriptionId());
                cariersICC.add(1, subInfoList.get(1).getSubscriptionId());
                cariersNAME.add(0, subInfoList.get(0).getCarrierName().toString());
                cariersNAME.add(1, subInfoList.get(1).getCarrierName().toString());

                view.hideLoading();
                view.renderSimChooserView(cariersNAME.get(0), cariersNAME.get(1));

            } else {

                sendSmsFromSubscriptionId(0);

            }


        } else {
            sendSmsForOldPhones();
        }
    }

    public void sendSmsFromSubscriptionId(int i) {


        sendSmsForNewPhones(mySmsId, body, subInfoList.size(), i, ((cariersNAME.size() > 1) ?
                cariersNAME.get(i) : ""), callBack);
    }

    @Override
    public void cancelSendSms() {

        if (view != null) {
            view.endView();
        }
    }

    @Override
    public void removeView() {

    }

    @Override
    public void setView(SendSmsView view) {

        this.view = view;
    }

    private void sendSmsForNewPhones(final int smsId, String body, int carrierSlutCount, int
            carrierSlutNum,
                                     String carrierName, final MySmsManager.SMSManagerCallBack
                                             callBack) {

        model.generateSMSForMultiSimCards(smsId, body, carrierSlutCount, carrierSlutNum,
                carrierName,
                new MySmsManager.SMSManagerCallBack() {
                    @Override
                    public void afterSuccessfulSMS(int smsId) {
                        // if need to some override
                        if (mySmsId == smsId && view != null) {
                            view.endView();
                        }
                        callBack.afterSuccessfulSMS(smsId);
                    }

                    @Override
                    public void afterDelivered(int smsId) {
                        // if need to some override
                        callBack.afterDelivered(smsId);

                    }

                    @Override
                    public void afterUnSuccessfulSMS(int smsId, String message) {
                        // if need to some override
                        if (mySmsId == smsId && view != null) {
                            view.hideLoading();
                        }
                        callBack.afterUnSuccessfulSMS(smsId, message);
                    }

                    @Override
                    public void onCarrierNameNotMatch(int smsId, String message) {
                        // if need to some override
                        if (mySmsId == smsId && view != null) {
                            view.hideLoading();
                        }
                        callBack.onCarrierNameNotMatch(smsId, message);
                    }
                });

    }

    private void sendSmsForOldPhones() {
        TelephonyManager telephonyManager = ((TelephonyManager) context.getSystemService
                (Context.TELEPHONY_SERVICE));

        model.generateSMSForSingleSimCard(mySmsId, body, new MySmsManager.SMSManagerCallBack() {
            @Override
            public void afterSuccessfulSMS(int smsId) {
                // if need to some override
                callBack.afterSuccessfulSMS(smsId);
            }

            @Override
            public void afterDelivered(int smsId) {
                // if need to some override
                callBack.afterDelivered(smsId);

            }

            @Override
            public void afterUnSuccessfulSMS(int smsId, String message) {
                // if need to some override
                callBack.afterUnSuccessfulSMS(smsId, message);

            }

            @Override
            public void onCarrierNameNotMatch(int smsId, String message) {
                // if need to some override
                callBack.onCarrierNameNotMatch(smsId, message);
            }
        });
    }

    public void setContext(Context context) {
        this.context = context;
    }

}
