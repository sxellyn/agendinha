package com.AgendinhaPontoCom.service;

import com.AgendinhaPontoCom.model.Contato;
import com.AgendinhaPontoCom.model.Usuario;
import com.AgendinhaPontoCom.repository.ContatoRepository;
import com.AgendinhaPontoCom.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ContatoService {
    private final ContatoRepository contatoRepository;
    private final UsuarioRepository usuarioRepository;

    public ContatoService(ContatoRepository contatoRepository, UsuarioRepository usuarioRepository) {
        this.contatoRepository = contatoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Contato> buscarPorNome(String nome, String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return contatoRepository.findByNomeContainingAndUsuarioId(nome, usuario.getId());
    }

    public List<Contato> listarPorUsuario(String email) {
        try {
            Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            return contatoRepository.findByUsuarioId(usuario.getId());
        } catch (Exception e) {
            e.printStackTrace(); // Para ver o erro no console
            throw new RuntimeException("Erro ao listar contatos: " + e.getMessage());
        }
    }

    @Transactional
    public Contato salvarContato(Contato contato, String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        contato.setUsuario(usuario);
        return contatoRepository.save(contato);
    }

    public Contato buscarContato(Long id, String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return contatoRepository.findByIdAndUsuarioId(id, usuario.getId())
            .orElseThrow(() -> new RuntimeException("Contato não encontrado"));
    }

    @Transactional
    public void excluirContato(Long id, String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        Contato contato = contatoRepository.findByIdAndUsuarioId(id, usuario.getId())
            .orElseThrow(() -> new RuntimeException("Contato não encontrado"));
        contatoRepository.delete(contato);
    }
}
