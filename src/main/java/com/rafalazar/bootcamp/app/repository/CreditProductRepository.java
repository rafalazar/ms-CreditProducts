package com.rafalazar.bootcamp.app.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.rafalazar.bootcamp.app.document.CreditProduct;

import reactor.core.publisher.Mono;

public interface CreditProductRepository extends ReactiveMongoRepository<CreditProduct, String>{
	
	@Query("{'numberAccount' : ?0}")
	public Mono<CreditProduct> findByNumberAccount(String numberAccount);
}
