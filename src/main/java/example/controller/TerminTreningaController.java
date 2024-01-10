package example.controller;

import example.domen.TerminTreninga;
import example.dto.FiskulturnaSalaDTO;
import example.dto.TerminTreningaDTO;
import example.dto.ViseTerminaTreninga;
import example.mapper.TerminTreningaMapper;
import example.security.CheckSecurity;
import example.service.TerminTreningaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/termin-treninga")
public class TerminTreningaController {
    private final TerminTreningaService terminTreningaService;

    public TerminTreningaController(TerminTreningaService terminTreningaService) {
        this.terminTreningaService = terminTreningaService;
    }

    @PostMapping("/zakazi-termin")
    @CheckSecurity(roles = {"KLIJENT"})
    public ResponseEntity<TerminTreningaDTO> zakaziTermin(@RequestBody TerminTreningaDTO terminTreningaDTO){
        TerminTreninga terminTreninga = TerminTreningaMapper.toEntity(terminTreningaDTO);
        TerminTreninga zakazano = terminTreningaService.zakaziTermin(terminTreninga);


        return new ResponseEntity<>(TerminTreningaMapper.toDTO(zakazano),HttpStatus.OK);
    }
    @GetMapping("/izlistaj-Termine")
    public ResponseEntity<List<TerminTreninga>> izlistajTermine(){
        List<TerminTreninga> listaTermina = terminTreningaService.dohvatiSveTermine();
        return new ResponseEntity<>(listaTermina,HttpStatus.OK);
    }

    @GetMapping("/filtirajPoIndividualni-Grupni")
    public ResponseEntity<List<TerminTreninga>> filtirajPoIndividualniGrupni(@RequestBody String tip){
        List<TerminTreninga> svi = terminTreningaService.dohvatiSveTermine();
        List<TerminTreninga> filtirano = new ArrayList<>();
        for (TerminTreninga terminTreninga : svi){
            if (terminTreninga.getTipTreninga().getTip().equalsIgnoreCase(tip)){
                filtirano.add(terminTreninga);
            }
        }
        return new ResponseEntity<>(filtirano,HttpStatus.OK);
    }

    @GetMapping("/filtirajPoTipu")
    public ResponseEntity<List<TerminTreninga>> filtrirajPoTipu(@RequestBody String tip){
        List<TerminTreninga> svi = terminTreningaService.dohvatiSveTermine();
        List<TerminTreninga> filtirano = new ArrayList<>();
        for (TerminTreninga terminTreninga : svi){
            if (terminTreninga.getTipTreninga().getNaziv().equalsIgnoreCase(tip)){
                filtirano.add(terminTreninga);
            }
        }
        return new ResponseEntity<>(filtirano,HttpStatus.OK);
    }

    @GetMapping("/filtirajPoDanu")
    public ResponseEntity<List<TerminTreninga>> filtrirajPoDanu(@RequestBody String dan){
        List<TerminTreninga> svi = terminTreningaService.dohvatiSveTermine();
        List<TerminTreninga> filtirano = new ArrayList<>();

        for (TerminTreninga terminTreninga : svi){
            LocalDate localDate = terminTreninga.getDatum().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();

            if (localDate.getDayOfWeek().toString().equalsIgnoreCase(dan)){
                filtirano.add(terminTreninga);
            }
        }
        return new ResponseEntity<>(filtirano,HttpStatus.OK);
    }


    @DeleteMapping("/otkaziTermin/{id}")
    @CheckSecurity(roles = {"KLIJENT","MENADZER"})
    public ResponseEntity<String> otkaziTermin(@PathVariable Long id){
        terminTreningaService.otkaziTermin(id);
        return new ResponseEntity<>("Termin " + id + " otkazan uspesno.", HttpStatus.OK);
    }

}
