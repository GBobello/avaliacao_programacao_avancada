CREATE DATABASE veiculos;
USE veiculos;

CREATE TABLE automovel (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(20),
    marca VARCHAR(50),
    modelo VARCHAR(50),
    ano INT,
    portas INT,
    carga DOUBLE
);