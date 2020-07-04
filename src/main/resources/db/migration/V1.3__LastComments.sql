create table last_comments
(
    id uuid not null
        constraint last_comments_pkey
            primary key,
    book_id uuid
        constraint fk_last_comments_book
            references book(id),
    comment_id uuid
        constraint fk_last_comments_comment
            references comment(id)
);