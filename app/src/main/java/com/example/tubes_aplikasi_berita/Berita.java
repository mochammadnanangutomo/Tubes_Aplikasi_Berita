package com.example.tubes_aplikasi_berita;

public class Berita {
    private String _id, _judul, _isi, _waktu, _kronologi;
    private byte[] _image;

    public Berita(String id, byte[] image, String judul, String isi, String waktu, String kronologi) {
        this._id = id;
        this._image = image;
        this._judul = judul;
        this._isi = isi;
        this._waktu = waktu;
        this._kronologi = kronologi;
    }
    public Berita() {
    }
    public String get_id() {
        return _id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }
    public byte[] get_image() {
        return _image;
    }
    public void set_image(byte[] _image) {
        this._image = _image;
    }
    public String get_judul() {
        return _judul;
    }
    public void set_judul(String _judul) {
        this._judul = _judul;
    }
    public String get_isi() {
        return _isi;
    }
    public void set_isi(String _isi) {
        this._isi = _isi;
    }
    public String get_waktu() {
        return _waktu;
    }
    public void set_waktu(String _waktu) {
        this._waktu = _waktu;
    }
    public String get_kronologi() {
        return _kronologi;
    }
    public void set_kronologi(String _kronologi) {
        this._kronologi = _kronologi;
    }
}