package example.repository;

import example.domen.FiskulturnaSala;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FiskulturnaSalaRepository extends JpaRepository<FiskulturnaSala, Long> {
    Optional<FiskulturnaSala> findByIme(String ime);
}

