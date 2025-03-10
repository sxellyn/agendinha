package com.AgendinhaPontoCom.controller;

import com.AgendinhaPontoCom.model.Contato;
import com.AgendinhaPontoCom.model.Usuario;
import com.AgendinhaPontoCom.service.ContatoService;
import com.AgendinhaPontoCom.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/contatos")
public class ContatoController {
    private final ContatoService contatoService;
    private final UsuarioService usuarioService;

    public ContatoController(ContatoService contatoService, UsuarioService usuarioService) {
        this.contatoService = contatoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String listar(@RequestParam(required = false) String busca, Model model) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth == null || !auth.isAuthenticated()) {
                return "redirect:/login";
            }

            String email = auth.getName();
            Usuario usuario = usuarioService.buscarPorEmail(email);
            
            List<Contato> contatos = busca != null && !busca.isEmpty() 
                ? contatoService.buscarPorNome(busca, email)
                : contatoService.listarPorUsuario(email);
            
            model.addAttribute("contatos", contatos);
            model.addAttribute("busca", busca);
            model.addAttribute("usuarioNome", usuario.getNome());
            model.addAttribute("usuario", usuario);
            return "contatos/index";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erro", "Erro ao carregar contatos: " + e.getMessage());
            return "contatos/index";
        }
    }

    @GetMapping("/novo")
    public String novoContato(Model model) {
        model.addAttribute("contato", new Contato());
        return "contatos/form";
    }

    @PostMapping("/salvar")
    public String salvarContato(@ModelAttribute Contato contato, RedirectAttributes redirectAttributes) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            contatoService.salvarContato(contato, email);
            redirectAttributes.addFlashAttribute("mensagem", "Contato salvo com sucesso!");
            return "redirect:/contatos";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao salvar contato: " + e.getMessage());
            return "redirect:/contatos/novo";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarContato(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            Contato contato = contatoService.buscarContato(id, email);
            model.addAttribute("contato", contato);
            return "contatos/form";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao editar contato: " + e.getMessage());
            return "redirect:/contatos";
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluirContato(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            String email = SecurityContextHolder.getContext().getAuthentication().getName();
            contatoService.excluirContato(id, email);
            redirectAttributes.addFlashAttribute("mensagem", "Contato excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir contato: " + e.getMessage());
        }
        return "redirect:/contatos";
    }
}