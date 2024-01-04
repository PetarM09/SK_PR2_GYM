package example.domen;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tip_treninga")
public class TipTreninga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_treninga")
    private Long id;

    @Column(name = "naziv", nullable = false)
    private String naziv;

    @Column(name = "tip", nullable = false)
    private String tip;

    @ManyToMany(mappedBy = "podrzaniTipoviTreninga")
    private Set<FiskulturnaSala> saleKojePodrzavajuTrening = new HashSet<>();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Set<FiskulturnaSala> getSaleKojePodrzavajuTrening() {
        return saleKojePodrzavajuTrening;
    }

    public void setSaleKojePodrzavajuTrening(Set<FiskulturnaSala> saleKojePodrzavajuTrening) {
        this.saleKojePodrzavajuTrening = saleKojePodrzavajuTrening;
    }
}
