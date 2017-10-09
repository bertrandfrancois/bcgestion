package com.repository;

import com.beans.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long>{

    Client findTopByOrderByIdDesc();
}
