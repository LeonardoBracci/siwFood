package it.uniroma3.siwfood.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siwfood.Model.Admin;
import it.uniroma3.siwfood.Repository.AdminRepository;


@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public void save(Admin admin) {
        adminRepository.save(admin);
    }
    public Iterable<Admin> findAll() {
		return adminRepository.findAll();
	}
}
