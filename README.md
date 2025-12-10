# Sistema de Mobilidade Urbana (Java)

Protótipo de um sistema de corridas estilo Uber/99/Cabify, desenvolvido em Java para a disciplina de Orientação a Objetos.

O projeto simula um ecossistema de mobilidade urbana, onde **passageiros** solicitam corridas e **motoristas** oferecem o serviço, com diferentes **métodos de pagamento**, **categorias de corrida** e **tratamento de exceções de negócio**.

---

## Tecnologias e Conceitos Utilizados

- **Linguagem:** Java (8+)
- **Paradigma:** Programação Orientada a Objetos
- **Conceitos aplicados:**
  - Encapsulamento
  - Herança
  - Polimorfismo (interfaces, sobrescrita e sobrecarga)
  - Generics (`Repositorio<T>`)
  - Exceções personalizadas (checked exceptions)
  - Organização em pacotes (camadas: entidades, serviços, exceções)

---

## Estrutura de Pacotes

```text
src/
└── mobilidade/
    ├── Main.java
    ├── entidades/
    │   ├── Usuario.java
    │   ├── Passageiro.java
    │   ├── Motorista.java
    │   ├── Veiculo.java
    │   ├── Corrida.java
    │   ├── MetodoPagamento.java
    │   ├── PagamentoDinheiro.java
    │   ├── PagamentoCartaoCredito.java
    │   ├── PagamentoPix.java
    │   ├── StatusMotorista.java
    │   ├── StatusCorrida.java
    │   └── CategoriaCorrida.java
    ├── servicos/
    │   ├── Repositorio.java
    │   └── ServicoCorridas.java
    └── excessoes/
        ├── SaldoInsuficienteException.java
        ├── PagamentoRecusadoException.java
        ├── NenhumMotoristaDisponivelException.java
        ├── EstadoInvalidoDaCorridaException.java
        ├── PassageiroPendenteException.java
        └── MotoristaInvalidoException.java
```

---

## Visão Geral das Principais Classes

### `Main` (pacote `mobilidade`)

- Classe responsável pela **interface de linha de comando**.
- Exibe um **menu interativo** e chama os métodos da camada de serviço.
- Fluxos principais:
  - Cadastro de passageiro e motorista
  - Motorista online/offline
  - Cadastro de método de pagamento
  - Solicitação de corrida
  - Início e fim da corrida
  - Avaliação de passageiro e motorista

---

### Pacote `mobilidade.entidades`

#### `Usuario` (abstrata)

- Superclasse para `Passageiro` e `Motorista`.
- Atributos comuns:
  - `id` (gerado automaticamente)
  - `nome`, `cpf`, `email`, `telefone`, `senha`
- Controle de avaliações:
  - `quantidadeAvaliacoes`
  - `somaAvaliacoes`
  - `getMediaAvaliacoes()`

#### `Passageiro`

- Especializa `Usuario`.
- Atributos:
  - `pendentePagamento : boolean`
  - `saldoCarteira : BigDecimal`
  - `metodosPagamento : List<MetodoPagamento>`
- Responsabilidades:
  - Gerenciar **saldo da carteira** (para pagamento em dinheiro)
  - Gerenciar **métodos de pagamento** cadastrados
  - Indicar se está com **pendência de pagamento**

#### `Motorista`

- Especializa `Usuario`.
- Atributos:
  - `cnh : String`
  - `cnhValida : boolean`
  - `veiculoAtivo : Veiculo`
  - `status : StatusMotorista` (`ONLINE`, `OFFLINE`, `EM_CORRIDA`)
- Responsabilidades:
  - Ficar `ONLINE` ou `OFFLINE`
  - Associar um veículo ativo
  - Ser vinculado a uma corrida

#### `Veiculo`

- Dados básicos de um carro:
  - `placa`, `modelo`, `cor`, `ano`.

#### Enums

- `StatusMotorista`: `ONLINE`, `OFFLINE`, `EM_CORRIDA`
- `StatusCorrida`: `SOLICITADA`, `ACEITA`, `EM_ANDAMENTO`, `FINALIZADA`, `CANCELADA`, `PENDENTE_PAGAMENTO`
- `CategoriaCorrida`:
  - `COMUM` → tarifa-base e valor por km
  - `LUXO` → tarifa-base e valor por km maiores
  - Cada categoria guarda:
    - `tarifaBase : BigDecimal`
    - `valorPorKm : BigDecimal`

#### Estratégias de pagamento (`Strategy`)

Interface:

```java
public interface MetodoPagamento {
    BigDecimal processarPagamento(BigDecimal valor, Passageiro passageiro)
        throws SaldoInsuficienteException, PagamentoRecusadoException;

    String getDescricao();
}
```

Implementações:

- `PagamentoDinheiro`
  - Usa o saldo da carteira no app.
  - Pode lançar `SaldoInsuficienteException`.
- `PagamentoCartaoCredito`
  - Armazena `numeroCartao`, `nomeTitular`, `limiteDisponivel`.
  - Pode lançar `PagamentoRecusadoException`.
- `PagamentoPix`
  - Usa uma `chavePix` (simulação).
  - No protótipo, o pagamento é sempre aprovado.

#### `Corrida`

- Entidade central do sistema.
- Atributos principais:
  - `id`
  - `passageiro`
  - `motorista`
  - `categoria`
  - `metodoPagamento`
  - `origem`, `destino`, `distanciaKm`
  - `valorBase`, `valorDistancia`, `valorTotal`
  - `status : StatusCorrida`
- Responsabilidades:
  - Calcular valores com base em:
    - tarifa base da `CategoriaCorrida`
    - multiplicador por quilômetro
  - Gerenciar o ciclo de vida:
    - `atribuirMotorista(...)`
    - `iniciarViagem()`
    - `finalizarViagem()`
    - `cancelar()`
  - Marcar corrida como `PENDENTE_PAGAMENTO` em caso de erro
  - Registrar avaliações:
    - `avaliarMotorista(nota)`
    - `avaliarPassageiro(nota)`

---

### Pacote `mobilidade.servicos`

#### `Repositorio`

- Implementa um repositório genérico simples.
- Internamente usa `List<Object>` (no código) para armazenar os itens.
- Métodos:
  - `adicionar(item)`
  - `listarTodos()`

#### `ServicoCorridas`

- Classe de **regras de negócio**.
- Responsável por:
  - Armazenar:
    - Passageiros
    - Motoristas
    - Corridas
  - Métodos principais:
    - `cadastrarPassageiro(Passageiro p)`
    - `cadastrarMotorista(Motorista m)`
    - `listarPassageiros()`, `listarMotoristas()`, `listarCorridas()`
    - `buscarPassageiroPorId(long id)`
    - `buscarMotoristaPorId(long id)`
    - `buscarCorridaPorId(long id)`
    - `solicitarCorrida(...)`  
      - Verifica pendência do passageiro  
      - Procura motorista `ONLINE`  
      - Cria e registra a corrida
    - `iniciarCorrida(Corrida c)`
    - `finalizarCorrida(Corrida c)`  
      - Finaliza a corrida  
      - Tenta processar o pagamento  
      - Em caso de falha, marca a corrida como `PENDENTE_PAGAMENTO` e o passageiro como pendente

---

### Pacote `mobilidade.excessoes`

Exceções de domínio que representam erros de negócio:

- `SaldoInsuficienteException`
- `PagamentoRecusadoException`
- `NenhumMotoristaDisponivelException`
- `EstadoInvalidoDaCorridaException`
- `PassageiroPendenteException`
- `MotoristaInvalidoException`

Essas exceções são usadas no fluxo para:

- Impedir que um passageiro com pendências peça nova corrida.
- Impedir que um motorista inválido (CNH vencida/sem veículo) fique online.
- Bloquear transições de estado inválidas (ex.: tentar finalizar corrida que não foi iniciada).
- Lidar com falhas no pagamento.

---

## Fluxos de Uso (Menu)

Ao executar o programa, o console apresenta um menu semelhante a:

1. Cadastrar passageiro  
2. Cadastrar motorista  
3. Motorista online/offline  
4. Adicionar método de pagamento ao passageiro  
5. Solicitar corrida  
6. Iniciar corrida  
7. Finalizar corrida  
8. Listar corridas  
9. Avaliar corrida  
0. Sair  

### Exemplos de fluxos:

- **Cadastro inicial:**
  1. Opção `1` → cadastra passageiro  
  2. Opção `2` → cadastra motorista e veículo  
  3. Opção `3` → coloca um motorista como `ONLINE`  

- **Configuração de pagamento:**
  1. Opção `4` → adiciona:
     - Dinheiro (saldo em carteira)
     - Cartão de crédito
     - Pix

- **Realizar uma corrida:**
  1. Opção `5` → solicitar corrida  
     - escolhe passageiro  
     - escolhe categoria (COMUM/LUXO)  
     - informa origem, destino e distância (km)  
  2. Opção `6` → iniciar corrida  
  3. Opção `7` → finalizar corrida  
     - processamento de pagamento é disparado  
     - em caso de problema → corrida fica `PENDENTE_PAGAMENTO`  

- **Avaliar usuários:**
  1. Opção `9` → avalia o motorista e o passageiro após uma corrida finalizada  

---

## Como Compilar e Executar

### Pré-requisitos

- **Java JDK 8+** instalado
- Opcional: **VS Code**, IntelliJ ou outra IDE

### Via linha de comando

Na raiz do projeto (onde está a pasta `src`):

#### Linux / macOS

```bash
# Compilar
javac -d out $(find src -name "*.java")

# Executar
java -cp out mobilidade.Main
```

#### Windows (PowerShell)

```powershell
# Compilar
javac -d out (Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName })

# Executar
java -cp out mobilidade.Main
```

> Se estiver usando VS Code ou IntelliJ, basta importar o projeto como **projeto Java** e executar a classe `mobilidade.Main`.

---

## UML

O diagrama de classes foi modelado em **PlantUML** e representa:

- Pacotes:
  - `mobilidade`
  - `mobilidade.entidades`
  - `mobilidade.servicos`
  - `mobilidade.excessoes`

##  Descrição do Diagrama de Classes – Sistema de Mobilidade Urbana

O diagrama de classes apresentado modela a arquitetura do sistema de mobilidade urbana, inspirado em aplicações de transporte por aplicativo (como Uber, 99 ou Cabify). As classes estão organizadas em quatro pacotes principais:

- mobilidade – interface de usuário  
- mobilidade.entidades – modelo de domínio  
- mobilidade.servicos – regras de negócio  
- mobilidade.excessoes – tratamento de exceções  

Essa organização favorece a *modularidade* e a *separação de responsabilidades*.

---

### 1. Pacote mobilidade – Interface com o usuário

A classe Main concentra a interface em modo texto do sistema. Ela possui dois atributos estáticos:

- um Scanner, utilizado para leitura de dados via console;  
- uma instância de ServicoCorridas, responsável pelas operações de negócio.

O método main implementa um laço de menu, lendo a opção do usuário e delegando as ações para métodos privados como:

- cadastrarPassageiro()  
- cadastrarMotorista()  
- solicitarCorrida()  
- iniciarCorrida()  
- finalizarCorrida()  
- avaliarCorrida()

A classe Main não contém regras de negócio complexas; sua função é *orquestrar a interação com o usuário* e encaminhar as requisições para a camada de serviço, mantendo um bom acoplamento entre interface e lógica de domínio.

---

### 2. Pacote mobilidade.entidades – Modelo de domínio

No centro do modelo temos a classe abstrata Usuario, que representa os atributos comuns a todos os usuários do sistema:

- id  
- nome  
- cpf  
- email  
- telefone  
- senha

Além disso, Usuario mantém o controle das avaliações recebidas por meio dos atributos quantidadeAvaliacoes e somaAvaliacoes, bem como do método protegido adicionarAvaliacao(int nota) e do método público getMediaAvaliacoes().  
A geração de id é feita por um atributo estático proximoId, ilustrando o uso de *escopo de classe*.

As classes Passageiro e Motorista estendem Usuario, exemplificando o uso de *herança*.

#### Passageiro

Passageiro adiciona atributos específicos, como:

- pendentePagamento  
- saldoCarteira  
- uma lista de metodosPagamento (associação 1-para-muitos com MetodoPagamento)

Essa classe oferece operações para cadastrar métodos de pagamento, gerenciar o saldo da carteira e marcar ou regularizar pendências financeiras.

#### Motorista

Motorista inclui os atributos:

- cnh  
- cnhValida  
- veiculoAtivo  
- status

Há uma associação 1–1 entre Motorista e Veiculo, que agrupa informações sobre *placa, modelo, cor e ano* do veículo.  
O atributo status utiliza o enum StatusMotorista, que indica se o motorista está ONLINE, OFFLINE ou EM_CORRIDA.

#### Corrida

A classe Corrida é a entidade central do sistema. Ela se associa a:

- um Passageiro  
- um Motorista  
- uma CategoriaCorrida (enum que define COMUM ou LUXO, cada uma com tarifaBase e valorPorKm)  
- um MetodoPagamento (interface implementada por diferentes estratégias de pagamento)

Além disso, Corrida possui atributos como:

- origem  
- destino  
- distanciaKm  
- valorBase  
- valorDistancia  
- valorTotal  
- status (baseado no enum StatusCorrida: SOLICITADA, ACEITA, EM_ANDAMENTO, FINALIZADA, CANCELADA, PENDENTE_PAGAMENTO)

Métodos como atribuirMotorista(), iniciarViagem(), finalizarViagem() e cancelar() controlam o *ciclo de vida da corrida*, lançando EstadoInvalidoDaCorridaException quando uma transição de estado é inconsistente com as regras de negócio.

---

### 3. Métodos de pagamento e polimorfismo

O sistema adota o padrão de projeto *Strategy* por meio da interface MetodoPagamento. Ela define o comportamento:

```java
processarPagamento(BigDecimal valor, Passageiro passageiro)

---

## Conceitos de Orientação a Objetos Evidenciados

- **Encapsulamento**
  - Atributos privados com acesso via getters/setters.
  - Alterações de estado controladas por métodos de negócio (ex.: `iniciarViagem()`, `finalizarViagem()`).

- **Herança**
  - `Passageiro` e `Motorista` herdam de `Usuario`.

- **Polimorfismo**
  - **Por interface:** `MetodoPagamento` e suas implementações.
  - **Por sobrescrita:** `toString()` em várias classes; implementação específica de métodos de pagamento.
  - **Por sobrecarga:** versões diferentes de `solicitarCorrida` em `ServicoCorridas` (quando existir no código).

- **Polimorfismo paramétrico (Generics)**
  - `Repositorio<T>` (conceito de repositório genérico para entidades do domínio).

- **Exceções personalizadas**
  - Tratamento de regras de negócio via exceções específicas em vez de erros genéricos.

---

## Possíveis Melhorias Futuras

Algumas ideias para evolução do projeto:

- Persistência em **banco de dados** ao invés de listas em memória.
- Implementar **filtros de localização** para motoristas próximos.
- Simulação de **tempo real** (atualização de rota, localização).
- Tela gráfica (JavaFX, Swing ou aplicação Web) em vez de console.
- Histórico detalhado de corridas por passageiro/motorista.
- Relatórios (corridas por período, por categoria, faturamento simulado, etc.).

---
