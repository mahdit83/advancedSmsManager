package ir.mtajik.android.advancedsmsmanager.model;

public class SendSmsModelImpl implements SendSmsModel {


    private MySmsManager smsManager;

    public SendSmsModelImpl(MySmsManager smsManager) {
        this.smsManager = smsManager;
    }

    @Override
    public void generateSMSForMultiSimCards(int smsId, String body, int carrierSlotCount, int
            carrierSlutNumber, String carrierName, String carrierNameFilter, MySmsManager.SMSManagerCallBack callBack) {

        if(carrierNameFilter!=null){
            smsManager.setCarrierNameFilter(carrierNameFilter);
        }
        smsManager.generateSMS(smsId,body,carrierSlotCount,carrierSlutNumber,carrierName,callBack);

    }

    @Override
    public void generateSMSForSingleSimCard(int smsId,String body, String carrierNameFilter, MySmsManager.SMSManagerCallBack callBack) {

        if(carrierNameFilter!=null){
            smsManager.setCarrierNameFilter(carrierNameFilter);
        }
        smsManager.generateSMS(smsId,body,callBack);

    }
}
