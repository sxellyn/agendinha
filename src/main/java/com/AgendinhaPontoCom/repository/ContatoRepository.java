package com.AgendinhaPontoCom.repository;

import com.AgendinhaPontoCom.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {
    List<Contato> findByUsuarioId(Long usuarioId);
    List<Contato> findByNomeContainingAndUsuarioId(String nome, Long usuarioId);
    Optional<Contato> findByIdAndUsuarioId(Long id, Long usuarioId);
}
