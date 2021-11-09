package com.aline.openApi.models.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {

    private Long id;

    @Schema(description = "Nome do produto",
            example = "Aline",
            required = true)
    private String name;

    @Schema(description = "Data de validade do produto",
            example = "2021/10/20")
    private LocalDate validDate;


}