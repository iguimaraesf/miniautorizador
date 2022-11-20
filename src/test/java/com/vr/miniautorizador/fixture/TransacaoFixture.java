package com.vr.miniautorizador.fixture;

import com.vr.miniautorizador.service.cartao.TransacaoDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransacaoFixture {
    public static TransacaoDto transacaoOk() {
        TransacaoDto res = new TransacaoDto();
        res.setValor(new BigDecimal("175.0"));
        res.setSenhaCartao("1234");
        res.setNumeroCartao("12341234512341234");
        return res;
    }

}
