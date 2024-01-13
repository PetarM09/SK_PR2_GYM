package example.service.impl;

import example.domen.TerminTreninga;
import example.domen.ZakazaniTermin;
import example.repository.ZakazaniTerminRepository;
import example.security.service.TokenService;
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
    private TokenService tokenService;

    public ZakazaniTerminServiceImpl(ZakazaniTerminRepository zakazaniTerminRepository, TokenService tokenService) {
        this.zakazaniTerminRepository = zakazaniTerminRepository;
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
}
