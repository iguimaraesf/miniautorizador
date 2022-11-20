package com.vr.miniautorizador.service.validatransacao;

import com.vr.miniautorizador.exception.autorizador.TransacaoSenhaInvalida;
import com.vr.miniautorizador.repository.cartao.CartaoVale;
import com.vr.miniautorizador.service.cartao.TransacaoDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public final class TransacaoNivel1MesmaSenha extends BaseValidacaoTransacao {
    public TransacaoNivel1MesmaSenha() {
        super(new TransacaoNivel2TemSaldo());
    }

    @Override
    public Object validar(CartaoVale entidade, TransacaoDto transacao, PasswordEncoder encoder) {
        boolean comparacao = encoder.matches(transacao.getSenhaCartao(), entidade.getSenha());
        return Optional.of(comparacao).filter(v -> v).orElseThrow(TransacaoSenhaInvalida::new);
    }
}
