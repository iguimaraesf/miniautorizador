package com.vr.miniautorizador.service;

import com.vr.miniautorizador.exception.autorizador.*;
import com.vr.miniautorizador.repository.CartaoValeRepository;
import com.vr.miniautorizador.repository.cartao.CartaoVale;
import com.vr.miniautorizador.service.cartao.CartaoNovoDto;
import com.vr.miniautorizador.service.cartao.TransacaoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class CartaoValeService {
    private final CartaoValeRepository repository;
    private PasswordEncoder encoder = new BCryptPasswordEncoder(16);

    public CartaoNovoDto criar(CartaoNovoDto cartao) {
        var entidade = repository.findById(cartao.getNumeroCartao());
        entidade.ifPresent(info -> {
            throw new CartaoJaExisteException(cartao);
        });
        var incluir = cartao.paraEntidade();
        incluir.setSenha(encoder.encode(incluir.getSenha()));
        repository.save(incluir);
        return cartao;
    }

    public BigDecimal vefiricarSaldo(String numeroCartao) {
        var entidade = repository.findById(numeroCartao).orElseThrow(CartaoNaoEncontradoException::new);
        return entidade.getSaldo();
    }

    public void realizarTransacao(TransacaoDto transacao) {
        var entidade = repository.findById(transacao.getNumeroCartao()).orElse(null);
        this.validarExistencia(entidade);
        this.validarMesmaSenha(entidade, transacao.getSenhaCartao());
        this.validarSaldoSuficiente(entidade, transacao.getValor());
        entidade.setSaldo(entidade.getSaldo().subtract(transacao.getValor()));
        repository.save(entidade);
    }

    private void validarExistencia(CartaoVale entidade) {
        Optional.ofNullable(entidade).orElseThrow(TransacaoCartaoInexistenteException::new);
    }

    private void validarMesmaSenha(CartaoVale entidade, String senha) {
        boolean res = encoder.matches(senha, entidade.getSenha());
        Optional.of(res).filter(v -> v).orElseThrow(TransacaoSenhaInvalida::new);
    }

    private void validarSaldoSuficiente(CartaoVale entidade, BigDecimal valor) {
        BigDecimal saldo = entidade.getSaldo();
        Optional.of(saldo).filter(naConta -> naConta.compareTo(valor) >= 0).orElseThrow(TransacaoSaldoInsuficiente::new);
    }

}
