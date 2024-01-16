package example.controller;

import antlr.Token;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.domen.TerminTreninga;
import example.domen.ZakazaniTermin;
import example.dto.FiskulturnaSalaDTO;
import example.dto.TerminTreningaDTO;
import example.dto.ViseTerminaTreninga;
import example.mapper.TerminTreningaMapper;
import example.security.CheckSecurity;
import example.security.service.TokenService;
import example.service.TerminTreningaService;
import example.service.ZakazaniTerminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/termin-treninga")
public class TerminTreningaController {
    private final TerminTreningaService terminTreningaService;
    private final ZakazaniTerminService zakazaniTerminService;
    private TokenService tokenService;

    public TerminTreningaController(TerminTreningaService terminTreningaService, ZakazaniTerminService zakazaniTerminService, TokenService tokenService) {
        this.terminTreningaService = terminTreningaService;
        this.tokenService = tokenService;
        this.zakazaniTerminService = zakazaniTerminService;
    }

    @PostMapping("/zakazi-termin")
    @CheckSecurity(roles = {"KLIJENT"})
    public ResponseEntity<ZakazaniTermin> zakaziTermin(@RequestBody String jsonRequestBody){

        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = null;
        int klijentId = 0;
        TerminTreningaDTO terminTreninga = null;
        try {
            jsonNode = mapper.readTree(jsonRequestBody);
            klijentId = jsonNode.get("klijentID").asInt();
            terminTreninga = mapper.readValue(jsonRequestBody, TerminTreningaDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


        ZakazaniTermin zakazaniTermin = zakazaniTerminService.zakaziTermin(terminTreninga,klijentId);

        return new ResponseEntity<>(zakazaniTermin,HttpStatus.OK);
    }

    @GetMapping("/izlistaj-Termine")
    public ResponseEntity<List<TerminTreninga>> izlistajTermine(@RequestHeader("Authorization") String authorization){
        List<TerminTreninga> listaTermina = terminTreningaService.dohvatiSveTermine();
        return new ResponseEntity<>(listaTermina,HttpStatus.OK);
    }

    @GetMapping("/izlistaj-slobodne-termine")
    public ResponseEntity<List<TerminTreninga>> izlistajSlobodneTermine(@RequestHeader("Authorization") String authorization){
        List<TerminTreninga> listaTermina = terminTreningaService.dohvatiSveTermine();
        List<TerminTreninga> slobodniTermini = new ArrayList<>();
        List<ZakazaniTermin> zakazaniTermini = zakazaniTerminService.dohvatiZakazaneTreninge();
        Long id = tokenService.parseId(authorization);

        for (TerminTreninga terminTreninga : listaTermina){
            boolean slobodan = true;
            for (ZakazaniTermin zakazaniTermin : zakazaniTermini){
                if (zakazaniTermin.getTerminTreninga().getId() == terminTreninga.getId()){
                    slobodan = false;
                }
            }
            if (slobodan){
                slobodniTermini.add(terminTreninga);
            }
        }
        return new ResponseEntity<>(slobodniTermini,HttpStatus.OK);
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

    @GetMapping("/izlistaj-Termine-Korisnika")
    public ResponseEntity<List<TerminTreninga>> izlistajTermineKorisnika(@RequestHeader("Authorization") String authorization){

        List<ZakazaniTermin> zakazaniTermini = zakazaniTerminService.dohvatiZakazaneTreninge();

        List<TerminTreninga> termini = new ArrayList<>();
        Long id = tokenService.parseId(authorization);

        List<TerminTreninga> sviTermini = terminTreningaService.dohvatiSveTermine();

        for (ZakazaniTermin zakazaniTermin : zakazaniTermini){
            if (zakazaniTermin.getKlijentId() == id.intValue()) {
                for (TerminTreninga terminTreninga : sviTermini) {
                    if (zakazaniTermin.getTerminTreninga().getId() == terminTreninga.getId()) {
                        termini.add(terminTreninga);
                    }
                }
            }
        }

        return new ResponseEntity<>(termini,HttpStatus.OK);
    }

    @CheckSecurity(roles = {"ADMIN","MENADZER"})
    @GetMapping("/izlistaj-Termine-sve")
    public ResponseEntity<List<TerminTreninga>> izlistajTermineSve(@RequestHeader("Authorization") String authorization){
        List<ZakazaniTermin> zakazaniTermini = zakazaniTerminService.dohvatiZakazaneTreninge();
        List<TerminTreninga> termini = new ArrayList<>();

        List<TerminTreninga> sviTermini = terminTreningaService.dohvatiSveTermine();

        for (ZakazaniTermin zakazaniTermin : zakazaniTermini){
                for (TerminTreninga terminTreninga : sviTermini) {
                    if(zakazaniTermin.getTerminTreninga().getId() == terminTreninga.getId()){
                        termini.add(terminTreninga);
                }
            }
        }
        return new ResponseEntity<>(termini,HttpStatus.OK);
    }
}
