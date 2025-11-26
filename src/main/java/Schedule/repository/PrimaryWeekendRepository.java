package Schedule.repository;

import Schedule.entity.PrimaryWeekend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PrimaryWeekendRepository extends JpaRepository<PrimaryWeekend, Long> {

    PrimaryWeekend getByEmployeeId(@Param("employee_id") Long employeeId);

    @Modifying
    @Query("DELETE FROM primary_weekends pw WHERE pw.employee.id = :employeeId")
    void deleteAllByEmployeeId(@Param("employee_id") Long employeeId);
}
