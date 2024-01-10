package example.controller;

import example.domen.FiskulturnaSala;
import example.domen.TerminTreninga;
import example.dto.FiskulturnaSalaDTO;
import example.dto.TerminTreningaDTO;
import example.mapper.FiskulturnaSalaMapper;
import example.mapper.TerminTreningaMapper;
import example.security.CheckSecurity;
import example.service.FiskulturnaSalaService;
import example.service.TerminTreningaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fiskulturne-sale")
public class FiskulturnaSalaController {

    private final FiskulturnaSalaService salaService;

    public FiskulturnaSalaController(FiskulturnaSalaService salaService) {
        this.salaService = salaService;
    }

    @PostMapping("/sacuvaj-ili-azuriraj")
    @CheckSecurity(roles = {"ADMIN", "MENADZER"})
    public ResponseEntity<FiskulturnaSalaDTO> sacuvajIliAzurirajSalu(@RequestBody FiskulturnaSalaDTO salaDTO) {
        FiskulturnaSala sala = FiskulturnaSalaMapper.toEntity(salaDTO);
        FiskulturnaSala sacuvanaSala = salaService.sacuvajIliAzurirajSalu(sala);
        return new ResponseEntity<>(FiskulturnaSalaMapper.toDTO(sacuvanaSala), HttpStatus.OK);
    }


}