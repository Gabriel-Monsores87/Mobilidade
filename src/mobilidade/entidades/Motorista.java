package mobilidade.entidades;

//importa a exception para uso em determinada situação
import mobilidade.excessoes.MotoristaInvalidoException;

public class Motorista extends Usuario{ //Motorista herdando Usuario, padrão

    //atributos de motorista
    private String cnh;

    //validação de CNH para permitir ficar ONLINE
    private boolean cnhValida;

    //referência para Veiculo
    private Veiculo veiculoAtivo;

    //Status começando como OFFLINE
    private StatusMotorista status = StatusMotorista.OFFLINE;
    
    //Construtor padrão, definindo atributos
    public Motorista(String nome, String cpf, String email, String telefone, String senha, String cnh, boolean cnhValida, Veiculo veiculoAtivo) {
        super(nome, cpf, email, telefone, senha);
        this.cnh = cnh;
        this.cnhValida = cnhValida;
        this.veiculoAtivo = veiculoAtivo;
    }

    //Métodos get´s para os atributos serem usados em outras classes
    public String getCnh() {
        return cnh;
    }

    //chamando o método que vai verificar a validade da CNH
    public boolean isCnhValida() {
        return cnhValida;
    }

    public Veiculo getVeiculoAtivo() {
        return veiculoAtivo;
    }

    public StatusMotorista getStatus() {
        return status;
    }

    //Especificando que só pode ficar online com CNH válida e veículo registrado
    public void ficarOnline() throws MotoristaInvalidoException{
        if(!cnhValida || veiculoAtivo == null){
            throw new MotoristaInvalidoException("Motorista inválido para ficar online (CNH ou veículo).");
        }
        this.status = StatusMotorista.ONLINE;
    }

    //Métodos que são usados na Corrida para verificar o status do motorista
    public void ficarOffline(){
        this.status = StatusMotorista.OFFLINE;
    }

    public void iniciarCorrida(){
        this.status = StatusMotorista.EM_CORRIDA;
    }

    public void finalizarCorrida(){
        this.status = StatusMotorista.ONLINE;
    }

    //Sobreescrita do método toString() --> retorna uma mensagem com o status do motoris "Bonitinho"
    @Override
    public String toString() {
        return "Motorista: " + super.toString() + " - " + veiculoAtivo + " - Status: " + status;
    }
}
