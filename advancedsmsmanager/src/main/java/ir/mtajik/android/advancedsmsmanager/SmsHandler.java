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
    private int sendSmsCustomDialogId = 0;
    private int choseSimCustomDialogId = 0;
    private boolean needDialog;
    private String carrierNameFilter;

    /**
     * before using SmsHandler you had to
     * permit user with Manifest.permission.SEND_SMS and
     * Manifest.permission.READ_PHONE_STATE .
     *
     * @param context      for context
     * @param smsNumber    for destination number
     * @param sendSmsCustomDialogId if you pass the custom layout with two {@link android.widget.Button}
     *                     that should have these ids with your desired style:
     *                     <p>
     *                     send_button
     *                     cancel_button
     *                     <p>
     *                     it will use your custom layout to show user for sending sms.
     *                     if you pass 0 as sendSmsCustomDialogId, advancedSmsManager will inflate is own
     *                     simple layout.
     */
    public SmsHandler(Context context, String smsNumber, @Nullable Integer sendSmsCustomDialogId,
                      @Nullable Integer choseSimCustomDialogId, boolean needDialog, String
                              carrierNameFilter) {
        this.context = context;
        this.smsNumber = smsNumber;
        this.sendSmsCustomDialogId = sendSmsCustomDialogId;
        this.choseSimCustomDialogId = choseSimCustomDialogId;
        this.needDialog = needDialog;
        this.carrierNameFilter = carrierNameFilter;

        wireUp();
    }

    public static Builder builder(Context context, String smsNumber) {
        return new Builder(context, smsNumber);
    }

    public void sendSms(String dialogMessage, String smsBody, MySmsManager.SMSManagerCallBack
            callback) {
        presenter.startWiringUp(dialogMessage, smsBody, callback);
    }


    private void wireUp() {

        DaggerSmsManagerComponent.builder().smsManagerModule(new SmsManagerModule(
                smsNumber, context)).build().inject(this);

        presenter.setNeedDialog(needDialog);
        presenter.setCarrierNameFilter(carrierNameFilter);
        view.setCustomLayout(sendSmsCustomDialogId);
        view.setCustomLayoutForTwoSim(choseSimCustomDialogId);

    }

    public static class Builder {

        private Context context;
        private String smsNumber;
        private int sendSmsCustomDialogId = 0;
        private int choseSimCustomDialogId = 0;
        private boolean needDialog = true;
        private String carrierNameFilter;

        public Builder(Context context, String smsNumber) {
            this.context = context;
            this.smsNumber = smsNumber;
        }

        /**
         * @param sendSmsCustomDialogId
         * @return
         * Should have two button with these ids: send_button , cancel_button
         * and a TextView with id: dialog_title
         * and a ProgressBar with id: progressBar_total
         */
        public Builder withCustomDialogForSendSms(Integer sendSmsCustomDialogId) {
            this.sendSmsCustomDialogId = sendSmsCustomDialogId;
            return this;
        }

        /**
         *
         * @param choseSimCustomDialogId
         * @return
         * Should have two button with these ids: sim1_button , sim2_button
         * and a TextView with id: dialog_title
         * and a ProgressBar with id: progressBar_total
         */
        public Builder withCustomDialogForChoseSim(Integer choseSimCustomDialogId) {
            this.choseSimCustomDialogId = choseSimCustomDialogId;
            return this;
        }

        public Builder needToShowDialog(Boolean needDialog) {
            this.needDialog = needDialog;
            return this;
        }


        public Builder withCarrierNameFilter(String carrierNameFilter) {
            this.carrierNameFilter = carrierNameFilter;
            return this;
        }

        public SmsHandler build() {

            return new SmsHandler(context, smsNumber, sendSmsCustomDialogId, choseSimCustomDialogId,
                    needDialog, carrierNameFilter);
        }
    }
}
