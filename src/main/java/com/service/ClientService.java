package com.service;

import com.beans.Client;
import com.google.common.collect.Lists;
import com.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClientService implements BaseService<Client> {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> findAll() {
        return Lists.newArrayList(clientRepository.findAll());
    }

    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }

    @Override
    public Client find(long id) {
        return clientRepository.findById(id).get();
    }

    @Override
    public void delete(long id) {
        clientRepository.deleteById(id);
    }

    public Client findLastClient() {
        return clientRepository.findTopByOrderByIdDesc();
    }
}
