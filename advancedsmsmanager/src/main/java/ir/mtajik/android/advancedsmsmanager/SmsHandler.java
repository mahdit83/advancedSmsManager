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

    private boolean isNeedDialog;
    private Context context;
    private String smsNumber;
    private int dialogLayout;

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
}
