package com.vr.miniautorizador.controller;

import com.vr.miniautorizador.service.CartaoValeService;
import com.vr.miniautorizador.service.cartao.TransacaoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/transacoes")
@RequiredArgsConstructor
@RestController
public class TransacaoController {

    private final CartaoValeService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String realizarTransacao(@RequestBody @Valid TransacaoDto transacao) {
        service.realizarTransacao(transacao);
        return "OK";
    }
}
