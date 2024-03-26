package com.example.projectandroid;

public class Data {
    private String id, nik, nama, alamat, kota; // Menambahkan variabel kota

    public Data() {
    }

    public Data(String id, String nik, String nama, String alamat, String kota){ // Menambahkan parameter kota ke konstruktor
        this.id = id;
        this.nama = nama;
        this.nik = nik;
        this.alamat = alamat;
        this.kota = kota; // Menginisialisasi variabel kota
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
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

    // Menambahkan getter dan setter untuk kota
    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }
}
