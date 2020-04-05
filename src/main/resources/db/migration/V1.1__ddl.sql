create table tbl_configuracao
(
    id         bigserial not null
        constraint tbl_configuracao_pkey
            primary key,
    nome       varchar(25),
    valor      varchar(255),
    cd_usu_atu varchar(25),
    dh_atu     timestamp,
    id_sit     varchar(1)
);

create table tbl_arquivo
(
    id            bigserial not null
        constraint tbl_arquivo_pkey
            primary key,
    nome          varchar(100),
    dt_fechamento date,
    cartao        varchar(50),
    cd_usu_atu    varchar(25),
    dh_atu        timestamp,
    id_sit        varchar(1)
);

create table tbl_conta_recorrente
(
    id          bigserial   not null
        constraint tbl_assinatura_pkey
            primary key,
    descricao   varchar(100),
    valor       numeric(18, 2),
    categoria   varchar(50) not null,
    responsavel varchar(50),
    observacao  varchar(255),
    cd_usu_atu  varchar(25),
    dh_atu      timestamp,
    id_sit      varchar(1)
);

create table tbl_lancamento
(
    id                   bigserial not null
        constraint tbl_lancamento_pkey
            primary key,
    dt_compra            date,
    hash                 varchar(65) unique,
    descricao            varchar(255),
    observacao           varchar(255),
    tipo                 varchar(20),
    valor                numeric(18, 2),
    qtd_parcela          int,
    qtd_parcela_atual    int,
    qtd_parcela_restante int,
    categoria            varchar(50),
    sit_lancamento       varchar(50),
    cartao               varchar(50),
    responsavel          varchar(50),
    cd_usu_atu           varchar(25),
    dh_atu               timestamp,
    id_sit               varchar(1)
);

create index idx_tbl_lancamento_hash on tbl_lancamento (hash);

create table tbl_lembrete
(
    id          bigserial   not null
        constraint tbl_lembrete_pkey
            primary key,
    observacao  varchar(255),
    valor       numeric(18, 2),
    categoria   varchar(50) not null,
    dt_compra   date,
    responsavel varchar(50),
    cd_usu_atu  varchar(25),
    dh_atu      timestamp,
    id_sit      varchar(1)
);

