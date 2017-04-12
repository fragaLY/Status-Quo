package sq.vk.client.service.impl;

import java.util.List;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import sq.vk.client.converter.ClientConverter;
import sq.vk.client.dao.ClientDao;
import sq.vk.client.domain.Client;
import sq.vk.client.domain.ClientRole;
import sq.vk.client.dto.ClientDto;
import sq.vk.client.service.ClientService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Vadzim_Kavalkou on 4/12/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class ClientServiceImplTest {

  private static final String EMAIL = "email@something.com";
  private static final String FIRSTNAME = "FirstName";
  private static final String SECONDNAME = "SecondName";
  private static final String PASSWORD = "Password";

  @Mock
  private ClientDao dao;

  @Mock
  private ClientConverter converter;

  @InjectMocks
  private ClientService service = new ClientServiceImpl();

  @Test
  public void getAllClients() {

    //given
    final ClientRole role = ClientRole.DEVELOPER;

    final Integer firstID = 1;
    final Integer secondID = 2;

    final Client firstClient = new Client.Builder(EMAIL).setId(firstID).setFirstName(FIRSTNAME).setSecondName(
      SECONDNAME).setRole(role).setPassword(PASSWORD).build();

    final Client secondClient = new Client.Builder(EMAIL).setId(secondID).setFirstName(
      FIRSTNAME).setSecondName(SECONDNAME).setRole(role).setPassword(PASSWORD).build();

    final ClientDto firstExpectedClientDto = new ClientDto.Builder(EMAIL).setId(firstID).setFirstName(
      FIRSTNAME).setSecondName(SECONDNAME).setRole(role).setPassword(PASSWORD).build();

    final ClientDto secondExpectedClientDto = new ClientDto.Builder(EMAIL).setId(secondID).setFirstName(
      FIRSTNAME).setSecondName(SECONDNAME).setRole(role).setPassword(PASSWORD).build();

    List<Client> clients = ImmutableList.of(firstClient, secondClient);

    List<ClientDto> expectedDtos = ImmutableList.of(firstExpectedClientDto, secondExpectedClientDto);

    //when
    when(dao.getAllClients()).thenReturn(clients);

    when(converter.apply(firstClient)).thenReturn(firstExpectedClientDto);
    when(converter.apply(secondClient)).thenReturn(secondExpectedClientDto);

    List<ClientDto> actualClientDtos = service.getAllClients();

    //then
    assertEquals(expectedDtos, actualClientDtos);

  }

  @Test
  public void getClientByEmail() {

    //given
    final ClientRole role = ClientRole.DEVELOPER;
    final Integer id = 1;

    final Client client = new Client.Builder(EMAIL).setId(id).setFirstName(FIRSTNAME).setSecondName(
        SECONDNAME).setRole(role).setPassword(PASSWORD).build();

    final ClientDto expectedClientDto = new ClientDto.Builder(EMAIL).setId(id).setFirstName(
        FIRSTNAME).setSecondName(SECONDNAME).setRole(role).setPassword(PASSWORD).build();

    //when
    when(dao.getClientByEmail(EMAIL)).thenReturn(client);

    when(converter.apply(client)).thenReturn(expectedClientDto);

    ClientDto actualClientDto = service.getClientByEmail(EMAIL);

    //then
    assertEquals(expectedClientDto,actualClientDto);

  }

  @Test
  public void getClientById() {

    //given
    final ClientRole role = ClientRole.DEVELOPER;
    final Integer id = 1;

    final Client client = new Client.Builder(EMAIL).setId(id).setFirstName(FIRSTNAME).setSecondName(
        SECONDNAME).setRole(role).setPassword(PASSWORD).build();

    final ClientDto expectedClientDto = new ClientDto.Builder(EMAIL).setId(id).setFirstName(
        FIRSTNAME).setSecondName(SECONDNAME).setRole(role).setPassword(PASSWORD).build();

    //when
    when(dao.getClientById(id)).thenReturn(client);

    when(converter.apply(client)).thenReturn(expectedClientDto);

    ClientDto actualClientDto = service.getClientById(id);

    //then
    assertEquals(expectedClientDto,actualClientDto);

  }

  @Test
  public void saveClient() {

    //given
    final ClientRole role = ClientRole.DEVELOPER;
    final Integer id = 1;

    final Client client = new Client.Builder(EMAIL).setId(id).setFirstName(FIRSTNAME).setSecondName(
        SECONDNAME).setRole(role).setPassword(PASSWORD).build();

    final ClientDto expectedClientDto = new ClientDto.Builder(EMAIL).setId(id).setFirstName(
        FIRSTNAME).setSecondName(SECONDNAME).setRole(role).setPassword(PASSWORD).build();

    //when
    when(converter.transform(expectedClientDto)).thenReturn(client);

    when(dao.saveClient(client)).thenReturn(client);

    ClientDto actualClientDto = service.saveClient(expectedClientDto);

    //then
    assertEquals(expectedClientDto,actualClientDto);

  }

  @Test
  public void deleteClient() {

    //given
    final ClientRole role = ClientRole.DEVELOPER;
    final Integer id = 1;

    final Client client = new Client.Builder(EMAIL).setId(id).setFirstName(FIRSTNAME).setSecondName(
        SECONDNAME).setRole(role).setPassword(PASSWORD).build();

    final ClientDto clientDto = new ClientDto.Builder(EMAIL).setId(id).setFirstName(
        FIRSTNAME).setSecondName(SECONDNAME).setRole(role).setPassword(PASSWORD).build();

    //when
    when(converter.transform(clientDto)).thenReturn(client);

    when(dao.deleteClient(client)).thenReturn(client);

    ClientDto deletedClientDto = service.deleteClient(clientDto);

    //then
    assertEquals(clientDto,deletedClientDto);

  }

  @Test
  public void deleteClientById() {

    //given
    final Integer id = 1;

    //when
    when(dao.deleteClient(id)).thenReturn(id);

    Integer expectedId = service.deleteClient(id);

    //then
    assertEquals(id,expectedId);

  }

  @Test
  public void deleteClientByEmail() {

    //given
    final String email = EMAIL;

    //when
    when(dao.deleteClient(email)).thenReturn(email);

    String actualEmail = service.deleteClient(email);

    //then
    assertEquals(email,actualEmail);

  }

}