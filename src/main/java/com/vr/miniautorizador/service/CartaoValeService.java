package com.vr.miniautorizador.service;

import com.vr.miniautorizador.service.cartao.CartaoNovoDto;
import com.vr.miniautorizador.exception.autorizador.CartaoJaExisteException;
import com.vr.miniautorizador.repository.CartaoValeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartaoValeService {
    private final CartaoValeRepository repository;

    public CartaoNovoDto criar(CartaoNovoDto cartao) {
        var entidade = repository.findById(cartao.getNumeroCartao());
        entidade.ifPresent(info -> {
            throw new CartaoJaExisteException(cartao);
        });
        var incluir = cartao.paraEntidade();
        repository.save(incluir);
        return cartao;
    }
}
