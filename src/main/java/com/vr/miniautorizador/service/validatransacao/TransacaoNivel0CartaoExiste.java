package com.vr.miniautorizador.service.validatransacao;

import com.vr.miniautorizador.exception.autorizador.TransacaoCartaoInexistenteException;
import com.vr.miniautorizador.repository.cartao.CartaoVale;
import com.vr.miniautorizador.service.cartao.TransacaoDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public final class TransacaoNivel0CartaoExiste extends BaseValidacaoTransacao {
    public TransacaoNivel0CartaoExiste() {
        super(new TransacaoNivel1MesmaSenha());
    }

    @Override
    public Object validar(CartaoVale entidade, TransacaoDto transacao, PasswordEncoder encoder) {
        return Optional.ofNullable(entidade.getNumeroCartao()).orElseThrow(TransacaoCartaoInexistenteException::new);
    }
}
