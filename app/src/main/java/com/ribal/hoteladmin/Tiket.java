package com.ribal.hoteladmin;

import java.io.Serializable;

public class Tiket  implements Serializable {
    private String nama;
    private String tanggal;
    private String hotel;
    private int total;
    private int jumlah;

    public  Tiket(){

    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    @Override
    public String toString() {
        return " "+hotel+"\n" +
                " "+nama+"\n" +
                " "+tanggal +"\n" +
                " "+total+ "\n" +
                " "+jumlah+"\n";
    }

    public Tiket( String nama,String hotel, String tanggal, int total, int jumlah){
        this.nama = nama;
        this.tanggal = tanggal;
        this.total = total;
        this.jumlah = jumlah;


    }
}
