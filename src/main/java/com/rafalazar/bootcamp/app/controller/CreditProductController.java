package com.rafalazar.bootcamp.app.controller;

import java.net.URI;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.rafalazar.bootcamp.app.dto.ClientDto;
import com.rafalazar.bootcamp.app.service.CreditProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/creditProduct")
public class CreditProductController {
	
	private static final Logger log = LoggerFactory.getLogger(CreditProductController.class);
	
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
	
	//---------------------------------------->>>>>>>>>>>
	// Métodos de webClient
	
	@GetMapping("/findAllClients")
	Flux<ClientDto> findAllClients() {
		return service.findAllClients();
	}
	
	@GetMapping("/createById/{id}")
	Mono<CreditProduct> createById(@PathVariable("id") String id,@RequestBody CreditProduct cp){
		if(cp.getJoinDate() == null) {
			cp.setJoinDate(new Date());
		}else {
			cp.setJoinDate(cp.getJoinDate());
		}
		
		if(cp.getExpirationDate() == null) {
			cp.setExpirationDate(new Date());
		}else {
			cp.setExpirationDate(cp.getExpirationDate());
		}
		
		return service.createById(id)
				.flatMap(c -> {
					//Bank
					cp.setBank(c.getBank());
					//typeOwner
					cp.setTypeOwner(c.getType());
					//productName
					cp.setProductName(cp.getProductName());
					//numberAccount
					cp.setNumberAccount(UUID.randomUUID().toString());
					//dniOwner
					cp.setDniOwner(c.getNumDoc());
					//creditAmount
					if(cp.getCreditAmount() == null) {
						cp.setCreditAmount(0.0);
					}else {
						cp.setCreditAmount(cp.getCreditAmount());
					}
					
					//balance
					if(cp.getBalance() == null) {
						cp.setBalance(cp.getCreditAmount());
					}else {
						cp.setBalance(cp.getBalance());
					}
					
					//consume
					if(cp.getConsume() == null) {
						cp.setConsume(0.0);
					}else {
						cp.setConsume(cp.getConsume());
					}
					
					return service.save(cp);
					
				});
	}
	
	//------------------------------------------------------->
	// ------------ Métodos propios ----------------------//
	
	//Buscar por número de cuenta
	@GetMapping("/findByNumAccount/{numberAccount}")
	public Mono<ResponseEntity<CreditProduct>> findByNumberAccount(@PathVariable("numberAccount") String numberAccount){
		return service.findByNumberAccount(numberAccount)
				.map(c -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(c))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	//Depositar
	@PutMapping("/depositC/{amount}/{numberAccount}")
	public Mono<CreditProduct> depositC(@PathVariable String amount, @PathVariable("numberAccount") String numberAccount){
		log.info("entró!");
		return service.depositC(Double.parseDouble(amount), numberAccount);
	}
	
	//Retirar
	@PutMapping("/retiroC/{amount}/{numberAccount}")
	public Mono<CreditProduct> retiroC(@PathVariable String amount, @PathVariable("numberAccount") String numberAccount){
		log.info("entró!");
		return service.retiroC(Double.parseDouble(amount), numberAccount);
	}
	
}
