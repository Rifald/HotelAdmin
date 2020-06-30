package com.ribal.hoteladmin;

import java.io.Serializable;

public class Hotel implements Serializable {
    private String nama;
    private String alamat;
    private  String url;
    private int harga;
    private int jumlah;

    public Hotel(){

    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    @Override
    public String toString() {
        return " "+nama+"\n" +
                " "+alamat +"\n" +
                " "+harga+ "\n" +
                " "+jumlah+"\n"+
                " "+url;
    }

    public Hotel(String nama, String alamat, int hrg, int jlh, String url){
        this.nama = nama;
        this.alamat = alamat;
        this.harga = hrg;
        this.jumlah = jlh;
        this.url = url;

    }
}
