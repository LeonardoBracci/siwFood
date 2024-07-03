package it.uniroma3.siwfood.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import it.uniroma3.siwfood.Model.Credentials;
import it.uniroma3.siwfood.Repository.CredentialsRepository;

@Service
public class CredentialsService {
    @Autowired
    private CredentialsRepository credentialsRepository;

    public Credentials getCredentials(Long id){
        return this.credentialsRepository.findById(id).get();
    }

    public Credentials getCredentials(String username){
        return this.credentialsRepository.findByUsername(username).get();
    }



    @Autowired
    private PasswordEncoder passwordEncoder;

    public Credentials saveCredentials(Credentials credentials){
        credentials.setPassword(passwordEncoder.encode(credentials.getPassword()));
        return this.credentialsRepository.save(credentials);
    }

    public boolean authenticate(String username, String password) {
        Credentials credentials = credentialsRepository.findByUsername(username).get();
        if (credentials != null) {
            return passwordEncoder.matches(password, credentials.getPassword());
        }
        return false;
    }
    
}
