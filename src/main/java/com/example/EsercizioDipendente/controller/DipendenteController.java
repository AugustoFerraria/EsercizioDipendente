package com.example.EsercizioDipendente.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.EsercizioDipendente.entity.Dipendente;
import com.example.EsercizioDipendente.entity.EMese;
import com.example.EsercizioDipendente.service.DipendenteService;

@RestController
@RequestMapping("/api/dipendente")
public class DipendenteController {

	@Autowired
	private DipendenteService dipendenteService;

	@GetMapping
	public ResponseEntity<List<Dipendente>> getAllDipendenti() {
		List<Dipendente> dipendenti = dipendenteService.getAllDipendenti();
		return new ResponseEntity<>(dipendenti, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Dipendente> createDipendente(@RequestBody Dipendente dipendente) {
		Dipendente savedDipendente = dipendenteService.createDipendente(dipendente);
		return new ResponseEntity<>(savedDipendente, HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> removeDipendente(@PathVariable int id) {
		dipendenteService.removeDipendente(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("/{id}/bonusMalus")
	public ResponseEntity<Dipendente> updateBonusMalus(@PathVariable int id, @RequestBody Map<EMese, Double> bonusMalusMap) {
	    Dipendente updated = dipendenteService.updateBonusMalus(id, bonusMalusMap);
	    return new ResponseEntity<>(updated, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Dipendente> getDipendenteById(@PathVariable int id) {
		Dipendente dipendente = dipendenteService.getDipendenteById(id);
		return new ResponseEntity<>(dipendente, HttpStatus.OK);
	}

	@GetMapping("/stipendioPiuAlto")
	public ResponseEntity<Dipendente> getDipendenteWithHighestStipendio() {
		Dipendente dipendente = dipendenteService.getDipendenteWithHighestGrossStipendio();
		return new ResponseEntity<>(dipendente, HttpStatus.OK);
	}

	@GetMapping("/stipendioNetPiuAlto")
	public ResponseEntity<Dipendente> getDipendenteWithHighestNetStipendio() {
		Dipendente dipendente = dipendenteService.getDipendenteWithHighestNetStipendio();
		return new ResponseEntity<>(dipendente, HttpStatus.OK);
	}
	
	@GetMapping("/bonusMalusPiuAlto")
	public ResponseEntity<Dipendente> getDipendenteWithHighestBonusMalus() {
		Dipendente dipendente = dipendenteService.getDipendenteWithHighestBonusMalus();
		return new ResponseEntity<>(dipendente, HttpStatus.OK);
	}
}
