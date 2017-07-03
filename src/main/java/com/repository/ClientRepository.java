package com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.beans.Client;

@Repository
public interface ClientRepository extends CrudRepository<Client, Long>{

}
