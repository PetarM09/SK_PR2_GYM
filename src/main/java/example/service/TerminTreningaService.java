package example.service;

import example.domen.TerminTreninga;

import java.util.List;

public interface TerminTreningaService {
    TerminTreninga zakaziTermin(TerminTreninga terminTreninga);

    List<TerminTreninga> dohvatiSveTermine();

    void otkaziTermin(Long id);

    Integer brojTreninga(Long id);

}
