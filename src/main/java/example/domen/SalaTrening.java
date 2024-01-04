package example.domen;

import javax.persistence.*;

@Entity
@Table(name = "sala_trening")
public class SalaTrening {

    @EmbeddedId
    private SalaTreningId id;

    @ManyToOne
    @JoinColumn(name = "id_sale", insertable = false, updatable = false)
    private FiskulturnaSala sala;

    @ManyToOne
    @JoinColumn(name = "id_treninga", insertable = false, updatable = false)
    private TipTreninga tipTreninga;

    public SalaTreningId getId() {
        return id;
    }

    public void setId(SalaTreningId id) {
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
}


