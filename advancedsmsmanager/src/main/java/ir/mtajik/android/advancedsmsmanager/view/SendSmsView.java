package ir.mtajik.android.advancedsmsmanager.view;
import android.content.Context;
import android.view.WindowManager;

public interface SendSmsView {

    void renderView(Context context, String message);

    void renderSimChooserView(Context context , String sim1CarrierName, String sim2CarrierName);

    void endView();

    void showMessage(String message);

    void showLoading();

    void hideLoading();

    void setCustomLayout(int id);

    void setLayoutParam(WindowManager.LayoutParams layoutParams);

    void setHeightAndWidth(int height, int width);

    void setCustomLayoutForTwoSim(int id);
}
