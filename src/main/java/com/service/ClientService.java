package com.service;

import com.model.Client;
import com.google.common.collect.Lists;
import com.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClientService implements BaseService<Client> {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> findAll() {
        return Lists.newArrayList(clientRepository.findAll());
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client find(long id) {
        return clientRepository.findById(id).get();
    }

    @Override
    public void delete(long id) {
        clientRepository.deleteById(id);
    }
}
