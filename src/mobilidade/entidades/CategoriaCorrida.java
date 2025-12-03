package mobilidade.entidades;

import java.math.BigDecimal;

public enum CategoriaCorrida { /*servem para criar atributos, métodos etc ... 
                                fixos, instanciados implicitamente*/

    COMUM(new BigDecimal("5.00"), new BigDecimal("1.00")),
    LUXO(new BigDecimal("9.00"), new BigDecimal("2.20"));
    // --> Definindo os valores para os tipos de corrida, podendo ser comum ou de luxo

    // --> Atribuindo varíaveis específicas:
    private final BigDecimal tarifaBase;
    private final BigDecimal valorPorKm;

    CategoriaCorrida (BigDecimal tarifaBase, BigDecimal valorPorKm){
        this.tarifaBase = tarifaBase;
        this.valorPorKm = valorPorKm;

    }

    //Métodos gets, para que outras classes possam acessar esses atributos
    public BigDecimal getTarifaBase() {
        return tarifaBase;
    }

    public BigDecimal getValorPorKm() {
        return valorPorKm;
    }

    /*Método específico da classe Corrida, 
    que calcula o valor da corrida com base no preço fixo 
    e nos Km rodados */
    public BigDecimal calcularPreco(BigDecimal distanciaKm){
        return tarifaBase.add(valorPorKm.multiply(distanciaKm)); 
    }
}
