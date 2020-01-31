package com.rafalazar.bootcamp.app.document;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Document(collection="credit_products")
public class CreditProduct {
	
	@Id
	private String id;
	private String bank;
	private String typeOwner;
	private String productName;
	private String dniOwner;
	private Double creditAmount;
	private Double balance;
	private Double consume;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date joinDate;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date expirationDate;
	

}