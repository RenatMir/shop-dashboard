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

CREATE TABLE IF NOT EXISTS shop_dashboard.products (
    id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR ( 255 ) UNIQUE NOT NULL,
    product_type VARCHAR ( 255 ) NOT NULL,
    price NUMERIC NOT NULL,
    expiration_days INTEGER DEFAULT NULL,
    version INTEGER NOT NULL DEFAULT 0,
    last_change_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    FOREIGN KEY (product_type)
        REFERENCES shop_dashboard.product_types (type)
);

CREATE TABLE IF NOT EXISTS shop_dashboard.orders (
    id uuid DEFAULT uuid_generate_v4(),
    order_amount NUMERIC NOT NULL,
    config JSON NOT NULL,
    version INTEGER NOT NULL DEFAULT 0,
    last_change_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id)
);
------------------------------------------------------------------------------------

CREATE TRIGGER product_types_set_version_trg
    BEFORE INSERT OR UPDATE
        ON shop_dashboard.product_types
        FOR EACH ROW
        EXECUTE FUNCTION shop_dashboard.set_version();

CREATE TRIGGER products_set_version_trg
    BEFORE INSERT OR UPDATE
        ON shop_dashboard.products
        FOR EACH ROW
        EXECUTE FUNCTION shop_dashboard.set_version();

CREATE TRIGGER orders_set_version_trg
    BEFORE INSERT OR UPDATE
        ON shop_dashboard.orders
        FOR EACH ROW
        EXECUTE FUNCTION shop_dashboard.set_version();
------------------------------------------------------------------------------------

INSERT INTO shop_dashboard.product_types (type) VALUES ('Drink');

INSERT INTO shop_dashboard.products (name, product_type, price, expiration_days) VALUES ('Coca-Cola 2L', 'Drink', 3.99, 60);
INSERT INTO shop_dashboard.products (name, product_type, price) VALUES ('Coca-Cola 0.5L', 'Drink', 1.50);