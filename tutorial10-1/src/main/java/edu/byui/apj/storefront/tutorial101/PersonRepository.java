package edu.byui.apj.storefront.tutorial101;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//It uses the @RepositoryRestResource annotation to direct Spring MVC to create RESTful endpoints at /people
@RepositoryRestResource(collectionResourceRel="people", path="people")
public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByLastName(@Param("name") String name);

}
