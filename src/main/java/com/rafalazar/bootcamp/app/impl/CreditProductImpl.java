package com.rafalazar.bootcamp.app.impl;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rafalazar.bootcamp.app.client.ClienteClient;
import com.rafalazar.bootcamp.app.document.CreditProduct;
import com.rafalazar.bootcamp.app.dto.ClientDto;
import com.rafalazar.bootcamp.app.repository.CreditProductRepository;
import com.rafalazar.bootcamp.app.service.CreditProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditProductImpl implements CreditProductService{
	
	@Autowired
	private CreditProductRepository repo;
	
	@Autowired
	private ClienteClient client;

	@Override
	public Flux<CreditProduct> findAll() {
		return repo.findAll();
	}

	@Override
	public Mono<CreditProduct> findById(String id) {
		return repo.findById(id);
	}

	@Override
	public Mono<CreditProduct> save(CreditProduct cp) {
		//joinDate
		if(cp.getJoinDate() == null) {
			cp.setJoinDate(new Date());
		}else {
			cp.setJoinDate(cp.getJoinDate());
		}
		//expirationDate
		if(cp.getExpirationDate() == null) {
			cp.setExpirationDate(new Date());
		}else {
			cp.setExpirationDate(cp.getExpirationDate());
		}
		
		//Bank
		if(cp.getBank() == null) {
			cp.setBank("-");
		}else {
			cp.setBank(cp.getBank());
		}
		//TypeOwner
		if(cp.getTypeOwner() == null) {
			cp.setTypeOwner("-");
		}else {
			cp.setTypeOwner(cp.getTypeOwner());
		}
		//ProductName
		if(cp.getProductName() == null) {
			cp.setProductName("-");
		}else {
			cp.setProductName(cp.getProductName());
		}
		//NumberProduct
		if(cp.getNumberAccount() == null) {
			cp.setNumberAccount(UUID.randomUUID().toString());
		}else {
			cp.setNumberAccount(cp.getNumberAccount());
		}
		
		//DniOwner
		if(cp.getDniOwner() == null) {
			cp.setDniOwner("-");
		}else {
			cp.setDniOwner(cp.getDniOwner());
		}
		//CreditAmount
		if(cp.getCreditAmount() == null) {
			cp.setCreditAmount(0.0);
		}else {
			cp.setCreditAmount(cp.getCreditAmount());
		}
		//Balance
		if(cp.getBalance() == null) {
			cp.setBalance(cp.getCreditAmount());
		}else {
			cp.setBalance(cp.getBalance());
		}
		//Consume
		if(cp.getConsume() == null) {
			cp.setConsume(0.0);
		}else {
			cp.setConsume(cp.getConsume());
		}
		
		return repo.save(cp);
	}

	@Override
	public Mono<CreditProduct> update(CreditProduct cp, String id) {
		return repo.findById(id)
				.flatMap(c ->{
					//joinDate
					if(cp.getJoinDate() == null) {
						c.setJoinDate(c.getJoinDate());
					}else {
						c.setJoinDate(cp.getJoinDate());
					}
					//expirationDate
					if(cp.getExpirationDate() == null) {
						c.setExpirationDate(c.getExpirationDate());
					}else {
						c.setExpirationDate(cp.getExpirationDate());
					}
					
					//Bank
					if(cp.getBank() == null) {
						c.setBank(c.getBank());
					}else {
						c.setBank(cp.getBank());
					}
					//TypeOwner
					if(cp.getTypeOwner() == null) {
						c.setTypeOwner(c.getTypeOwner());
					}else {
						c.setTypeOwner(cp.getTypeOwner());
					}
					//ProductName
					if(cp.getProductName() == null) {
						c.setProductName(c.getProductName());
					}else {
						c.setProductName(cp.getProductName());
					}
					//NumberAccount
					if(cp.getNumberAccount() == null) {
						c.setNumberAccount(c.getNumberAccount());
					}else {
						c.setNumberAccount(UUID.randomUUID().toString());
					}
					//DniOwner
					if(cp.getDniOwner() == null) {
						c.setDniOwner(c.getDniOwner());
					}else {
						c.setDniOwner(cp.getDniOwner());
					}
					//CreditAmount
					if(cp.getCreditAmount() == null) {
						c.setCreditAmount(c.getCreditAmount());
					}else {
						c.setCreditAmount(cp.getCreditAmount());
					}
					//Balance
					if(cp.getBalance() == null) {
						c.setBalance(c.getBalance());
					}else {
						c.setBalance(cp.getBalance());
					}
					//Consume
					if(cp.getConsume() == null) {
						c.setConsume(c.getConsume());
					}else {
						c.setConsume(cp.getConsume());
					}
					
					return repo.save(c);
				});
	}

	@Override
	public Mono<Void> delete(CreditProduct cp) {
		return repo.delete(cp);
	}

	
	//--------------------------------------------->
	//Métodos del webClient
	
	@Override
	public Flux<ClientDto> findAllClients() {
		return client.findAllClients();
	}

	@Override
	public Mono<ClientDto> createById(String id) {
		return client.createById(id);
	}

	//------------------------------------->
	// Métodos propios
	@Override
	public Mono<CreditProduct> findByNumberAccount(String numberAccount) {
		return repo.findByNumberAccount(numberAccount);
	}
	
	@Override
	public Mono<CreditProduct> deposit(Double amount, String id) {
		return repo.findById(id)
				.flatMap(c -> {
					if((c.getBalance()+amount) > c.getCreditAmount()) {
						return Mono.error(new InterruptedException("No puede superar su crédito"));
					}else {
						c.setBalance(c.getBalance()+amount);
						c.setConsume(c.getConsume()-amount);
						
						return repo.save(c);
					}
				});
	}

	@Override
	public Mono<CreditProduct> retiro(Double amount, String id) {
		return repo.findById(id)
				.flatMap(c -> {
					if(amount > c.getCreditAmount()) {
						return Mono.error(new InterruptedException("No puede retirar monto superior a su crédito"));
					}else if(amount > c.getBalance()) {
						return Mono.error(new InterruptedException("No puede retirar más dinero."));
					}else {
						c.setBalance(c.getBalance() - amount);
						c.setConsume(c.getConsume() + amount);
						
						return repo.save(c);
					}
				});
	}

}
