package com.exomatik.desacenranabaru.model;

import java.util.ArrayList;

public class ModelWisata {
    String id, namaWisata, lokasi, desc;
    ArrayList<String> listFoto;

    public ModelWisata() {
    }

    public ModelWisata(String id, String namaWisata, String lokasi, String desc, ArrayList<String> listFoto) {
        this.id = id;
        this.namaWisata = namaWisata;
        this.lokasi = lokasi;
        this.desc = desc;
        this.listFoto = listFoto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamaWisata() {
        return namaWisata;
    }

    public void setNamaWisata(String namaWisata) {
        this.namaWisata = namaWisata;
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

    public ArrayList<String> getListFoto() {
        return listFoto;
    }

    public void setListFoto(ArrayList<String> listFoto) {
        this.listFoto = listFoto;
    }
}
