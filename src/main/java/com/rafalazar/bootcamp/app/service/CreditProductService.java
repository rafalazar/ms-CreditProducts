package com.rafalazar.bootcamp.app.service;

import com.rafalazar.bootcamp.app.document.CreditProduct;
import com.rafalazar.bootcamp.app.dto.ClientDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditProductService {
	
	//LISTAR TODOS
	public Flux<CreditProduct> findAll();
	//LISTAR POR ID
	public Mono<CreditProduct> findById(String id);
	//CREAR
	public Mono<CreditProduct> save(CreditProduct cp);
	//ACTUALIZAR
	public Mono<CreditProduct> update(CreditProduct cp, String id);
	//ELIMINAR
	public Mono<Void> delete(CreditProduct cp);
	
	//------------------->
	//Métodos propios
	public Mono<CreditProduct> findByNumberAccount(String numberAccount);
	
	public Mono<CreditProduct> depositC(Double amount, String numberAccount); //Experimental
	
	public Mono<CreditProduct> retiroC(Double amount, String numberAccount); //Experimental
	
	
	//------------------->
	//Métodos del webClient
	
	public Flux<ClientDto> findAllClients();
	
	public Mono<ClientDto> createById(String id);
	
}
