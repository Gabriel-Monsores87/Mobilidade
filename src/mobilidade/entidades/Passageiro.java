package mobilidade.entidades;

//Aqui perdi a mão...
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Herda de usuário, padrão, nada de novo aq
public class Passageiro extends Usuario {

    // cria atributos para usar nos métodos
    private boolean pendentePagamento;
    private BigDecimal saldoCarteira = BigDecimal.ZERO;

    // lista de métodos de pagamento registrados para o passageiro
    private final List<MetodoPagamento> metodosPagamento = new ArrayList<>();

    // construtor
    public Passageiro(String nome, String cpf, String email, String telefone, String senha) {
        super(nome, cpf, email, telefone, senha);
    }

    // Adiciona um meio de pagamento
    public void adicionarMetodoPagamento(MetodoPagamento metodoPagamento) {
        metodosPagamento.add(metodoPagamento);
    }

    // retorna uma lista fixa
    public List<MetodoPagamento> getMetodosPagamento() {
        return Collections.unmodifiableList(metodosPagamento);
    }

    // retorna o primeiro da lista passada e lança a exception IllegalStateException
    // se não tiver nenhum registrado
    public MetodoPagamento getMetodoPagamentoPadrao() {
        if (metodosPagamento.isEmpty()) {
            throw new IllegalStateException("Passageiro não possui método de pagamento registrado.");
        }
        return metodosPagamento.get(0);
    }

    public BigDecimal getSaldoCarteira() {
        return saldoCarteira;
    }

    // gerencia a carteira interna, adicionando valor a ela
    public void adicionarSaldo(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) > 0) {
            saldoCarteira = saldoCarteira.add(valor);
        }
    }

    // mesma coisa, tirando valor
    public void debitarSaldo(BigDecimal valor) {
        saldoCarteira = saldoCarteira.subtract(valor);
    }

    // Controla se o passageiro tem carteira bloqueada ou não por pagamentos
    // pendentes
    public boolean isPendentePagamento() {
        return pendentePagamento;
    }

    public void marcarComoPendente() {
        this.pendentePagamento = true;
    }

    public void regularizarPendencia() {
        this.pendentePagamento = false;
    }

    // Para dar nota ao motorista que vai ser usado em corrida depois
    public void avaliarMotorista(Motorista motorista, int nota) {
        motorista.adicionarAvaliacao(nota);
    }

    // sobreescrita do toString() para retornar dados do passageiro
    @Override
    public String toString() {
        return "Passageiro: " + super.toString();
    }
}
