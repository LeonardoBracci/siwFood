package it.uniroma3.siwfood.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import it.uniroma3.siwfood.Service.IngredienteService;
import it.uniroma3.siwfood.Service.RicettaService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class IngredienteController {
    @Autowired
    private IngredienteService ingredienteService;
    @Autowired
    private RicettaService ricettaService;

    @GetMapping("/siwfood/ingrediente/{id}")
    public String getIngrediente(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ingrediente", this.ingredienteService.findById(id)); 
        model.addAttribute("ricette", this.ricettaService.findAllByIngredienteId(id));
        return "Ingrediente/ingrediente.html";
    }

    @GetMapping("/siwfood/ingredienti")
    public String getIngredienti(Model model) {
        model.addAttribute("ingredienti", this.ingredienteService.findAll());
        return "Ingrediente/ingredienti.html";
    }
    
    
}
