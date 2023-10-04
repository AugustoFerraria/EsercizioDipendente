package com.example.EsercizioDipendente.entity;

import java.util.EnumMap;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "dipendenti")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dipendente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    
    private int id;
    private String nome;
    private String cognome;
    private double stipendio;
    private Map<EMese, Double> monthlyBonusMalus = new EnumMap<>(EMese.class);
	
    
    public Dipendente (String nome, String cognome, double stipendio) {
		this.nome = nome;
		this.cognome = cognome;
		this.stipendio = stipendio;
	}


}
