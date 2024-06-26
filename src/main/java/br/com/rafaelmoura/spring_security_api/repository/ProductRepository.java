package br.com.rafaelmoura.spring_security_api.repository;

import br.com.rafaelmoura.spring_security_api.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySerialNumber(String serialNumber);
}
