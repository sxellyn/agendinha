package com.AgendinhaPontoCom.controller;

import com.AgendinhaPontoCom.model.Usuario;
import com.AgendinhaPontoCom.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/perfil")
public class PerfilController {
    
    private final UsuarioService usuarioService;

    public PerfilController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String perfil(Model model) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuario = usuarioService.buscarPorEmail(auth.getName());
            model.addAttribute("usuario", usuario);
            model.addAttribute("usuarioNome", usuario.getNome());
            return "perfil/index";
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao carregar perfil: " + e.getMessage());
            return "redirect:/login";
        }
    }

    @PostMapping("/atualizar")
    public String atualizar(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Usuario usuarioAtual = usuarioService.buscarPorEmail(auth.getName());
            
            // Atualiza apenas nome e senha
            usuarioAtual.setNome(usuario.getNome());
            if (!usuario.getSenha().isEmpty()) {
                usuarioAtual.setSenha(usuario.getSenha());
            }
            
            usuarioService.atualizar(usuarioAtual);
            redirectAttributes.addFlashAttribute("mensagem", "Perfil atualizado com sucesso!");
            return "redirect:/perfil";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao atualizar perfil: " + e.getMessage());
            return "redirect:/perfil";
        }
    }
} 