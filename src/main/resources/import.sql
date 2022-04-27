INSERT INTO products (id, product_name, price, number_of_products, product_type) VALUES (1, 'test', 10, 10, 'CHAIN');
INSERT INTO cart (id) VALUES (1);
INSERT INTO users (id, email, password, full_name, fk_cart) VALUES (1, 'email1@gmail.com', 'password', 'Ion Popescu', 1);
INSERT INTO product_cart (fk_cart_id, fk_product_id) VALUES (1, 1);
INSERT INTO reviews (id, text, fk_product_id, fk_user_id) VALUES (1, 'good', 1, 1);
