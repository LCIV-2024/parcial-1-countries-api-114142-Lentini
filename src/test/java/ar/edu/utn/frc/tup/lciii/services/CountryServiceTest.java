package ar.edu.utn.frc.tup.lciii.services;

import ar.edu.utn.frc.tup.lciii.clients.CountryDTO;
import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CountryServiceTest {
    @Autowired
    private CountryService countryService;
    @Mock
    private CountryRepository countryRepository;
    @Test
    void getAllTest(){
        List<Country> response = countryService.getAllCountries();
        assertNotNull(response);
    }
    @Disabled
    @Test
    void getByNameTest() {
        Country country = countryService.getByName("Argentina");
        assertEquals("Arg", country.getCode());
    }
    @Test
    void getByContinentTest() {
        List<Country> response = countryService.getByContinent("Europe");
        assertNotNull(response);
    }
    @Test
    void getByLanguageTest() {
        List<Country> response = countryService.getByLanguage("spanish");
        assertNotNull(response);
    }
    @Test
    void postCountry(){
        CountryEntity countryEntity = new CountryEntity(Long.valueOf("2"),"Argentina","Arg",Long.valueOf("2"),"America");
        when(countryRepository.save(countryEntity)).thenReturn(countryEntity);
        List<CountryDTO> countryDTOS = countryService.post(1);
        assertEquals(1,countryDTOS.size());
    }

}
