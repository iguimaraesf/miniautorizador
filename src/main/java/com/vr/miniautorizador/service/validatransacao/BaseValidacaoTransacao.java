package com.vr.miniautorizador.service.validatransacao;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class BaseValidacaoTransacao implements ValidacaoTransacao {
    private final BaseValidacaoTransacao proxima;
}
