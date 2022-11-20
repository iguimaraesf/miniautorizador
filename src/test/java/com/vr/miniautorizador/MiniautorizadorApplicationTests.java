package com.vr.miniautorizador;

import com.vr.miniautorizador.controller.CartaoValeController;
import com.vr.miniautorizador.exception.autorizador.*;
import com.vr.miniautorizador.fixture.CartaoValeFixture;
import com.vr.miniautorizador.fixture.JsonFixture;
import com.vr.miniautorizador.fixture.TransacaoFixture;
import com.vr.miniautorizador.service.CartaoValeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class MiniautorizadorApplicationTests {

    private static final String XPATH_NUMERO_CARTAO = "$.numeroCartao";
    private static final String XPATH_SENHA = "$.senha";
    @Autowired
    private CartaoValeController cartaoValeController;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CartaoValeService service;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(cartaoValeController);
    }

    @Test
    void cartaoCriarQuandoOk() throws Exception {
        var cartao = CartaoValeFixture.criarCartaoNovo();
        Mockito.when(service.criar(Mockito.any())).thenReturn(cartao);
        String json = JsonFixture.toJson(cartao);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/cartoes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath(XPATH_NUMERO_CARTAO).value(cartao.getNumeroCartao()))
                .andExpect(MockMvcResultMatchers.jsonPath(XPATH_SENHA).value(cartao.getSenha()));
    }

    @Test
    void cartaoCriarQuandoJaExiste() throws Exception {
        var cartao = CartaoValeFixture.criarCartaoNovo();
        Mockito.when(service.criar(Mockito.any())).thenThrow(new CartaoJaExisteException(cartao));
        String json = JsonFixture.toJson(cartao);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/cartoes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                ).andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.jsonPath(XPATH_NUMERO_CARTAO).value(cartao.getNumeroCartao()))
                .andExpect(MockMvcResultMatchers.jsonPath(XPATH_SENHA).value(cartao.getSenha()));
    }

    @Test
    void cartaoCriarQuandoNumeroForaDoFormato() throws Exception {
        var cartao = CartaoValeFixture.criarCartaoNovo();
        cartao.setNumeroCartao("91912222");
        String json = JsonFixture.toJson(cartao);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/cartoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void cartaoCriarQuandoSenhaForaDoFormato() throws Exception {
        var cartao = CartaoValeFixture.criarCartaoNovo();
        cartao.setSenha("0");
        String json = JsonFixture.toJson(cartao);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/cartoes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void saldoCartaoQuandoOk() throws Exception {
        var numeroCartao = "1234567888889999";
        Mockito.when(service.vefiricarSaldo(numeroCartao)).thenReturn(new BigDecimal("498.5"));
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/cartoes/" + numeroCartao)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("498.5"));
    }

    @Test
    void saldoCartaoQuandoNaoEncontrado() throws Exception {
        var numeroCartao = "1111";
        Mockito.when(service.vefiricarSaldo(numeroCartao)).thenThrow(CartaoNaoEncontradoException.class);
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/cartoes/" + numeroCartao)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    void transacaoQuandoOk() throws Exception {
        var transacao = TransacaoFixture.transacaoOk();
        String json = JsonFixture.toJson(transacao);
        Mockito.doNothing().when(service).realizarTransacao(Mockito.any());
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/transacoes")
                                .contentType(MediaType.APPLICATION_JSON)
								.content(json)
                ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("OK"));
    }

	@Test
	void transacaoQuandoCartaoNaoEncontrado() throws Exception {
		var transacao = TransacaoFixture.transacaoOk();
		String json = JsonFixture.toJson(transacao);
		Mockito.doThrow(new TransacaoCartaoInexistenteException()).when(service).realizarTransacao(Mockito.any());
		mockMvc.perform(
						MockMvcRequestBuilders.post("/transacoes")
								.contentType(MediaType.APPLICATION_JSON)
								.content(json)
				).andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
				.andExpect(MockMvcResultMatchers.content().string("CARTAO_INEXISTENTE"));
	}

	@Test
	void transacaoQuandoSenhaErrada() throws Exception {
		var transacao = TransacaoFixture.transacaoOk();
		String json = JsonFixture.toJson(transacao);
		Mockito.doThrow(new TransacaoSenhaInvalida()).when(service).realizarTransacao(Mockito.any());
		mockMvc.perform(
						MockMvcRequestBuilders.post("/transacoes")
								.contentType(MediaType.APPLICATION_JSON)
								.content(json)
				).andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
				.andExpect(MockMvcResultMatchers.content().string("SENHA_INVALIDA"));
	}

	@Test
	void transacaoQuandoSaldoInsuficiente() throws Exception {
		var transacao = TransacaoFixture.transacaoOk();
		String json = JsonFixture.toJson(transacao);
		Mockito.doThrow(new TransacaoSaldoInsuficiente()).when(service).realizarTransacao(Mockito.any());
		mockMvc.perform(
						MockMvcRequestBuilders.post("/transacoes")
								.contentType(MediaType.APPLICATION_JSON)
								.content(json)
				).andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
				.andExpect(MockMvcResultMatchers.content().string("SALDO_INSUFICIENTE"));
	}

	@Test
	void transacaoQuandoValorNegativo() throws Exception {
		var transacao = TransacaoFixture.transacaoOk();
		transacao.setValor(new BigDecimal("-50.0"));
		String json = JsonFixture.toJson(transacao);
		mockMvc.perform(
						MockMvcRequestBuilders.post("/transacoes")
								.contentType(MediaType.APPLICATION_JSON)
								.content(json)
				).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

}
