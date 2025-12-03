package mobilidade.entidades;

import mobilidade.excessoes.PagamentoRecusadoException;
//import mobilidade.excessoes.SaldoInsuficienteException;

import java.math.BigDecimal;

public class PagamentoCartaoCredito implements MetodoPagamento {

    // Atributos específicos da classse PagamentoCartaoCredito:
    private String numeroCartao;
    // private String nomeTitular; --> ngm precisa saber disso aqui
    private BigDecimal limiteDisponivel;

    // Método construtor da classe PagamentoCart...
    public PagamentoCartaoCredito(String numeroCartao, String nomeTitular, BigDecimal limiteDisponivel) {
        this.numeroCartao = numeroCartao;
        // this.nomeTitular = nomeTitular;
        this.limiteDisponivel = limiteDisponivel;
    }

    /*
     * Simula um cartão de crédito, diminuindo o "limite" a cada pagamento,
     * verifica se o limite é menor que o valor e, se sim, lança a exception de
     * PagamentoRecusado...
     */
    @Override
    public void processarPagamento(BigDecimal valor, Passageiro passageiro) throws PagamentoRecusadoException {
        if (limiteDisponivel.compareTo(valor) < 0) {
            throw new PagamentoRecusadoException("Pagamento recusado (Limite insuficiente).");
        }
        limiteDisponivel = limiteDisponivel.subtract(valor);
        System.out.println("Pagamento aprovado no cartão de crédito.");
    }

    @Override // Sobreescrita do método getDescricao()
    public String getDescricao() {
        return "Cartão de crédito (" + numeroCartao + ")";
    }
}
