package com.example.EsercizioDipendente;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.EsercizioDipendente.entity.Dipendente;
import com.example.EsercizioDipendente.entity.EMese;
import com.example.EsercizioDipendente.repository.DipendenteRepository;
import com.example.EsercizioDipendente.service.DipendenteService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EsercizioDipendenteApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DipendenteRepository dipendenteRepository;

    @Autowired
    private DipendenteService dipendenteService;

    @BeforeEach
    public void setup() {
        // Cancella il database o inizializzalo come necessario prima di ogni test
        dipendenteRepository.deleteAll();
    }

    @Test
    public void testCreateDipendente() {
        // Crea un nuovo oggetto Dipendente per il test
        Dipendente dipendente = new Dipendente("John", "Doe", 50000.0);

        // Invia una richiesta POST per creare il Dipendente
        ResponseEntity<Dipendente> responseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/api/dipendente", dipendente, Dipendente.class);

        // Verifica se la richiesta è stata completata con successo (codice di stato HTTP 201 Created)
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // Verifica se il Dipendente è stato creato e restituito nella risposta
        Dipendente createdDipendente = responseEntity.getBody();
        assertNotNull(createdDipendente);
        assertEquals("John", createdDipendente.getCognome());
        assertEquals("Doe", createdDipendente.getCognome());
        assertEquals(50000.0, createdDipendente.getStipendio());

        // Verifica che il Dipendente esista nel database
        Dipendente retrievedDipendente = dipendenteService.getDipendenteById(createdDipendente.getId());
        assertNotNull(retrievedDipendente);
    }

    @Test
    public void testDeleteDipendente() {
        // Crea un Dipendente per il test
        Dipendente dipendente = new Dipendente("Jane", "Smith", 60000.0);
        dipendenteRepository.save(dipendente);

        // Invia una richiesta DELETE per eliminare il Dipendente per ID
        restTemplate.delete("http://localhost:" + port + "/api/dipendente/" + dipendente.getId());

        // Verifica che il Dipendente sia stato eliminato dal database
        Dipendente deletedDipendente = dipendenteRepository.findById(dipendente.getId()).orElse(null);
        assertNull(deletedDipendente);
    }

    @Test
    public void testGetDipendenteWithHighestBonusMalus() {
        // Crea oggetti Dipendente con valori bonus/malus diversi
        Dipendente dipendente1 = new Dipendente( "Alice", "Johnson", 55000.0);
        dipendenteRepository.save(dipendente1);

        Dipendente dipendente2 = new Dipendente("Bob", "Smith", 60000.0);
        dipendenteRepository.save(dipendente2);

        // Aggiungi bonus/malus ai Dipendenti utilizzando il servizio
        dipendenteService.addBonusMalus(dipendente1.getId(), EMese.GENNAIO, 1000.0);
        dipendenteService.addBonusMalus(dipendente1.getId(), EMese.FEBBRAIO, 1500.0);
        dipendenteService.addBonusMalus(dipendente2.getId(), EMese.GENNAIO, 2000.0);
        dipendenteService.addBonusMalus(dipendente2.getId(), EMese.FEBBRAIO, 2500.0);

        // Invia una richiesta GET per recuperare il Dipendente con il bonus/malus più alto
        ResponseEntity<Dipendente> responseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/api/dipendente/highest-bonus-malus", Dipendente.class);

        // Verifica se la richiesta è stata completata con successo (codice di stato HTTP 200 OK)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verifica se il Dipendente con il bonus/malus più alto è stato recuperato
        Dipendente highestBonusMalusDipendente = responseEntity.getBody();
        assertNotNull(highestBonusMalusDipendente);

        // Verifica che sia il Dipendente atteso (ad esempio, dipendente2 in questo caso)
        assertEquals("Bob", highestBonusMalusDipendente.getNome());
        assertEquals("Smith", highestBonusMalusDipendente.getCognome());
    }

    // Aggiungi ulteriori metodi di test per altri scenari

}
