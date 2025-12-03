package mobilidade;

import mobilidade.entidades.*;
import mobilidade.excessoes.*;
import mobilidade.servicos.ServicoCorridas;
//importando entidades, exceções e a classe servicoCorridas

import java.math.BigDecimal;
import java.util.Scanner;
/*Importando Bigdecimal para contas, evitando problemas com 
precisão. Scanner para ler oq for digitado no teclado*/

public class Main {
    // Ponto de entrada do programa, o Main é a parte principal do código

    private static final Scanner sc = new Scanner(System.in);
    /*
     * static --> pertence a classe, não a qualquer objeto
     * final --> atribuição final, definitiva
     * Scanner --> cria um leitor que le a entrada no console
     */

    private static final ServicoCorridas servico = new ServicoCorridas();
    /*
     * cria um objeto do tipo ServicoCorridas chamado servico,
     * é por meio dela que o main interage com o resto do código
     */

    public static void main(String[] args) {

        int opcao;
        // declaração de uma variável inteira

        do {
            // inicio de um laço do{} while{} que garante que oq for codado acontece ao
            // menos uma vez

            exibirMenu();
            // chama o exibirMenu() para imprimir as opções na tela

            opcao = lerInt("Escolha uma das opções para continuar: ");
            // A variavél int opcao recebe uma entrada do console

            switch (opcao) {
                /*
                 * estrutura de opções que escolhe qual bloco de código
                 * vai rodar com base na variável opcao
                 */

                case 1:
                    cadastrarPassageiro();
                    break;
                /*
                 * Se a opcao == 1, chama o metodo cadastrarPassageiro()
                 * --> vai criar um objeto tipo Passageiro que pergunta e recebe os dados
                 * do mesmo e manda para o serviço
                 */

                case 2:
                    cadastrarMotorista();
                    break;
                /*
                 * Se a opcao == 2, chama o metodo cadastrarMotorista()
                 * --> vai criar um objeto tipo Motorista que pergunta e recebe os dados
                 * do mesmo e manda para o serviço
                 */

                case 3:
                    motoristaOnlineOffline();
                    break;
                // chama o método que alterna o status do motorista

                case 4:
                    adicionarMetodoPagamentoPassageiro();
                    break;
                // Chama o método de cadastro de forma de pagamento pro passageiro

                case 5:
                    solicitarCorrida();
                    break;
                // Chama o fluxo de solicitação de corrida

                case 6:
                    iniciarCorrida();
                    break;
                // Chama o método pra iniciar uma corrida

                case 7:
                    finalizarCorrida();
                    break;
                // Chama o método que finaliza a corrida

                case 8:
                    listarCorridas();
                    break;
                // Lista todas as corridas com mais detalhes

                case 9:
                    avaliarCorrida();
                    break;
                // Fluxo p/avaliar passageiro e motorista após a corrida

                case 0:
                    System.out.println("Saindo...");
                    break;
                /*
                 * Imprime a saída "Saindo..." no terminal e fecha o laço do while,
                 * em que a condição é != 0
                 */

                default:
                    System.out.println("Opção inválida!");
                    /*
                     * Se nenhuma das opções acima for digitada na entrada de opcao
                     * --> imprime "Opção inválida!" no console
                     */

            }

            if (opcao != 0) {
                System.out.println("\nPressione ENTER para continuar...");
                sc.nextLine();
            }
            /*
             * Se o usuário não escolhe sair do programa
             * --> imprime a msg "Pressione ENTER para continuar..."
             * no terminal e sc.nextLine() lê a prox linha esperando o ENTER,
             * antes de mostrar o menu mais uma vez
             */

        } while (opcao != 0);
        sc.close();
        /*
         * Fecha o bloco do{} e a condição de repetição
         * Enquanto a opcao for != 0 o laço vai se repetir
         * voltando a exibirMenu()
         * Quando o usuário digita 0 o Scanner é fechado, fecha o método Main
         */

    }

    private static void exibirMenu() {
        // É o método exibir menu, que já chamamos antes no loop...

        System.out.println("Sistema de mobilidade urbana");
        System.out.println("1 - Cadastrar passageiro");
        System.out.println("2 - Cadastrar motorista");
        System.out.println("3 - Motorista online/offline");
        System.out.println("4 - Adicionar método de pagamento ao passageiro");
        System.out.println("5 - Solicitar corrida");
        System.out.println("6 - Iniciar corrida");
        System.out.println("7 - Finalizar Corrida");
        System.out.println("8 - Listar corridas");
        System.out.println("9 - Avaliar corrida");
        System.out.println("0 - Sair");
        // Só imprime o menu de opções no console, puramente visual

    }

    private static int lerInt(String msg) {
        System.out.print(msg);
        // mensagem a ser mostrada quando for pedir a entrada no terminal

        while (!sc.hasNextInt()) {
            // verifica se o proximo comando digitado e um inteiro

            System.out.print("Digite um número válido: ");
            sc.next();
            /*
             * Se não for digita a mensagem "Digite um número válido: "
             * no terminal e tira o que foi digitado pro loop não ficar infinito
             */

        }
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }
    // Lê o conteúdo válido inteiro, tira a quebra de linha e retorna o valor

    private static long lerLong(String msg) {
        System.out.print(msg);
        while (!sc.hasNextLong()) {
            System.out.print("Digite um número válido: ");
            sc.next();
        }
        long valor = sc.nextLong();
        sc.nextLine();
        return valor;
    }
    // Igual ao de int, mas com valores long(ID´s de objeto)

    private static BigDecimal lerBigDecimal(String msg) {
        System.out.print(msg);
        while (!sc.hasNextBigDecimal()) {
            System.out.print("Digite um número válido: ");
            sc.next();
        }
        BigDecimal valor = sc.nextBigDecimal();
        sc.nextLine();
        return valor;
    }
    // Usado para valores de dinheiro e distâncias, garante que oq foi digitado pode
    // ser BigDecimal

    private static String lerLinha(String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }
    // Imprime a msg e retorna toda a linha digitada pelo usuário, incluindo espaços

    // Declarando as opções do menu:
    private static void cadastrarPassageiro() {
        System.out.println("Cadastro do Passageiro: ");
        // Informa que o usuário está entrando no fluxo de cadastro

        String nome = lerLinha("Nome: ");
        String cpf = lerLinha("CPF: ");
        String email = lerLinha("Email: ");
        String telefone = lerLinha("Telefone: ");
        String senha = lerLinha("Senha: ");
        // Para cada dado chama o método lerLinha, com a msg apropriada

        Passageiro p = new Passageiro(nome, cpf, email, telefone, senha);
        // Cria um objeto p do tipo Passageiro, com seus respectivos atributos

        servico.cadastrarPassageiro(p);
        // Chama o servico para cadastrar o passageiro no "Banco de dados"

        System.out.println("Passageiro cadastrado. Seu ID: " + p.getId());
        // Mostra o ID gerado automaticamente para o Usuário
    }

    private static void cadastrarMotorista() {

        System.out.println("Cadastro do Motorista: ");
        // Mostra o cabeçalho pro Usuário

        String nome = lerLinha("Nome: ");
        String cpf = lerLinha("CPF: ");
        String email = lerLinha("Email: ");
        String telefone = lerLinha("Telefone: ");
        String senha = lerLinha("Senha: ");
        String cnh = lerLinha("CNH: ");
        String cnhValidaStr = lerLinha("CNH Válida? (s/n): ");
        boolean cnhValida = cnhValidaStr.equalsIgnoreCase("s");
        // Lê todos os dados/retorna cnhValida como true ou false se o Usuário digitar
        // (s/n)

        System.out.println("Dados do veículo: ");
        String placa = lerLinha("Placa do carro: ");
        String modelo = lerLinha("Modelo do carro: ");
        String cor = lerLinha("Cor do carro: ");
        Integer ano = lerInt("Ano de fabricação: ");
        // Lê as informações do veículo

        Veiculo veiculo = new Veiculo(placa, modelo, cor, ano);
        // Cria um objeto veículo

        Motorista m = new Motorista(nome, cpf, email, telefone, senha, cnh, cnhValida, veiculo);
        servico.cadastrarMotorista(m);
        // Cria um objeto do tipo motorista com todos os dados lidos
        // Registra o motorista no repositório

        System.out.println("Motorista cadastrado. Seu ID: " + m.getId());
        // Informa o ID para o usuário
    }

    private static void motoristaOnlineOffline() {
        System.out.println("Motoristas cadastrados: ");
        for (Motorista m : servico.listarMotoristas()) {
            System.out.println("ID " + m.getId() + " - " + m);
        }
        /*
         * Imprime o título "Motoristas cadastrados: "
         * retorna a lista de motoristas e
         * O for imprime o ID e o toString() de cada mototorista registrado
         */

        long id = lerLong("Informe o ID do motorista: ");
        Motorista m = servico.buscarMotoristaPorId(id);
        // Lê um ID e busca o motorista respectivo a esse ID no repositório

        if (m == null) {
            System.out.println("Motorista não encontrado.");
            return;
        }
        // Se não achar nenhum motorista com esse ID, avisa e retorna (Saí do método)

        System.out.println("1 - Ficar ONLINE");
        System.out.println("2 - Ficar OFFLINE");
        int opcao = lerInt("Escolha (1/2): ");
        /*
         * Mostra as opções de status e lê a escolha
         * digitada pelo usuário guardando ela em opcao
         */

        try {
            if (opcao == 1) {
                m.ficarOnline();
                System.out.println("O motorista está online.");
            } else if (opcao == 2) {
                m.ficarOffline();
                System.out.println("O motorista está offline.");
            } else {
                System.out.println("A opção selecionada é inválida.");
            }
        } catch (MotoristaInvalidoException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        /*
         * Um bloco try que verificar o valor de cnhVálida, e, se for false
         * ou se o motorista não tiver um veículo cadastrado retorna a
         * exception de MotoristaInválido...
         * 
         * Se for a opcao 1 coloca o status como ONLINE
         * 2 coloca OFFLINE
         * 
         * Se a exception acontecer, cai no catch e
         * mostra a msg "Bonitinha" pro usuário
         */
    }

    private static void adicionarMetodoPagamentoPassageiro() {
        System.out.println("Passageiros cadastrados: ");
        for (Passageiro p : servico.listarPassageiros()) {
            System.out.println("ID " + p.getId() + " - " + p);
        }
        // Lista todos os passageiros para o Usuário ver os seus ID´s

        long id = lerLong("Informe o ID do passageiro: ");
        Passageiro p = servico.buscarPassageiroPorId(id);
        // Lê o ID e busca o passageiro correspondete

        if (p == null) {
            System.out.println("Passageiro não encontrado.");
            return;
            // Se não achar o passageiro, saí do método
        }

        System.out.println("1 - Dinheiro (Carteira do App)");
        System.out.println("2 - Cartão de crédito");
        System.out.println("3 - Pix");
        int opcao = lerInt("Escolha a forma de pagamento (1/2/3)");
        // Mostra os tipos de método de pagamento e lê a escolha do Usuário

        switch (opcao) {

            case 1:
                BigDecimal saldoInicial = lerBigDecimal("Saldo inicial na carteira do App (R$): ");
                p.adicionarSaldo(saldoInicial);
                p.adicionarMetodoPagamento(new PagamentoDinheiro());
                System.out.println("Método de pagamento em dinheiro carregado e registrado.");
                break;
            /*
             * Pede um valor inicial de saldo na carteira para o Usuário
             * chamap p.adicionarSaldo que vai receber esse valor
             * Adiciona um pagamento do tipo dinheiro á lista de métodos de
             * pagamento do passageiro e informa que o pagamento foi registrado
             */

            case 2:
                String numero = lerLinha("Número do cartão: ");
                String nomeTitular = lerLinha("Nome do titular da conta: ");
                BigDecimal limite = lerBigDecimal("Limite disponível (R$): ");
                p.adicionarMetodoPagamento(new PagamentoCartaoCredito(numero, nomeTitular, limite));
                System.out.println("Cartão registrado.");
                break;
            /*
             * Lê os dados do cartão e cira PagamentoCartaoCredito que recebe os dados
             * do Usuário, adicionando o método de pagamento a passageiro
             */

            case 3:
                String chave = lerLinha("Chave Pix: ");
                p.adicionarMetodoPagamento(new PagamentoPix(chave));
                System.out.println("Pix registrado.");
                break;
            /*
             * Lê a chave, que recebe o número do pix do usuário
             * cria um pagamentoPix e adiciona ao Passageiro
             */

            default:
                System.out.println("Opção inválida.");
                // Caso o usuário digite uma opção inválida, um número fora do esperado etc...
        }

    }

    public static void solicitarCorrida() {
        System.out.println("Solicitar Corrida: ");
        // public --> pode ser chamado de fora da classe, estático
        // imprime a msg que começa o método

        if (servico.listarPassageiros().isEmpty()) {
            System.out.println("Não há passageiros cadastrados.");
            return;
        }
        if (servico.listarMotoristas().isEmpty()) {
            System.out.println("Não há motoristas cadastrados.");
            return;
        }
        /*
         * Verifica se há passageiros e motoristas no sistema
         * Se faltar algum não solicita a corritda --> return
         */

        System.out.println("Passageiros: ");
        for (Passageiro p : servico.listarPassageiros()) {
            System.out.println("ID " + p.getId() + " - " + p);
        }
        long idPass = lerLong("ID do passageiro: ");
        Passageiro passageiro = servico.buscarPassageiroPorId(idPass);
        /*
         * Lista os passageiros um a um, lendo o ID atribuido
         * e depois busca esse passageiro pelo ID fornecido pelo usuário
         */

        if (passageiro == null) {
            System.out.println("Passageiro não encontrado.");
            return;
            // Se não achar o passageiro encerra o fluxo
        }

        if (passageiro.getMetodosPagamento().isEmpty()) {
            System.out.println("O passageiro não possui método de pagamento registrado.");
            return;
            /*
             * Garante que o passageiro tenha ao menos uma forma de pagamento registrada
             * E caso não --> encerra o fluxo --> return
             */
        }

        System.out.println("Categoria da corrida: ");
        System.out.println("1 - COMUM");
        System.out.println("2 - LUXO");
        System.out.println("Escolha o tipo de corrida: (1/2)");
        int opcCat = lerInt("Opção: ");
        CategoriaCorrida categoria = (opcCat == 2) ? CategoriaCorrida.LUXO : CategoriaCorrida.COMUM;
        /*
         * Mostra as opções de corrida ao usuário e recebe a escolha dele em opcCat
         * usando um operador ternário --> if/else otimizado
         * Se for opcCat == 2, categoria = LUXO, caso contrário recebe COMUM
         */

        BigDecimal distancia = lerBigDecimal("Distância estimada em (Km): ");
        String origem = lerLinha("Origem: ");
        String destino = lerLinha("Destino: ");
        // Lê os dados específicos da corrida que o usuário deve fornecer

        try {
            Corrida c = servico.solicitarCorrida(passageiro, categoria,
                    passageiro.getMetodoPagamentoPadrao(), distancia, origem, destino);
            System.out.println("Corrida criada com ID: " + c.getId());
        } catch (PassageiroPendenteException | NenhumMotoristaDisponivelException e) {
            System.out.println("Erro ao solicitar a corrida: " + e.getMessage());
        }
        /*
         * Chama o servico.solicitarCorrida passando os dados de
         * pasageiro, categoria, metodo de pagamento...
         * Se tudo correr bem, recebe um objeto do c tipo Corrida e mostra o ID da
         * corrida
         * 
         * Se não, o catch pega alguma pendência de pagamento do passageiro ou
         * motorista não estando online e imprime a msg de ERRO
         */
    }

    private static void iniciarCorrida() {
        System.out.println("Iniciar corrida: ");
        listarCorridasSimples();
        // Imprime o cabeçalho que vai iniciar o método
        // Chama listarCorridasSimples() --> mostra o ID e status de cada corrida

        long id = lerLong("Informe o ID da corrida: ");
        Corrida c = servico.buscarCorridaPorId(id);
        // Lê o ID da corrida informado pelo usuário e busca a corrida correspondente

        if (c == null) {
            System.out.println("Corrida não encontrada.");
            return;
            // Se não achar a corrida, encerra o fluxo
        }

        try {
            servico.iniciarCorrida(c);
        } catch (EstadoInvalidoDaCorridaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        /*
         * Tenta iniciar a corrida via servico e se estado da
         * corrida for Inválido --> estiver finalizada, etc...
         * Cai no catch que imprime o ERRO
         */
    }

    private static void finalizarCorrida() {
        System.out.println("Finalizar corrida: ");
        listarCorridasSimples();
        // Igual a iniciar corrida, mas para finalizá-la

        long id = lerLong("Informe o ID da corrida: ");
        Corrida c = servico.buscarCorridaPorId(id);

        if (c == null) {
            System.out.println("Corrida não encontrada.");
            return;
        }
        try {
            servico.finalizarCorrida(c);
        } catch (EstadoInvalidoDaCorridaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        /*
         * Busca a corrida e chama servico.finalizarCorrida(c)
         * onde c --> corrida cuscada pelo ID
         * faz o procesamento do pagamento e marca as exceptions se der algo errado
         */
    }

    private static void listarCorridas() {
        System.out.println("Corridas: ");
        for (Corrida c : servico.listarCorridas()) {
            System.out.println("ID " + c.getId() + " - " + c);
        }
    }
    // Lista todas as corridas com toString() completo, com os dados fornecidos

    private static void listarCorridasSimples() {
        for (Corrida c : servico.listarCorridas()) {
            System.out.println("ID " + c.getId() + " - Status: " + c.getStatus());
        }
        /*
         * Versão menor, mostra só ID + status,
         * usada para quando precisar escolher uma corrida pela situação atual
         */
    }

    private static void avaliarCorrida() {
        System.out.println("Avaliação da corrida: ");
        listarCorridas();
        /*
         * Cabeçalho e listagem de todas as corridas,
         * de forma detalhada, para a escolha de qual avaliar
         */

        long id = lerLong("Informe o ID da corrida finalizada: ");
        Corrida c = servico.buscarCorridaPorId(id);
        if (c == null) {
            System.out.println("Corrida não encontrada.");
            return;
        }
        // Lê o ID da corrida e valida se ela exista no "Banco de dados"

        try {
            int notaMotorista = lerInt("Nota para o motorista (1 a 5): ");
            c.avaliarMotorista(notaMotorista);
            /*
             * Pergunta a nota do motorista ao passageiri/usuário
             * chama o c.avaliarMotorista, que recebe essa nota e verifica
             * se a corrida está finalizada
             * Se sim, chama motorista.Avaliacao(nota)
             */

            int notaPassageiro = lerInt("Nota para o passageiro (1 a 5): ");
            c.avaliarPassageiro(notaPassageiro);
            // Mesmo processo do motorista, mas para o passageiro

            System.out.println("Avaliações registradas.");
        } catch (EstadoInvalidoDaCorridaException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        /*
         * Se a corrida não tiver sido finalizada é lançado uma exception
         * e amensagem de ERRO é exibida
         */
    }
}