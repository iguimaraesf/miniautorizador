package com.vr.miniautorizador.repository.cartao;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class CartaoVale {
    @Id
    @Column(length = 16)
    private String numeroCartao;
    @Column(length = 60)
    private String senha;
    @Column(length = 14, scale = 2)
    private BigDecimal saldo = new BigDecimal("500.0");

}
