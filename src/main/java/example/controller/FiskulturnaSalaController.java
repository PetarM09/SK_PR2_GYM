package example.controller;

import example.domen.FiskulturnaSala;
import example.dto.FiskulturnaSalaDTO;
import example.mapper.FiskulturnaSalaMapper;
import example.security.CheckSecurity;
import example.service.FiskulturnaSalaService;
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