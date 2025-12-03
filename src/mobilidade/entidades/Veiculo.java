package mobilidade.entidades;

//representa o carro de um motorista
public class Veiculo {

    // Atributos específicos da classe Veiculo:
    private String placa;
    private String modelo;
    private String cor;
    private int ano;

    // Método construtor da classe Veiculo:
    public Veiculo(String placa, String modelo, String cor, int ano) {
        this.placa = placa;
        this.modelo = modelo;
        this.cor = cor;
        this.ano = ano;
    }

    // Métodos gets para que outras classes possam chamar os atributos de Veiculo
    public String getPlaca() {
        return placa;
    }

    public String getModelo() {
        return modelo;
    }

    public String getCor() {
        return cor;
    }

    public int getAno() {
        return ano;
    }

    @Override // --> Sobreescrita do método toString()
    public String toString() {
        return modelo + " " + cor + " (" + ano + ") - Placa: " + placa;
    }
}
