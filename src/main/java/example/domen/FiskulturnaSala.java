package example.domen;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "fisjulturna_sala")
public class FiskulturnaSala {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sale")
    private Long id;

    @Column(name = "ime", nullable = false)
    private String ime;

    @Column(name = "kratak_opis")
    private String kratakOpis;

    @Column(name = "broj_personalnih_trenera")
    private Integer brojPersonalnihTrenera;

    @ManyToMany
    @JoinTable(
            name = "sala_trening",
            joinColumns = @JoinColumn(name = "id_sale"),
            inverseJoinColumns = @JoinColumn(name = "id_treninga")
    )
    private Set<TipTreninga> podrzaniTipoviTreninga = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getKratakOpis() {
        return kratakOpis;
    }

    public void setKratakOpis(String kratakOpis) {
        this.kratakOpis = kratakOpis;
    }

    public Integer getBrojPersonalnihTrenera() {
        return brojPersonalnihTrenera;
    }

    public void setBrojPersonalnihTrenera(Integer brojPersonalnihTrenera) {
        this.brojPersonalnihTrenera = brojPersonalnihTrenera;
    }

    public Set<TipTreninga> getPodrzaniTipoviTreninga() {
        return podrzaniTipoviTreninga;
    }

    public void setPodrzaniTipoviTreninga(Set<TipTreninga> podrzaniTipoviTreninga) {
        this.podrzaniTipoviTreninga = podrzaniTipoviTreninga;
    }
}
