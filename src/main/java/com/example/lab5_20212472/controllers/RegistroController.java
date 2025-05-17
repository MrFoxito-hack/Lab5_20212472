package com.example.lab5_20212472.controllers;

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
    public String registrarUsuario(@ModelAttribute Usuario usuario, Model model) {
        try {
            // Validación básica
            if(usuarioRepository.existsByEmail(usuario.getEmail())) {
                model.addAttribute("error", "El email ya está registrado");
                return "registro";
            }

            // Asignar rol USER por defecto
            Rol rolUser = rolRepository.findByNombre("USER")
                    .orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));

            usuario.setPwd(passwordEncoder.encode(usuario.getPwd()));
            usuario.setIdrol(rolUser.getIdrol());
            usuario.setActivo(1);

            usuarioRepository.save(usuario);

            return "redirect:/login?registroExitoso";
        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar: " + e.getMessage());
            return "registro";
        }
    }
}