package com.aline.openApi.models;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProdutoEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    public Optional<String> getOptionalName() {
        return Optional.ofNullable(name);
    }

    private String name;

    private LocalDate validDate;

    private BigDecimal valor;

    public ProdutoEntity(String name, BigDecimal valor) {
        this.name = name;
        this.valor = valor;
    }

    public boolean valorNaMedia() {
        boolean valorNaMedia = false;
        if (this != null && this.valor != null
                && (this.valor.compareTo(BigDecimal.valueOf(10)) == 0 || this.valor.compareTo(BigDecimal.valueOf(10)) > 0) && this.valor.compareTo(BigDecimal.valueOf(15)) < 0) {
            valorNaMedia = true;
        }
        return valorNaMedia;
    }

    public boolean valorNaMediaOptional() {
        return Optional.ofNullable(this)
                .map(ProdutoEntity::getValor)
                .filter(v -> v.compareTo(BigDecimal.valueOf(10)) == 0 || v.compareTo(BigDecimal.valueOf(10)) > 0)
                .filter(v -> v.compareTo(BigDecimal.valueOf(15)) < 0)
                .isPresent();
    }

}