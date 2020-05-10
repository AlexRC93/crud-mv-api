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

import com.alex.crudmv.dtos.ProfissionalDto;
import com.alex.crudmv.entity.Profissional;
import com.alex.crudmv.response.Response;
import com.alex.crudmv.service.ProfissionalService;

@RestController
@RequestMapping(value = "profissionais")
@CrossOrigin(origins = "*")
public class ProfissionalController {
	
	@Autowired
	private ProfissionalService profissionalService;
	
	@GetMapping
	public ResponseEntity<Response<List<ProfissionalDto>>> listar() {
		Response<List<ProfissionalDto>> response = new Response<List<ProfissionalDto>>();
		List<Profissional> profissionais = profissionalService.listar();
		List<ProfissionalDto> profissionalDtos = profissionais.stream().map(profissional -> converterProfissionalParaDto(profissional))
				.collect(Collectors.toList());
		response.setData(profissionalDtos);
		return ResponseEntity.ok(response);
	}
	
	@PostMapping
	public ResponseEntity<Response<ProfissionalDto>> save(
			@Valid @RequestBody ProfissionalDto profissionalDto, BindingResult result) {
		Response<ProfissionalDto> response = new Response<ProfissionalDto>();
		Profissional profissional = converterDtoParaEstabelecimento(profissionalDto);
		if (result.hasErrors()) {
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		profissionalService.persistir(profissional);
		response.setData(converterProfissionalParaDto(profissional));
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> remover(@PathVariable("id") Long id) {
		Response<String> response = new Response<String>();
		Optional<Profissional> profissional = profissionalService.buscarPorId(id);
		if (!profissional.isPresent()) {
			response.getErrors().add("Este registro não existe mais.");
			return ResponseEntity.badRequest().body(response);
		}
		profissionalService.remover(id);
		return ResponseEntity.ok(new Response<String>());
	}

	private Profissional converterDtoParaEstabelecimento(@Valid ProfissionalDto profissionalDto) {
		Profissional profissional = new Profissional();
		profissional.setId(profissionalDto.getId());
		profissional.setNome(profissionalDto.getNome());
		profissional.setEndereco(profissionalDto.getEndereco());
		profissional.setEstabelecimento(profissionalDto.getEstabelecimento());
		return profissional;
	}

	private ProfissionalDto converterProfissionalParaDto(Profissional profissional) {
		ProfissionalDto profissionalDto = new ProfissionalDto();
		profissionalDto.setId(profissional.getId());
		profissionalDto.setNome(profissional.getNome());
		profissionalDto.setEndereco(profissional.getEndereco());
		profissionalDto.setEstabelecimento(profissional.getEstabelecimento());
		return profissionalDto;
	}
}
