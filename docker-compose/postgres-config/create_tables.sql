CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE SCHEMA IF NOT EXISTS shop_dashboard;
------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION shop_dashboard.set_version()
    RETURNS TRIGGER
AS $$
BEGIN
    NEW.version := coalesce(NEW.version,0) + 1;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;
------------------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS shop_dashboard.product_types (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    type VARCHAR ( 255 ) UNIQUE NOT NULL,
    version INTEGER NOT NULL DEFAULT 0,
    last_change_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS shop_dashboard.product (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR ( 255 ) UNIQUE NOT NULL,
    product_type VARCHAR ( 255 ) NOT NULL,
    expiration_days INTEGER DEFAULT NULL,
    version INTEGER NOT NULL DEFAULT 0,
    last_change_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    FOREIGN KEY (product_type)
        REFERENCES shop_dashboard.product_types (type)
);

CREATE TABLE IF NOT EXISTS shop_dashboard.products_to_sell (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    product_name VARCHAR ( 255 ) UNIQUE NOT NULL,
    price NUMERIC NOT NULL,
    version INTEGER NOT NULL DEFAULT 0,
    last_change_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    FOREIGN KEY (product_name)
        REFERENCES shop_dashboard.product (name)
);

CREATE TABLE IF NOT EXISTS shop_dashboard.products_to_order (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    product_name VARCHAR ( 255 ) UNIQUE NOT NULL,
    price NUMERIC NOT NULL,
    version INTEGER NOT NULL DEFAULT 0,
    last_change_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    FOREIGN KEY (product_name)
        REFERENCES shop_dashboard.product (name)
);

CREATE TABLE IF NOT EXISTS shop_dashboard.orders (
    uuid uuid DEFAULT uuid_generate_v4(),
    client_name VARCHAR ( 255 ) NOT NULL,
    order_amount NUMERIC NOT NULL,
    config JSON NOT NULL,
    order_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    version INTEGER NOT NULL DEFAULT 0,
    last_change_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    PRIMARY KEY (uuid)
);
------------------------------------------------------------------------------------

CREATE TRIGGER product_types_set_version_trg
    BEFORE INSERT OR UPDATE
        ON shop_dashboard.product_types
        FOR EACH ROW
        EXECUTE FUNCTION shop_dashboard.set_version();

CREATE TRIGGER products_set_version_trg
    BEFORE INSERT OR UPDATE
        ON shop_dashboard.products_to_sell
        FOR EACH ROW
        EXECUTE FUNCTION shop_dashboard.set_version();

CREATE TRIGGER orders_set_version_trg
    BEFORE INSERT OR UPDATE
        ON shop_dashboard.orders
        FOR EACH ROW
        EXECUTE FUNCTION shop_dashboard.set_version();
------------------------------------------------------------------------------------

INSERT INTO shop_dashboard.product_types (type) VALUES ('Drink');

INSERT INTO shop_dashboard.product (name, product_type, expiration_days) VALUES ('Coca-Cola 2L', 'Drink', 60);
INSERT INTO shop_dashboard.product (name, product_type) VALUES ('Coca-Cola 0.5L', 'Drink');