package example.service;

import example.domen.TerminTreninga;
import example.dto.TerminTreningaDTO;

import java.util.List;

public interface TerminTreningaService {
    TerminTreninga zakaziTermin(TerminTreninga terminTreninga);

    List<TerminTreninga> dohvatiSveTermine();

    void otkaziTermin(Long id);

    Integer brojTreninga(Long id);

    TerminTreninga dodajTermin(TerminTreningaDTO terminTreningaDTO);
    void smanjiBrojUcesnika(TerminTreninga terminTreninga);
    void povecajBrojUcesnika(TerminTreningaDTO terminTreningaDTO);

    void povecajKlijentuTreninge(long klijentId);
}
