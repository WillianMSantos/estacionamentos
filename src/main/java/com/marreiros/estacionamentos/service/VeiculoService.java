package com.marreiros.estacionamentos.service;


import com.marreiros.estacionamentos.dto.request.VeiculoRequestDto;
import com.marreiros.estacionamentos.exception.ExistingVeiculePlacaException;
import com.marreiros.estacionamentos.model.Veiculo;
import com.marreiros.estacionamentos.repository.VeiculoRepository;
import com.sun.media.sound.InvalidDataException;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    public Veiculo salvarVeiculo(VeiculoRequestDto veiculoRequestDto) throws InvalidDataException {

        if(veiculoRequestDto.isNullOrEmpty()) {
            throw new InvalidDataException();
        }

        if(veiculoRepository.findByPlaca(veiculoRequestDto.getPlaca()).isPresent()) {
            throw new ExistingVeiculePlacaException();
        }

        val veiculo = Veiculo.builder()
                                     .placa(veiculoRequestDto.getPlaca().toUpperCase(Locale.ROOT))
                                     .modelo(veiculoRequestDto.getModelo())
                                     .build();

        veiculoRepository.save(veiculo);

        return veiculo;
    }

    public Veiculo consultaVeiculoPorPlaca(String placa) {
        return veiculoRepository.findByPlaca(placa)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                           "veiculo nao encontrado por essa placa"));


    }

    public void excluirVeiculoPorPlaca(String placa) {

        veiculoRepository.findByPlaca(placa.toUpperCase(Locale.ROOT))
                .map(veiculo -> {
                    veiculoRepository.delete(veiculo);
                    return veiculo;

                }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Veiculo nao encontrado"));

    }

    public List<Veiculo> consultarVeiculosComBaseEmFiltros(Veiculo filtro) {
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Veiculo> example = Example.of(filtro, exampleMatcher);

        return veiculoRepository.findAll(example);
    }

    public List<Veiculo> listarVeiculos() {
        return veiculoRepository.findAll();
    }

}
