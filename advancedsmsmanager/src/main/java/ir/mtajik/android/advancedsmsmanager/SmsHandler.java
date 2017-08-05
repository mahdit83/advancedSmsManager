package ir.mtajik.android.advancedsmsmanager;

import android.content.Context;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import ir.mtajik.android.advancedsmsmanager.dagger.DaggerSmsManagerComponent;
import ir.mtajik.android.advancedsmsmanager.dagger.SmsManagerModule;
import ir.mtajik.android.advancedsmsmanager.model.MySmsManager;
import ir.mtajik.android.advancedsmsmanager.presenter.SendSmsPresenter;
import ir.mtajik.android.advancedsmsmanager.view.SendSmsView;

public class SmsHandler {

    @Inject
    SendSmsPresenter presenter;

    @Inject
    SendSmsView view;

    private Context context;
    private String smsNumber;
    private int dialogLayout;

    /**
     * before using SmsHandler you had to
     * permit user with Manifest.permission.SEND_SMS and
     * Manifest.permission.READ_PHONE_STATE .
     *
     * @param context      for context
     * @param smsNumber    for destination number
     * @param dialogLayout if you pass the custom layout with two {@link android.widget.Button}
     *                     that should have these ids with your desired style:
     *                     <p>
     *                     send_button
     *                     cancel_button
     *                     <p>
     *                     it will use your custom layout to show user for sending sms.
     *                     if you pass 0 as dialogLayout, advancedSmsManager will inflate is own
     *                     simple layout.
     */
    public SmsHandler(Context
                              context, String smsNumber, @Nullable Integer dialogLayout) {
        this.context = context;
        this.smsNumber = smsNumber;
        this.dialogLayout = dialogLayout;

        wireUp();
    }


    public void sendSms(String dialogMessage, String smsBody, MySmsManager.SMSManagerCallBack
            callback) {
        presenter.startWiringUp(dialogMessage, smsBody, callback);
    }

    private void wireUp() {

        DaggerSmsManagerComponent.builder().smsManagerModule(new SmsManagerModule(dialogLayout,
                smsNumber, context)).build().inject(this);


    }

    /**
     * Carrier name filter works for sdk >21
     * @param carrierNameFilter
     */

    public void setCarrierNameFilter(String carrierNameFilter) {

        presenter.setCarrierNameFilter(carrierNameFilter);

    }
}
