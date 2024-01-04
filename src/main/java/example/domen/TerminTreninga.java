package example.domen;

import javax.persistence.*;
import java.util.Date;
import java.sql.Time;

@Entity
@Table(name = "termin_treninga")
public class TerminTreninga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_termina")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_sale")
    private FiskulturnaSala sala;

    @ManyToOne
    @JoinColumn(name = "id_treninga")
    private TipTreninga tipTreninga;

    @Column(name = "datum", nullable = false)
    private Date datum;

    @Column(name = "vreme_pocetka", nullable = false)
    private Time vremePocetka;

    @Column(name = "maksimalni_broj_ucesnika", nullable = false)
    private Integer maksimalanBrojUcesnika;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FiskulturnaSala getSala() {
        return sala;
    }

    public void setSala(FiskulturnaSala sala) {
        this.sala = sala;
    }

    public TipTreninga getTipTreninga() {
        return tipTreninga;
    }

    public void setTipTreninga(TipTreninga tipTreninga) {
        this.tipTreninga = tipTreninga;
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
