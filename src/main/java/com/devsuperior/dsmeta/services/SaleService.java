package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.DateRange;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleReportDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleReportDTO(entity);
	}

	public Page<SaleReportDTO> findSaleReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable){

		DateRange date = getDates(minDate, maxDate);

		Page<SaleReportDTO> result = repository.findSaleReport(date.minDate(), date.maxDate(), name, pageable);
		return result;
	}

	public Page<SaleSummaryDTO> findSaleSummary(LocalDate minDate, LocalDate maxDate, Pageable pageable){

		DateRange date = getDates(minDate, maxDate);

		Page<SaleSummaryDTO> result = repository.findSaleSummary(date.minDate(), date.maxDate(), pageable);
		return result;
	}

	public DateRange getDates(LocalDate minDate, LocalDate maxDate) {

		if(maxDate == null){
			maxDate = java.time.LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}

		if(minDate == null){
			minDate = maxDate.minusYears(1L);
		}

		return new DateRange(minDate, maxDate);
	}




}
