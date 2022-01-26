package com.eroad.darkhand.eroad;


public class RegisterAdapter {
    private String acc_type;
    private String name;
    private String phone_no;
    private String email;
    private String shop;
    private String place;

    public RegisterAdapter() {
    }

    public RegisterAdapter(String acc_type, String name, String phone_no, String email) {
        this.acc_type = acc_type;
        this.name = name;
        this.phone_no = phone_no;
        this.email = email;

    }

    public RegisterAdapter(String acc_type, String name, String phone_no, String email, String shop, String place) {
        this.acc_type = acc_type;
        this.name = name;
        this.phone_no = phone_no;
        this.email = email;

    }

    public String getName() {
        return name;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public String getEmail() {
        return email;
    }

    public String getAcc_type() {
        return acc_type;
    }

}

