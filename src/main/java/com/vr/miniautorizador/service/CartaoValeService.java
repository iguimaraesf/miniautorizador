package com.vr.miniautorizador.service;

import com.vr.miniautorizador.exception.autorizador.CartaoJaExisteException;
import com.vr.miniautorizador.exception.autorizador.CartaoNaoEncontradoException;
import com.vr.miniautorizador.repository.CartaoValeRepository;
import com.vr.miniautorizador.service.cartao.CartaoNovoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

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
}
