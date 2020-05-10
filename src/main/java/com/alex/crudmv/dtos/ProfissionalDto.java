package com.alex.crudmv.dtos;

import javax.validation.constraints.NotEmpty;

import com.alex.crudmv.entity.Estabelecimento;

public class ProfissionalDto {
	
	private Long id;
	private String nome;
	private String endereco;
	private Estabelecimento estabelecimento;
	
	public ProfissionalDto() {
		
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@NotEmpty(message = "Nome não pode ser vazio.")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@NotEmpty(message = "Endereço não pode ser vazio.")
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}

	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}
	
}
