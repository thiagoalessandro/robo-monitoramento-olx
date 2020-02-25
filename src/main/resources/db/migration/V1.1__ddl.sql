create table tbl_configuracao
(
    id         bigserial not null
        constraint tbl_configuracao_pkey
            primary key,
    cd_usu_atu varchar(25),
    dh_atu     timestamp,
    id_sit     varchar(1),
    nome       varchar(25),
    valor      varchar(255)
);

create table tbl_monitoramento
(
    id         bigserial not null
        constraint tbl_monitoramento_pkey
            primary key,
    cd_usu_atu varchar(25),
    dh_atu     timestamp,
    id_sit     varchar(1),
    link       text,
    bot        varchar(15),
    titulo     varchar(255)
);

create table tbl_anuncio
(
    id         bigint not null
        constraint tbl_anuncio_pkey
            primary key,
    cd_usu_atu varchar(25),
    dh_atu     timestamp,
    id_sit     varchar(1),
    id_mnt     bigint    not null
        constraint fk_anuncio_id_mnt
            references tbl_monitoramento,
    detalhe    text,
    imagem     text,
    link       text,
    titulo     varchar(255),
    valor      varchar(25)
);
