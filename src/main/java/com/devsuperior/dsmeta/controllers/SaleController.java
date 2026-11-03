package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleReportDTO> findById(@PathVariable Long id) {
		SaleReportDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<List<SaleSummaryDTO>> getSummary(
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@RequestParam(required = false)
			LocalDate minDate,

			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@RequestParam(required = false)
			LocalDate maxDate
	) {

		List<SaleSummaryDTO> dto = service.findSaleSummary(minDate, maxDate);

		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleReportDTO>> getReport(
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@RequestParam(required = false)
			LocalDate minDate,

			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
			@RequestParam(required = false)
			LocalDate maxDate,

			@RequestParam(name = "name", defaultValue = "")
			String name,

			Pageable pageable
	) {

		Page<SaleReportDTO> dto = service.findSaleReport(minDate, maxDate, name, pageable);

		return ResponseEntity.ok(dto);
	}
}
