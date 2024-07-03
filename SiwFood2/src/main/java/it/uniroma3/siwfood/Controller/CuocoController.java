package it.uniroma3.siwfood.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import it.uniroma3.siwfood.Service.CuocoService;
import it.uniroma3.siwfood.Service.RicettaService;

@Controller
public class CuocoController {
    @Autowired
    private CuocoService cuocoService;
    @Autowired
    private RicettaService ricettaService;

    @GetMapping("/siwfood/cuoco/{id}")
    public String getCuoco(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cuoco", this.cuocoService.findById(id)); 
        model.addAttribute("ricette", this.ricettaService.findAllByCuocoId(id));
        return "cuoco.html";
    }

    @GetMapping("/siwfood/cuochi")
    public String getCuochi(Model model) {
        model.addAttribute("cuochi", this.cuocoService.findAll());
        return "cuochi.html";
    }

}
