package com.eroad.darkhand.eroad;

public class Orders {
    private String type;
    private String quantity;
    private String fname;
    private String pnumber;

    public Orders() {
    }


    public Orders(String type, String quantity, String fname, String pnumber) {
        this.type = type;
        this.quantity = quantity;
        this.fname = fname;
        this.pnumber = pnumber;
    }

    public String getType() {
        return type;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getFname() {
        return fname;
    }

    public String getPnumber() {
        return pnumber;
    }
}

