package edu.byui.apj.storefront.tutorial101;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;

public interface CustomerRepository extends CrudRepository<Customer, Long>{

    List<Customer> findByLastName(String lastName);

    Customer findById(long id);

    @RestResource(path = "findByFirstLastName")
    List<Customer> findCustomersByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(String  firstName, String lastName);
}
