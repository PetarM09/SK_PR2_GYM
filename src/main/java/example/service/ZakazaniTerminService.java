package example.service;

import example.domen.TerminTreninga;
import example.domen.ZakazaniTermin;
import example.dto.TerminTreningaDTO;
import example.repository.ZakazaniTerminRepository;

import java.util.List;

public interface ZakazaniTerminService{
    List<ZakazaniTermin> korisnikoviTreninzi(String token);

    List<ZakazaniTermin> dohvatiZakazaneTreninge();

    public ZakazaniTermin zakaziTermin(TerminTreningaDTO terminTreninga, Integer klijentID);
}
