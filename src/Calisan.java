/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author İREM SAMUR
 */
public class Calisan {
    //Veritabanındaki her bir sütunu bir class yapısında tutmak için Calisan classı oluşturduk
    //Veritabanındaki özellikleri tutuyoruz
    private int id;
    private String ad;
    private String soyad;
    private String departman;
    private String maaş;

    public Calisan(int id, String ad, String soyad, String departman, String maaş) {
        this.id = id;
        this.ad = ad;
        this.soyad = soyad;
        this.departman = departman;
        this.maaş = maaş;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getDepartman() {
        return departman;
    }

    public void setDepartman(String departman) {
        this.departman = departman;
    }

    public String getMaaş() {
        return maaş;
    }

    public void setMaaş(String maaş) {
        this.maaş = maaş;
    }
    
    
    
}
