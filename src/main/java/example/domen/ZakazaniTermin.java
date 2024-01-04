package example.domen;

import javax.persistence.*;

@Entity
@Table(name = "zakazani_termin")
public class ZakazaniTermin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zakazanog_termina")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_termina")
    private TerminTreninga terminTreninga;

    @Column(name = "klijent_id", nullable = false)
    private Integer klijentId;

    @Column(name = "cena", nullable = false)
    private Integer cena;

    @Column(name = "je_besplatan", nullable = false)
    private Boolean jeBesplatan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TerminTreninga getTerminTreninga() {
        return terminTreninga;
    }

    public void setTerminTreninga(TerminTreninga terminTreninga) {
        this.terminTreninga = terminTreninga;
    }

    public Integer getKlijentId() {
        return klijentId;
    }

    public void setKlijentId(Integer klijentId) {
        this.klijentId = klijentId;
    }

    public Integer getCena() {
        return cena;
    }

    public void setCena(Integer cena) {
        this.cena = cena;
    }

    public Boolean getJeBesplatan() {
        return jeBesplatan;
    }

    public void setJeBesplatan(Boolean jeBesplatan) {
        this.jeBesplatan = jeBesplatan;
    }
}
