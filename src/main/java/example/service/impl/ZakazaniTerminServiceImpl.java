package example.service.impl;

import example.domen.TerminTreninga;
import example.domen.ZakazaniTermin;
import example.dto.TerminReservationEmailDataTO;
import example.dto.TerminTreningaDTO;
import example.dto.ZakazaniTerminDTO;
import example.helper.MessageHelper;
import example.repository.TerminTreningaRepository;
import example.repository.TipTreningaRepository;
import example.repository.ZakazaniTerminRepository;
import example.security.service.TokenService;
import example.service.TerminTreningaService;
import example.service.ZakazaniTerminService;
import io.jsonwebtoken.Claims;
import io.github.resilience4j.retry.Retry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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
    private final RestTemplate userServiceApiClient;

    private MessageHelper messageHelper;
    private JmsTemplate jmsTemplate;
    private String destination;
    private String destinationOtkazi;
    private Retry userServiceRetry;

    public ZakazaniTerminServiceImpl(ZakazaniTerminRepository zakazaniTerminRepository, TerminTreningaRepository terminTreningaRepository, TokenService tokenService,
                                     TipTreningaRepository tipTreningaRepository, TerminTreningaService terminTreningaService,
                                     RestTemplate userServiceApiClient, MessageHelper messageHelper, JmsTemplate jmsTemplate,
                                     @Value("${destination.trainingScheduleNotification}") String destination,
                                     @Value("${destination.otkazivanjeTreninga}" ) String destinationOtkazi, Retry userServiceRetry) {
        this.zakazaniTerminRepository = zakazaniTerminRepository;
        this.terminTreningaRepository = terminTreningaRepository;
        this.tokenService = tokenService;
        this.tipTreningaRepository = tipTreningaRepository;
        this.terminTreningaService = terminTreningaService;
        this.userServiceApiClient = userServiceApiClient;
        this.messageHelper = messageHelper;
        this.jmsTemplate = jmsTemplate;
        this.destination = destination;
        this.destinationOtkazi = destinationOtkazi;
        this.userServiceRetry = userServiceRetry;
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
        TerminTreninga terminTreninga1 = terminTreningaRepository.getOne(terminTreninga.getId());

        zakazaniTermin.setTerminTreninga(terminTreninga1);
        zakazaniTermin.setCena(terminTreninga1.getCena());
        zakazaniTermin.setKlijentId(klijentID.intValue());
        zakazaniTermin.setJeBesplatan(false);
        zakazaniTerminRepository.save(zakazaniTermin);

        TerminReservationEmailDataTO terminReservationEmailDataTO = new TerminReservationEmailDataTO();
        terminReservationEmailDataTO.setDate(terminTreninga1.getDatum());
        terminReservationEmailDataTO.setVremePocetka(terminTreninga1.getVremePocetka());
        terminReservationEmailDataTO.setNazivSale(terminTreninga1.getSala().getIme());
        terminReservationEmailDataTO.setTipNotifikacije("SCHEUDLE_RESERVATION_NOTIFICATION");
        terminReservationEmailDataTO.setKorisnikId(klijentID);

        jmsTemplate.convertAndSend(destination,messageHelper.createTextMessage(terminReservationEmailDataTO));
        return zakazaniTermin;
    }

    @Override
    public void otkaziZakazaniTermin(ZakazaniTerminDTO zakazaniTerminDTO) {
        if(zakazaniTerminRepository.existsById(zakazaniTerminDTO.getId())){
            TerminTreninga terminTreninga = terminTreningaRepository.getOne(zakazaniTerminDTO.getTerminTreningaDTO().getId());
            terminTreningaService.smanjiBrojUcesnika(terminTreninga);
            terminTreningaService.smanjiKlijentuTreninge(zakazaniTerminDTO.getKlijentId());
            zakazaniTerminRepository.deleteById(zakazaniTerminDTO.getId());

            TerminReservationEmailDataTO terminReservationEmailDataTO = new TerminReservationEmailDataTO();
            terminReservationEmailDataTO.setDate(terminTreninga.getDatum());
            terminReservationEmailDataTO.setVremePocetka(terminTreninga.getVremePocetka());
            terminReservationEmailDataTO.setNazivSale(terminTreninga.getSala().getIme());
            terminReservationEmailDataTO.setTipNotifikacije("SCHEUDLE_CANCEL_NOTIFICATION");
            terminReservationEmailDataTO.setKorisnikId(Long.valueOf(zakazaniTerminDTO.getKlijentId()));

            jmsTemplate.convertAndSend(destinationOtkazi,messageHelper.createTextMessage(terminReservationEmailDataTO));
        }
    }

    @Override
    public void obrisiZakazaniTermin(Long id) {
        zakazaniTerminRepository.deleteById(id);
    }
}
