package sq.vk.core.converter.client;

import org.junit.Before;
import org.junit.Test;
import sq.vk.core.domain.client.Client;
import sq.vk.core.domain.client.ClientRole;
import sq.vk.core.dto.client.ClientDto;
import sq.vk.core.exceptions.client.ClientNotFoundException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Vadzim_Kavalkou on 4/12/2017.
 */
public class ClientConverterTest {

  private static final String EMAIL = "email@something.com";
  private static final String FIRSTNAME = "FirstName";
  private static final String SECONDNAME = "SecondName";
  private static final String PASSWORD = "Password";

  private ClientConverter converter;

  @Before
  public void setUp() {

    converter = new ClientConverter();
  }

  @Test
  public void apply() {

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
    ClientDto actualClientDto = converter.apply(client);

    //then
    assertEquals(expectedClientDto,actualClientDto);

  }

  @Test(expected = ClientNotFoundException.class)
  public void apply_whenClientIsNull() {

    //when
    converter.apply(null);

  }

  @Test
  public void transform() {

    //given
    final ClientRole role = ClientRole.DEVELOPER;
    final Integer id = 1;

    final Client expectedClient = new Client.Builder(EMAIL)
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
    Client actualClient = converter.transform(clientDto);

    //then
    assertEquals(expectedClient,actualClient);

  }

}