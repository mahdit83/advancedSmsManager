package ir.mtajik.android.advancedsmsmanager.view;


import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import ir.mtajik.android.advancedsmsmanager.R;
import ir.mtajik.android.advancedsmsmanager.presenter.SendSmsPresenter;


public class SendSmsViewImpl implements SendSmsView {

    private Dialog dialog, simChoseDialog;
    private View dialogView;
    private SendSmsPresenter presenter;
    private Context context;
    private ProgressBar progressBar;
    private int layoutId, twoSimLayoutId;
    private WindowManager.LayoutParams layoutParams;
    private int dialogWidth ,dialogHeight;

    public SendSmsViewImpl(SendSmsPresenter presenter) {
        this.presenter = presenter;

        presenter.setView(this);
    }

    @Override
    public void renderView(Context context, String message) {

        this.context = context;
        showDialogForSendSms(context, message);

    }

    @Override
    public void renderSimChooserView(Context context, String sim1CarrierName, String
            sim2CarrierName) {

        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);

        if (twoSimLayoutId == 0) {
            dialogView = inflater.inflate(R.layout.sms_dialog, null);
        } else {
            dialogView = inflater.inflate(twoSimLayoutId, null);
        }


        dialogView = inflater.inflate(R.layout.simcard_choosing_dialog, null);
        TextView titleText = (TextView) dialogView.findViewById(R.id.dialog_title);
        titleText.setText("Chose witch sim card to send sms");

        Button sim1Button = (Button) dialogView.findViewById(R.id.sim1_button);
        sim1Button.setText("1. " + sim1CarrierName);
        sim1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.sendSmsFromSubscriptionIdIndex(0);
                simChoseDialog.dismiss();
            }
        });

        Button sim2Button = (Button) dialogView.findViewById(R.id.sim2_button);
        sim2Button.setText("2. " + sim2CarrierName);
        sim2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.sendSmsFromSubscriptionIdIndex(1);
                simChoseDialog.dismiss();
            }
        });


        simChoseDialog = new Dialog(context);
        simChoseDialog.setContentView(dialogView);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lp.dimAmount = 0.5f;
        simChoseDialog.show();
        simChoseDialog.getWindow().setAttributes(lp);


    }


    @Override
    public void endView() {

        if (progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
        }
        if(dialog!=null){
            dialog.dismiss();
        }
    }

    @Override
    public void showMessage(String message) {

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {

        if (progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void setCustomLayout(int id) {
        this.layoutId = id;
    }

    @Override
    public void setLayoutParam(WindowManager.LayoutParams layoutParams) {
        this.layoutParams = layoutParams;
    }

    @Override
    public void setHeightAndWidth(int height, int width) {

        this.dialogWidth = width;
        this.dialogHeight = height;
    }

    @Override
    public void setCustomLayoutForTwoSim(int id) {

        this.twoSimLayoutId = id;
    }

    private void showDialogForSendSms(Context context, String message) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE);


        if (layoutId == 0) {
            dialogView = inflater.inflate(R.layout.sms_dialog, null);
        } else {
            dialogView = inflater.inflate(layoutId, null);
        }

        TextView title = (TextView) dialogView.findViewById(R.id.dialog_title);
        title.setText(message);

        progressBar = (ProgressBar) dialogView.findViewById(R.id.progressBar_total);

        Button sendButton = (Button) dialogView.findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.prepareSendSms();

            }
        });

        Button cancelButton = (Button) dialogView.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.cancelSendSms();

            }
        });

        dialog = new Dialog(context);
        dialog.setContentView(dialogView);

        if(this.layoutParams == null){

            this.layoutParams = new WindowManager.LayoutParams();
            this.layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            this.layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            this.layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            this.layoutParams.dimAmount = 0.5f;
        }

        if(dialogHeight!=0 ){
            this.layoutParams.height = dpToPx(dialogHeight);
        }
        if(dialogWidth !=0){
            this.layoutParams.width = dpToPx(dialogWidth);
        }

        dialog.show();
        dialog.getWindow().setAttributes(layoutParams);

    }

    private int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
