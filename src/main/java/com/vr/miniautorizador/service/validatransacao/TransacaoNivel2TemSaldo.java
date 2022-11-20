package com.vr.miniautorizador.service.validatransacao;

import com.vr.miniautorizador.exception.autorizador.TransacaoSaldoInsuficiente;
import com.vr.miniautorizador.repository.cartao.CartaoVale;
import com.vr.miniautorizador.service.cartao.TransacaoDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Optional;

public class TransacaoNivel2TemSaldo extends BaseValidacaoTransacao {
    public TransacaoNivel2TemSaldo() {
        super(null);
    }

    @Override
    public Object validar(CartaoVale entidade, TransacaoDto transacao, PasswordEncoder encoder) {
        BigDecimal saldo = entidade.getSaldo();
        return Optional.of(saldo).filter(naConta -> naConta.compareTo(transacao.getValor()) >= 0).orElseThrow(TransacaoSaldoInsuficiente::new);
    }
}
