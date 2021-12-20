package com.bankingTransactions.controller;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankingTransactions.model.Transaction;
import com.bankingTransactions.model.Status;
import com.bankingTransactions.repository.TransactionRepository;
import com.bankingTransactions.utils.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TransactionController {

	@Autowired
	TransactionRepository transactionRepository;
	
	Utils utils = new Utils();
	


	@GetMapping("/transactions")
	public ResponseEntity<List<Transaction>> getAllTutorials(@RequestParam(required = false) String title) {
		try {
			List<Transaction> tutorials = new ArrayList<Transaction>();

			if (title == null)
				transactionRepository.findAll().forEach(tutorials::add);
			else
				//transactionRepository.findByTitleContaining(title).forEach(tutorials::add);

			if (tutorials.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(tutorials, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/*@GetMapping("/transactions/{id}")
	public ResponseEntity<Transaction> getTutorialById(@PathVariable("id") long id) {
		Optional<Transaction> tutorialData = transactionRepository.findById(id);

		if (tutorialData.isPresent()) {
			return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}*/

	@PostMapping("/transactions")
	public Map<String, String> createTutorial(@RequestBody Transaction transaction) {
		try {
			Double total = transactionRepository.selectTotals();
			HashMap<String,String>  map = new HashMap<>();
			Double amount = transaction.getAmount();
			Double fee = transaction.getFee();
			String reference = transaction.getReference();
			String date = transaction.getDate();
			String account_iban = transaction.getAccount_iban();
			String description = transaction.getDescription();
			if(fee == null) {
				fee = 0.00;
			}
			
			
			if(account_iban == null) {
				map.put("status", "Error");
				map.put("Error", "The account_iban is mandatory");
				return map;	
			}
			if(amount == null) {
				map.put("status", "Error");
				map.put("Error", "The amount is mandatory");
				return map;	
			}
			
			
			if(reference == null ) {
				
				reference = utils.generateRandomString(10);
				
			}
			
			
			
			if(date == null) {
				SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
				 Date newDate = new Date(System.currentTimeMillis());
				 date=formatter.format(newDate);
				 
			}else {
				Boolean res = utils.isValid(date);
				if(res == false) {
					
					map.put("status", "Error");
					map.put("Error", "Date not valid");
					return map;	
					
				}
				
			}
			
			if(total+amount+fee>=0.00) {
				Transaction _transactions2 = new Transaction();
				_transactions2.setAccount_iban(account_iban);
				_transactions2.setReference(reference);
				_transactions2.setFee(fee);
				_transactions2.setAmount(amount);
				_transactions2.setDate(date);
				_transactions2.setDescription(description);
				transactionRepository.save(_transactions2);
				
				map.put("status", "OK");
				map.put("OK", "Created Ok");
				map.put("account_iban", account_iban);
				map.put("reference", reference);
				map.put("fee", fee.toString());
				map.put("amount", amount.toString());
				map.put("date", date);
				map.put("description", description);
				return map;	
				
				
			}else {
				map.put("status", "Error");
				map.put("Error", "The amount 0 in not allowed");
				return map;	
			}
			
		} catch (Exception e) {
			//return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return null;
	}

	

	@GetMapping("/transactions/status")
	public Map<String, String>  getStatus(@RequestBody Status status) {
		HashMap<String,String>  map = new HashMap<>();
		try {
		  Transaction transactions = transactionRepository.findPrimero(status.getReference());
		  
		   
		   if(transactions == null) {
			   map.put("reference", status.getReference());
				map.put("status", "INVALID");
				return map;
		   }else {
			   if(status.getChannel().equals("CLIENT") || status.getChannel().equals("ATM") || status.getChannel().equals("INTERNAL")) {
				   
				   if(status.getChannel().equals("INTERNAL")) {
					   if(utils.isEqualsDate(transactions.getDate())) {
						    map.put("reference", status.getReference());
							map.put("status", "PENDING");
							map.put("amount", transactions.getAmount().toString());
							map.put("fee", transactions.getFee().toString());
							return map;
					   }else if(utils.isGreaterDate(transactions.getDate())) {
						   map.put("reference", status.getReference());
							map.put("status", "FUTURE");
							map.put("amount", transactions.getAmount().toString());
							map.put("fee", transactions.getFee().toString());
							return map;
					   }else if(utils.isAftersDate(transactions.getDate())) {
						   map.put("reference", status.getReference());
							map.put("status", "SETTLED");
							map.put("amount", transactions.getAmount().toString());
							map.put("fee", transactions.getFee().toString());
							return map;
					   }
					   
				   }else if (status.getChannel().equals("ATM")) {
					   Double result= transactions.getAmount()-transactions.getFee();
					   
					   
					   if(utils.isEqualsDate(transactions.getDate())) {
						    map.put("reference", status.getReference());
							map.put("status", "PENDING");
							map.put("amount", result.toString());
							
							return map;
					   }else if(utils.isGreaterDate(transactions.getDate())) {
						   map.put("reference", status.getReference());
							map.put("status", "PENDING");
							map.put("amount", result.toString());
							
							return map;
					   }else if(utils.isAftersDate(transactions.getDate())) {
						   map.put("reference", status.getReference());
							map.put("status", "SETTLED");
							map.put("amount", result.toString());
							
							return map;
					   }
					   
				   }else {//CLIENT
					   
					   Double result= transactions.getAmount()-transactions.getFee();
					   
					   
					   
					  Boolean equals =  utils.isEqualsDate(transactions.getDate());
					   if(utils.isEqualsDate(transactions.getDate())) {
						    map.put("reference", status.getReference());
							map.put("status", "PENDING");
							map.put("amount", result.toString());
							
							return map;
					   }else if(utils.isGreaterDate(transactions.getDate())) {
						   map.put("reference", status.getReference());
							map.put("status", "FUTURE");
							map.put("amount", result.toString());
							
							return map;
					   }else if(utils.isAftersDate(transactions.getDate())) {
						   map.put("reference", status.getReference());
							map.put("status", "SETTLED");
							map.put("amount",result.toString());							
							return map;
					   }
				   }
				   
			   }else {
				   map.put("Info", "The channel is not valid");
					
					return map;
			   }
		   }
		   
		   

			
			
		} catch (Exception e) {
			e.toString();
		}
		return map;
	}
	
	@GetMapping("/transactions/searchTransactions/{account_iban}/{order}")
	public ResponseEntity<List<Transaction>> findByAccountIban(@PathVariable("account_iban") String account_iban,@PathVariable("order") String order) {
		try {
			
			//List<Transaction> tutorials;
			
			List<Transaction> tutorials;
			if(order == null || order.equals("ascending")) {
				 tutorials = transactionRepository.findByAccountIbanOrderByAsc(account_iban);	
			}else {
				 tutorials = transactionRepository.findByAccountIbanOrderByDesc(account_iban);
			}
			

			
			if (tutorials.isEmpty()) {
				HashMap<String,String>  map = new HashMap<>();
				map.put("status", "Error");
				map.put("Error", "The amount 0 in not allowed");
				return new ResponseEntity<>(tutorials,HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(tutorials, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
