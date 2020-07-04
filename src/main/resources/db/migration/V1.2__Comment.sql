create table comment
(
    id uuid not null
        constraint comment_pkey
            primary key,
    add_time timestamp,
    book_id uuid
        constraint fk_comment_book
            references book(id),
    comment varchar(255),
    nickname varchar(255)
);