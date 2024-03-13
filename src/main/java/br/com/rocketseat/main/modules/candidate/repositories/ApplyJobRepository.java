package br.com.rocketseat.main.modules.candidate.repositories;

import br.com.rocketseat.main.modules.candidate.entities.ApplyJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, UUID> {
}
