# ROBÔ DE MONITORAMENTO DE ANÚNCIOS DA OLX

## NOTIFICAÇÃO

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

Definir e-mail a ser notificado

```postgresql
insert into public.tbl_configuracao (cd_usu_atu, dh_atu, id_sit, nome, valor) 
values('ADMIN', now(), 'A', 'EMAIL_TO', 'thiago.alessandro.farias@gmail.com');
```

## CONFIGURAR MONITORAMENTO

```postgresql
insert into public.tbl_monitoramento (cd_usu_atu, dh_atu, id_sit, link, titulo, bot) 
values ('ADMIN', now(), 'A', 'https://pa.olx.com.br/regiao-de-belem/autos-e-pecas/carros-vans-e-utilitarios/fiat/argo?re=38&rs=35&sf=1', 'Fiat Argo 2017 a 2019', 'OLX');
```
## COMO CRIAR UM BOT NO TELEGRAM?

1. Primeiro passo é ter um Telegram Bot. Para criá-lo, abra seu app do Telegram, busque por: @BotFather e clique sobre ele;
2. Envie o comando: /newbot;
3. Insira um nome para o seu bot;
4. Insira um username. ...
5. Feito isso, você receberá um Token. ...

## COMO DESCOBRIR O CHAT ID?

Acessar o link abaixo:

```
https://api.telegram.org/bot{TOKEN}/getUpdates
```

## INICIANDO CONTAINER POSTGRES

```
docker-compose up -d
```

## EXEMPLO DE NOTIFICAÇÕES

### TELEGRAM

Inline-style: 
![alt text](https://github.com/adam-p/markdown-here/raw/master/src/common/images/icon48.png "Logo Title Text 1")

### E-MAIL

Inline-style: 
![alt text](https://github.com/adam-p/markdown-here/raw/master/src/common/images/icon48.png "Logo Title Text 1")
