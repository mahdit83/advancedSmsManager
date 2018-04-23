[ ![Download](https://api.bintray.com/packages/mahdi/maven/advancedsmsmanager/images/download.svg) ](https://bintray.com/mahdi/maven/advancedsmsmanager/_latestVersion)
	
# Advanced Sms Manager
AdvancedSmsManager is library for sending sms for single and two sim-card phones. it is very handy and usefull. It's two-sim mode works on Android.SDK > 21. For lower SDKs it send sms from default sim.
For using in android studio add this to your dependency:

```groovy
compile 'ir.mtajik.android:advancedsmsmanager:1.1.0'                    
```
Before using SmsHandler you had to permit user with `Manifest.permission.SEND_SMS` and `Manifest.permission.READ_PHONE_STATE` . 
    
After that simply call sendSms that have a Interface for all callbacks. smsId is a random unique auto generated Id that generated for every single sms that created by your app.
In version 1.0.5 , i implement Builder design pattern. All the ```with``` parameters are optional. 
```java

SmsHandler.builder(context, "+989120000000")
                .withCarrierNameFilter("MCI")
                .withCustomDialogForSendSms(R.layout.my_sms_dialog)
                .withCustomDialogForChoseSim(R.layout.simcard_choosing_dialog)
                .needToShowSendSmsDialog(false)
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
If you do not want to ask user for send sms after premitted, put ```.needToShowSendSmsDialog(false)``` or else leave it and sms confirem dialog will be displayed. With new feature you can send Sms from specific carrier and do not show sim-card chosing dialog to user. Mention that sim chose dialog on two sim phones will always diplayed in pre 1.1.0 versions. 
````java
.needSendSmsFromSpecificCarrierWithOutAskingUser("MTN")
````
This will be check phone sim-cards carrier names in lower-case that contains "mtn". If one exists then Sms will send from that carrier without asking from user.

Also you can inflate you custom view for both **sendSmsDialog** and **simChoseDialog** but  mention that they must have these component and ids. ( Extended components from these components are acceptable) 

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

This library created with MVP architecture and Uses Dagger2 as DI container with these dependencies:

```groovy
compile 'com.google.dagger:dagger:2.7'
annotationProcessor 'com.google.dagger:dagger-compiler:2.7'
```           
           
So if you use dagger2 make sure that use compatible dependencies. I hope this library would be useful and wait for your comments.


![Mahdi Tajik](http://www.mahditajik.ir/wp-content/uploads/2015/03/sample-logo-MT22.png)

This is my weblog: http://www.mahditajik.ir


