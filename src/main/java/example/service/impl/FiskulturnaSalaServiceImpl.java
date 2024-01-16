package example.service.impl;

import example.domen.FiskulturnaSala;
import example.repository.FiskulturnaSalaRepository;
import example.security.service.TokenService;
import example.service.FiskulturnaSalaService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Transactional
public class FiskulturnaSalaServiceImpl implements FiskulturnaSalaService {

    private final FiskulturnaSalaRepository salaRepository;
    private final RestTemplate userServiceApiClient;
    private TokenService tokenService;

    public FiskulturnaSalaServiceImpl(FiskulturnaSalaRepository salaRepository, RestTemplate userServiceApiClient, TokenService tokenService) {
        this.salaRepository = salaRepository;
        this.userServiceApiClient = userServiceApiClient;
        this.tokenService = tokenService;
    }

    @Override
    public FiskulturnaSala sacuvajIliAzurirajSalu(FiskulturnaSala sala) {
        return salaRepository.save(sala);
    }

    @Override
    public FiskulturnaSala vratiSalu(String imeSale) {
        Optional<FiskulturnaSala> sala = salaRepository.findByIme(imeSale);
        if (!sala.isPresent()) {
            throw new RuntimeException("Sala nije pronadjena.");
        }else{
            return sala.get();
        }
    }

    @Override
    public String vratiImeSale(String authorization) {;
        Long id = tokenService.parseId(authorization);
        ResponseEntity<String> imeSale = userServiceApiClient.exchange("/api/korisnici/getImeSale/" + id, org.springframework.http.HttpMethod.GET, null, String.class);
        return imeSale.getBody();
    }

    @Override
    public void azurirajImeSaleUser(String ime, String authorization) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonBody = "{\"ime\": \"" + ime + "\", \"token\": \"" + authorization + "\"}";

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<String> response = userServiceApiClient.postForEntity("/api/korisnici/azuriraj-ime-sale/", requestEntity,  String.class);
    }
}
