package com.example.customer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.config.Projection;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
}

@RepositoryRestResource
interface CustomerRepo extends JpaRepository<Customer, Long> {


}

@Projection(name = "p1", types = Customer.class)
interface CustomerProjection {
    public String getId();

    public String getName();
}


@SpringBootApplication
public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepo customerRepo, RepositoryRestConfiguration repositoryRestConfiguration) {
        return args -> {
            repositoryRestConfiguration.exposeIdsFor(Customer.class);
            customerRepo.save(new Customer(null, "nom1", "reda1@gmail.com"));
            customerRepo.save(new Customer(null, "nom2", "reda2@gmail.com"));
            customerRepo.save(new Customer(null, "nom3", "reda3@gmail.com"));
            customerRepo.findAll().forEach(System.out::println);
        };


    }


}
