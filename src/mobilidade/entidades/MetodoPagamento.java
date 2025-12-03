package mobilidade.entidades;

import java.math.BigDecimal;

import mobilidade.excessoes.PagamentoRecusadoException;
import mobilidade.excessoes.SaldoInsuficienteException;
//importa as exceptions De pagamentoRecu... e SaldoInsuf... para usar no método

public interface MetodoPagamento { // interface --> define um "Contrato" de pagamento

    // Esse método pode retornar dois tipos exception dependendo do pagamento ficar
    // pendente etc...
    void processarPagamento(BigDecimal valor, Passageiro passageiro)
            throws SaldoInsuficienteException, PagamentoRecusadoException;

    // getDescricap() --> vai exibir o tipo de oagamento
    String getDescricao();
}
