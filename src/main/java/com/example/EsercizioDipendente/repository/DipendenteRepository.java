package com.example.EsercizioDipendente.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.EsercizioDipendente.entity.Dipendente;

public interface DipendenteRepository extends MongoRepository<Dipendente, Integer> {
}
