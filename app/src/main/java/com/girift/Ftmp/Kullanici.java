package com.girift.Ftmp;

public class Kullanici {
    public String KullaniciID;
    private String KullaniciAd;
    private String KullaniciSifre;
    private String KullaniciBoylam;
    private String KullaniciEnlem;

    public Kullanici() {}

    public Kullanici(String kullaniciID, String kullaniciAd, String kullaniciSifre, String kullaniciBoylam, String kullaniciEnlem) {
        KullaniciID = kullaniciID;
        KullaniciAd = kullaniciAd;
        KullaniciSifre = kullaniciSifre;
        KullaniciBoylam = kullaniciBoylam;
        KullaniciEnlem = kullaniciEnlem;
    }

    public String getKullaniciID() {
        return KullaniciID;
    }

    public void setKullaniciID(String kullaniciID) {
        KullaniciID = kullaniciID;
    }

    public String getKullaniciAd() {
        return KullaniciAd;
    }

    public void setKullaniciAd(String kullaniciAd) {
        KullaniciAd = kullaniciAd;
    }

    public String getKullaniciSifre() {
        return KullaniciSifre;
    }

    public void setKullaniciSifre(String kullaniciSifre) {
        KullaniciSifre = kullaniciSifre;
    }

    public String getKullaniciBoylam() {
        return KullaniciBoylam;
    }

    public void setKullaniciBoylam(String kullaniciBoylam) {
        KullaniciBoylam = kullaniciBoylam;
    }

    public String getKullaniciEnlem() {
        return KullaniciEnlem;
    }

    public void setKullaniciEnlem(String kullaniciEnlem) {
        KullaniciEnlem = kullaniciEnlem;
    }
}
