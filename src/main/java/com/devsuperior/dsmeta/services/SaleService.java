package com.devsuperior.dsmeta.services;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleReportDTO> findReport(String minDate, String maxDate, String name, Pageable pageable) {
		LocalDate maxD;
		LocalDate minD;
		try {
			maxD = LocalDate.parse(maxDate);
		} catch (DateTimeParseException e) {
			maxD = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}
		try {
			minD = LocalDate.parse(minDate);
		} catch (DateTimeParseException e) {
			minD = maxD.minusYears(1L);
		}
		return repository.searchReport(minD, maxD, name, pageable);
	}

	public List<SaleSummaryDTO> findSummary(String minDate, String maxDate) {
		LocalDate maxD;
		LocalDate minD;
		try {
			maxD = LocalDate.parse(maxDate);
		} catch (DateTimeParseException e) {
			maxD = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		}
		try {
			minD = LocalDate.parse(minDate);
		} catch (DateTimeParseException e) {
			minD = maxD.minusYears(1L);
		}
		return repository.searchSummary(minD, maxD);
	}
}
