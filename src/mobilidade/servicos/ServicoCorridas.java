package mobilidade.servicos;

//importando os outros pacotes
import mobilidade.entidades.*;
import mobilidade.excessoes.*;

import java.math.BigDecimal;
import java.util.List;

//Essa classe vai juntar tudo e fazer as "regras de negócio"
public class ServicoCorridas {

    // Cadastro/listagem/busca de uma vez só
    private final Repositorio<Passageiro> passageiros = new Repositorio<>();
    private final Repositorio<Motorista> motoristas = new Repositorio<>();
    private final Repositorio<Corrida> corridas = new Repositorio<>();

    // cadastro do passageiro e do motorista:
    public void cadastrarPassageiro(Passageiro passageiro) {
        passageiros.adicionar(passageiro);
    }

    public void cadastrarMotorista(Motorista motorista) {
        motoristas.adicionar(motorista);
    }

    // Listas com os passageiors, motoristas e corridas armazenadas:
    public List<Passageiro> listarPassageiros() {
        return passageiros.listarTodos();
    }

    public List<Motorista> listarMotoristas() {
        return motoristas.listarTodos();
    }

    public List<Corrida> listarCorridas() {
        return corridas.listarTodos();
    }

    // Buscando passagerio, corrida ou motorista por ID:
    public Passageiro buscarPassageiroPorId(long id) {
        return passageiros.listarTodos().stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public Motorista buscarMotoristaPorId(long id) {
        return motoristas.listarTodos().stream().filter(m -> m.getId() == id).findFirst().orElse(null);
    }

    public Corrida buscarCorridaPorId(long id) {
        return corridas.listarTodos().stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    /*
     * Regras das corridas, com exceptions que não permiter ações fora da norma
     * como pendências de pagamento ou motoristas offline.
     * Cria corrida, atribui motorista, guarda no repositório...
     */
    public Corrida solicitarCorrida(Passageiro passageiro, CategoriaCorrida categoria,
            MetodoPagamento metodoPagamento, BigDecimal distanciaKm, String origem,
            String destino) throws PassageiroPendenteException, NenhumMotoristaDisponivelException {

        if (passageiro.isPendentePagamento()) {
            throw new PassageiroPendenteException(
                    "O passageiro possui pendência de pagamento --> Não pode solicitar nova corrida.");
        }

        // Filtra os motoristas com status ONLINE, se não achar lança a exception
        Motorista motoristaDisponivel = motoristas.listarTodos().stream()
                .filter(m -> m.getStatus() == StatusMotorista.ONLINE).findFirst()
                .orElseThrow(
                        () -> new NenhumMotoristaDisponivelException("Nenhum motorista online disponível no momento."));

        Corrida corrida = new Corrida(passageiro, categoria, metodoPagamento,
                origem, destino, distanciaKm);
        try {
            corrida.atribuirMotorista(motoristaDisponivel);
        } catch (EstadoInvalidoDaCorridaException e) {
            throw new RuntimeException(e);
        }

        corridas.adicionar(corrida);
        System.out.println("Corrida solicitada com sucesso: " + corrida);
        return corrida;
    }

    // Assumindo uma categoria de corrida COMUM e um método padrão de pagamento:
    // Sobrecarga do método --> com parâmetros diferentes
    public Corrida solicitarCorrida(Passageiro passageiro, BigDecimal distanciaKm,
            String origem, String destiono) throws PassageiroPendenteException,
            NenhumMotoristaDisponivelException {
        return solicitarCorrida(passageiro, CategoriaCorrida.COMUM, passageiro.getMetodoPagamentoPadrao(),
                distanciaKm, origem, destiono);
    }

    // Chama iniciar ou finalizar, oq vai mudar o status e atualizar o motorista
    // tenta processar o pagamento --> Se houver exception:
    // Marca corrida como pendente ou
    // Marca passageiro como pendente --> tem que regularizar senão não pede mais
    // corridas
    public void iniciarCorrida(Corrida corrida) throws EstadoInvalidoDaCorridaException {
        corrida.iniciarViagem();
        System.out.println("Corrida iniciada: " + corrida);
    }

    public void finalizarCorrida(Corrida corrida) throws EstadoInvalidoDaCorridaException {
        corrida.finalizarViagem();
        System.out.println("Corrida finalizada (pré pagamento): " + corrida);

        try {
            corrida.getMetodoPagamento().processarPagamento(corrida.getValorTotal(),
                    corrida.getPassageiro());
        } catch (SaldoInsuficienteException | PagamentoRecusadoException e) {
            corrida.marcarPendentePagamento();
            corrida.getPassageiro().marcarComoPendente();
            System.out.println("Falha no pagamento: " + e.getMessage());
        }
    }
}
