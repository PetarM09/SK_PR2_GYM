package example.service;

import example.domen.TerminTreninga;
import example.domen.ZakazaniTermin;
import example.repository.ZakazaniTerminRepository;

import java.util.List;

public interface ZakazaniTerminService{
    List<ZakazaniTermin> korisnikoviTreninzi(String token);

    List<ZakazaniTermin> dohvatiZakazaneTreninge();
}
