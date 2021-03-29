CREATE TABLE IF NOT EXISTS item
(
    id uuid,
    description text,
    create_time timestamp without time zone not null,
    edit_time timestamp without time zone,
    done_time timestamp without time zone,
    fact_done_time timestamp without time zone,
    is_done boolean,
    CONSTRAINT item_pkey PRIMARY KEY (id)
);