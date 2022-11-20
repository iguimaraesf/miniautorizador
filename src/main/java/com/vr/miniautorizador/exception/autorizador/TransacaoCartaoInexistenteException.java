package com.vr.miniautorizador.exception.autorizador;

import com.vr.miniautorizador.exception.BaseTransacaoException;
import com.vr.miniautorizador.exception.ErroDeTransacao;

public class TransacaoCartaoInexistenteException extends BaseTransacaoException {
    public TransacaoCartaoInexistenteException() {
        super(ErroDeTransacao.CARTAO_INEXISTENTE);
    }
}
