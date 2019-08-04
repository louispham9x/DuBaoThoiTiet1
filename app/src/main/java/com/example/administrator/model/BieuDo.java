package com.example.administrator.model;

public class BieuDo {
    String Max;
    String Min;
    String Ngaythang;

    public BieuDo(String max, String min, String ngaythang) {
        Max = max;
        Min = min;
        Ngaythang = ngaythang;
    }

    public void setMax(String max) {
        Max = max;
    }

    public void setMin(String min) {
        Min = min;
    }

    public void setNgaythang(String ngaythang) {
        Ngaythang = ngaythang;
    }

    public String getMax() {
        return Max;
    }

    public String getMin() {
        return Min;
    }

    public String getNgaythang() {
        return Ngaythang;
    }
}
