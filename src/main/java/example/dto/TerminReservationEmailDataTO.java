package example.dto;

import java.sql.Time;
import java.util.Date;

public class TerminReservationEmailDataTO {
    private Date date;
    private Time vremePocetka;
    private String nazivSale;
    private String tipNotifikacije;
    private Long korisnikId;

    public Long getKorisnikId() {
        return korisnikId;
    }

    public void setKorisnikId(Long korisnikId) {
        this.korisnikId = korisnikId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getVremePocetka() {
        return vremePocetka;
    }

    public void setVremePocetka(Time vremePocetka) {
        this.vremePocetka = vremePocetka;
    }

    public String getNazivSale() {
        return nazivSale;
    }

    public void setNazivSale(String nazivSale) {
        this.nazivSale = nazivSale;
    }

    public String getTipNotifikacije() {
        return tipNotifikacije;
    }

    public void setTipNotifikacije(String tipNotifikacije) {
        this.tipNotifikacije = tipNotifikacije;
    }
}
