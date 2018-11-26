# ProjetoDengueSoS_v3
Esse projeto simula a criação de vários sistemas que, juntos, provém um comportamento maior do que o presente em apenas um sistema,
lembrando o conceito de sistemas-de-sistemas. Cada pasta é considerado um sistema do SoS, que juntos tem o intuito de prover o
comportamento emergente, denominado "evitar epidemia".

#Servico
Nessa pasta estão contidos as implementações dos sensores, onde são gerados dados aleatórios e armazenados em um banco de dados.
Esses dados são disponíveis por meio da implementação de uma camada de serviços, no qual outros sistemas que sabem o caminho, podem
ter acesso aos dados gerados pelos sensores.

#SistemaAlerta
Nessa pasta está contido a implementação do coordenador central, o responsável por obter os dados gerados pelos sensores e analisá-los.
Caso seja detectado a presença do mosquito Aedes, o coordenador central enviará alertas para os outros sitemas, a saber: Sistema da
Secrataria de Saúde e Sistema da Secrataria de Serviços Públicos.

#SistemaSecSaude
Nessa pasta está contido a implementação do sistema da Secretaria de Saúde, que contém uma camada de serviço para recebimento de alerta
proveniente do Sistema de Alerta.

#SistemaServicosPublicos
Nessa pasta está contido a implementação do sistema da Secretaria de Serviços Públicos, que contém uma camada de serviço para recebimento 
de alerta proveniente do Sistema de Alerta.

Essa implementação foi realizada localmente, de forma que as configurações devem estar de acordo com o endereço da máquina em que a 
aplicação será executada, pois os serviços são executados e acessados localmente. O código contém comentários que ajudam no melhor
entendimento e execução.
