package com.hackthon.model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class Customer {

    @Schema(description = "customerName",
            example = "customerName", required = false)
    @CsvBindByPosition(position = 0)
    @CsvBindByName(column = "customerName", required = true)
    private String customerName;

    @Schema(description = "customerReferenceId",
            example = "Cust123444", required = false)
    @CsvBindByPosition(position = 1)
    @CsvBindByName(column = "customerReferenceId", required = true)
    private String customerReferenceId;

    @Schema(description = "email",
            example = "test@gmail.com", required = false)
    @CsvBindByPosition(position = 2)
    @CsvBindByName(column = "email", required = true)
    private String email;

    @Schema(description = "status",
            example = "status", required = false)
    @CsvBindByPosition(position = 3)
    @CsvBindByName(column = "status", required = true)
    private String status;


}
