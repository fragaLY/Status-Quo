package sq.vk.client.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import sq.vk.client.domain.ClientRole;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
//TODO VK: implement validations
public class ClientDto {

  private Integer id;

  @Pattern(regexp = "\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,6}")
  @Size(min = 7, max = 100)
  private String email;

  @JsonIgnore
  private String password;

  @Pattern(regexp = "[A-Za-z]+")
  @Size(min = 2, max = 100)
  private String firstName;

  @Pattern(regexp = "[A-Za-z]+")
  @Size(min = 2, max = 100)
  private String secondName;

  @Size(min = 2, max = 20)
  private ClientRole role;

  public ClientDto() {
  }

  private ClientDto(Builder builder) {

    this.id = builder.id;
    this.email = builder.email;
    this.firstName = builder.firstName;
    this.secondName = builder.secondName;
    this.role = builder.role;
    this.password = builder.password;

  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getSecondName() {
    return secondName;
  }

  public void setSecondName(String secondName) {
    this.secondName = secondName;
  }

  public ClientRole getRole() {
    return role;
  }

  public void setRole(ClientRole role) {
    this.role = role;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;

    if (o == null || getClass() != o.getClass())
      return false;

    ClientDto clientDto = (ClientDto)o;

    return new EqualsBuilder()
            .append(id, clientDto.id)
            .append(email, clientDto.email)
            .append(password,clientDto.password)
            .append(firstName,clientDto.firstName)
            .append(secondName, clientDto.secondName)
            .append(role, clientDto.role)
          .isEquals();

  }

  @Override
  public int hashCode() {

    return new HashCodeBuilder(17, 37)
            .append(id)
            .append(email)
            .append(password)
            .append(firstName)
            .append(secondName)
            .append(role)
          .toHashCode();

  }

  @Override
  public String toString() {

    return new ToStringBuilder(this)
            .append("id", id)
            .append("email", email)
            .append("password",password)
            .append("firstName", firstName)
            .append("secondName", secondName)
            .append("role", role)
          .toString();

  }

  public static class Builder {

    private final String email;
    private Integer id;
    private String firstName;
    private String secondName;
    private ClientRole role;
    private String password;

    public Builder(final String email) {
      this.email = email;
    }

    public Builder setId(final Integer id) {
      this.id = id;
      return this;
    }

    public Builder setFirstName(final String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder setSecondName(final String secondName) {
      this.secondName = secondName;
      return this;
    }

    public Builder setRole(final ClientRole role) {
      this.role = role;
      return this;
    }

    public Builder setPassword(String password) {
      this.password = password;
      return this;
    }

    public ClientDto build() {
      return new ClientDto(this);
    }

  }

}
