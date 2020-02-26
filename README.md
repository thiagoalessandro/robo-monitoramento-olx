# Robô de monitoramento de anúncios da olx

## Sobre a aplicação

Robô de monitoramento de novos anúncios da OLX com notificãção via e-mail e telegram.

## Sobre o desenvolvimento

```
Spring boot 
Postgres 10 
Docker 
Flyway
Jsoup
```

## Configurar notificação

Definir token e chatId do telegram

```yaml
telegram:
    baseUrl: XXX
    token: XXX
    chatId: XXX
```

Definir configurações de SMTP

```yaml
spring:
  mail:
    host: XXX
    password: XXX
    port: XXX
    username: XXX
```    

Definir enereço de e-mail a ser notificado

```sql
insert into public.tbl_configuracao (cd_usu_atu, dh_atu, id_sit, nome, valor) 
values('ADMIN', now(), 'A', 'EMAIL_TO', 'thiago.alessandro.farias@gmail.com');
```

## Configurar monitoramento

```sql
insert into public.tbl_monitoramento (cd_usu_atu, dh_atu, id_sit, link, titulo, bot) 
values ('ADMIN', now(), 'A', 'https://pa.olx.com.br/regiao-de-belem/autos-e-pecas/carros-vans-e-utilitarios/fiat/argo?re=38&rs=35&sf=1', 'Fiat Argo 2017 a 2019', 'OLX');
```
## Telegram - Como criar um bot?

1. Primeiro passo é ter um Telegram Bot. Para criá-lo, abra seu app do Telegram, busque por: @BotFather e clique sobre ele.
2. Envie o comando: /newbot.
3. Insira um nome para o seu bot.
4. Insira um username.
5. Feito isso, você terá o Token.

## Telegram - Como descobrir o chat id?

Acessar o link abaixo:

```
https://api.telegram.org/bot{TOKEN}/getUpdates
```

## Docker - Iniciar container postgres

```
docker-compose up -d
```

## Exemplo de notificações

### Telegram

![alt text](https://github.com/thiagoalessandro/robo-monitoramento-olx/blob/master/exemplo/bot-notificacao-telegram.jpg "Notificação via telegram")

### E-mail

![alt text](https://github.com/thiagoalessandro/robo-monitoramento-olx/blob/master/exemplo/bot-notificacao-email.png "Notificação via e-mail")
