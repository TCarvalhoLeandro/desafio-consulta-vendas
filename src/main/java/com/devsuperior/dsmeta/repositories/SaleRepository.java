package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SaleSummaryMinProjection;

public interface SaleRepository extends JpaRepository<Sale, Long> {

	// relatorio de vendas paginado
	// ManyToOne
	@Query(value = "SELECT obj "
			+ "FROM Sale obj "
			+ "JOIN FETCH obj.seller "
			+ "WHERE obj.date "
			+ "BETWEEN :minDate AND :maxDate "
			+ "AND UPPER(obj.seller.name) "
			+ "LIKE UPPER(CONCAT('%', :name, '%'))",
			countQuery = "SELECT COUNT(obj) FROM Sale obj JOIN obj.seller "
					+ "WHERE obj.date BETWEEN :minDate AND :maxDate "
					+ "AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))")
	Page<Sale> searchReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);
	
	
	// sumario de vendas 
	@Query(nativeQuery = true, value = "SELECT tb_seller.name AS sellerName, SUM(tb_sales.amount) AS total "
										+ "FROM tb_sales "
										+ "INNER JOIN tb_seller ON tb_sales.seller_id = tb_seller.id "
										+ "WHERE tb_sales.date BETWEEN :minDate AND :maxDate "
										+ "GROUP BY tb_seller.name")
	List<SaleSummaryMinProjection> searchSummary(LocalDate minDate, LocalDate maxDate);
}

