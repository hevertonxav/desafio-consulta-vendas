package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleReportDTO( " +
            "obj.id, " +
            "obj.amount, " +
            "obj.date, " +
            "obj.seller.name " +
            ") " +
            "FROM Sale obj " +
            "LEFT JOIN obj.seller seller " +
            "WHERE obj.date BETWEEN :minDate AND :maxDate " +
            "AND ( :name IS NULL OR " +
            "UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%')))")
    Page<SaleReportDTO> findSaleReport(
            @Param("minDate") LocalDate minDate,
            @Param("maxDate") LocalDate maxDate,
            @Param("name") String name,
            Pageable pageable
    );


    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO( " +
            "obj.seller.name, " +
            "SUM(obj.amount)) " +
            "FROM Sale obj " +
            "LEFT JOIN obj.seller seller " +
            "WHERE obj.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY obj.seller.name")
    Page<SaleSummaryDTO> findSaleSummary(
            @Param("minDate") LocalDate minDate,
            @Param("maxDate") LocalDate maxDate,
            Pageable pageable);

}
