CREATE TABLE IF NOT EXISTS user_details (
    id BIGINT NOT NULL AUTO_INCREMENT,
    address VARCHAR(40) NOT NULL,
    phone_number VARCHAR(40) NOT NULL,
    other_information VARCHAR(40),

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS cart (
    id BIGINT NOT NULL AUTO_INCREMENT,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    full_name VARCHAR(40) NOT NULL,
    user_type VARCHAR(40) NOT NULL,
    email VARCHAR(40) UNIQUE NOT NULL,
    password VARCHAR(40) NOT NULL,
    fk_user_details BIGINT,
    fk_cart BIGINT,

    PRIMARY KEY (id),
    FOREIGN KEY (fk_user_details) REFERENCES user_details(id),
    FOREIGN KEY (fk_cart) REFERENCES cart(id)
);

CREATE TABLE IF NOT EXISTS orders (
    id BIGINT NOT NULL AUTO_INCREMENT,
    status VARCHAR(40) NOT NULL,
    fk_user_id BIGINT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (fk_user_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS products (
    id BIGINT NOT NULL AUTO_INCREMENT,
    product_name VARCHAR(40) NOT NULL,
    price DOUBLE(10,2) NOT NULL,
    number_of_products INT NOT NULL,
    product_type VARCHAR(40) NOT NULL,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS product_cart (
    id BIGINT NOT NULL AUTO_INCREMENT,
    fk_cart_id BIGINT NOT NULL,
    fk_product_id BIGINT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (fk_cart_id) REFERENCES cart(id),
    FOREIGN KEY (fk_product_id) REFERENCES products(id)
);


CREATE TABLE IF NOT EXISTS product_order (
    id BIGINT NOT NULL AUTO_INCREMENT,
    fk_order_id BIGINT NOT NULL,
    fk_product_id BIGINT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (fk_order_id) REFERENCES orders(id),
    FOREIGN KEY (fk_product_id) REFERENCES products(id)
);

CREATE TABLE IF NOT EXISTS reviews (
    id BIGINT NOT NULL AUTO_INCREMENT,
    text VARCHAR(40) NOT NULL,
    fk_user_id BIGINT NOT NULL,
    fk_product_id BIGINT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (fk_user_id) REFERENCES users(id),
    FOREIGN KEY (fk_product_id) REFERENCES products(id)
    );

