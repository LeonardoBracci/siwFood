package it.uniroma3.siwfood.Controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siwfood.Model.Cuoco;
import it.uniroma3.siwfood.Model.Ingrediente;
import it.uniroma3.siwfood.Model.Ricetta;
import it.uniroma3.siwfood.Service.CuocoService;
import it.uniroma3.siwfood.Service.IngredienteService;
import it.uniroma3.siwfood.Service.RicettaService;

@Controller
public class HomeController {
    @Autowired
    private RicettaService ricettaService;
    @Autowired
    private CuocoService cuocoService;
    @Autowired
    private IngredienteService ingredienteService;

    @PostMapping("/siwfood/cerca")
    public String cerca(Model model, @RequestParam("nome") String nome) {
        String searchQuery = nome.toLowerCase(); // Converte la query di ricerca in minuscolo per una ricerca case-insensitive
        List<Ricetta> ricette = this.ricettaService.findByNameContainingIgnoreCase(searchQuery);
        List<Cuoco> cuochi = this.cuocoService.findByNameContainingIgnoreCase(searchQuery);
        List<Ingrediente> ingredientiDuplicati = this.ingredienteService.findByNameContainingIgnoreCase(searchQuery);
        Set<Ingrediente> ingredienti = new HashSet<>(ingredientiDuplicati);

        model.addAttribute("ricette", ricette);
        model.addAttribute("ingredienti", ingredienti);
        model.addAttribute("cuochi", cuochi);

        return "paginaRicerca.html";
    }
}
