package com.example.EsercizioDipendente.eccezioni;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)

public class DipendenteNotFoundException extends RuntimeException {

	public DipendenteNotFoundException(String message) {
		super(message);
	}
}
