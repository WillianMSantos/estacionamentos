package com.marreiros.estacionamentos.controller;

import com.marreiros.estacionamentos.dto.request.LoginRequestDto;
import com.marreiros.estacionamentos.dto.request.RegisterRequestDto;
import com.marreiros.estacionamentos.dto.response.DefaultMessageResponseDto;
import com.marreiros.estacionamentos.dto.response.TokenDto;
import com.marreiros.estacionamentos.exception.ExistingEmailException;
import com.marreiros.estacionamentos.repository.UsuarioRepository;
import com.marreiros.estacionamentos.service.AutenticacaoService;
import com.marreiros.estacionamentos.service.TokenService;
import com.sun.istack.NotNull;
import com.sun.media.sound.InvalidDataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AutenticacaoService authService;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> userRegistration(@NotNull @RequestBody RegisterRequestDto request) {
        try {
            authService.userRegistration(request);
            return ResponseEntity.ok().build();
        }
        catch (ExistingEmailException | InvalidDataException e) {
            return new ResponseEntity(new DefaultMessageResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()),
                    HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/logar")
    public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginRequestDto loginRequest) {
        UsernamePasswordAuthenticationToken dadosLogin = loginRequest.converter();

        try {
            Authentication authentication = authenticationManager.authenticate(dadosLogin);
            String token = tokenService.gerarToken(authentication);

            return ResponseEntity.ok(new TokenDto(token, "Bearer"));
        }catch (AuthenticationException e){
            return ResponseEntity.badRequest().build();
        }
    }

}
