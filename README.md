# advancedSmsManager
Advanced SmsManager is library for sending sms for single and two sim-card phones. it is very handy and usefull.

before using SmsHandler you had to permit user with 'Manifest.permission.SEND_SMS' and 'Manifest.permission.READ_PHONE_STATE' . 
after that simply call sendSms that have a Interface for all callbacks.

If you pass a dialog layout to SmsHandler, it will show your cutom dialog to confirm Send sms to user. but you should have two Android Button with these id in you layout:

    
           send_button                    
           cancel_button


I hope this library would be useful and wait for your comments.

this my weblog: http://www.mtajik.ir
