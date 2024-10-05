package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.clients.CountryDTO;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CountryControllerTest {
    @Mock
    private CountryService countryService;
    @InjectMocks
    private CountryController countryController;
    @Test
    void getAllTest(){
        List<Country> countries = new ArrayList<>();
        Country country = new Country();
        country.setName("Argentina");
        country.setCode("Arg");
        countries.add(country);
        when(countryService.getAllCountries()).thenReturn(countries);
        ResponseEntity<List<Country>> response = countryController.getAll();
        assertNotNull(response.getBody());
        assertEquals(countries.size(), response.getBody().size());
    }
    @Test
    void getByContinentTest() {
        List<Country> countries = new ArrayList<>();
        Country country = new Country();
        country.setName("Finland");
        country.setCode("Fin");
        Country country1 = new Country();
        country.setName("Iceland");
        country.setCode("Ice");
        countries.add(country);
        when(countryService.getByContinent("Europe")).thenReturn(countries);
        ResponseEntity<List<Country>> response = countryController.getByContinent("Europe");
        assertNotNull(response.getBody());
        assertEquals(countries.size(), response.getBody().size());
    }
    @Test
    void getByLanguage(){
        List<Country> countries = new ArrayList<>();
        Country country = new Country();
        country.setName("Argentina");
        country.setCode("Arg");
        Country country1 = new Country();
        country.setName("Uruguay");
        country.setCode("Uru");
        countries.add(country);
        when(countryService.getByLanguage("spanish")).thenReturn(countries);
        ResponseEntity<List<Country>> response = countryController.getByLanguage("spanish");
        assertNotNull(response.getBody());
        assertEquals(countries.size(), response.getBody().size());
    }
    @Test
    void postFailTest() {
        ResponseEntity<List<CountryDTO>> response = countryController.postCountry(11);
        assertNull(response);
    }
    @Test
    void postSuccessTest() {
        List<CountryDTO> countries = new ArrayList<>();
        CountryDTO country = new CountryDTO("Argentina","Arg");
        countries.add(country);
        when(countryService.post(1)).thenReturn(countries);
        ResponseEntity<List<CountryDTO>> response = countryController.postCountry(1);
        assertNotNull(response.getBody());
        assertEquals("Argentina",response.getBody().get(0).name());

    }
}
