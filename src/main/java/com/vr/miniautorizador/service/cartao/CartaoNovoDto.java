package com.vr.miniautorizador.service.cartao;

import com.vr.miniautorizador.repository.cartao.CartaoVale;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartaoNovoDto implements Serializable {
    @Size(min = 16, max = 16)
    @NotNull
    private String numeroCartao;
    @Size(min = 4, max = 4)
    @NotNull
    private String senha;

    public CartaoVale paraEntidade() {
        CartaoVale res = new CartaoVale();
        BeanUtils.copyProperties(this, res);
        return res;
    }
}
