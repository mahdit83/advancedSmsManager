package ir.mtajik.android.advancedsmsmanager.view;
import android.content.Context;

public interface SendSmsView {

    void renderView(Context context, String message);

    void renderSimChooserView(String sim1CarrierName, String sim2CarrierName);

    void endView();

    void showMessage(String message);

    void showLoading();

    void hideLoading();
}
