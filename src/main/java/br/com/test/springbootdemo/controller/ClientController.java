package br.com.test.springbootdemo.controller;

import br.com.test.springbootdemo.model.dto.response.ResponseError;
import br.com.test.springbootdemo.model.dto.request.ClientRequest;
import br.com.test.springbootdemo.model.entity.Client;
import br.com.test.springbootdemo.service.ClientService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/v1/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ModelMapper modelMapper;

    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Internal Server Error", response = ResponseError.class)
    })
    @GetMapping
    public ResponseEntity<Page<Client>> findAll(
                                 @RequestParam(defaultValue = "${app.page.default}") Integer page,
                                 @RequestParam(defaultValue = "${app.size.default}") Integer size,
                                 @ApiParam ClientRequest findClient) {

        var client = modelMapper.map(findClient, Client.class);
        var pageable = PageRequest.of(page, size);

        return ResponseEntity.ok(clientService.findAll(client,pageable));
    }

    @ApiOperation(value = "Create client", notes = "This method create a new client")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request", response = ResponseError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ResponseError.class)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Long> createClient(@Validated @RequestBody ClientRequest createClient) {
        var client = modelMapper.map(createClient, Client.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(client).getId());
    }


    @ApiOperation(value = "Update client", notes = "This method update a client")
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Bad Request", response = ResponseError.class),
            @ApiResponse(code = 404, message = "Not found", response = ResponseError.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ResponseError.class)
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @Validated @RequestBody ClientRequest updateClient) {
        var client = modelMapper.map(updateClient, Client.class);
        client.setId(id);
        return ResponseEntity.ok(clientService.udpateClient(client));
    }
}
