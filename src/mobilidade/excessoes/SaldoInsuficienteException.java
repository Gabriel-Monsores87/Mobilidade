package mobilidade.excessoes;

/*Extends Exception --> torna essa classe uma exceção verificada +
O compilador trata com try/catch ou throws*/
public class SaldoInsuficienteException extends Exception {

    // Recebe uma msg e passa pro construtor da classe exception +
    // Essa msg aparece quando e.getMessage() --> capturando a exceção
    public SaldoInsuficienteException(String message) {
        super(message);
    }
}
