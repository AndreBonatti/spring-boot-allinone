package br.com.test.springbootdemo.controller;

import br.com.test.springbootdemo.model.dto.request.ClientRequest;
import br.com.test.springbootdemo.model.entity.Client;
import br.com.test.springbootdemo.service.ClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @InjectMocks
    private ClientController controller;

    @Mock
    private ClientService service;

    @Mock
    private ModelMapper modelMapper;

    private ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    private String pathDto= Paths.get("src","test","resources","model","dto").toFile().getAbsolutePath();
    private String pathEntity = Paths.get("src","test","resources","model","entity").toFile().getAbsolutePath();

    private Client getClient() throws IOException {
        return mapper.readValue(new File(pathEntity + "/Client.json"),Client.class);
    }

    private ClientRequest getClientRequest() throws IOException {
        return mapper.readValue(new File(pathDto + "/ClientRequest.json"),ClientRequest.class);
    }
    @Test
    void findAllEmpty() throws IOException {
        ClientRequest clientRequest = getClientRequest();
        var findResult = new PageImpl(Arrays.asList());

        when(service.findAll(any(), any())).thenReturn(findResult);

        var result = controller.findAll(0,10,clientRequest);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(0L, result.getBody().getTotalElements());
    }

    @Test
    void findAllSuccess() throws IOException {

        ClientRequest clientRequest = getClientRequest();
        Client client = getClient();
        Client client2 = getClient();
        var findResult = new PageImpl(Arrays.asList(client, client2));

        when(service.findAll(any(), any())).thenReturn(findResult);

        var result = controller.findAll(0,10,clientRequest);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2L, result.getBody().getTotalElements());
    }



    @Test
    void createClientSuccess()  throws IOException{

        ClientRequest clientRequest = getClientRequest();
        Client client = getClient();

        when(service.createClient(any())).thenReturn(client);

        var result = controller.createClient(clientRequest);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
    }

    @Test
    void updateClientSuccess()  throws IOException{

        ClientRequest clientRequest = getClientRequest();
        Client client = getClient();

        when(modelMapper.map(any(),any())).thenReturn(client);
        when(service.udpateClient(any())).thenReturn(client);

        var result = controller.updateClient(1L,clientRequest);

        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}