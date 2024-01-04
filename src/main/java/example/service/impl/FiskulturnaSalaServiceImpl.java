package example.service.impl;

import example.domen.FiskulturnaSala;
import example.repository.FiskulturnaSalaRepository;
import example.service.FiskulturnaSalaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FiskulturnaSalaServiceImpl implements FiskulturnaSalaService {

    private final FiskulturnaSalaRepository salaRepository;

    public FiskulturnaSalaServiceImpl(FiskulturnaSalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    @Override
    public FiskulturnaSala sacuvajIliAzurirajSalu(FiskulturnaSala sala) {
        return salaRepository.save(sala);
    }

    // Implementirajte ostale metode prema potrebi
}
