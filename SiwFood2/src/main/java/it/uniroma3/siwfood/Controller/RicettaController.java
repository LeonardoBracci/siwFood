package it.uniroma3.siwfood.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siwfood.Service.IngredienteService;
import it.uniroma3.siwfood.Service.RicettaService;

@Controller
public class RicettaController {
    @Autowired
    private RicettaService ricettaService;
    @Autowired
    private IngredienteService ingredienteService;


    @GetMapping("/siwfood/ricetta/{id}")
    public String getRicetta(@PathVariable("id") Long id, Model model) {
        model.addAttribute("ricetta", this.ricettaService.findById(id)); 
        model.addAttribute("ingredienti", this.ingredienteService.findAllByRicettaId(id));
        return "ricetta.html";
    }

    @GetMapping("/siwfood/ricette")
    public String getRicette(Model model) {
        model.addAttribute("ricette", this.ricettaService.findAll());
        
        return "ricette.html";
    }
}
