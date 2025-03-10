package com.AgendinhaPontoCom.controller;

import com.AgendinhaPontoCom.model.Usuario;
import com.AgendinhaPontoCom.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

@Controller
public class AuthController {
    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/registro")
    public String registroForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String registrar(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.salvar(usuario);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Registro realizado com sucesso!");
            return "redirect:/login?registroSucesso";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao registrar: " + e.getMessage());
            return "redirect:/registro?erro";
        }
    }

    @GetMapping("/login")
    public String loginForm() {
        return "auth/login";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "Controller is working!";
    }
}