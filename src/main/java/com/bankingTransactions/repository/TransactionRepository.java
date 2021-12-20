package com.bankingTransactions.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.bankingTransactions.model.Transaction;
import com.bankingTransactions.model.Status;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  /*List<Transaction> findByPublished(boolean published);

  List<Transaction> findByTitleContaining(String title);*/
	
	@Query("SELECT COALESCE(SUM(amount),0.00)  FROM Transaction")
    Double selectTotals();
	
	@Query("FROM Transaction WHERE account_iban = :account_iban ORDER BY amount ASC")
    List<Transaction> findByAccountIbanOrderByAsc(@Param("account_iban") String account_iban);
	
	@Query("FROM Transaction WHERE account_iban = :account_iban ORDER BY amount DESC")
    List<Transaction> findByAccountIbanOrderByDesc(@Param("account_iban") String account_iban);
	
	public static final String FIND_REFERENCE = "SELECT * FROM Transactions where reference = ?1";
	
	@Query(value = FIND_REFERENCE, nativeQuery = true)
	Transaction findPrimero(String reference);
	
	
	
	
	
	
	
}
