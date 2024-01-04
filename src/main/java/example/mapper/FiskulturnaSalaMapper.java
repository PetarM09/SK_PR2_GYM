package example.mapper;

import example.domen.FiskulturnaSala;
import example.dto.FiskulturnaSalaDTO;

public class FiskulturnaSalaMapper {
    public static FiskulturnaSalaDTO toDTO(FiskulturnaSala sala) {
        FiskulturnaSalaDTO dto = new FiskulturnaSalaDTO();
        dto.setId(sala.getId());
        dto.setIme(sala.getIme());
        dto.setKratakOpis(sala.getKratakOpis());
        // Postavite ostala polja po potrebi
        return dto;
    }

    public static FiskulturnaSala toEntity(FiskulturnaSalaDTO dto) {
        FiskulturnaSala sala = new FiskulturnaSala();
        sala.setId(dto.getId());
        sala.setIme(dto.getIme());
        sala.setKratakOpis(dto.getKratakOpis());
        // Postavite ostala polja po potrebi
        return sala;
    }
}
