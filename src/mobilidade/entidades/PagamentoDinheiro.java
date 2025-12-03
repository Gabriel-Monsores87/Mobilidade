package mobilidade.entidades;

import mobilidade.excessoes.SaldoInsuficienteException;
//import mobilidade.excessoes.PagamentoRecusadoException;

import java.math.BigDecimal;

//implementa/tem que definir os métodos de pagamento
public class PagamentoDinheiro implements MetodoPagamento {

    /*
     * Compara o saldo da carteira do passsageiro com o valor da corrida
     * Se for menor, lança a exception SaldoInsuficiente...
     * Se tiver ok debita o saldo necessário da carteira
     */
    @Override
    public void processarPagamento(BigDecimal valor, Passageiro passageiro) throws SaldoInsuficienteException {
        if (passageiro.getSaldoCarteira().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException("Saldo insuficiente na carteira do App.");
        }
        passageiro.debitarSaldo(valor);
        System.out.println("Pagamento em dinheiro (carteira do App) realizado com sucesso.");
    }

    @Override // --> Sobreescrita do método toString()
    public String getDescricao() {
        return "Dinheiro (carteira do App)";
    }
}
