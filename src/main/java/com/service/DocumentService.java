package com.service;

import com.beans.Document;
import com.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DocumentService implements BaseService<Document> {

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public List<Document> findAll() {
        return null;
    }

    @Override
    public void save(Document document) {
        documentRepository.save(document);
    }

    @Override
    public Document find(long id) {
        return documentRepository.findOne(id);
    }

    @Override
    public void delete(long id) {

    }
}
