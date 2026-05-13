package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
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
	
	public Page<SaleReportMinDTO>  findPaged(String minDate, String maxDate, String name, Pageable pageable){
		LocalDate today;
		if(maxDate == null || maxDate.trim().isEmpty()) {
			today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}
		else {
			today = LocalDate.parse(maxDate);
		}
		
		LocalDate yesterday;
		if(minDate == null || minDate.trim().isEmpty()) {
			yesterday = today.minusYears(1L);
		}
		else {
			yesterday = LocalDate.parse(minDate);
		}
		
		if(name == null || name.trim().isEmpty()) {
			name = "";
		}
		
		Page<Sale> page = repository.searchReport(yesterday, today, name, pageable);
		return page.map(x -> new SaleReportMinDTO(x));
		
	}
}
