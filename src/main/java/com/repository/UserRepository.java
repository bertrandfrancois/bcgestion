package com.repository;

import com.beans.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.beans.Client;

@Repository
public interface UserRepository extends CrudRepository<User, String>{

}