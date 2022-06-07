package br.com.test.springbootdemo.repository;

import br.com.test.springbootdemo.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
