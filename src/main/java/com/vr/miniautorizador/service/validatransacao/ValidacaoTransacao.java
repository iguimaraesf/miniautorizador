package com.vr.miniautorizador.service.validatransacao;

import com.vr.miniautorizador.repository.cartao.CartaoVale;
import com.vr.miniautorizador.service.cartao.TransacaoDto;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface ValidacaoTransacao {
    Object validar(CartaoVale entidade, TransacaoDto transacao, PasswordEncoder encoder);
}
