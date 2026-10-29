package com.devsuperior.dsmeta.dto;

import java.time.LocalDate;

public record DateRange(
        LocalDate minDate,
        LocalDate maxDate
) {}
