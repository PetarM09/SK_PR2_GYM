package example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import example.domen.TerminTreninga;
import example.domen.ZakazaniTermin;
import example.dto.TerminTreningaDTO;
import example.dto.ZakazaniTerminDTO;
import example.mapper.ZakazaniTerminMapper;
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
    public ResponseEntity<ZakazaniTerminDTO> zakaziTermin(@RequestHeader("Authorization") String authorization, @RequestBody String jsonRequestBody) {
        ObjectMapper mapper = new ObjectMapper();

        long klijentId = 0;
        TerminTreningaDTO terminTreninga = null;
        try {
            klijentId = tokenService.parseId(authorization);
            terminTreninga = mapper.readValue(jsonRequestBody, TerminTreningaDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (terminTreninga.getBrojUcesnika() + 1 <= terminTreninga.getMaksimalanBrojUcesnika()) {
            ZakazaniTermin zakazaniTermin = zakazaniTerminService.zakaziTermin(terminTreninga, klijentId);
            terminTreningaService.povecajBrojUcesnika(terminTreninga);
            terminTreningaService.povecajKlijentuTreninge(klijentId);
            ZakazaniTerminDTO zakazaniTerminDTO = ZakazaniTerminMapper.toDTO(zakazaniTermin);
            return new ResponseEntity<>(zakazaniTerminDTO, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(null, HttpStatus.valueOf("Maksimalan broj je dostignut"));
        }
    }
    @PostMapping("/otkaziTermin")
    @CheckSecurity(roles = {"KLIJENT","MENADZER"})
    public ResponseEntity<String> otkaziZakazaniTermin(@RequestBody String jsonRequestBody, @RequestHeader("Authorization") String authorization){
        ZakazaniTerminDTO zakazaniTerminDTO = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            zakazaniTerminDTO = mapper.readValue(jsonRequestBody, ZakazaniTerminDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        zakazaniTerminService.otkaziZakazaniTermin(zakazaniTerminDTO);
        return new ResponseEntity<>("Termin " + zakazaniTerminDTO.getId() + " otkazan uspesno.", HttpStatus.OK);
    }

    @PostMapping("/dodaj-termin")
    public ResponseEntity<TerminTreninga> dodajTermin(@RequestBody String jsonRequestBody) {

        ObjectMapper mapper = new ObjectMapper();

        JsonNode jsonNode = null;
        TerminTreningaDTO terminTreningaDTO = null;
        try {
            jsonNode = mapper.readTree(jsonRequestBody);
            terminTreningaDTO = mapper.readValue(jsonRequestBody, TerminTreningaDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        TerminTreninga terminTreninga = terminTreningaService.dodajTermin(terminTreningaDTO);
        return new ResponseEntity<>(terminTreninga, HttpStatus.OK);

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
            if(terminTreninga.getMaksimalanBrojUcesnika() <= terminTreninga.getBrojUcesnika()){
                continue;
            }
            slobodniTermini.add(terminTreninga);
            System.out.println(terminTreninga);
        }
        return new ResponseEntity<>(slobodniTermini,HttpStatus.OK);
    }

    @GetMapping("/filtirajPoIndividualni-Grupni")
    public ResponseEntity<List<TerminTreninga>> filtirajPoIndividualniGrupni(@RequestBody String tip){
        List<TerminTreninga> svi = terminTreningaService.dohvatiSveTermine();
        List<TerminTreninga> filtirano = new ArrayList<>();
        for (TerminTreninga terminTreninga : svi){
            if (terminTreninga.getTipTreninga().getTip().equalsIgnoreCase(tip)
                && terminTreninga.getMaksimalanBrojUcesnika() >= terminTreninga.getBrojUcesnika()){
                filtirano.add(terminTreninga);
            }
        }
        return new ResponseEntity<>(filtirano,HttpStatus.OK);
    }

    @GetMapping("/filtrirajPoNazivu")
    public ResponseEntity<List<TerminTreninga>> filtrirajPoTipu(@RequestBody String tip){
        List<TerminTreninga> svi = terminTreningaService.dohvatiSveTermine();
        List<TerminTreninga> filtirano = new ArrayList<>();
        for (TerminTreninga terminTreninga : svi){
            if (terminTreninga.getTipTreninga().getNaziv().equalsIgnoreCase(tip)
                && terminTreninga.getMaksimalanBrojUcesnika() >= terminTreninga.getBrojUcesnika()){
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
            System.out.println(localDate.getDayOfWeek().toString());
            System.out.println(dan);
            if (localDate.getDayOfWeek().toString().equalsIgnoreCase(dan)
            && terminTreninga.getMaksimalanBrojUcesnika() >= terminTreninga.getBrojUcesnika()){
                filtirano.add(terminTreninga);
            }
        }
        return new ResponseEntity<>(filtirano,HttpStatus.OK);
    }


    @GetMapping("/izlistaj-Termine-Korisnika")
    public ResponseEntity<List<ZakazaniTermin>> izlistajTermineKorisnika(@RequestHeader("Authorization") String authorization){

        List<ZakazaniTermin> zakazaniTermini = zakazaniTerminService.dohvatiZakazaneTreninge();
        List<ZakazaniTermin> prikazi = new ArrayList<>();
        Long id = tokenService.parseId(authorization);
        for(ZakazaniTermin zakazaniTermin : zakazaniTermini){
            if(zakazaniTermin.getKlijentId() == id.intValue()){
                prikazi.add(zakazaniTermin);
            }
        }

        return new ResponseEntity<>(prikazi,HttpStatus.OK);
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
