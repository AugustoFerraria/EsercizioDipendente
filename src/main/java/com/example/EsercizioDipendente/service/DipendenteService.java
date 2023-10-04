package com.example.EsercizioDipendente.service;

import java.util.List;
import java.util.Map;

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
			throw new DipendenteNotFoundException("Dipendente con ID " + id + " non trovato");
		}
		updatedDipendente.setId(id);
		return dipendenteRepository.save(updatedDipendente);
	}

	public void removeDipendente(int id) {
		if (!dipendenteRepository.existsById(id)) {
			throw new DipendenteNotFoundException("Dipendente con ID " + id + " non trovato");
		}
		dipendenteRepository.deleteById(id);
	}

	public Dipendente addBonusMalus(int id, EMese month, double bonusMalus) {
		Dipendente dipendente = getDipendenteById(id);
		dipendente.getMonthlyBonusMalus().put(month, bonusMalus);
		return dipendenteRepository.save(dipendente);
	}

	public Dipendente getDipendenteWithHighestGrossStipendio() {
		List<Dipendente> dipendenti = getAllDipendenti();
		Dipendente highestSalaryDipendente = null;
		double highestSalary = Double.MIN_VALUE;

		for (Dipendente dipendente : dipendenti) {
			double totalSalary = dipendente.getStipendio();
			if (totalSalary > highestSalary) {
				highestSalary = totalSalary;
				highestSalaryDipendente = dipendente;
			}
		}

		return highestSalaryDipendente;
	}

	public Dipendente getDipendenteWithHighestNetStipendio() {
		List<Dipendente> dipendenti = getAllDipendenti();
		Dipendente highestSalaryDipendente = null;
		double highestNetSalary = Double.MIN_VALUE;

		for (Dipendente dipendente : dipendenti) {
			double totalSalary = dipendente.getStipendio();
			Map<EMese, Double> monthlyBonusMalus = dipendente.getMonthlyBonusMalus();

			for (Double bonusMalus : monthlyBonusMalus.values()) {
				totalSalary += bonusMalus;

			}
			if (totalSalary > highestNetSalary) {
				highestNetSalary = totalSalary;
				highestSalaryDipendente = dipendente;
			}
		}

		return highestSalaryDipendente;
	}

	public Dipendente updateBonusMalus(int id, Map<EMese, Double> bonusMalusMap) {
		Dipendente dipendente = dipendenteRepository.findById(id)
				.orElseThrow(() -> new DipendenteNotFoundException("ipendente con ID " + id + " non trovato"));

		Map<EMese, Double> currentBonusMalus = dipendente.getMonthlyBonusMalus();

		for (Map.Entry<EMese, Double> entry : bonusMalusMap.entrySet()) {
			EMese month = entry.getKey();
			Double bonusMalus = entry.getValue();

			currentBonusMalus.put(month, bonusMalus);
		}

		dipendente.setMonthlyBonusMalus(currentBonusMalus);

		return dipendenteRepository.save(dipendente);
	}

	public Dipendente getDipendenteWithHighestBonusMalus() {
		List<Dipendente> dipendenti = getAllDipendenti();
		Dipendente highestBonusMalusDipendente = null;
		double highestBonusMalus = Double.MIN_VALUE;

		for (Dipendente dipendente : dipendenti) {
			double totalBonusMalus = 0.0;
			Map<EMese, Double> monthlyBonusMalus = dipendente.getMonthlyBonusMalus();

			for (Double bonusMalus : monthlyBonusMalus.values()) {
				totalBonusMalus += bonusMalus;
			}

			if (totalBonusMalus > highestBonusMalus) {
				highestBonusMalus = totalBonusMalus;
				highestBonusMalusDipendente = dipendente;
			}
		}

		return highestBonusMalusDipendente;
	}

	public Dipendente getDipendenteById(int id) {
		return dipendenteRepository.findById(id)
				.orElseThrow(() -> new DipendenteNotFoundException("Dipendente con ID " + id + " non trovato"));
	}

}
