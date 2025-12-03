package mobilidade.entidades;

/*Classe abstrata usuario --> nada é instanciado aqui, 
e não é possivel criar um objeto do tipo Usuario 
Todas as suas classes filhas herdam seus atributos...*/
public abstract class Usuario {

    // Já comentei isso aqui em Corrida ou Passageiro ou Motorista se não me engano
    private static long proximoId = 1;

    /*
     * definindo os Atributos da classe abstrata Usuario
     * que vai servir de referência pra Motorista e Passageiro
     */
    private final long id;
    // final --> basicamente depois de setado no construtor nada muda
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String senha;

    private int quantidadeAvaliacoes;
    private int somaAvaliacoes;

    // Construtor da classe Usuário:
    protected Usuario(String nome, String cpf, String email, String telefone, String senha) {
        this.id = proximoId++; // --> soma 1 a Id
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
    }

    // Métodos gets para os atributos, para que eles podem ser acessados por outras
    // classes
    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getSenha() {
        return senha;
    }

    /*
     * Métodos específicos da classe Usuário:
     * protected --> Só classes filhas e outras no mesmo pacote podem chamar
     * Soma a nota a somaAvaliacoes, incrementa o número de avaliações em +1
     */
    protected void adicionarAvaliacao(int nota) {
        if (nota < 1 || nota > 5) {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 5.");
        } // --> Testa se a avaliação é valida e retorna a mensagem de erro ou a avaliação
        somaAvaliacoes += nota;
        quantidadeAvaliacoes++; // --> adicionando + 1 a quantidade de avaliações
    }

    // Retorna 0 se ninguém avaliou e faz cast para double pra não ter div inteira
    public double getMediaAvaliacoes() {
        if (quantidadeAvaliacoes == 0) {
            return 0.0;
        } else {
            return (double) somaAvaliacoes / quantidadeAvaliacoes;
        } /*
           * --> O método que calcula a média das avaliações,
           * podendo ser do cliente ou do motorista
           */
    }

    // Retorna o usuário de forma "Bonitinha" --> Sobreescrita do Método toString(),
    // com dados do Usuário
    @Override
    public String toString() {
        return nome + " (CPF: " + cpf + ", média: " + String.format("%.2f", getMediaAvaliacoes()) + ")";
    }

}
