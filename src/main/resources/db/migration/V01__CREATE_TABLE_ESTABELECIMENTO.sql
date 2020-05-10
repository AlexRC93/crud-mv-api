CREATE TABLE IF NOT EXISTS estabelecimento (
	id serial PRIMARY KEY,
	nome VARCHAR(100) NOT NULL,
	endereco VARCHAR(100)
);

insert into estabelecimento (nome, endereco) values('Supermercado Ismael', 'Rua Doutor Pedro Leal - nº234');