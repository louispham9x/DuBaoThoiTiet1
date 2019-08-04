package com.example.administrator.model;

public class Gio {
    private String gio;
    private int icon;
    private String nhietdo;
    private String tocdogio;
    private String luongmua;

    public Gio(String gio, int icon, String nhietdo, String tocdogio, String luongmua) {
        this.gio = gio;
        this.icon = icon;
        this.nhietdo = nhietdo;
        this.tocdogio = tocdogio;
        this.luongmua = luongmua;
    }

    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getNhietdo() {
        return nhietdo;
    }

    public void setNhietdo(String nhietdo) {
        this.nhietdo = nhietdo;
    }

    public String getTocdogio() {
        return tocdogio;
    }

    public void setTocdogio(String tocdogio) {
        this.tocdogio = tocdogio;
    }

    public String getLuongmua() {
        return luongmua;
    }

    public void setLuongmua(String luongmua) {
        this.luongmua = luongmua;
    }
}
