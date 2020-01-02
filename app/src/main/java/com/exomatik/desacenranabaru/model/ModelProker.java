package com.exomatik.desacenranabaru.model;

public class ModelProker {
    private String id, nama, tanggal, foto, lokasi, desc;

    public ModelProker() {
    }

    public ModelProker(String id, String nama, String tanggal, String foto, String lokasi, String desc) {
        this.id = id;
        this.nama = nama;
        this.tanggal = tanggal;
        this.foto = foto;
        this.lokasi = lokasi;
        this.desc = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
