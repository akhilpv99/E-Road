package com.eroad.darkhand.eroad;

public class PaidmoneyAdapter {
    private String paidamount;
    private String payeename;
    //private String cvv;
    //private String expmonth;
    //private String expday;


    public PaidmoneyAdapter() {
    }

    public PaidmoneyAdapter(String paidamount, String payeename) {
        this.paidamount = paidamount;
        this.payeename = payeename;
    }

    public String getPaidamount() {
        return paidamount;
    }

    public String getPayeename() {
        return payeename;
    }
}