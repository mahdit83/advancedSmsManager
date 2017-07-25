package ir.mtajik.android.advancedsmsmanager.model;

public class SendSmsModelImpl implements SendSmsModel {


    private MySmsManager smsManager;

    public SendSmsModelImpl(MySmsManager smsManager) {
        this.smsManager = smsManager;
    }

    @Override
    public void generateSMSForMultiSimCards(int smsId, String body, int carrierSlotCount, int
            carrierSlutNumber, String carrierName, MySmsManager.SMSManagerCallBack callBack) {

        smsManager.generateSMS(smsId,body,carrierSlotCount,carrierSlutNumber,carrierName,callBack);

    }

    @Override
    public void generateSMSForSingleSimCard(int smsId,String body, MySmsManager.SMSManagerCallBack callBack) {

        smsManager.generateSMS(smsId,body,callBack);

    }
}
