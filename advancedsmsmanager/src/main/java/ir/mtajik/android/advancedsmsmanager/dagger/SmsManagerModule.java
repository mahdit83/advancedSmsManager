package ir.mtajik.android.advancedsmsmanager.dagger;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ir.mtajik.android.advancedsmsmanager.model.MySmsManager;
import ir.mtajik.android.advancedsmsmanager.model.SendSmsModel;
import ir.mtajik.android.advancedsmsmanager.model.SendSmsModelImpl;
import ir.mtajik.android.advancedsmsmanager.presenter.SendSmsPresenter;
import ir.mtajik.android.advancedsmsmanager.presenter.SendSmsPresenterImpl;
import ir.mtajik.android.advancedsmsmanager.view.SendSmsView;
import ir.mtajik.android.advancedsmsmanager.view.SendSmsViewImpl;

@Module
public class SmsManagerModule {


    private String smsNumber;
    private Context context;


    public SmsManagerModule(String smsNumber, Context context) {
        this.context = context;
        this.smsNumber = smsNumber;
    }


    @Singleton
    @Provides
    Context provideContext() {
        return this.context;
    }

    @Singleton
    @Provides
    MySmsManager providesMySmsManager(Context context) {
        return new MySmsManager(smsNumber, context);
    }

    @Singleton
    @Provides
    SendSmsModel providesSendSmsModel(MySmsManager mySmsManager) {
        return new SendSmsModelImpl(mySmsManager);
    }

    @Singleton
    @Provides
    SendSmsPresenter providesSendSmsPresenter(SendSmsModel model, Context context) {
        return new SendSmsPresenterImpl(model, context);
    }

    @Singleton
    @Provides
    SendSmsView providesSendSmsView(SendSmsPresenter presenter) {
        return new SendSmsViewImpl(presenter);
    }
}
