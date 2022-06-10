package com.marreiros.estacionamentos.repository;

import com.marreiros.estacionamentos.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Integer> {
}
