package com.rafalazar.bootcamp.app.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.rafalazar.bootcamp.app.document.CreditProduct;

public interface CreditProductRepository extends ReactiveMongoRepository<CreditProduct, String>{
	
}
