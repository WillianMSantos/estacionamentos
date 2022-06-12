package com.marreiros.estacionamentos.repository;

import com.marreiros.estacionamentos.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VeiculoRepository extends JpaRepository<Veiculo, Integer> {


     Optional<Veiculo> findByPlaca(String placa);

}
