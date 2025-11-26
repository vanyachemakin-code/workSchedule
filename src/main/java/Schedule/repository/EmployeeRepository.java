package Schedule.repository;

import Schedule.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Modifying
    @Query("DELETE FROM employees e WHERE e.company.id = :companyId")
    void deleteAllByCompanyId(@Param("companyId") Long companyId);
}
