package example.mapper;

import example.domen.SalaTreningId;
import example.dto.SalaTreningDTO;
import example.domen.SalaTrening;

public class SalaTreningMapper {

    public static SalaTreningDTO toDTO(SalaTrening salaTrening) {
        SalaTreningDTO salaTreningDTO = new SalaTreningDTO();
        salaTreningDTO.setIdSale(salaTrening.getId().getIdSale());
        salaTreningDTO.setIdTreninga(salaTrening.getId().getIdTreninga());
        return salaTreningDTO;
    }

    public static SalaTrening toEntity(SalaTreningDTO salaTreningDTO) {
        SalaTrening salaTrening = new SalaTrening();

        SalaTreningId salaTreningId = new SalaTreningId();
        salaTreningId.setIdSale(salaTreningDTO.getIdSale());
        salaTreningId.setIdTreninga(salaTreningDTO.getIdTreninga());

        salaTrening.setId(salaTreningId);

        return salaTrening;
    }
}
