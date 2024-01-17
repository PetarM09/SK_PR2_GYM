package example.mapper;

import example.domen.FiskulturnaSala;
import example.domen.TipTreninga;
import example.dto.TerminTreningaDTO;
import example.domen.TerminTreninga;
import example.repository.FiskulturnaSalaRepository;
import example.repository.TerminTreningaRepository;
import example.repository.TipTreningaRepository;

import java.util.Optional;


public class TerminTreningaMapper {

    private static TipTreningaRepository tipTreningaRepository;
    private static FiskulturnaSalaRepository fiskulturnaSalaRepository;

    public TerminTreningaMapper(TipTreningaRepository tipTreningaRepository, FiskulturnaSalaRepository fiskulturnaSalaRepository) {
        this.tipTreningaRepository = tipTreningaRepository;
        this.fiskulturnaSalaRepository = fiskulturnaSalaRepository;
    }

    public static TerminTreningaDTO toDTO(TerminTreninga terminTreninga) {
        TerminTreningaDTO terminTreningaDTO = new TerminTreningaDTO();
        terminTreningaDTO.setId(terminTreninga.getId());
        terminTreningaDTO.setIdSale(terminTreninga.getSala().getId());
        terminTreningaDTO.setIdTreninga(terminTreninga.getTipTreninga().getId());
        terminTreningaDTO.setDatum(terminTreninga.getDatum());
        terminTreningaDTO.setVremePocetka(terminTreninga.getVremePocetka());
        terminTreningaDTO.setMaksimalanBrojUcesnika(terminTreninga.getMaksimalanBrojUcesnika());
        terminTreningaDTO.setBrojUcesnika(terminTreninga.getBrojUcesnika());
        return terminTreningaDTO;
    }

    public static TerminTreninga toEntity(TerminTreningaDTO terminTreningaDTO) {
        TerminTreninga terminTreninga = new TerminTreninga();
        terminTreninga.setId(terminTreningaDTO.getId());
        terminTreninga.setSala(getSalaById(terminTreningaDTO.getIdSale()));
        terminTreninga.setTipTreninga(getTipTreningaById(terminTreningaDTO.getIdTreninga()));
        terminTreninga.setDatum(terminTreningaDTO.getDatum());
        terminTreninga.setVremePocetka(terminTreningaDTO.getVremePocetka());
        terminTreninga.setMaksimalanBrojUcesnika(terminTreningaDTO.getMaksimalanBrojUcesnika());
        terminTreninga.setBrojUcesnika(terminTreningaDTO.getBrojUcesnika());
        return terminTreninga;
    }

    private static FiskulturnaSala getSalaById(Long id) {
        Optional<FiskulturnaSala> fiskulturnaSalaOptional = fiskulturnaSalaRepository.findById(id);
        return fiskulturnaSalaOptional.get(); // Placeholder, replace with actual logic
    }

    private static TipTreninga getTipTreningaById(Long id) {
        Optional<TipTreninga> TipTreningaOptional = tipTreningaRepository.findById(id);
        return TipTreningaOptional.get();
    }
}
