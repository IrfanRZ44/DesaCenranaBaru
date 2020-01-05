package com.exomatik.desacenranabaru.model;

public class ModelStruktur {
    String idJabatan, nama, kontak;

    public ModelStruktur() {
    }

    public ModelStruktur(String idJabatan, String nama, String kontak) {
        this.idJabatan = idJabatan;
        this.nama = nama;
        this.kontak = kontak;
    }

    public String getIdJabatan() {
        return idJabatan;
    }

    public void setIdJabatan(String idJabatan) {
        this.idJabatan = idJabatan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKontak() {
        return kontak;
    }

    public void setKontak(String kontak) {
        this.kontak = kontak;
    }
}
