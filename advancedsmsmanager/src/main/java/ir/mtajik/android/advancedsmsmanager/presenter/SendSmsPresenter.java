package ir.mtajik.android.advancedsmsmanager.presenter;

import android.app.Activity;

import ir.mtajik.android.advancedsmsmanager.model.MySmsManager;
import ir.mtajik.android.advancedsmsmanager.view.SendSmsView;


public interface SendSmsPresenter {

    void closeDialog();

    void startWiringUp(String dialogMessage, String smsBody, MySmsManager.SMSManagerCallBack callback);

    void prepareSendSms();

    void sendSmsFromSubscriptionIdIndex(int id);

    void cancelSendSms();

    void removeView();

    void setView(SendSmsView view);

    void setCarrierNameFilter(String filter);

    void setNeedDialog(boolean ifNeed);

}
