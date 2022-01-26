package com.eroad.darkhand.eroad;

public class ListViewAdapter {
    private String fullnameD;
    private String fueltypeD;
    private String fuelquantity;

    public ListViewAdapter(){

    }

    public ListViewAdapter(String fullnameD, String fueltypeD, String fuelquantity) {
        this.fullnameD = fullnameD;
        this.fueltypeD = fueltypeD;
        this.fuelquantity = fuelquantity;
    }

    public String getFullnameD() {
        return fullnameD;
    }

    public String getFueltypeD() {
        return fueltypeD;
    }

    public String getFuelquantity() {
        return fuelquantity;
    }
}


