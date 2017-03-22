package sq.vk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sq.vk.dto.Client.ClientDto;
import sq.vk.service.client.ClientService;

import java.util.List;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
@RestController
@RequestMapping("/clients")
public class TestRestController {

    @Autowired
    private ClientService clientService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public List<ClientDto> greeting() {
        return clientService.getAllClients();
    }
}
