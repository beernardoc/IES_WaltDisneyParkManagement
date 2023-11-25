# Walt Disney Park Management

## Instruções de instalação e execução


### Instalação:
#### No projeto SpringBoot:
bash run.sh

### Execução:
De forma a conseguir visualizar o nosso protótipo, no index, deverá carregar no card "Magic Kingdom", e em seguida na atração "Walt Disney World Railroad".
Além disso, de volta no index, deverá carregar no card "Parking Cars 2" para acessar a página do parque de estacionamento.







##  Protótipo Funcional:



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
