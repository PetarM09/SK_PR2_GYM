package example.service.impl;

import example.domen.TerminTreninga;
import example.domen.ZakazaniTermin;
import example.dto.TerminTreningaDTO;
import example.repository.TerminTreningaRepository;
import example.repository.ZakazaniTerminRepository;
import example.security.service.TokenService;
import example.service.ZakazaniTerminService;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ZakazaniTerminServiceImpl implements ZakazaniTerminService {
    private ZakazaniTerminRepository zakazaniTerminRepository;
    private TerminTreningaRepository terminTreningaRepository;

    private TokenService tokenService;

    public ZakazaniTerminServiceImpl(ZakazaniTerminRepository zakazaniTerminRepository, TerminTreningaRepository terminTreningaRepository, TokenService tokenService) {
        this.zakazaniTerminRepository = zakazaniTerminRepository;
        this.terminTreningaRepository = terminTreningaRepository;
        this.tokenService = tokenService;
    }

    @Override
    public List<ZakazaniTermin> korisnikoviTreninzi(String token) {
        Claims claims = tokenService.parseToken(token);
        List<ZakazaniTermin> svi = dohvatiZakazaneTreninge();
        List<ZakazaniTermin> trazeni = new ArrayList<>();
        for (ZakazaniTermin zakazaniTermin : svi){
            if (zakazaniTermin.getKlijentId() == Integer.parseInt(claims.get("id",String.class))){
                trazeni.add(zakazaniTermin);
            }
        }
        return trazeni;
    }

    @Override
    public List<ZakazaniTermin> dohvatiZakazaneTreninge() {
        return zakazaniTerminRepository.findAll();
    }

    @Override
    public ZakazaniTermin zakaziTermin(TerminTreningaDTO terminTreninga, Integer klijentID) {
        ZakazaniTermin zakazaniTermin = new ZakazaniTermin();
        TerminTreninga terminTreninga1 = terminTreningaRepository.getOne(terminTreninga.getIdTreninga());
        zakazaniTermin.setTerminTreninga(terminTreninga1);
        zakazaniTermin.setCena(terminTreninga1.getCena());
        zakazaniTermin.setKlijentId(klijentID);
        zakazaniTermin.setJeBesplatan(false);

        List<ZakazaniTermin> zakazaniTermini = zakazaniTerminRepository.findAll();
        zakazaniTermini.add(zakazaniTermin);

        return zakazaniTerminRepository.save(zakazaniTermin);
    }

}
