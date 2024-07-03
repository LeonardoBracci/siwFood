package it.uniroma3.siwfood.Repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siwfood.Model.Admin;

public interface AdminRepository extends CrudRepository<Admin, Long>{
    //public Optional<Admin> findByUsername(String username);
}
