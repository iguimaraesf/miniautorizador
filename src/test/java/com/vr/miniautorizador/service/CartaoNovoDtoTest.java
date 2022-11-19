package com.vr.miniautorizador.service;

import com.vr.miniautorizador.fixture.CartaoValeFixture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CartaoNovoDtoTest {
    @Test
    void copiarValores() {
        var dto = CartaoValeFixture.criarCartaoNovo();
        var res = dto.paraEntidade();
        Assertions.assertAll(() -> {
            Assertions.assertNotNull(res.getNumeroCartao());
            Assertions.assertNotNull(res.getSenha());
            Assertions.assertEquals(dto.getNumeroCartao(), res.getNumeroCartao());
            Assertions.assertEquals(dto.getSenha(), res.getSenha());
        });
    }
}
