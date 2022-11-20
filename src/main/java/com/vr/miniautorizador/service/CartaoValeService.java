package com.vr.miniautorizador.service;

import com.vr.miniautorizador.exception.autorizador.CartaoJaExisteException;
import com.vr.miniautorizador.exception.autorizador.CartaoNaoEncontradoException;
import com.vr.miniautorizador.repository.CartaoValeRepository;
import com.vr.miniautorizador.repository.cartao.CartaoVale;
import com.vr.miniautorizador.service.cartao.CartaoNovoDto;
import com.vr.miniautorizador.service.cartao.TransacaoDto;
import com.vr.miniautorizador.service.validatransacao.BaseValidacaoTransacao;
import com.vr.miniautorizador.service.validatransacao.TransacaoNivel0CartaoExiste;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartaoValeService {
    private final CartaoValeRepository repository;
    private PasswordEncoder encoder = new BCryptPasswordEncoder(16);

    @Transactional(isolation = Isolation.READ_COMMITTED)
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

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void realizarTransacao(TransacaoDto transacao) {
        var entidade = repository.findById(transacao.getNumeroCartao()).orElse(new CartaoVale());
        BaseValidacaoTransacao condicao = new TransacaoNivel0CartaoExiste();
        while (condicao != null) {
            var res = condicao.validar(entidade, transacao, encoder);
            log.trace("{} - {}", condicao.getClass().getName(), res);
            condicao = condicao.getProxima();
        }
        entidade.setSaldo(entidade.getSaldo().subtract(transacao.getValor()));
        repository.save(entidade);
    }

}
