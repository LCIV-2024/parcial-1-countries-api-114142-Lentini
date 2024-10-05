package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.clients.CountryDTO;
import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

        private final CountryRepository countryRepository;

        private final RestTemplate restTemplate = new RestTemplate();

        public List<Country> getAllCountries() {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                return response.stream().map(this::mapToCountry).collect(Collectors.toList());
        }
        public Country getByName(String name) {
                String url = "https://restcountries.com/v3.1/name/" + name;
                Country response = restTemplate.getForObject(url, Country.class);
                return response;
        }
        public Country getByCode(String code) {
                String url = "https://restcountries.com/v3.1/alpha/" + code;
                Country response = restTemplate.getForObject(url, Country.class);
                return response;
        }
        public List<Country> getByContinent(String continent) {
                String url = "https://restcountries.com/v3.1/region/" + continent;
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                return response.stream().map(this::mapToCountry).collect(Collectors.toList());
        }
        public List<Country> getByLanguage(String language) {
                String url = "https://restcountries.com/v3.1/lang/" + language;
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                return response.stream().map(this::mapToCountry).collect(Collectors.toList());
        }
        public List<CountryDTO> post(Integer amountOfCountryToSave) {
                List<Country> countries = getAllCountries();
                List<Country> selectedCountries = new ArrayList<Country>();

                for(int i = 0; i < amountOfCountryToSave; i++){
                        selectedCountries.add(countries.get(i));
                }
                Collections.shuffle(selectedCountries);
                List<CountryDTO> countryDTOS = new ArrayList<>();
                for(Country country : selectedCountries) {
                        CountryEntity countryEntity = new CountryEntity();
                        countryEntity.setName(country.getName());
                        countryEntity.setCode(country.getCode());
                        countryEntity.setPopulation(country.getPopulation());
                        countryEntity.setArea(Double.toString(country.getArea()));
                        countryRepository.save(countryEntity);
                        countryDTOS.add(mapToDTO(country));
                }
                return countryDTOS;
        }

        /**
         * Agregar mapeo de campo cca3 (String)
         * Agregar mapeo campos borders ((List<String>))
         */
        private Country mapToCountry(Map<String, Object> countryData) {
                Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
                return Country.builder()
                        .name((String) nameData.get("common"))
                        .population(((Number) countryData.get("population")).longValue())
                        .area(((Number) countryData.get("area")).doubleValue())
                        .region((String) countryData.get("region"))
                        .languages((Map<String, String>) countryData.get("languages"))
                        .code((String) countryData.get("cca3"))
                        .borders((List<String>) countryData.get("border"))
                        .build();
        }


        private CountryDTO mapToDTO(Country country) {
                return new CountryDTO(country.getCode(), country.getName());
        }
}