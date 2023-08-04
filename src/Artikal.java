/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Urkom
 */
public class Artikal {

    private String upc;
    private String ime;
    private double cena;

    public Artikal(String upc, String ime, double cena) {
        this.upc = upc;
        this.ime = ime;
        this.cena = cena;
    }

    public Artikal() {
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

}
