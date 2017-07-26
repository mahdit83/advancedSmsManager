# advancedSmsManager
Advanced SmsManager is library for sending sms for single and two sim-card phones. it is very handy and usefull. 
For using in android studio add this to your dependency:

           compile 'ir.mtajik.android:advancedsmsmanager:1.0.1'                    

It created with MVP architecture and Uses Dagger2 as DI container with these dependencies:

           compile 'com.google.dagger:dagger:2.7'
           annotationProcessor 'com.google.dagger:dagger-compiler:2.7'
           
So if you use dagger2 make sure that use compatible dependencies.
    
before using SmsHandler you had to permit user with 'Manifest.permission.SEND_SMS' and 'Manifest.permission.READ_PHONE_STATE' . 
after that simply call sendSms that have a Interface for all callbacks.

        SmsHandler smsHandler = new SmsHandler(this, "+0121212", dialogLayoutId);
        smsHandler.sendSms("dialog message", "sms body", new MySmsManager.SMSManagerCallBack() {
            @Override
            public void afterSuccessfulSMS(int smsId) {
                
            }

            @Override
            public void afterDelivered(int smsId) {

            }

            @Override
            public void afterUnSuccessfulSMS(int smsId, String message) {

            }

            @Override
            public void onCarrierNameNotMatch(int smsId, String message) {

            }
        });

If you pass a dialog layout to SmsHandler, you can pass 0 as dialogLayoutId but if you pass a one, it will show your custom dialog to confirm Send sms to user. but you should have two Android Button with these id in you layout:

    
           <Button
            android:id="@+id/send_button"
            ...
            />        
            
            <Button
            android:id="@+id/cancel_button"
            ...
            />        
           

I hope this library would be useful and wait for your comments.

this my weblog: http://www.mahditajik.ir
