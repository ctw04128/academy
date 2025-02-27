create table t_team
(
    id               BIGINT        DEFAULT random()      PRIMARY KEY,
    name             varchar(255)                        not null,
    product          varchar(20)                         not null,
    created_at       timestamp     default now(),
    modified_at      timestamp     default now(),
    default_location varchar(20)
        constraint location_constraint
            check ((default_location)::text = ANY
        ((ARRAY ['LISBON'::character varying, 'PORTO'::character varying, 'BRAGA'::character varying])::text[]))
    );
CREATE SEQUENCE IF NOT EXISTS SEQ_TEAM_ID;

alter table t_team
    owner to postgres;

create table t_rack
(
    id               BIGINT      DEFAULT random()           PRIMARY KEY,
    serial_number    varchar(20)                            not null
        constraint unique_serial
            unique,
    team_id          BIGINT      DEFAULT random()           not null
        references t_team,
    created_at       timestamp   default now(),
    status           varchar(20) default 'AVAILABLE'::character varying
        constraint status_constraint
            check ((status)::text = ANY
                   ((ARRAY ['AVAILABLE'::character varying, 'BOOKED'::character varying, 'UNAVAILABLE'::character varying])::text[])),
    modified_at      timestamp   default now(),
    default_location varchar(20)
        constraint location_constraint
            check ((default_location)::text = ANY
        ((ARRAY ['LISBON'::character varying, 'PORTO'::character varying, 'BRAGA'::character varying])::text[]))
);
CREATE SEQUENCE IF NOT EXISTS SEQ_RACK_ID;

alter table t_rack
    owner to postgres;

create table t_rack_asset
(
    id        BIGINT      DEFAULT random()        PRIMARY KEY,
    asset_tag varchar(10)                         not null,
    rack_id   BIGINT      DEFAULT random()        not null
        references t_rack
);
CREATE SEQUENCE IF NOT EXISTS SEQ_RACK_ASSET_ID;

alter table t_rack_asset
    owner to postgres;

create table t_team_member
(
    id          BIGINT    DEFAULT random()          PRIMARY KEY,
    team_id     BIGINT    DEFAULT random()          not null
        references t_team,
    ctw_id      varchar(8)                          not null,
    name        varchar(20)                         not null,
    created_at  timestamp default now(),
    modified_at timestamp default now()
);
CREATE SEQUENCE IF NOT EXISTS SEQ_TEAM_MEMBER_ID;

alter table t_team_member
    owner to postgres;

create table t_booking
(
    id           BIGINT    DEFAULT random()          PRIMARY KEY,
    rack_id      BIGINT    DEFAULT random()          not null
        references t_rack,
    requester_id BIGINT    DEFAULT random()          not null
        references t_team_member,
    book_from    timestamp                           not null,
    book_to      timestamp                           not null,
    created_at   timestamp default now(),
    modified_at  timestamp default now()
);
CREATE SEQUENCE IF NOT EXISTS SEQ_BOOKING_ID;

alter table t_booking
    owner to postgres;

