package br.com.test.springbootdemo.service;

import br.com.test.springbootdemo.model.entity.Client;
import br.com.test.springbootdemo.model.exception.BusinessException;
import br.com.test.springbootdemo.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Page<Client> findAll(Client client, Pageable pageable){

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase();

        return clientRepository.findAll(Example.of(client, matcher),pageable);
    }

    public Client createClient(Client client){
        return clientRepository.save(client);
    }

    public Client udpateClient(Client client) {

        return clientRepository.findById(client.getId())
                .map(clientUpdate -> {
                    clientUpdate.setAnnotation(client.getAnnotation());
                    clientUpdate.setBirthday(client.getBirthday());
                    clientUpdate.setName(client.getName());
                    return clientRepository.save(clientUpdate);
                })
                .orElseThrow(() ->
                        new BusinessException("Não encontrado!", HttpStatus.NOT_FOUND));
    }

}
