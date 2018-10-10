package com.repository;

import com.beans.Document;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DocumentRepository extends CrudRepository<Document, Long>{

    Optional<Document> findByCode(String value);
}
