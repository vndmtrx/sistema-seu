# 📖 sistema-seu - Sistema Educacional Unificado

O Sistema SEU é um projeto acadêmico desenvolvido para servir como um modelo de sistema de gestão educacional. Utilizando Spring Boot, o sistema oferece uma plataforma completa para a administração de dados estudantis e escolares.

## Descrição do Projeto

O objetivo do Sistema SEU é ensinar sobre as práticas e conceitos essenciais no desenvolvimento de software, demonstrando a aplicação prática de Spring Boot em um contexto bem próximo do real. Durante o desenvolvimento, são abordados temas como arquitetura de software, gerenciamento de banco de dados, segurança, testes automatizados e integração contínua.

### Prováveis funcionalidades futuras:

1. Gestão Acadêmica: Matrículas, gerenciamento de cursos, turmas e horários.
1. Gestão de Dados Estudantis: Cadastro de alunos, acompanhamento de desempenho acadêmico, emissão de relatórios e históricos escolares.
1. Portal do Aluno e do Professor: Interface para acesso a informações acadêmicas, submissão de atividades e comunicação entre alunos e professores.
1. Segurança e Controle de Acesso: Implementação de autenticação e autorização para proteção dos dados.

A evolução do Sistema SEU é documentada de forma didática, permitindo que se possa acompanhar cada etapa do desenvolvimento, compreendendo as decisões de design e implementações técnicas realizadas.

## Tecnologias Utilizadas

- Java 22
- Spring Boot 3.3.2
- Spring Data JPA
- Spring Boot Actuator
- Spring Boot DevTools
- Thymeleaf
- Lombok

## Como Configurar o Ambiente de Desenvolvimento

### Requisitos

- Java 22
- Maven
- SDKMAN

### Instalação do Java 22 usando SDKMAN

1. **Instalar o SDKMAN**
   
   ```bash
   curl -s "https://get.sdkman.io" | bash
   source "$HOME/.sdkman/bin/sdkman-init.sh"
   ```

2. **Instalar o Java 22 e o Maven**
   
   ```bash
   sdk install java 22-tem
   sdk default java 22-tem
   sdk install maven 3.9.8
   sdk default maven 3.9.8
   ```

3. **Verificar a instalação**
   
   ```bash
   java -version
   ```

### Configuração com .sdkmanrc

Na raiz do projeto, há um arquivo `.sdkmanrc` para configurar o ambiente Java 22 do Temurin automaticamente:

```plaintext
# .sdkmanrc
java=22-tem
```

Para ativar a configuração do `.sdkmanrc`, execute:

```bash
sdk env install
```

### Executando a Aplicação

Para iniciar a aplicação, utilize o Maven:

```bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:9001`.

### Build e Execução com Docker

Para construir e executar a aplicação usando Docker, siga os passos abaixo:

1. **Entre na pasta do Projeto**

Você deve primeiro entrar em um dos projetos em desenvolvimento nesse repositório. Atualmente são os seguintes:

- diario-classe-v1

2. **Construir a imagem Docker**

Execute o comando abaixo para construir a imagem Docker da aplicação:

```bash
docker build -t PROJETO
```

3. **Executar a imagem Docker**

Execute o comando abaixo para rodar a imagem Docker construída:

```bash
docker run --rm -p 9001:9001 PROJETO
```

## Nota sobre o Banco de Dados

Atualmente, todos os projetos estão sendo escritos utilizando o SQLite embarcado diretamente no contêiner Docker. Isso significa que o arquivo do banco de dados SQLite está incluído na imagem Docker e, portanto, não é persistido entre execuções consecutivas do contêiner. Toda vez que o contêiner é parado e reiniciado, qualquer dado salvo no banco de dados será perdido e o banco será recriado do zero.

Essa abordagem é prática para desenvolvimento e testes locais, pois permite que o ambiente seja rapidamente configurado e executado sem a necessidade de dependências externas adicionais. No entanto, para ambientes de produção, esta configuração não é ideal devido à falta de persistência dos dados.

Futuras Melhorias: No futuro, planejamos alterar a arquitetura para utilizar um banco de dados externo, como PostgreSQL e/ou MySQL, o que permitirá a persistência dos dados entre execuções do contêiner e melhorará a robustez e a escalabilidade do sistema.

## Contribuindo

Se você deseja contribuir para o Sistema SEU, por favor, faça um fork do repositório e envie um pull request com suas melhorias e correções. Agradecemos antecipadamente por seu apoio!

## Licença

Este projeto está licenciado sob os termos da licença GNU Affero General Public License v3.0. Consulte o arquivo `LICENSE` para obter mais informações.
