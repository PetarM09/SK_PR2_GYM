package example.repository;

import example.domen.TerminTreninga;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TerminTreningaRepository extends JpaRepository<TerminTreninga, Long> {

}
