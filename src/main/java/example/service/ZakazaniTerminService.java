package example.service;

import example.domen.TerminTreninga;
import example.domen.ZakazaniTermin;
import example.dto.TerminTreningaDTO;
import example.dto.ZakazaniTerminDTO;
import example.repository.ZakazaniTerminRepository;

import java.util.List;

public interface ZakazaniTerminService{
    List<ZakazaniTermin> korisnikoviTreninzi(String token);

    List<ZakazaniTermin> dohvatiZakazaneTreninge();

    ZakazaniTermin zakaziTermin(TerminTreningaDTO terminTreninga, Long klijentID);

    void otkaziZakazaniTermin(ZakazaniTerminDTO zakazaniTerminDTO);

    void obrisiZakazaniTermin(Long id);
}
