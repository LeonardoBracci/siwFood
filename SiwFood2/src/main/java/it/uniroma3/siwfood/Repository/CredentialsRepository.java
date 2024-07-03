package it.uniroma3.siwfood.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwfood.Model.Credentials;

public interface CredentialsRepository extends CrudRepository<Credentials, Long>{
    public Optional<Credentials> findByUsername(String username);
}