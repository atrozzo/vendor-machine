CREATE TABLE app_user (
      user_id       BIGINT NOT NULL AUTO_INCREMENT UNIQUE,
      username      VARCHAR(255) NOT NULL UNIQUE ,
      password      VARCHAR(255) NOT NULL,
      authority     VARCHAR(255) NOT NULL,
      PRIMARY KEY(user_id)
);
CREATE TABLE app_role (
      role_id       BIGINT NOT NULL AUTO_INCREMENT UNIQUE,
      role_name      VARCHAR(255) NOT NULL,
      description    VARCHAR(255),
      PRIMARY KEY(role_id)
);


CREATE TABLE user_role (
       role_id       BIGINT NOT NULL,
       user_id       BIGINT NOT NULL
);

CREATE TABLE app_product (
      product_id     BIGINT NOT NULL AUTO_INCREMENT UNIQUE,
      product_name      VARCHAR(255) NOT NULL,
      product_cost    INT NOT NULL,
      amount_available INT NOT NULL,
      PRIMARY KEY(product_id)
);

CREATE TABLE user_account (
      id           BIGINT NOT NULL AUTO_INCREMENT UNIQUE,
      user_id   BIGINT NOT NULL,
      user_amount    INT
);



