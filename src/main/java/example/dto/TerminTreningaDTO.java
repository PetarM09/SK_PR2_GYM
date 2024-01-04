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
}
