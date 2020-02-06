package com.rafalazar.bootcamp.app;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.rafalazar.bootcamp.app.document.CreditProduct;
import com.rafalazar.bootcamp.app.service.CreditProductService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreditServiceTest {
	
	@Autowired
	private WebTestClient client;
	
	@Autowired
	private CreditProductService service; 

	@Test
	void findAllCreditClients() {
		client.get()
		.uri("/creditProduct/findAll")
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBodyList(CreditProduct.class)
		.consumeWith(res -> {
			List<CreditProduct> credit = res.getResponseBody();
			credit.forEach(cp -> {
				System.out.println(cp.getProductName() + " - " + cp.getNameOwner());
			});
			
			Assertions.assertThat(credit.size()>0).isTrue();
		});
	}
	
	@Test
	void findByIdCredit() {
		CreditProduct credit = service.findById("5e387f55760005456184707b").block();
		client.get().uri("/creditProduct/findById/{id}",Collections.singletonMap("id", credit.getId()))
		.accept(MediaType.APPLICATION_JSON)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON);
	}

}
