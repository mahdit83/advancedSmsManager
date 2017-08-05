package ir.mtajik.android.advancedsmsmanager.model;

public interface SendSmsModel {

    void generateSMSForMultiSimCards(int smsId, String body, int carrierSlotCount, int carrierSlutNumber,
                                     String carrierName, String carrierNameFilter,
                                     MySmsManager.SMSManagerCallBack callBack);

    void generateSMSForSingleSimCard(int smsId, String body, String carrierNameFilter,
                                     MySmsManager.SMSManagerCallBack callBack);
}

