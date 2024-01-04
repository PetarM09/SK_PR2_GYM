package example.repository;

import example.domen.SalaTrening;
import example.domen.SalaTreningId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaTreningRepository extends JpaRepository<SalaTrening, SalaTreningId> {
}
