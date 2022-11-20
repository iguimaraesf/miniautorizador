package com.vr.miniautorizador.service.cartao;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TransacaoDto {
    @NotEmpty
    private String numeroCartao;
    @NotEmpty
    private String senhaCartao;
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(fraction = 2, integer = 12)
    private BigDecimal valor;
}
