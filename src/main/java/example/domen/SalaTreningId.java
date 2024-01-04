package example.domen;

import java.io.Serializable;
import javax.persistence.*;

@Embeddable
public class SalaTreningId implements Serializable {

    @Column(name = "id_sale")
    private Long idSale;

    @Column(name = "id_treninga")
    private Long idTreninga;

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
}
