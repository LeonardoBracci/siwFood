package it.uniroma3.siwfood.Controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.siwfood.Authentication.GlobalController;
import it.uniroma3.siwfood.Model.Credentials;
import it.uniroma3.siwfood.Model.Cuoco;
import it.uniroma3.siwfood.Model.Ingrediente;
import it.uniroma3.siwfood.Model.Ricetta;
import it.uniroma3.siwfood.Service.CredentialsService;
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

    @Autowired
    private CredentialsService credentialsService;

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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
	        return "paginaRicerca.html";
		}else{
            UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
            model.addAttribute("username", userDetails.getUsername());
            
			if (credentials.getRole().equals("ADMIN")) {
                model.addAttribute("admin", credentials.getAdmin());
				return "Admin/paginaRicerca.html";
			}else{
                model.addAttribute("cuoco", credentials.getCuoco());
                return "Chef/paginaRicerca.html";
            }

        }
    }

    

    
}
