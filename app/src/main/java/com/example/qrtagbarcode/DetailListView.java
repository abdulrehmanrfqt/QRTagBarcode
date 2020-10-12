package com.example.qrtagbarcode;

public class DetailListView {

    private String QRDetail_Id, QRDetail_QRMaster_Id, QRDetail_Code;

    public DetailListView(String QRDetail_Id, String QRDetail_QRMaster_Id, String QRDetail_Code) {
        this.QRDetail_Id = QRDetail_Id;
        this.QRDetail_QRMaster_Id = QRDetail_QRMaster_Id;
        this.QRDetail_Code = QRDetail_Code;
    }

    public String getQRDetail_Id() {
        return QRDetail_Id;
    }

    public void setQRDetail_Id(String QRDetail_Id) {
        this.QRDetail_Id = QRDetail_Id;
    }

    public String getQRDetail_QRMaster_Id() {
        return QRDetail_QRMaster_Id;
    }

    public void setQRDetail_QRMaster_Id(String QRDetail_QRMaster_Id) {
        this.QRDetail_QRMaster_Id = QRDetail_QRMaster_Id;
    }

    public String getQRDetail_Code() {
        return QRDetail_Code;
    }

    public void setQRDetail_Code(String QRDetail_Code) {
        this.QRDetail_Code = QRDetail_Code;
    }
}
