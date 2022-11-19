package com.vr.miniautorizador.controller;

import com.vr.miniautorizador.service.CartaoValeService;
import com.vr.miniautorizador.service.cartao.CartaoNovoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
public class CartaoValeController {
    private final CartaoValeService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CartaoNovoDto criar(@RequestBody @Valid CartaoNovoDto param) {
        return service.criar(param);
    }

    @RequestMapping("/{numeroCartao}")
    @GetMapping
    public BigDecimal verSaldo(@PathVariable("numeroCartao") String numeroCartao) {
        return service.vefiricarSaldo(numeroCartao);
    }
}
