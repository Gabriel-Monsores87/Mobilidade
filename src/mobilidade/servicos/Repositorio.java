package mobilidade.servicos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Criando classe genérica T para ser substituída e usada em outras classes conforme necessário
//Serve como "Banco de dados" --> embora não seja um
public class Repositorio<T> {

    private final List<T> itens = new ArrayList<>();

    public void adicionar(T item) {
        itens.add(item);
    }

    public List<T> listarTodos() {
        return Collections.unmodifiableList(itens);
    }

}
