package ir.mtajik.android.advancedsmsmanager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.WindowManager;

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
    private WindowManager.LayoutParams layoutParams = null;
    private boolean needsSendSmsDialog;
    private boolean needsChoseSimDialog;
    private String carrierNameFilter;
    private int dialogWidth = 0, dialogHeight = 0;


    /**
     * before using SmsHandler you had to
     * permit user with Manifest.permission.SEND_SMS and
     * Manifest.permission.READ_PHONE_STATE .
     *
     * @param context               for context
     * @param smsNumber             for destination number
     * @param sendSmsCustomDialogId if you pass the custom layout with two
     *                              {@link android.widget.Button}
     *                              that should have these ids with your desired style:
     *                              <p>
     *                              send_button
     *                              cancel_button
     *                              <p>
     *                              it will use your custom layout to show user for sending sms.
     *                              if you pass 0 as sendSmsCustomDialogId, advancedSmsManager
     *                              will inflate is own
     *                              simple layout.
     */
    public SmsHandler(Context context, String smsNumber, @Nullable Integer sendSmsCustomDialogId,
                      @Nullable Integer choseSimCustomDialogId, boolean needsSendSmsDialog,
                      boolean needsChoseSimDialog, String carrierNameFilter, WindowManager.LayoutParams layoutParams, int
                              dialogWidth, int dialogHeight) {
        this.context = context;
        this.smsNumber = smsNumber;
        this.sendSmsCustomDialogId = sendSmsCustomDialogId;
        this.choseSimCustomDialogId = choseSimCustomDialogId;
        this.needsSendSmsDialog = needsSendSmsDialog;
        this.needsChoseSimDialog = needsChoseSimDialog;
        this.carrierNameFilter = carrierNameFilter;
        this.layoutParams = layoutParams;
        this.dialogWidth = dialogWidth;
        this.dialogHeight = dialogHeight;


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

        presenter.setNeedsSendSmsDialog(needsSendSmsDialog);
        presenter.setCarrierNameFilter(carrierNameFilter);
        presenter.setNeedsChoseSimDialog(needsChoseSimDialog);
        view.setLayoutParam(layoutParams);
        view.setHeightAndWidth(dialogHeight, dialogWidth);
        view.setCustomLayout(sendSmsCustomDialogId);
        view.setCustomLayoutForTwoSim(choseSimCustomDialogId);

    }

    public static class Builder {

        private Context context;
        private String smsNumber;
        private int sendSmsCustomDialogId = 0;
        private int choseSimCustomDialogId = 0;
        private boolean needsSendSmsDialog = true;
        private boolean needsChoseSimDialog = true;
        private String carrierNameFilter;
        private WindowManager.LayoutParams layoutParams = null;
        private int dialogWidth = 0, dialogHeight = 0;


        public Builder(Context context, String smsNumber) {
            this.context = context;
            this.smsNumber = smsNumber;
        }

        /**
         * @param sendSmsCustomDialogId
         * @return Should have two button with these ids: send_button , cancel_button
         * and a TextView with id: dialog_title
         * and a ProgressBar with id: progressBar_total
         */
        public Builder withCustomDialogForSendSms(Integer sendSmsCustomDialogId) {
            this.sendSmsCustomDialogId = sendSmsCustomDialogId;
            return this;
        }

        /**
         * @param choseSimCustomDialogId
         * @return Should have two button with these ids: sim1_button , sim2_button
         * and a TextView with id: dialog_title
         * and a ProgressBar with id: progressBar_total
         */
        public Builder withCustomDialogForChoseSim(Integer choseSimCustomDialogId) {
            this.choseSimCustomDialogId = choseSimCustomDialogId;
            return this;
        }

        public Builder needToShowSendSmsDialog(Boolean needDialog) {
            this.needsSendSmsDialog = needDialog;
            return this;
        }


        /**
         * For two sim-card phones if you want
         * to just send sms from specific carrier name
         * without ask for choosing sim card
         * add this properties with carrier name filter that will check
         * contains in carrier names.
         *
         * @param carrierNameFilter
         * @return
         */
        public Builder needSendSmsFromSpecificCarrierWithOutAskingUser(@NonNull String carrierNameFilter) {
            this.needsChoseSimDialog = false;
            this.carrierNameFilter = carrierNameFilter;
            return this;
        }


        public Builder withCarrierNameFilter(String carrierNameFilter) {
            this.carrierNameFilter = carrierNameFilter;
            return this;
        }

        /**
         * For example you can pass params like this:
         * WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
         * layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
         * layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
         * layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
         * layoutParams.dimAmount = 0.5f;
         *
         * @param layoutParams
         * @return
         */
        public Builder withLayoutParams(WindowManager.LayoutParams layoutParams) {
            this.layoutParams = layoutParams;
            return this;
        }

        /**
         * You can enter height in dp.
         * If you leave this it will be match_parent
         *
         * @param dialogWidth
         * @return
         */
        public Builder withWidth(int dialogWidth) {
            this.dialogWidth = dialogWidth;
            return this;
        }

        /**
         * You can enter height in dp.
         * If you leave this it will be wrap_content
         *
         * @param dialogHeight
         * @return
         */
        public Builder withHeight(int dialogHeight) {
            this.dialogHeight = dialogHeight;
            return this;
        }

        public SmsHandler build() {

            return new SmsHandler(context, smsNumber, sendSmsCustomDialogId, choseSimCustomDialogId,
                    needsSendSmsDialog,needsChoseSimDialog, carrierNameFilter, layoutParams, dialogWidth, dialogHeight);
        }
    }
}
