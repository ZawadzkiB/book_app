create table book
(
    id uuid not null
        constraint book_pkey
            primary key,
    author varchar(255),
    isbn varchar(255),
    pages integer not null,
    rate integer not null,
    title varchar(255)
);