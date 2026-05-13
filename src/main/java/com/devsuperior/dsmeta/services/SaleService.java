package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportMinDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SaleSummaryMinProjection;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}
	
	// busca paginada
	public Page<SaleReportMinDTO>  findReport(String minDate, String maxDate, String name, Pageable pageable){
		LocalDate dataAtual;
		if(maxDate == null || maxDate.trim().isEmpty()) {
			dataAtual = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}
		else {
			dataAtual = LocalDate.parse(maxDate);
		}
		
		LocalDate dataAnterior;
		if(minDate == null || minDate.trim().isEmpty()) {
			dataAnterior = dataAtual.minusYears(1L);
		}
		else {
			dataAnterior = LocalDate.parse(minDate);
		}
		
		if(name == null || name.trim().isEmpty()) {
			name = "";
		}
		
		Page<Sale> page = repository.searchReport(dataAnterior, dataAtual, name, pageable);
		return page.map(x -> new SaleReportMinDTO(x));
	}
	
	// busca listagem
	public List<SaleSummaryMinDTO> findSummary(String minDate, String maxDate){
		LocalDate dataAtual;
		if(maxDate == null || maxDate.trim().isEmpty()) {
			dataAtual = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}
		else {
			dataAtual = LocalDate.parse(maxDate);
		}
		
		LocalDate dataAnterior;
		if(minDate == null || minDate.trim().isEmpty()) {
			dataAnterior = dataAtual.minusYears(1L);
		}
		else {
			dataAnterior = LocalDate.parse(minDate);
		}
		
		List<SaleSummaryMinProjection> list = repository.searchSummary(dataAnterior, dataAtual);
		List<SaleSummaryMinDTO> result = list.stream().map(x -> new SaleSummaryMinDTO(x)).toList();
		return result;
	}
}
