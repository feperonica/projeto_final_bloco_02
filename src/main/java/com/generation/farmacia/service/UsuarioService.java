package com.generation.farmacia.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.farmacia.model.Usuario;
import com.generation.farmacia.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Cadastrar usuário
    public Optional<Usuario> cadastrarUsuario(Usuario usuario) {
        // Verifica se já existe o usuário
        if (usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent()) {
            return Optional.empty();
        }

        // Verifica se é maior de 18 anos
        if (!checarMaiorDeIdade(usuario.getDataNascimento())) {
            return Optional.empty();
        }

        // Salva no banco (senha sem criptografia)
        return Optional.of(usuarioRepository.save(usuario));
    }

    // Atualizar usuário
    public Optional<Usuario> atualizarUsuario(Usuario usuario) {
        if (usuarioRepository.findById(usuario.getId()).isPresent()) {

            // Verifica se é maior de 18 anos
            if (!checarMaiorDeIdade(usuario.getDataNascimento())) {
                return Optional.empty();
            }

            return Optional.of(usuarioRepository.save(usuario));
        }

        return Optional.empty();
    }

    // Buscar usuário por ID
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Buscar usuário pelo username (login)
    public Optional<Usuario> buscarPorUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario);
    }

    // Listar todos os usuários
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    // Deletar usuário
    public void deletarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Método auxiliar para verificar se é maior de 18 anos
    private boolean checarMaiorDeIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears() >= 18;
    }
    
    // Buscar usuários por nome
    public List<Usuario> buscarPorNome(String nome) {
        return usuarioRepository.findAllByNomeContainingIgnoreCase(nome);
    }
}