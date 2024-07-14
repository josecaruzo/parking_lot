# Parking_lot
2º Tech Challenge - POS TECH FIAP

Essa aplicação possui interface gráfica para uma iteração.<br />
<br />
Foram criadas 4 tabelas para poder realizar este trabalho de gerenciamento de estacinamento<br />
- Usuário<br />
- Carro<br />
- Vagas de estacionamento<br />
- Ticket de estacionamento<br />


E o intuito da aplicação é poder realizar as ações de:<br />
<br />
Usuário<br />
- Criar usuário<br />
- Logar na aplicação<br />
- Alterar o usuário criado<br />

Carro <br />
- Consultar os carros do usuário<br />
- Criar carro de um usuário<br />
- Alterar carro de um usuário<br />
- Remover carro de um usuário<br />

Vagas de estacionamento<br />
- Se tratando de um sistema de estacionamento de uma cidade as vagas seriam como entidades que seriam mantidas pela cidade, <br />
 não se fazendo necessário o usuário da aplicação ter que gerenciá-las<br />

Ticket de estacionamento<br />
- Só pode realizar um estacionamento por vez<br />
- Criar ticket de estaciomento<br />
- Encerrar ticket de esacionmaneto (no momento da retirada do carro)<br />
- Consultar tickets de estacionamento de um usuário<br />


Para essa aplicação se utilizou: <br />
 - JDK 21 (Oracle Open JDK) <br />
 - Java 21 <br />
 - Gradle (Groovy) <br />
 - My SQL <br />

Passos para poder testar a aplicação:
- Criar o Schema no My SQL (Foi disponibilizado no arquivo parking_lot.sql)<br />
- Baixar, abrir o projeto e compilar a aplicação (As tabelas serão criadas automaticamente pelo Spring Boot).<br />
- Popule a tabela de parking_spots manualmente utilizando o arquivo parking_lot.sql
- Acesse a aplicação por http://localhost:8080/login ou http://localhost:8080 se preferir
- Cheguei a colocar o swagger na minha aplicação, mas como estou fazendo mapeamento do Controller para as telas não aparecem as chamadas da controller

   


