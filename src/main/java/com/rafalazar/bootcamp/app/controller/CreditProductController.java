package com.rafalazar.bootcamp.app.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rafalazar.bootcamp.app.document.CreditProduct;
import com.rafalazar.bootcamp.app.service.CreditProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/creditProduct")
public class CreditProductController {
	
	@Autowired
	private CreditProductService service;
	
	//LISTAR CLIENTE
	@GetMapping("/findAll")
	public Flux<CreditProduct> findAll(){
		return service.findAll();
	}
	
	//LISTAR POR ID
	@GetMapping("/findById/{id}")
	public Mono<CreditProduct> findById(@PathVariable("id") String id){
		return service.findById(id);
	}
	
	//CREAR UN PRODUCTO DE CRÉDITO
	@PostMapping("/create")
	public Mono<ResponseEntity<CreditProduct>> create(@RequestBody CreditProduct cp){
		return service.save(cp).map(c -> ResponseEntity.created(URI.create("/creditProduct/".concat(c.getId())))
				.contentType(MediaType.APPLICATION_JSON).body(c));
	}
	
	//ACTUALIZAR PRODUCTO DE CRÉDITO
	@PutMapping("/update/{id}")
	public Mono<ResponseEntity<CreditProduct>> update(@PathVariable("id") String id, @RequestBody CreditProduct cp){
		return service.update(cp, id)
				.map(c -> ResponseEntity.created(URI.create("/creditProduct/".concat(c.getId())))
						.contentType(MediaType.APPLICATION_JSON).body(c))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	//ELIMINAR
	@DeleteMapping("/delete/{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable String id){
		return service.findById(id).flatMap(c -> {
			return service.delete(c).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}

}
