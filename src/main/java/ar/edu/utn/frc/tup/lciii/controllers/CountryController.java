package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.clients.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@RestController()
@RequestMapping("/api")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;
    @GetMapping("/countries")
    public ResponseEntity<List<Country>> getAll(){
        return ResponseEntity.ok(countryService.getAllCountries());
    }
    @GetMapping("/countries/name/{name}")
    public ResponseEntity<Country> getByName(@RequestParam(required = false) String name, @RequestParam(required = false) String code ){
        if(!code.isEmpty()) {
            return ResponseEntity.ok(countryService.getByCode(code));
        }
        if(!name.isEmpty()){
            return ResponseEntity.ok(countryService.getByName(name));
        }
        return null;
    }
    @GetMapping("/countries/continent/{continent}")
    public ResponseEntity<List<Country>> getByContinent(@RequestParam String continent){
        return ResponseEntity.ok(countryService.getByContinent(continent));
    }
    @GetMapping("/countries/language/{language}")
    public ResponseEntity<List<Country>> getByLanguage(@RequestParam String language){
        return ResponseEntity.ok(countryService.getByLanguage(language));
    }
    @GetMapping("/countries/most-borders")
    public ResponseEntity<Country> getByMostBorders(){
        return ResponseEntity.ok(null);
    }
    @PostMapping("/countries")
    public ResponseEntity<List<CountryDTO>> postCountry(@RequestParam Integer amountOfCountryToSave) {
        if(amountOfCountryToSave > 10) {
            return null;
        }
        return ResponseEntity.ok(countryService.post(amountOfCountryToSave));
    }

}