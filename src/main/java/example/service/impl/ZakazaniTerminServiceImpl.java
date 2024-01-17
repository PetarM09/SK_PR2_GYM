package example.service.impl;

import example.domen.TerminTreninga;
import example.domen.ZakazaniTermin;
import example.dto.TerminTreningaDTO;
import example.dto.ZakazaniTerminDTO;
import example.mapper.ZakazaniTerminMapper;
import example.repository.TerminTreningaRepository;
import example.repository.TipTreningaRepository;
import example.repository.ZakazaniTerminRepository;
import example.security.service.TokenService;
import example.service.TerminTreningaService;
import example.service.ZakazaniTerminService;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ZakazaniTerminServiceImpl implements ZakazaniTerminService {
    private ZakazaniTerminRepository zakazaniTerminRepository;
    private TerminTreningaRepository terminTreningaRepository;
    private TokenService tokenService;
    private final TipTreningaRepository tipTreningaRepository;

    private TerminTreningaService terminTreningaService;

    public ZakazaniTerminServiceImpl(ZakazaniTerminRepository zakazaniTerminRepository, TerminTreningaRepository terminTreningaRepository, TokenService tokenService,
                                     TipTreningaRepository tipTreningaRepository, TerminTreningaService terminTreningaService) {
        this.zakazaniTerminRepository = zakazaniTerminRepository;
        this.terminTreningaRepository = terminTreningaRepository;
        this.tokenService = tokenService;
        this.tipTreningaRepository = tipTreningaRepository;
        this.terminTreningaService = terminTreningaService;
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
    public ZakazaniTermin zakaziTermin(TerminTreningaDTO terminTreninga, Long klijentID) {
        ZakazaniTermin zakazaniTermin = new ZakazaniTermin();
        TerminTreninga terminTreninga1 = terminTreningaRepository.getOne(terminTreninga.getIdTreninga());

        zakazaniTermin.setTerminTreninga(terminTreninga1);
        zakazaniTermin.setCena(terminTreninga1.getCena());
        zakazaniTermin.setKlijentId(klijentID.intValue());
        zakazaniTermin.setJeBesplatan(false);

        zakazaniTerminRepository.save(zakazaniTermin);
        return zakazaniTermin;
    }

    @Override
    public void otkaziZakazaniTermin(ZakazaniTerminDTO zakazaniTerminDTO) {
        TerminTreninga terminTreninga = terminTreningaRepository.getOne(zakazaniTerminDTO.getIdTermina());
        terminTreningaService.smanjiBrojUcesnika(terminTreninga);
        zakazaniTerminRepository.deleteById(zakazaniTerminDTO.getId());
    }
}
