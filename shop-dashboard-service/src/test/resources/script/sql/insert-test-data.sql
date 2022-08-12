INSERT INTO shop_dashboard.product_types (type) VALUES ('Test Product Type');

INSERT INTO shop_dashboard.product (name, product_type, expiration_days) VALUES ('Test Product Name', 'Test Product Type', 5);

INSERT INTO shop_dashboard.orders (client_name, order_amount, config) VALUES ('Test Client', 5.99, '{"test": "test"}');

INSERT INTO shop_dashboard.products_to_sell (product_name, price) VALUES ('Test Product Name', 5.99);
INSERT INTO shop_dashboard.products_to_order (product_name, price) VALUES ('Test Product Name', 5.99);