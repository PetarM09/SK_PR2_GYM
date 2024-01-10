package example.mapper;

import example.dto.TipTreningaDTO;
import example.domen.TipTreninga;

public class TipTreningaMapper {

    public static TipTreningaDTO toDTO(TipTreninga tipTreninga) {
        TipTreningaDTO tipTreningaDTO = new TipTreningaDTO();
        tipTreningaDTO.setId(tipTreninga.getId());
        tipTreningaDTO.setNaziv(tipTreninga.getNaziv());
        tipTreningaDTO.setTip(tipTreninga.getTip());
        return tipTreningaDTO;
    }

    public static TipTreninga toEntity(TipTreningaDTO tipTreningaDTO) {
        TipTreninga tipTreninga = new TipTreninga();
        tipTreninga.setId(tipTreningaDTO.getId());
        tipTreninga.setNaziv(tipTreningaDTO.getNaziv());
        tipTreninga.setTip(tipTreningaDTO.getTip());
        return tipTreninga;
    }
}
