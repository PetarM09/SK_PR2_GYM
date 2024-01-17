package example.dto;

import java.sql.Time;
import java.util.Date;

public class TerminTreningaDTO {

    private Long id;
    private Long idSale;
    private Long idTreninga;
    private Date datum;
    private Time vremePocetka;
    private Integer maksimalanBrojUcesnika;
    private Integer brojUcesnika;

    private String nazivSale;
    private String nazivTreninga;

    public String getNazivSale() {
        return nazivSale;
    }

    public void setNazivSale(String nazivSale) {
        this.nazivSale = nazivSale;
    }

    public String getNazivTreninga() {
        return nazivTreninga;
    }

    public void setNazivTreninga(String nazivTreninga) {
        this.nazivTreninga = nazivTreninga;
    }

    private Integer cena;

    public Integer getCena() {
        return cena;
    }

    public void setCena(Integer cena) {
        this.cena = cena;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdSale() {
        return idSale;
    }

    public void setIdSale(Long idSale) {
        this.idSale = idSale;
    }

    public Long getIdTreninga() {
        return idTreninga;
    }

    public void setIdTreninga(Long idTreninga) {
        this.idTreninga = idTreninga;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public Time getVremePocetka() {
        return vremePocetka;
    }

    public void setVremePocetka(Time vremePocetka) {
        this.vremePocetka = vremePocetka;
    }

    public Integer getMaksimalanBrojUcesnika() {
        return maksimalanBrojUcesnika;
    }

    public void setMaksimalanBrojUcesnika(Integer maksimalanBrojUcesnika) {
        this.maksimalanBrojUcesnika = maksimalanBrojUcesnika;
    }

    public Integer getBrojUcesnika() {
        return brojUcesnika;
    }

    public void setBrojUcesnika(Integer brojUcesnika) {
        this.brojUcesnika = brojUcesnika;
    }
}
