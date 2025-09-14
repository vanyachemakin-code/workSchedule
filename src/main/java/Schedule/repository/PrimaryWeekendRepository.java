package Schedule.repository;

import Schedule.entity.PrimaryWeekend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimaryWeekendRepository extends JpaRepository<PrimaryWeekend, Integer> {
}
