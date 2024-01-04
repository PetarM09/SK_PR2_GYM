package example.dto;

public class ZakazaniTerminDTO {

    private Long id;
    private Long idTermina;
    private Integer klijentId;
    private Integer cena;
    private Boolean jeBesplatan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdTermina() {
        return idTermina;
    }

    public void setIdTermina(Long idTermina) {
        this.idTermina = idTermina;
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
