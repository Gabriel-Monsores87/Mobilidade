package mobilidade.entidades;

import mobilidade.excessoes.EstadoInvalidoDaCorridaException;
//importando a classe que da o erro em exception de algum estado inválido da corrida

import java.math.BigDecimal;

public class Corrida {

    private static long proximoId = 1;
    /*
     * cria uma váriavel fixa proximoId que vai servir como
     * parâmetro para a instanciação de ID´s
     * ex) this.Id = proximoId++;
     */

    // Criação de objetos do tipo passageiro, motorista etc...
    private final long id;
    private Passageiro passageiro;
    private Motorista motorista;
    private CategoriaCorrida categoria;
    private MetodoPagamento metodoPagamento;

    // Criação de atributos específicos para Corrida, que vão auxiliar em métodos e
    // instâncias
    private String origem;
    private String destino;
    private BigDecimal distanciaKm;

    private BigDecimal valorBase;
    private BigDecimal valorDistancia;
    private BigDecimal valorTotal;

    // Definindo um objeto status do tipo StatusCorrida que está SOLICITADA
    private StatusCorrida status = StatusCorrida.SOLICITADA;

    // Construtor de corrida, é aqui onde começamos a ficar tristes com esse código
    // pqp
    // basicamente armazena tudo e chama o método calcularValores()
    public Corrida(Passageiro passageiro, CategoriaCorrida categoria, MetodoPagamento metodoPagamento, String origem,
            String destino, BigDecimal distanciaKm) {
        this.id = proximoId++;
        this.passageiro = passageiro;
        this.categoria = categoria;
        this.metodoPagamento = metodoPagamento;
        this.origem = origem;
        this.destino = destino;
        this.distanciaKm = distanciaKm;

        calcularValores();
        /*
         * Essa parada aqui vai usar os dados recebidos da classe CategoriaCorrida
         * para calcular o preço da corrida
         */
    }

    private void calcularValores() {
        this.valorBase = categoria.getTarifaBase();
        this.valorDistancia = categoria.getValorPorKm().multiply(distanciaKm);
        this.valorTotal = valorBase.add(valorDistancia);
    }

    // Métodos gets para puxar os atributos para/de outras classes, nesse caso
    // para...
    public long getId() {
        return id;
    }

    public Passageiro getPassageiro() {
        return passageiro;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public MetodoPagamento getMetodoPagamento() {
        return metodoPagamento;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public StatusCorrida getStatus() {
        return status;
    }

    /*
     * Esse aqui é para atribuir um motorista a uma corrida, e isso só
     * pode ocorrer se a corrida estiver SOLICITADA
     */
    public void atribuirMotorista(Motorista motorista) throws EstadoInvalidoDaCorridaException {
        if (status != StatusCorrida.SOLICITADA) {
            throw new EstadoInvalidoDaCorridaException("Não é possível atribuir um motorista em outra corrida.");
        }
        this.motorista = motorista;
        this.status = StatusCorrida.ACEITA;
        motorista.iniciarCorrida();
    }

    /*
     * Aqui tanto o iniciar quanto o finalizar viagem vão controlar o ciclo
     * de vida de corrida, mudando seu estado conforme necessário
     */
    public void iniciarViagem() throws EstadoInvalidoDaCorridaException {
        if (status != StatusCorrida.ACEITA) {
            throw new EstadoInvalidoDaCorridaException("Corrida só pode ser iniciada após ser aceita.");
        }
        this.status = StatusCorrida.EM_ANDAMENTO;
    }

    public void finalizarViagem() throws EstadoInvalidoDaCorridaException {
        if (status != StatusCorrida.EM_ANDAMENTO) {
            throw new EstadoInvalidoDaCorridaException("A corrida só pode ser finalizada se estiver em andamento.");
        }
        this.status = StatusCorrida.FINALIZADA;
        motorista.finalizarCorrida();
    }

    /*
     * Aqui é um método de cancelamento da corrida, que só pode ocorrer se o
     * motorista
     * não tiver sido atribuído e a corrida tiver sido SOLICITADA
     */
    public void cancelar() throws EstadoInvalidoDaCorridaException {
        if (motorista != null || status != StatusCorrida.SOLICITADA) {
            throw new EstadoInvalidoDaCorridaException(
                    "O passageiro só pode cancelar a corrida após ser solicitada e o motorista não tiver aceito a viagem.");
        }
        this.status = StatusCorrida.CANCELADA;
    }

    /*
     * define o status da corrida com pagamento pendente --> vai servir para
     * bloquear futuras
     * corridas do cliente se necessário e puxar exception
     */
    public void marcarPendentePagamento() {
        this.status = StatusCorrida.PENDENTE_PAGAMENTO;
    }

    // mesma brisa para avaliarPassageiro(), basicamente checa se a corrida foi
    // finalizada e atribui nota ao motorista
    public void avaliarMotorista(int nota) throws EstadoInvalidoDaCorridaException {
        if (status != StatusCorrida.FINALIZADA) {
            throw new EstadoInvalidoDaCorridaException("Só é possível avaliar o motorista após finalizada a corrida.");
        }
        motorista.adicionarAvaliacao(nota);
    }

    public void avaliarPassageiro(int nota) throws EstadoInvalidoDaCorridaException {
        if (status != StatusCorrida.FINALIZADA) {
            throw new EstadoInvalidoDaCorridaException("Só é possível avaliar o passageiro após finalizada a corrida.");
        }
        passageiro.adicionarAvaliacao(nota);
    }

    // sobreescrita do toString() para retornar um status "bonitinho"
    @Override
    public String toString() {
        return "Corrida #" + id + " - " + origem + " --> " + destino + " - Categoria:  " + categoria + " - Valor: R$ "
                + valorTotal + " - Status: " + status;
    }

}
