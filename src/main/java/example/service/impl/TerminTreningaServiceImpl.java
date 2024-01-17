package example.service.impl;

import example.domen.TerminTreninga;
import example.dto.KorisniciDto;
import example.dto.TerminTreningaDTO;
import example.repository.FiskulturnaSalaRepository;
import example.repository.TerminTreningaRepository;
import example.repository.TipTreningaRepository;
import example.security.service.TokenService;
import example.service.TerminTreningaService;
import io.github.resilience4j.retry.Retry;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Transactional
public class TerminTreningaServiceImpl implements TerminTreningaService {
    private final TerminTreningaRepository terminTreningaRepository;
    private final FiskulturnaSalaRepository fiskulturnaSalaRepository;
    private final TipTreningaRepository tipTreningaRepository;
    private Retry terminServiceRetry;
    private final RestTemplate userServiceApiClient;

    private TokenService tokenService;

    public TerminTreningaServiceImpl(TerminTreningaRepository terminTreningaRepository, FiskulturnaSalaRepository fiskulturnaSalaRepository, TipTreningaRepository tipTreningaRepository, Retry retry, RestTemplate userServiceApiClient, TokenService tokenService) {
        this.terminTreningaRepository = terminTreningaRepository;
        this.fiskulturnaSalaRepository = fiskulturnaSalaRepository;
        this.tipTreningaRepository = tipTreningaRepository;
        this.terminServiceRetry = retry;
        this.userServiceApiClient = userServiceApiClient;
        this.tokenService = tokenService;
    }

    @Override
    public TerminTreninga zakaziTermin(TerminTreninga terminTreninga) {
        return terminTreningaRepository.save(terminTreninga);
    }

    public List<TerminTreninga> dohvatiSveTermine(){
        return terminTreningaRepository.findAll();
    }

    public void otkaziTermin(Long id){
        terminTreningaRepository.deleteById(id);
    }

    public Integer brojTreninga(Long id){
        return Integer.valueOf(terminServiceRetry.executeSupplier(()->{
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject("http://localhost:8080/korisnici/brojTreninga/"+id, String.class);
        }));
//        Retry.decorateSupplier(retry ()-> OVDE TREBA GET REQ KA USER SERVISU VEROVATNO NOT SURE);
    }

    public TerminTreninga dodajTermin(TerminTreningaDTO terminTreningaDTO){
            TerminTreninga terminTreninga = new TerminTreninga();

            terminTreninga.setCena(terminTreningaDTO.getCena());
            terminTreninga.setDatum(terminTreningaDTO.getDatum());
            terminTreninga.setSala(fiskulturnaSalaRepository.getOne(terminTreningaDTO.getIdSale()));
            terminTreninga.setTipTreninga(tipTreningaRepository.getOne(terminTreningaDTO.getIdTreninga()));
            terminTreninga.setBrojUcesnika(0);
            terminTreninga.setMaksimalanBrojUcesnika(terminTreningaDTO.getMaksimalanBrojUcesnika());

            terminTreningaRepository.save(terminTreninga);
            return terminTreninga;
    }

    public void smanjiBrojUcesnika(TerminTreninga terminTreninga){
        System.out.println(terminTreninga.getBrojUcesnika());
        terminTreninga.setBrojUcesnika(terminTreninga.getBrojUcesnika()-1);
        System.out.println(terminTreninga.getBrojUcesnika());
        terminTreningaRepository.save(terminTreninga);
    }

    public void povecajBrojUcesnika(TerminTreningaDTO terminTreningaDTO){
            TerminTreninga terminTreninga = terminTreningaRepository.getOne(terminTreningaDTO.getId());
            terminTreninga.setBrojUcesnika(terminTreninga.getBrojUcesnika()+1);
            terminTreningaRepository.save(terminTreninga);
    }

    @Override
    public void povecajKlijentuTreninge(long klijentId) {
        KorisniciDto korisniciDto = new KorisniciDto();
        korisniciDto.setId((int) klijentId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<KorisniciDto> requestEntity = new HttpEntity<>(korisniciDto, headers);
        ResponseEntity<String> response = userServiceApiClient.postForEntity("/api/korisnici/povecaj-broj-treninga", requestEntity,  String.class);
    }

    @Override
    public void smanjiKlijentuTreninge(long klijentId) {
        KorisniciDto korisniciDto = new KorisniciDto();
        korisniciDto.setId((int) klijentId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<KorisniciDto> requestEntity = new HttpEntity<>(korisniciDto, headers);
        ResponseEntity<String> response = userServiceApiClient.postForEntity("/api/korisnici/smanji-broj-treninga", requestEntity,  String.class);
    }
}
