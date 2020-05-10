CREATE TABLE IF NOT EXISTS profissional (
	id serial PRIMARY KEY,
	nome VARCHAR(100) NOT NULL,
	endereco VARCHAR(100),
	estabelecimento_id INT8,
	FOREIGN KEY (estabelecimento_id) REFERENCES estabelecimento (id)
);

insert into profissional (nome, endereco, estabelecimento_id) values('Pedro Lima', 'Rua Antônio de Castro - 765', 1);