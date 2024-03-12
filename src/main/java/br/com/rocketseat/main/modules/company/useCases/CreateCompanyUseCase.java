package br.com.rocketseat.main.modules.company.useCases;

import br.com.rocketseat.main.exceptions.UserFoundException;
import br.com.rocketseat.main.modules.company.entities.CompanyEntity;
import br.com.rocketseat.main.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CompanyEntity execute(CompanyEntity companyEntity) {
        this.companyRepository.findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
                              .ifPresent((company) -> {
                                  throw new UserFoundException("Essa empresa já existe");
                              });

        var password = passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(password);

        return this.companyRepository.save(companyEntity);
    }
}
