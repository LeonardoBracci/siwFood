package it.uniroma3.siwfood.Authentication;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import it.uniroma3.siwfood.Model.Credentials;
import it.uniroma3.siwfood.Model.Cuoco;
import it.uniroma3.siwfood.Model.Ricetta;
import it.uniroma3.siwfood.Service.CredentialsService;
import it.uniroma3.siwfood.Service.CuocoService;
import it.uniroma3.siwfood.Service.RicettaService;
import jakarta.validation.Valid;


@Controller
public class AuthenticationController {
    @Autowired
    private CredentialsService credentialsService;
    @Autowired
    private CuocoService cuocoService;
    @Autowired
    private GlobalController globalController;

   @Autowired
    private RicettaService ricettaService; 

   @GetMapping("/")
    public String getIndex(Model model) {

        Iterable<Ricetta> ricette = ricettaService.findAll(); // Recupera tutte le ricette dal servizio
        Iterable<Cuoco> cuochi = cuocoService.findAll(); // Recupera tutti i cuochi dal servizio

        model.addAttribute("ricette", ricette);
        model.addAttribute("cuochi", cuochi);
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
	        return "index.html";
		}else{
            UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
            model.addAttribute("username", userDetails.getUsername());
            
			if (credentials.getRole().equals("ADMIN")) {
                model.addAttribute("admin", credentials.getAdmin());
				return "Admin/adminIndex.html";
			}else{
                model.addAttribute("cuoco", credentials.getCuoco());
                return "Chef/chefIndex.html";
            }

        }
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        model.addAttribute("credentials", new Credentials());
        return "login.html";
    }
    

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("credentials", new Credentials()); //crea una nuova istanza per l'html
        model.addAttribute("cuoco", new Cuoco());
        return "register.html";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("credentials") Credentials credentials,
                               @Valid @ModelAttribute("cuoco") Cuoco cuoco,
                               @RequestParam("confirmPassword") String confirmPassword,
                               @RequestParam("immagine") MultipartFile file,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register.html";
        }
        
        if (!credentials.getPassword().equals(confirmPassword)) {
            model.addAttribute("passwordError", "Le password non corrispondono");
            return "register.html";
        }
            credentials.setRole("CHEF");
            cuoco.setCredentials(credentials);
            credentials.setCuoco(cuoco);
            credentialsService.saveCredentials(credentials);
            if (!file.isEmpty()) {
                try {
                    byte[] byteFoto = file.getBytes();
                    cuoco.setBase64(Base64.getEncoder().encodeToString(byteFoto));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            cuocoService.save(cuoco);

        return "redirect:/login";
    }

    @GetMapping("/success")
    public String showSuccessForm() {
        if(credentialsService.getCredentials(globalController.getUser().getUsername()).getRole().equals("ADMIN")) 
            return "Admin/successAdmin.html";
        else 
        /* CHEF */
            return "Chef/successChef.html";
    }

    
}
