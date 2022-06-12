package com.marreiros.estacionamentos.controller;

import com.marreiros.estacionamentos.dto.request.VeiculoRequestDto;
import com.marreiros.estacionamentos.model.Veiculo;
import com.marreiros.estacionamentos.service.VeiculoService;
import com.sun.media.sound.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/veiculos")
public class VeiculoController {

    @Autowired
    public VeiculoService veiculoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody @Valid VeiculoRequestDto requestDto) throws InvalidDataException {
        Veiculo veiculo = veiculoService.salvarVeiculo(requestDto);

        return veiculo.getId();
    }

    @GetMapping("{placa}")
    public Veiculo consultar(@PathVariable String placa) {

        return veiculoService.consultaVeiculoPorPlaca(placa.toUpperCase(Locale.ROOT));
    }

    @DeleteMapping("{placa}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirVeiculoPorPlaca(@PathVariable String placa){

        veiculoService.excluirVeiculoPorPlaca(placa);
    }

    @GetMapping
    public List<Veiculo> consultaComBaseEmFiltros(Veiculo filtro){

        return veiculoService.consultarVeiculosComBaseEmFiltros(filtro);
    }

    @GetMapping("veiculos")
    public List<Veiculo> listarVeiculos(){
        return veiculoService.listarVeiculos();
    }

}