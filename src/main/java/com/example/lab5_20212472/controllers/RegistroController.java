package com.example.lab5_20212472.controllers;

import com.example.lab5_20212472.entities.Rol;
import com.example.lab5_20212472.entities.Usuario;
import com.example.lab5_20212472.repositories.RolRepository;
import com.example.lab5_20212472.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistroController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @GetMapping("/registro")
    public String mostrarFormulario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(
            @RequestParam String nombre,
            @RequestParam String apellido,
            @RequestParam String dni,
            @RequestParam String email,
            @RequestParam Integer edad,
            @RequestParam String password,
            Model model) {

        try {
            // Validaciones
            if(usuarioRepository.existsByEmail(email)) {
                model.addAttribute("error", "El email ya está registrado");
                return "registro";
            }

            if(usuarioRepository.existsByDni(dni)) {
                model.addAttribute("error", "El DNI ya está registrado");
                return "registro";
            }

            Rol rolUser = rolRepository.findByNombre("USER")
                    .orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));

            // Crear y guardar el nuevo usuario
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setApellido(apellido);
            nuevoUsuario.setDni(dni);
            nuevoUsuario.setEmail(email);
            nuevoUsuario.setEdad(edad);
            nuevoUsuario.setPwd(passwordEncoder.encode(password));
            nuevoUsuario.setRol(rolUser);
            nuevoUsuario.setActivo(1);

            usuarioRepository.save(nuevoUsuario);

            return "redirect:/login?registroExitoso";
        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar: " + e.getMessage());
            return "registro";
        }
    }
}

