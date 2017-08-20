# Advanced Sms Manager
AdvancedSmsManager is library for sending sms for single and two sim-card phones. it is very handy and usefull. It's two-sim mode works on Android.SDK > 21. For lower SDKs it send sms from default sim.
For using in android studio add this to your dependency:

```groovy
compile 'ir.mtajik.android:advancedsmsmanager:1.0.5'                    
```
Before using SmsHandler you had to permit user with `Manifest.permission.SEND_SMS` and `Manifest.permission.READ_PHONE_STATE` . 
    
After that simply call sendSms that have a Interface for all callbacks. smsId is a random unique auto generated Id that generated for every single sms that created by your app.
In version 1.0.5 , i implement Builder design pattern. All the ```with``` parameters are optional. 
```java

SmsHandler.builder(this, "+989120000000")
                .withCarrierNameFilter("MCI")
                .withCustomDialogForSendSms(R.layout.my_sms_dialog)
                .withCustomDialogForChoseSim(R.layout.simcard_choosing_dialog)
                .needToShowDialog(false)
                .build().sendSms(DIALOG_MESSAGE, SMS_BODY, new MySmsManager.SMSManagerCallBack() {
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
```
You can inflate you custom view for both **sendSmsDialog** and **simChoseDialog** but they must have these component and ids:
If you pass a dialog layout to SmsHandler it will show your custom dialog to confirm Send sms to user, if you don't want just pass 0 as dialogLayoutId. Your custom dialog should have two Android Button or custom button extends **android.widget.Button** with these id in you layout:

for **sendSmsDialog**:
```xml
<Button
	android:id="@+id/send_button"
	...
           />
            
<Button
	android:id="@+id/cancel_button"
	...
           />

<TextView
        android:id="@+id/dialog_title"
        ...
          />

<ProgressBar
        android:id="@+id/progressBar_total"
	...
	   />
```           

and for **simChoseDialog** :
```xml
<Button
	android:id="@+id/sim1_button"
	...
           />
            
<Button
	android:id="@+id/sim2_button"
	...
           />

<TextView
        android:id="@+id/dialog_title"
        ...
          />
```  

It created with MVP architecture and Uses Dagger2 as DI container with these dependencies:


```groovy
compile 'com.google.dagger:dagger:2.7'
annotationProcessor 'com.google.dagger:dagger-compiler:2.7'
```           
           
So if you use dagger2 make sure that use compatible dependencies. I hope this library would be useful and wait for your comments.

this my weblog: http://www.mahditajik.ir


