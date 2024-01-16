package example.service.impl;

import example.domen.TerminTreninga;
import example.dto.TerminTreningaDTO;
import example.repository.FiskulturnaSalaRepository;
import example.repository.TerminTreningaRepository;
import example.repository.TipTreningaRepository;
import example.security.service.TokenService;
import example.service.TerminTreningaService;
import io.github.resilience4j.retry.Retry;
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
    private Retry retry;
    private TokenService tokenService;

    public TerminTreningaServiceImpl(TerminTreningaRepository terminTreningaRepository, FiskulturnaSalaRepository fiskulturnaSalaRepository, TipTreningaRepository tipTreningaRepository, Retry retry, TokenService tokenService) {
        this.terminTreningaRepository = terminTreningaRepository;
        this.fiskulturnaSalaRepository = fiskulturnaSalaRepository;
        this.tipTreningaRepository = tipTreningaRepository;
        this.retry = retry;
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
        return Integer.valueOf(retry.executeSupplier(()->{
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


}
