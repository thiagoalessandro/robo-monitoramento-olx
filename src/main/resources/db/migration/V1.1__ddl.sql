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
    id         bigserial not null
        constraint tbl_arquivo_pkey
            primary key,
    nome       varchar(100),
    tag        varchar(50),
    cd_usu_atu varchar(25),
    dh_atu     timestamp,
    id_sit     varchar(1)
);

create table tbl_assinatura
(
    id              bigserial not null
        constraint tbl_assinatura_pkey
            primary key,
    descricao       varchar(100),
    tag_responsavel varchar(50),
    cd_usu_atu      varchar(25),
    dh_atu          timestamp,
    id_sit          varchar(1)
);

create table tbl_lancamento
(
    id                   bigserial not null
        constraint tbl_lancamento_pkey
            primary key,
    dt_compra            date,
    descricao            varchar(255),
    qtd_parcela          int,
    qtd_parcela_paga     int,
    qtd_parcela_restante int,
    sit_lancamento       varchar(50),
    tag_responsavel      varchar(50),
    id_ass               varchar(100),
    cd_usu_atu           varchar(25),
    dh_atu               timestamp,
    id_sit               varchar(1)
);
