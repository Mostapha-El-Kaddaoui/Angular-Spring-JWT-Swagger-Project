package ma.enset.tresorory.Repositories;

import ma.enset.tresorory.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    List<Customer> findByNameContains(String keyword);
}
