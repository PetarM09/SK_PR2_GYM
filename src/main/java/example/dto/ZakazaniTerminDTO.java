package example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ZakazaniTerminDTO {

    private Long id;
    private Long idTermina;
    private Integer klijentId;
    private Integer cena;
    private Boolean jeBesplatan;
    @JsonProperty("terminTreningaDto")
    private TerminTreningaDTO terminTreningaDTO;

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

    public TerminTreningaDTO getTerminTreningaDTO() {
        return terminTreningaDTO;
    }

    public void setTerminTreningaDTO(TerminTreningaDTO terminTreningaDTO) {
        this.terminTreningaDTO = terminTreningaDTO;
    }

    @Override
    public String toString() {
        return "ZakazaniTerminDTO{" +
                "id=" + id +
                ", idTermina=" + idTermina +
                ", klijentId=" + klijentId +
                ", cena=" + cena +
                ", jeBesplatan=" + jeBesplatan +
                ", terminTreningaDTO=" + terminTreningaDTO +
                '}';
    }
}
