package com.example.EsercizioDipendente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.EsercizioDipendente.eccezioni.DipendenteNotFoundException;
import com.example.EsercizioDipendente.entity.Dipendente;
import com.example.EsercizioDipendente.entity.EMese;
import com.example.EsercizioDipendente.repository.DipendenteRepository;

@Service
public class DipendenteService {

	@Autowired
	private DipendenteRepository dipendenteRepository;

	public List<Dipendente> getAllDipendenti() {
		return dipendenteRepository.findAll();
	}

	public Dipendente createDipendente(Dipendente dipendente) {
		return dipendenteRepository.save(dipendente);
	}

	public Dipendente updateDipendente(int id, Dipendente updatedDipendente) {
		if (!dipendenteRepository.existsById(id)) {
			throw new DipendenteNotFoundException("Dipendente not found with ID: " + id);
		}
		updatedDipendente.setId(id);
		return dipendenteRepository.save(updatedDipendente);
	}

	public void removeDipendente(int id) {
		if (!dipendenteRepository.existsById(id)) {
			throw new DipendenteNotFoundException("Dipendente not found with ID: " + id);
		}
		dipendenteRepository.deleteById(id);
	}

	public Dipendente addBonusMalus(int id, EMese month, double bonusMalus) {
		Dipendente dipendente = getDipendenteById(id);
		dipendente.getMonthlyBonusMalus().put(month, bonusMalus);
		return dipendenteRepository.save(dipendente);
	}

	public Dipendente getDipendenteWithHighestSalary() {
		List<Dipendente> dipendenti = getAllDipendenti();
		Dipendente highestSalaryDipendente = null;
		double highestSalary = Double.MIN_VALUE;

		for (Dipendente dipendente : dipendenti) {
			double totalSalary = dipendente.getStipendio() + calculateTotalBonusMalus(dipendente);
			if (totalSalary > highestSalary) {
				highestSalary = totalSalary;
				highestSalaryDipendente = dipendente;
			}
		}

		return highestSalaryDipendente;
	}

	public Dipendente getDipendenteWithHighestBonusMalus() {
		List<Dipendente> dipendenti = getAllDipendenti();
		Dipendente highestBonusMalusDipendente = null;
		double highestBonusMalus = Double.MIN_VALUE;

		for (Dipendente dipendente : dipendenti) {
			double totalBonusMalus = calculateTotalBonusMalus(dipendente);
			if (totalBonusMalus > highestBonusMalus) {
				highestBonusMalus = totalBonusMalus;
				highestBonusMalusDipendente = dipendente;
			}
		}

		return highestBonusMalusDipendente;
	}

	public Dipendente getDipendenteById(int id) {
		return dipendenteRepository.findById(id)
				.orElseThrow(() -> new DipendenteNotFoundException("Dipendente not found with ID: " + id));
	}

	private double calculateTotalBonusMalus(Dipendente dipendente) {
		double totalBonusMalus = 0.0;
		for (Double bonusMalus : dipendente.getMonthlyBonusMalus().values()) {
			totalBonusMalus += bonusMalus;
		}
		return totalBonusMalus;
	}
}
