-- START SCHEMA PUBLIC
DROP SCHEMA IF EXISTS public CASCADE;
CREATE SCHEMA public;

-- START TABLE USER
DROP TABLE IF EXISTS public.user;
CREATE TABLE public.user
(
    user_id                 varchar(36) DEFAULT ''::character varying            NOT NULL,
    name                    varchar(50) DEFAULT ''::character varying            NOT NULL,
    phone_number            varchar(20) DEFAULT ''::character varying            NOT NULL,
    dob                     varchar(12) DEFAULT ''::character varying            NOT NULL,
    member_status           varchar(25) DEFAULT ''::character varying            NOT NULL,
    payment_data_id         varchar(36) DEFAULT ''::character varying            NOT NULL,
    created_at              timestamp   DEFAULT now()                            NOT NULL,
    created_by              varchar(40) DEFAULT ''::character varying            NOT NULL,
    updated_at              timestamp   DEFAULT '1900-01-01 00:00:00'::timestamp NOT NULL,
    updated_by              varchar(40) DEFAULT ''::character varying            NOT NULL,

    CONSTRAINT user_pkey PRIMARY KEY (user_id)
);

-- START TABLE USER LOGIN
DROP TABLE IF EXISTS public.user_login;
CREATE TABLE public.user_login
(
    user_login_id           varchar(36) DEFAULT ''::character varying            NOT NULL,
    user_id                 varchar(36) DEFAULT ''::character varying            NOT NULL,
    email                   varchar(30) DEFAULT ''::character varying            NOT NULL,
    password                text        DEFAULT ''::text                         NOT NULL,
    is_active                boolean     DEFAULT false                            NOT NULL,
    created_at              timestamp   DEFAULT now()                            NOT NULL,
    created_by              varchar(40) DEFAULT ''::character varying            NOT NULL,
    updated_at              timestamp   DEFAULT '1900-01-01 00:00:00'::timestamp NOT NULL,
    updated_by              varchar(40) DEFAULT ''::character varying            NOT NULL,

    CONSTRAINT user_login_pkey PRIMARY KEY (user_login_id)
);

-- START TABLE PAYMENT DATA
DROP TABLE IF EXISTS public.payment_data;
CREATE TABLE public.payment_data
(
    payment_data_id         varchar(36) DEFAULT ''::character varying            NOT NULL,
    token_data              text        DEFAULT ''::text                         NOT NULL,
    card_number_masking     varchar(36) DEFAULT ''::character varying            NOT NULL,
    card_holder_name        varchar(50) DEFAULT ''::character varying            NOT NULL,
    created_at              timestamp   DEFAULT now()                            NOT NULL,
    created_by              varchar(40) DEFAULT ''::character varying            NOT NULL,
    updated_at              timestamp   DEFAULT '1900-01-01 00:00:00'::timestamp NOT NULL,
    updated_by              varchar(40) DEFAULT ''::character varying            NOT NULL,

    CONSTRAINT payment_data_pkey PRIMARY KEY (payment_data_id)
);

-- START TABLE OTP HANDLER
DROP TABLE IF EXISTS public.otp_handler;
CREATE TABLE public.otp_handler
(
    otp_id                  varchar(36) DEFAULT ''::character varying            NOT NULL,
    otp_type                varchar(25) DEFAULT ''::character varying            NOT NULL,
    otp_code                varchar(50) DEFAULT ''::character varying            NOT NULL,
    otp_at                  timestamp   DEFAULT now()                            NOT NULL,
    otp_status              varchar(20) DEFAULT ''::character varying            NOT NULL,
    otp_service_id          varchar(50) DEFAULT ''::character varying            NOT NULL,
    otp_service_type        varchar(50) DEFAULT ''::character varying            NOT NULL,
    created_at              timestamp   DEFAULT now()                            NOT NULL,
    created_by              varchar(40) DEFAULT ''::character varying            NOT NULL,
    updated_at              timestamp   DEFAULT '1900-01-01 00:00:00'::timestamp NOT NULL,
    updated_by              varchar(40) DEFAULT ''::character varying            NOT NULL,

    CONSTRAINT otp_handler_pkey PRIMARY KEY (otp_id)
);

-- START TABLE PRODUCT
DROP TABLE IF EXISTS public.product;
CREATE TABLE public.product
(
    product_id                  varchar(36) DEFAULT ''::character varying            NOT NULL,
    product_name                varchar(50) DEFAULT ''::character varying            NOT NULL,
    price                       numeric(17,2)                                        NOT NULL,
    product_detail              text        DEFAULT ''::text                         NOT NULL,
    created_at                  timestamp   DEFAULT now()                            NOT NULL,
    created_by                  varchar(40) DEFAULT ''::character varying            NOT NULL,
    updated_at                  timestamp   DEFAULT '1900-01-01 00:00:00'::timestamp NOT NULL,
    updated_by                  varchar(40) DEFAULT ''::character varying            NOT NULL,

    CONSTRAINT product_pkey PRIMARY KEY (product_id)
);

-- START TABLE USER SUBSCRIPTION
DROP TABLE IF EXISTS public.user_subscription;
CREATE TABLE public.user_subscription
(
    user_id                     varchar(36) DEFAULT ''::character varying            NOT NULL,
    product_id                  varchar(36) DEFAULT ''::character varying            NOT NULL,
    status_subscription         varchar(25) DEFAULT ''::character varying            NOT NULL,
    training_remaining          integer     DEFAULT 0                                NOT NULL,
    training_duration           integer     DEFAULT 0                                NOT NULL,
    subscribe_at                timestamp   DEFAULT '1900-01-01 00:00:00'::timestamp NOT NULL,
    created_at                  timestamp   DEFAULT now()                            NOT NULL,
    created_by                  varchar(40) DEFAULT ''::character varying            NOT NULL,
    updated_at                  timestamp   DEFAULT '1900-01-01 00:00:00'::timestamp NOT NULL,
    updated_by                  varchar(40) DEFAULT ''::character varying            NOT NULL,

    CONSTRAINT user_subscription_pkey PRIMARY KEY (user_id, product_id)
);

-- START TABLE INVOICE REQUEST
DROP TABLE IF EXISTS public.invoice_request;
CREATE TABLE public.invoice_request
(
    invoice_id                  varchar(36) DEFAULT ''::character varying            NOT NULL,
    invoice_status              varchar(25) DEFAULT ''::character varying            NOT NULL,
    user_id                     varchar(36) DEFAULT ''::character varying            NOT NULL,
    product_id                  varchar(36) DEFAULT ''::character varying            NOT NULL,
    amount                      numeric(17,2)                                        NOT NULL,
    duration                    integer     DEFAULT 0                                NOT NULL,
    invoice_at                  timestamp   DEFAULT now()                            NOT NULL,
    created_at                  timestamp   DEFAULT now()                            NOT NULL,
    created_by                  varchar(40) DEFAULT ''::character varying            NOT NULL,
    updated_at                  timestamp   DEFAULT '1900-01-01 00:00:00'::timestamp NOT NULL,
    updated_by                  varchar(40) DEFAULT ''::character varying            NOT NULL,

    CONSTRAINT invoice_request_pkey PRIMARY KEY (invoice_id)
);

-- START TABLE PAYMENT REQUEST
DROP TABLE IF EXISTS public.payment_request;
CREATE TABLE public.payment_request
(
    payment_id                  varchar(36) DEFAULT ''::character varying            NOT NULL,
    payment_status              varchar(25) DEFAULT ''::character varying            NOT NULL,
    invoice_id                  varchar(36) DEFAULT ''::character varying            NOT NULL,
    payment_at                  timestamp   DEFAULT now()                            NOT NULL,
    payment_data_id             varchar(36) DEFAULT ''::character varying            NOT NULL,
    payment_amount              numeric(17,2)                                        NOT NULL,
    payment_external_id         varchar(50) DEFAULT ''::character varying            NOT NULL,
    created_at                  timestamp   DEFAULT now()                            NOT NULL,
    created_by                  varchar(40) DEFAULT ''::character varying            NOT NULL,
    updated_at                  timestamp   DEFAULT '1900-01-01 00:00:00'::timestamp NOT NULL,
    updated_by                  varchar(40) DEFAULT ''::character varying            NOT NULL,

    CONSTRAINT payment_request_pkey PRIMARY KEY (payment_id)
);