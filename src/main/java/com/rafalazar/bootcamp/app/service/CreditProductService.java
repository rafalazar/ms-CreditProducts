package com.rafalazar.bootcamp.app.service;

import com.rafalazar.bootcamp.app.document.CreditProduct;

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
	
}
