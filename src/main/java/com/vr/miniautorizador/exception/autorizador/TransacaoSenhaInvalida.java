package com.vr.miniautorizador.exception.autorizador;

import com.vr.miniautorizador.exception.BaseTransacaoException;
import com.vr.miniautorizador.exception.ErroDeTransacao;

public class TransacaoSenhaInvalida extends BaseTransacaoException {
    public TransacaoSenhaInvalida() {
        super(ErroDeTransacao.SENHA_INVALIDA);
    }
}
