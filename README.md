# Walt Disney Park Management

## Instruções de instalação e execução

`bash run.sh` (na pasta raiz do projeto springboot)

## Credenciais de acesso:

| Email             | Senha  | 
|-------------------|--------|
| marianadias@ua.pt | 12345  |



## Iteração 2: 29/11/2023
- Basic data pipeline in-place: data streams generation, transmission, and storage.
- Product increment covering (at least) a user story related to data access (browse current values from streams, in the web presentation layer.)
- Increment deployed (in containers) at the server environment

### User Storie:
Como técnico responsável pela manutenção preventiva, quero ter uma visão completa dos parâmetros de funcionamento de uma atração, para poder tomar decisões informadas sobre a necessidade de intervenção.


### Guia de utilização:
1) Realizar login com as credenciais informadas
2) Selecionar o parque "Magic Kingdom Park"
3) Selecionar a atração "Walt Disney World Railroad"
4) Visualizar os dados da atração. Entre eles:
    - Tempo restante até o próximo ciclo
    - Visitantes esperados
    - Velocidade atual
    - Número de pessoas na fila
    - Altura
    - Temperatura
    - Vibração





##  Iteração 1.2: 15/11/2023
- Draft Project Specification (report); must include the Architecture Notebook part.
- Prototypes for the core user stories.

### index.html
#### (localhost:8080)


Nesta página mostramos o painel de opções a que um administrador tem acesso: cards com os nomes de todos os parques, o número de visitantes esperados para o dia, um histórico de atividades, um gráfico lotação/hora do dia e ainda um gráfico circular que mostra a percentagem de ocupantes em cada parque.

### park.html
#### (localhost:8080/parks/{parkName})

Esta página é mostrada quando o utilizador clica num parque da Walt Disney World e pretende ver todas as informações relativamente a ele, nomeadamente: cards com os nomes das zonas desse parque, o número de visitantes esperados nele, as atividades recentes desse parque, um gráfico lotação/hora do dia, um gráfico circular com a pergentagem dos ocupantes dividido pelas atrações disponíveis nessa zona e ainda, um mapa.

### attraction.html 
#### (localhost:8080/parks/{parkName}/attractions/{attractionName}")

Aqui, é vísivel, toda a informação relativa a cada atração daquela zona, tais como: aberta/fechada, horário de funcionamento, duração da diversão, número de visitantes esperados e a data da última manutenção de rotina feita. Também, um gráfico com o número de pessoas à espera na fila em cada momento, a média do tempo de espera da última pessoa na fila, um histórico de atividades, um gráfico de controlo de humidade e ainda uma tabela com algumas informações mais técnicas. 

### parking-lot.html 
#### (localhost:8080//Cars/{parkName})

Esta página mostra as informações relevantes sobre os parques de estacionamento, como: se o mesmo se encontra aberto ou fechado(e o motivo), o seu horário de funcionamento, a lotação máxima/atual e ainda o número de carros esperados.
