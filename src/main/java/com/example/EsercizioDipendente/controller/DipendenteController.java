package com.example.EsercizioDipendente.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
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

	@PostMapping("/{id}/bonus-malus")
	public ResponseEntity<Dipendente> addBonusMalus(@PathVariable int id, @RequestParam EMese month,
			@RequestParam Double bonusMalus) {
		Dipendente updated = dipendenteService.addBonusMalus(id, month, bonusMalus);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Dipendente> getDipendenteById(@PathVariable int id) {
		Dipendente dipendente = dipendenteService.getDipendenteById(id);
		return new ResponseEntity<>(dipendente, HttpStatus.OK);
	}

	@GetMapping("/highest-salary")
	public ResponseEntity<Dipendente> getDipendenteWithHighestSalary() {
		Dipendente dipendente = dipendenteService.getDipendenteWithHighestSalary();
		return new ResponseEntity<>(dipendente, HttpStatus.OK);
	}

	@GetMapping("/highest-bonus-malus")
	public ResponseEntity<Dipendente> getDipendenteWithHighestBonusMalus() {
		Dipendente dipendente = dipendenteService.getDipendenteWithHighestBonusMalus();
		return new ResponseEntity<>(dipendente, HttpStatus.OK);
	}
}
