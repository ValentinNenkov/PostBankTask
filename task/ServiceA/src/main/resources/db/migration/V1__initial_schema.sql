CREATE TABLE currency_course (
    id BIGSERIAL PRIMARY KEY,
    date DATE, 
    active bool
);

CREATE TABLE currency_course_row (
    id BIGSERIAL PRIMARY KEY,
    course_id BIGINT,
    gold INTEGER,
    "name" VARCHAR, 
    code VARCHAR,
    ratio INTEGER,
    reverse_rate NUMERIC(12, 5),
    rate NUMERIC(12, 5),
    curr_date DATE,
    f_star INTEGER
);

CREATE TABLE currency_course_receiver (
    id BIGSERIAL PRIMARY KEY,
    date DATE,
    active bool
);

CREATE TABLE currency_course_row_receiver (
    id BIGSERIAL PRIMARY KEY,
    course_id BIGINT,
    gold INTEGER,
    "name" VARCHAR,
    code VARCHAR,
    ratio INTEGER,
    reverse_rate NUMERIC(12, 5),
    rate NUMERIC(12, 5),
    curr_date DATE,
    f_star INTEGER
);
