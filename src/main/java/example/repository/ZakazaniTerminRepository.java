package example.repository;

import example.domen.ZakazaniTermin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZakazaniTerminRepository extends JpaRepository<ZakazaniTermin, Long> {
}
