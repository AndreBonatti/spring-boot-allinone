package br.com.test.springbootdemo.service;

import br.com.test.springbootdemo.model.entity.Client;
import br.com.test.springbootdemo.model.exception.BusinessException;
import br.com.test.springbootdemo.repository.ClientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @InjectMocks
    private ClientService service;

    @Mock
    private ClientRepository repository;

    private ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    private String pathEntity = Paths.get("src","test","resources","model","entity").toFile().getAbsolutePath();

    private Client getClient() throws IOException {
        return mapper.readValue(new File(pathEntity + "/Client.json"),Client.class);
    }

    @Test
    void createClientSuccess() throws IOException {

        when(repository.save(any())).thenReturn(getClient());

        Assertions.assertEquals(1L, service.createClient(any()).getId());
    }

    @Test
    void udpateClientSuccess() throws IOException {

        Client client = getClient();

        when(repository.findById(any())).thenReturn(Optional.of(client));
        when(repository.save(any())).thenReturn(client);

        Assertions.assertEquals(1L, service.udpateClient(client).getId());
    }

    @Test
    void udpateClientNotFound() throws IOException {

        when(repository.findById(any())).thenReturn(Optional.empty());

        Client findClient = new Client();
        findClient.setId(2L);

        assertThrows(BusinessException.class, () -> service.udpateClient(findClient));
    }


    @Test
    void findAllSuccess() throws IOException {

        Client client = getClient();
        Client client2 = getClient();
        var result = new PageImpl(Arrays.asList(client, client2));

        when(repository.findAll(any(Example.class),any(Pageable.class))).thenReturn(result);

        assertEquals(2, service.findAll(client, PageRequest.of(0,1)).getSize());
    }

}