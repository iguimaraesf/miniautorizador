package com.vr.miniautorizador.service;

import com.vr.miniautorizador.exception.autorizador.CartaoJaExisteException;
import com.vr.miniautorizador.exception.autorizador.CartaoNaoEncontradoException;
import com.vr.miniautorizador.fixture.CartaoValeFixture;
import com.vr.miniautorizador.repository.CartaoValeRepository;
import com.vr.miniautorizador.repository.cartao.CartaoVale;
import com.vr.miniautorizador.service.cartao.CartaoNovoDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class CartaoValeServiceTest {
    @Mock
    private CartaoValeRepository repository;
    private CartaoValeService service;

    @BeforeEach
    void setUp() {
        service = new CartaoValeService(repository);
    }

    @Test
    void cartaoJaExiste() {
        CartaoNovoDto param = CartaoValeFixture.criarCartaoNovo();
        CartaoVale entidade = param.paraEntidade();
        Mockito.when(repository.findById(param.getNumeroCartao())).thenReturn(Optional.of(entidade));
        Assertions.assertThrows(CartaoJaExisteException.class, () -> service.criar(param));
    }

    @Test
    void novoCartao() {
        CartaoNovoDto param = CartaoValeFixture.criarCartaoNovo();
        Mockito.when(repository.findById(param.getNumeroCartao())).thenReturn(Optional.empty());
        var res = service.criar(param);
        Assertions.assertAll(() -> {
            Assertions.assertEquals(res.getSenha(), param.getSenha());
            Assertions.assertEquals(res.getNumeroCartao(), param.getNumeroCartao());
            Mockito.verify(repository).save(Mockito.any());
        });
    }

    @Test
    void saldoCartaoInexistente() {
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(CartaoNaoEncontradoException.class, () -> service.vefiricarSaldo("1111222233334444"));
    }

    @Test
    void saldoCartao() {
        CartaoVale entidade = CartaoValeFixture.criarEntidade();
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(entidade));
        Assertions.assertEquals(entidade.getSaldo(), service.vefiricarSaldo("1111222233334444"));
    }
}
