package br.com.rocketseat.main.modules.company.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "job")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne() // Faz referência ao relacionamento da tabela com o Hibernate
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    // [insertable = false, updatable = false] Seta configurações para não salvar no banco
    private CompanyEntity companyEntity;

    @Column(name = "company_id", nullable = false)
    private UUID companyId;

    @NotBlank(message = "O campo [level] é obrigatório.")
    @Schema(example = "Sênior")
    private String level;

    @Schema(example = "Vaga para designer.")
    private String description;
    @Schema(example = "Gympass, plano de saúde.")
    private String benefits;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
