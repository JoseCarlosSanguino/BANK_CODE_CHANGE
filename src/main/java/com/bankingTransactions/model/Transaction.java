package com.bankingTransactions.model;

import java.math.BigDecimal;

import javax.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "reference",length=150, unique=true)
	private String reference;
	
	@Column(name = "account_iban",columnDefinition = "varchar(50)")
	private String account_iban;
	
	@Column(name = "date",columnDefinition = "DATE DEFAULT CURRENT_DATE")
	private String date;
	
	
	@Column(name = "amount",columnDefinition = "DECIMAL(10,2) default 0.00")
	private Double amount;
	
	@Column(name = "fee",columnDefinition = "DECIMAL(10,2) default 0.00")
	private Double fee;
	
	@Column(name = "description",columnDefinition = "varchar(150) default null")
	private String description;

	public Transaction() {

	}

	public Transaction(String reference, String account_iban, String date,Double amount, Double fee, String description) {
		this.reference = reference;
		this.account_iban = account_iban;
		this.date = date;
		this.amount = amount;
		this.fee = fee;
		this.description = description;
	}

	
	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getAccount_iban() {
		return account_iban;
	}

	public void setAccount_iban(String account_iban) {
		this.account_iban = account_iban;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Transactions [reference=" + reference + ", account_iban=" + account_iban + ", date=" + date
				+ ", amount=" + amount + ", fee=" + fee + ", description=" + description + "]";
	}

	
	

}
