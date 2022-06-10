package com.marreiros.estacionamentos.service;

import com.marreiros.estacionamentos.dto.request.RegisterRequestDto;
import com.marreiros.estacionamentos.exception.ExistingEmailException;
import com.marreiros.estacionamentos.model.Usuario;
import com.marreiros.estacionamentos.repository.UsuarioRepository;
import com.sun.media.sound.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private PasswordEncoder passwordEncoder;

    public void userRegistration(RegisterRequestDto request) throws InvalidDataException {
        if (request.isNullOrEmpty()) {
            throw new InvalidDataException();
        }

        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ExistingEmailException();
        }

        Usuario user = new Usuario();
        user.setEmail(request.getEmail());
        user.setNome(request.getNome());
        user.setSenha(new BCryptPasswordEncoder().encode(request.getSenha()));
        usuarioRepository.save(user);
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

        if (usuario.isPresent()){
            return usuario.get();

        }
        throw new UsernameNotFoundException("Dados Invalidos");
    }
}