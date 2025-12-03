package mobilidade.entidades;

//importa as exceptions
import mobilidade.excessoes.PagamentoRecusadoException;
import mobilidade.excessoes.SaldoInsuficienteException;

import java.math.BigDecimal;

//implementa/tem que usar os métodos de MetodoPagamento...
public class PagamentoPix implements MetodoPagamento {

    // Cria um atributo que vai receber a chave pix
    private String chavePix;

    public PagamentoPix(String chavePix) {
        this.chavePix = chavePix;
    }

    // Somente manda uma mensagem confirmando o pagamento, sem lançar exceptions
    // reais
    public void processarPagamento(BigDecimal valor, Passageiro passageiro)
            throws SaldoInsuficienteException, PagamentoRecusadoException {
        System.out.println("Pagamento via Pix enviado com sucesso para a chave: " + chavePix);
    }

    // sobreescrita de toString() com o pix
    @Override
    public String getDescricao() {
        return "Pix (" + chavePix + ")";
    }
}
