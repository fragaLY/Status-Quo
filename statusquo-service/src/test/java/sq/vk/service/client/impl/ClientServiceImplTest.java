package sq.vk.service.client.impl;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import sq.vk.core.converter.client.ClientConverter;
import sq.vk.core.dao.client.ClientDao;
import sq.vk.core.domain.client.Client;
import sq.vk.core.domain.client.ClientRole;
import sq.vk.core.dto.client.ClientDto;
import sq.vk.core.exceptions.client.ClientNotFoundException;
import sq.vk.service.client.ClientService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
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
  public void findAll() {

    //given
    final ClientRole role = ClientRole.DEVELOPER;

    final Integer firstID = 1;
    final Integer secondID = 2;

    final Client firstClient = new Client.Builder(EMAIL)
                                .setId(firstID)
                                .setFirstName(FIRSTNAME)
                                .setSecondName(SECONDNAME)
                                .setRole(role)
                                .setPassword(PASSWORD)
                              .build();

    final Client secondClient = new Client.Builder(EMAIL)
                                  .setId(secondID)
                                  .setFirstName(FIRSTNAME)
                                  .setSecondName(SECONDNAME)
                                  .setRole(role)
                                  .setPassword(PASSWORD)
                                .build();

    final ClientDto firstExpectedClientDto = new ClientDto.Builder(EMAIL)
                                              .setId(firstID)
                                              .setFirstName(FIRSTNAME)
                                              .setSecondName(SECONDNAME)
                                              .setRole(role)
                                              .setPassword(PASSWORD)
                                            .build();

    final ClientDto secondExpectedClientDto = new ClientDto.Builder(EMAIL)
                                                .setId(secondID)
                                                .setFirstName(FIRSTNAME)
                                                .setSecondName(SECONDNAME)
                                                .setRole(role)
                                                .setPassword(PASSWORD)
                                              .build();

    List<Client> clients = ImmutableList.of(firstClient, secondClient);

    List<ClientDto> expectedDtos = ImmutableList.of(firstExpectedClientDto, secondExpectedClientDto);

    //when
    when(dao.findAll()).thenReturn(clients);

    when(converter.apply(firstClient)).thenReturn(firstExpectedClientDto);
    when(converter.apply(secondClient)).thenReturn(secondExpectedClientDto);

    List<ClientDto> actualClientDtos = service.findAll();

    //then
    assertEquals(expectedDtos, actualClientDtos);

  }

  @Test
  public void findOneByEmail() {

    //given
    final ClientRole role = ClientRole.DEVELOPER;
    final Integer id = 1;

    final Client client = new Client.Builder(EMAIL)
                            .setId(id)
                            .setFirstName(FIRSTNAME)
                            .setSecondName(SECONDNAME)
                            .setRole(role)
                            .setPassword(PASSWORD)
                          .build();

    final ClientDto expectedClientDto = new ClientDto.Builder(EMAIL)
                                          .setId(id)
                                          .setFirstName(FIRSTNAME)
                                          .setSecondName(SECONDNAME)
                                          .setRole(role)
                                          .setPassword(PASSWORD)
                                        .build();

    //when
    when(dao.findOneByEmail(EMAIL)).thenReturn(Optional.of(client));

    when(converter.apply(client)).thenReturn(expectedClientDto);

    ClientDto actualClientDto = service.findOneByEmail(EMAIL);

    //then
    assertEquals(expectedClientDto,actualClientDto);

  }

  @Test(expected = ClientNotFoundException.class)
  public void findOneByEmail_whenClientIsAbsent() {

    //given
    final Client client = null;

    //when
    when(dao.findOneByEmail(EMAIL)).thenReturn(Optional.ofNullable(client));

    service.findOneByEmail(EMAIL);

  }


  @Test
  public void findOneById() {

    //given
    final ClientRole role = ClientRole.DEVELOPER;
    final Integer id = 1;

    final Client client = new Client.Builder(EMAIL)
                            .setId(id)
                            .setFirstName(FIRSTNAME)
                            .setSecondName(SECONDNAME)
                            .setRole(role)
                            .setPassword(PASSWORD)
                          .build();

    final ClientDto expectedClientDto = new ClientDto.Builder(EMAIL)
                                          .setId(id)
                                          .setFirstName(FIRSTNAME)
                                          .setSecondName(SECONDNAME)
                                          .setRole(role)
                                          .setPassword(PASSWORD)
                                        .build();

    //when
    when(dao.findOne(id)).thenReturn(client);

    when(converter.apply(client)).thenReturn(expectedClientDto);

    ClientDto actualClientDto = service.findOne(id);

    //then
    assertEquals(expectedClientDto,actualClientDto);

  }

  @Test
  public void save() {

    //given
    final ClientRole role = ClientRole.DEVELOPER;
    final Integer id = 1;

    final Client client = new Client.Builder(EMAIL)
                            .setId(id)
                            .setFirstName(FIRSTNAME)
                            .setSecondName(SECONDNAME)
                            .setRole(role)
                            .setPassword(PASSWORD)
                          .build();

    final ClientDto expectedClientDto = new ClientDto.Builder(EMAIL)
                                          .setId(id)
                                          .setFirstName(FIRSTNAME)
                                          .setSecondName(SECONDNAME)
                                          .setRole(role)
                                          .setPassword(PASSWORD)
                                        .build();

    //when
    when(converter.transform(expectedClientDto)).thenReturn(client);

    when(dao.save(client)).thenReturn(client);

    ClientDto actualClientDto = service.save(expectedClientDto);

    //then
    assertEquals(expectedClientDto,actualClientDto);

  }

  @Test
  public void delete() {

    //given
    final ClientRole role = ClientRole.DEVELOPER;
    final Integer id = 1;

    final Client client = new Client.Builder(EMAIL)
                            .setId(id)
                            .setFirstName(FIRSTNAME)
                            .setSecondName(SECONDNAME)
                            .setRole(role)
                            .setPassword(PASSWORD)
                          .build();

    final ClientDto clientDto = new ClientDto.Builder(EMAIL)
                                  .setId(id)
                                  .setFirstName(FIRSTNAME)
                                  .setSecondName(SECONDNAME)
                                  .setRole(role)
                                  .setPassword(PASSWORD)
                                .build();

    //when
    when(converter.transform(clientDto)).thenReturn(client);

    doNothing().when(dao).delete(client);

    ClientDto deletedClientDto = service.delete(clientDto);

    //then
    assertEquals(clientDto,deletedClientDto);

  }

  @Test
  public void deleteClientById() {

    //given
    final Integer id = 1;

    //when
    doNothing().when(dao).delete(id);

    Integer expectedId = service.delete(id);

    //then
    assertEquals(id,expectedId);

  }

  @Test
  public void deleteClientByEmail() {

    //given
    final String email = EMAIL;

    //when
    doNothing().when(dao).deleteByEmail(email);

    String actualEmail = service.delete(email);

    //then
    assertEquals(email,actualEmail);

  }

}