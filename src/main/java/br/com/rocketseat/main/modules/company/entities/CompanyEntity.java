package br.com.rocketseat.main.modules.company.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "company")
@Data
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Pattern(regexp = "^(\\w+)+$", message = "O campo [username] não deve conter espaços.")
    private String username;

    @Email(message = "O campo [email] deve conter um e-mail válido.")
    private String email;

    @Length(max = 100, min = 6, message = "O campo [password] deve conter entre 6 e 100 caracteres.")
    private String password;

    private String name;
    private String website;
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
