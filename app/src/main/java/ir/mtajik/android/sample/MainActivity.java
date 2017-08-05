package ir.mtajik.android.sample;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import ir.mtajik.android.advancedsmsmanager.SmsHandler;
import ir.mtajik.android.advancedsmsmanager.model.MySmsManager;


public class MainActivity extends AppCompatActivity {

    public static final String SMS_NUMBER = "09121311509";
    public static final String DIALOG_MESSAGE = "Send sms to confirm your phone";
    public static final String SMS_BODY = "Hi ali.";
    private static final int PERMISSION_CALLBACK_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    String[] permissionsRequired = new String[]{Manifest.permission.SEND_SMS,
            Manifest.permission.READ_PHONE_STATE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handlePermissions();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PERMISSION_SETTING) {
            if (ActivityCompat.checkSelfPermission(this, permissionsRequired[0]) ==
                    PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, permissionsRequired[1]) ==
                    PackageManager.PERMISSION_GRANTED) {
                //Got Permission
                proceedAfterPermission();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CALLBACK_CONSTANT) {

            //check if all permissions are granted
            boolean allgranted = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if (allgranted) {
                proceedAfterPermission();
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                        permissionsRequired[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                        permissionsRequired[1])) {
                    //Show Information about why you need the permission and convince the user
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Need Storage Permission");
                    builder.setMessage("This app needs storage permission");
                    builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                            ActivityCompat.requestPermissions(MainActivity.this,
                                    permissionsRequired, PERMISSION_CALLBACK_CONSTANT);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                } else {
                    Toast.makeText(getBaseContext(), "Unable to get Permission", Toast
                            .LENGTH_LONG).show();
                }
            }
        }
    }

    private void proceedAfterPermission() {

        final SmsHandler smsHandler = new SmsHandler(this, SMS_NUMBER, R.layout.my_sms_dialog);

        // you can add optional carrier filter
//        smsHandler.setCarrierNameFilter("MCI");

        smsHandler.sendSms(DIALOG_MESSAGE, SMS_BODY, new MySmsManager.SMSManagerCallBack() {
            @Override
            public void afterSuccessfulSMS(int smsId) {

                Toast.makeText(MainActivity.this, "first sms was send successfully", Toast
                        .LENGTH_SHORT).show();

                //send second sms after first one sent
                smsHandler.sendSms("second sms", "how do you do?", new MySmsManager
                        .SMSManagerCallBack() {
                    @Override
                    public void afterSuccessfulSMS(int smsId) {
                        Toast.makeText(MainActivity.this, "second was send successfully", Toast
                                .LENGTH_SHORT).show();
                    }

                    @Override
                    public void afterDelivered(int smsId) {

                        Toast.makeText(MainActivity.this, "second message delivered!", Toast
                                .LENGTH_SHORT).show();
                    }

                    @Override
                    public void afterUnSuccessfulSMS(int smsId, String message) {

                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCarrierNameNotMatch(int smsId, String message) {

                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void afterDelivered(int smsId) {

                Toast.makeText(MainActivity.this, "first message delivered!", Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void afterUnSuccessfulSMS(int smsId, String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCarrierNameNotMatch(int smsId, String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handlePermissions() {

        SharedPreferences permissionStatus = this.getSharedPreferences("permissionStatus",
                this.MODE_PRIVATE);

        if (ActivityCompat.checkSelfPermission(this, permissionsRequired[0]) != PackageManager
                .PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, permissionsRequired[1]) !=
                PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissionsRequired[0])
                    || ActivityCompat.shouldShowRequestPermissionRationale(this,
                    permissionsRequired[1])) {
                //true means user not allowed the permission but may we can convince him/her
                //false have two meaning: 1-user not asked for permission 2-user denied and check
                // 'Don't Ask Again'
                //so we had to Show Information about why you need the permission

                ActivityCompat.requestPermissions(this, permissionsRequired,
                        PERMISSION_CALLBACK_CONSTANT);

            } else if (permissionStatus.getBoolean(permissionsRequired[0], false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission

                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                this.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                Toast.makeText(this, "Go to Permissions to Grant Sms", Toast.LENGTH_LONG)
                        .show();

            } else {
                //just request the permission
                ActivityCompat.requestPermissions(this, permissionsRequired,
                        PERMISSION_CALLBACK_CONSTANT);
            }

            SharedPreferences.Editor editor = permissionStatus.edit();
            editor.putBoolean(permissionsRequired[0], true).apply();

        } else {

            proceedAfterPermission();

        }
    }
}
