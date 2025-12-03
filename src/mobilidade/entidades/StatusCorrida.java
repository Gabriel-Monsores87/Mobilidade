package mobilidade.entidades;

/*representa o status da corrida e quais tipos ela pode ter, 
usado para validar transações e "Contratos"
ex) iniciarViagem() só pode se estiver ACEITA*/
public enum StatusCorrida {
    SOLICITADA,
    ACEITA,
    EM_ANDAMENTO,
    FINALIZADA,
    CANCELADA,
    PENDENTE_PAGAMENTO
}
