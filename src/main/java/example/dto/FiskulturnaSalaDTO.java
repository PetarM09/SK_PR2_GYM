package example.dto;

public class FiskulturnaSalaDTO {
    private Long id;
    private String ime;
    private String kratakOpis;

    private Integer broj_personalnih_trenera;

    public Integer getBroj_personalnih_trenera() {
        return broj_personalnih_trenera;
    }

    public void setBroj_personalnih_trenera(Integer broj_personalnih_trenera) {
        this.broj_personalnih_trenera = broj_personalnih_trenera;
    }

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
}