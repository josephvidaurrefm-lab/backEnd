package com.Proyecto.backEnd.controller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Proyecto.backEnd.config.JwtUtil;
import com.Proyecto.backEnd.model.UsuariosModel;
import com.Proyecto.backEnd.model.DTO.UsuarioRolDTO;
import com.Proyecto.backEnd.service.UsuariosService;
import com.Proyecto.backEnd.utils.ApiResponse;
import com.Proyecto.backEnd.utils.AuthRequest;
import com.Proyecto.backEnd.utils.AuthResponse;

@RestController
public class UsuariosController {

    @Autowired
    UsuariosService usuService;

    @GetMapping("/api/usuarios")
    public List<UsuariosModel> listaUsuarios() {
        return usuService.listaUsuarios();
    }

    @GetMapping("/api/usuarios/{login}")
    public Optional<UsuariosModel> verUsuario(@PathVariable String login) {
        return usuService.verUsuario(login);
    }

    // ✅ Crear usuario (aplica MD5 a password)
    @PostMapping("/api/usuarios")
    public UsuariosModel agreUsuario(@RequestBody UsuariosModel usuario) {
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(MD5Hash(usuario.getPassword()));
        }
        return usuService.agreUsuario(usuario);
    }

    // ✅ Modificar usuario (aplica MD5 si cambia password)
    @PutMapping("/api/usuarios/{login}")
    public UsuariosModel modUsuario(@RequestBody UsuariosModel usuario, @PathVariable String login) {
        if (usuario.getPassword() != null && !usuario.getPassword().isEmpty()) {
            usuario.setPassword(MD5Hash(usuario.getPassword()));
        }
        return usuService.modUsuario(login, usuario);
    }

    @DeleteMapping("/api/usuarios/{login}")
    public void eliUsuario(@PathVariable String login) {
        usuService.eliUsuario(login);
    }

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    public UsuariosController(AuthenticationManager authenticationManager,
                              UserDetailsService userDetailsService,
                              JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    // ✅ Login con MD5
    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            // Buscar usuario por login
            UsuariosModel usuario = usuService.buscarPorLogin(request.getUsername());
            if (usuario == null) {
                return ResponseEntity.ok(Map.of(
                    "success", false,
                    "message", "Usuario o contraseña incorrectos"
                ));
            }

            String inputPassword = request.getPassword();
            String dbPassword = usuario.getPassword();

            // Verificar si la contraseña en DB ya está en MD5 (32 caracteres hexadecimales)
            boolean isMd5 = dbPassword != null && dbPassword.matches("^[a-fA-F0-9]{32}$");

            if (!isMd5) {
                // Si no está cifrada, la ciframos y actualizamos en DB
                String hashed = MD5Hash(dbPassword);
                usuario.setPassword(hashed);
                usuService.modUsuario(usuario.getLogin(), usuario);
                dbPassword = hashed;
            }

            // Verificar si la contraseña ingresada coincide con la DB (ya en MD5)
            String hashedInput = MD5Hash(inputPassword);
            if (!dbPassword.equals(hashedInput)) {
                return ResponseEntity.ok(Map.of(
                    "success", false,
                    "message", "Usuario o contraseña incorrectos"
                ));
            }

            // Generar JWT
            String token = jwtUtil.generateToken(usuario.getLogin());

            return ResponseEntity.ok(Map.of(
                "success", true,
                "token", token,
                "nombre", usuario.getPersonal().getNombre() + " " +
                          usuario.getPersonal().getAp() + " " +
                          usuario.getPersonal().getAm(),
                "roles", usuario.getRoles(),
                "fecha", LocalDate.now().toString()
            ));

        } catch (Exception e) {
            return ResponseEntity.ok(Map.of(
                "success", false,
                "message", "Error interno del servidor"
            ));
        }
    }


    // ✅ Método para hashear la contraseña en MD5
    private String MD5Hash(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashedText = number.toString(16);
            while (hashedText.length() < 32) {
                hashedText = "0" + hashedText;
            }
            return hashedText;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    // Asignar un rol a un usuario
    @PostMapping("/api/usuarios/{login}/roles/{codr}")
    public ResponseEntity<ApiResponse> asignarRol(@PathVariable String login, @PathVariable int codr) {
        return usuService.asignarRolAUsuario(login, codr);
    }

    // Desasignar un rol de un usuario
    @DeleteMapping("/api/usuarios/{login}/roles/{codr}")
    public ResponseEntity<ApiResponse> desasignarRol(@PathVariable String login, @PathVariable int codr) {
        return usuService.desasignarRolDeUsuario(login, codr);
    }

    // Listar roles de un usuario con flag asignado
    @GetMapping("/api/usuarios/{login}/roles")
    public List<UsuarioRolDTO> listarRoles(@PathVariable String login) {
        return usuService.listarRolesPorUsuario(login);
    }
}
