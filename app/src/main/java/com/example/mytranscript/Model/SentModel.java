package com.example.mytranscript.Model;

public class SentModel {
    private String paymentSlip;
    private String type;

    public SentModel(String paymentSlip, String type) {
        this.paymentSlip = paymentSlip;
        this.type = type;
    }

    public String getPaymentSlip() {
        return paymentSlip;
    }

    public String getType() {
        return type;
    }
}
