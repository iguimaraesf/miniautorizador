package com.vr.miniautorizador.repository;

import com.vr.miniautorizador.repository.cartao.CartaoVale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoValeRepository extends JpaRepository<CartaoVale, String> {
}
