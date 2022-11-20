package com.vr.miniautorizador.service;

import com.vr.miniautorizador.exception.autorizador.*;
import com.vr.miniautorizador.fixture.CartaoValeFixture;
import com.vr.miniautorizador.fixture.TransacaoFixture;
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

import java.math.BigDecimal;
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

    @Test
    void realizarTransacaoCartaoInexistente() {
        var trans = TransacaoFixture.transacaoOk();
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        Assertions.assertThrows(TransacaoCartaoInexistenteException.class, () -> service.realizarTransacao(trans));
    }

    @Test
    void realizarTransacaoSenhaInvalida() {
        var trans = TransacaoFixture.transacaoOk();
        var cartao = CartaoValeFixture.criarEntidade();
        cartao.setSenha("invalido");
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(cartao));
        Assertions.assertThrows(TransacaoSenhaInvalida.class, () -> service.realizarTransacao(trans));
    }

    @Test
    void realizarTransacaoSaldoInsuficiente() {
        var trans = TransacaoFixture.transacaoOk();
        var cartao = CartaoValeFixture.criarEntidade();
        cartao.setSaldo(trans.getValor().subtract(new BigDecimal("0.01")));
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(cartao));
        Assertions.assertThrows(TransacaoSaldoInsuficiente.class, () -> service.realizarTransacao(trans));
    }

    @Test
    void realizarTransacaoValorExato() {
        var trans = TransacaoFixture.transacaoOk();
        var cartao = CartaoValeFixture.criarEntidade();
        cartao.setSaldo(trans.getValor());
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(cartao));
        service.realizarTransacao(trans);
        Mockito.verify(repository).save(cartao);
    }

    @Test
    void realizarTransacaoSobraSaldo() {
        var trans = TransacaoFixture.transacaoOk();
        var cartao = CartaoValeFixture.criarEntidade();
        cartao.setSaldo(trans.getValor().add(new BigDecimal("0.01")));
        Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(cartao));
        service.realizarTransacao(trans);
        Mockito.verify(repository).save(cartao);
    }

}
