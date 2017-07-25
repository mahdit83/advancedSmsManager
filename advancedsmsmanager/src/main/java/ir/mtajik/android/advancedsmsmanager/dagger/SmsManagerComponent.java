package ir.mtajik.android.advancedsmsmanager.dagger;



import javax.inject.Singleton;

import dagger.Component;
import ir.mtajik.android.advancedsmsmanager.SmsHandler;

@Singleton
@Component(modules = {SmsManagerModule.class})
public interface SmsManagerComponent {

    void inject(SmsHandler handler);

}
