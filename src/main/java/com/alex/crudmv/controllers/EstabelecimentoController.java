package com.alex.crudmv.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alex.crudmv.dtos.EstabelecimentoDto;
import com.alex.crudmv.entity.Estabelecimento;
import com.alex.crudmv.entity.Profissional;
import com.alex.crudmv.response.Response;
import com.alex.crudmv.service.EstabelecimentoService;
import com.alex.crudmv.service.ProfissionalService;

@RestController
@RequestMapping(value = "estabelecimentos")
@CrossOrigin(origins = "*")
public class EstabelecimentoController {

	@Autowired
	private EstabelecimentoService estabelecimentoService;
	
	@Autowired
	private ProfissionalService profissionalService;
	
	@GetMapping
	public ResponseEntity<Response<List<EstabelecimentoDto>>> listar() {
		Response<List<EstabelecimentoDto>> response = new Response<List<EstabelecimentoDto>>();
		List<Estabelecimento> estabelecimentos = estabelecimentoService.listar();
		List<EstabelecimentoDto> estabelecimentoDtos = estabelecimentos.stream().map(estabelecimento -> converterEstabelecimentoParaDto(estabelecimento))
				.collect(Collectors.toList());
		response.setData(estabelecimentoDtos);
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<Response<EstabelecimentoDto>> save(
			@Valid @RequestBody EstabelecimentoDto estabelecimentoDto, BindingResult result) {
		Response<EstabelecimentoDto> response = new Response<EstabelecimentoDto>();
		Estabelecimento estabelecimento = converterDtoParaEstabelecimento(estabelecimentoDto);
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		estabelecimentoService.persistir(estabelecimento);
		response.setData(converterEstabelecimentoParaDto(estabelecimento));
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		Response<String> response = new Response<String>();
		Optional<Estabelecimento> estabelecimento = estabelecimentoService.buscarPorId(id);
		List<Profissional> profisisonaisVinculados = profissionalService.buscarPorEstabelecimento(id);
		if (possuiErros(estabelecimento,profisisonaisVinculados,response)) {
			return ResponseEntity.badRequest().body(response);
		}
		estabelecimentoService.remover(id);
		response.setData("Registro removido com sucesso.");
		return ResponseEntity.ok(response);
	}
 
	private boolean possuiErros(Optional<Estabelecimento> estabelecimento, List<Profissional> profisisonaisVinculados,
			Response<String> response) {
		if (!estabelecimento.isPresent()) {
			response.getErrors().add("Este registro não existe mais.");
		}
		if (!profisisonaisVinculados.isEmpty()) {
			response.getErrors().add("Existe um profisisonal vinculado a este estabelecimento.");
		}
		return !response.getErrors().isEmpty();
	}

	private EstabelecimentoDto converterEstabelecimentoParaDto(Estabelecimento estabelecimento) {
		EstabelecimentoDto estabelecimentoDto = new EstabelecimentoDto();
		estabelecimentoDto.setId(estabelecimento.getId());
		estabelecimentoDto.setNome(estabelecimento.getNome());
		estabelecimentoDto.setEndereco(estabelecimento.getEndereco());
		return estabelecimentoDto;
	}

	private Estabelecimento converterDtoParaEstabelecimento(
			@Valid EstabelecimentoDto estabelecimentoDto) {
		Estabelecimento estabelecimento = new Estabelecimento();
		estabelecimento.setId(estabelecimentoDto.getId());
		estabelecimento.setNome(estabelecimentoDto.getNome());
		estabelecimento.setEndereco(estabelecimentoDto.getEndereco());
		return estabelecimento;
	}

}
