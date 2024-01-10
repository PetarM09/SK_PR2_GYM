package example.service.impl;

import example.domen.TerminTreninga;
import example.repository.TerminTreningaRepository;
import example.service.TerminTreningaService;
import io.github.resilience4j.retry.Retry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TerminTreningaServiceImpl implements TerminTreningaService {
    private final TerminTreningaRepository terminTreningaRepository;
    private Retry retry;

    public TerminTreningaServiceImpl(TerminTreningaRepository terminTreningaRepository, Retry retry) {
        this.terminTreningaRepository = terminTreningaRepository;
        this.retry = retry;
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


}
