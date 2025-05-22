-- Creem la base de dades si no existeix --
CREATE DATABASE IF NOT EXISTS ecotrackerapp;
USE ecotrackerapp;

-- Creem la taula categoria --
CREATE TABLE IF NOT EXISTS categoria (
    id INT AUTO_INCREMENT,
    nom VARCHAR(25) NOT NULL UNIQUE,
    descripcio VARCHAR(100),
    co2UnitariEstalviat DOUBLE,
    PRIMARY KEY (id)
);

-- Creem la taula activitatssostenibles --
CREATE TABLE IF NOT EXISTS activitatssostenibles (
    id INT AUTO_INCREMENT,
    nom VARCHAR(25) NOT NULL,
    data DATE,
    nomCategoria VARCHAR(25),
    descripcio VARCHAR(100),
    quantitat DOUBLE,
    co2TotalEstalviat DOUBLE,
    PRIMARY KEY (id)
);

-- Afegim la clau forana a la taula activitatssostenibles --
ALTER TABLE activitatssostenibles ADD FOREIGN KEY(nomCategoria) REFERENCES categoria(nom)
ON UPDATE CASCADE ON DELETE CASCADE;

-- Carreguem les dades inicials a la taula categoria --
LOAD DATA INFILE 'Inserir aquí la ruta del fitxer categoria.csv'
INTO TABLE categoria
FIELDS TERMINATED BY ';'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;

-- Carreguem les dades inicials a la taula activitatssostenibles --
LOAD DATA INFILE 'Inserir aquí la ruta del fitxer activitatssostenibles.csv'
INTO TABLE activitatssostenibles
FIELDS TERMINATED BY ';'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS;