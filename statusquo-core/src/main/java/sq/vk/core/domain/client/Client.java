package sq.vk.core.domain.client;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import sq.vk.core.domain.statistic.Statistic;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
@Entity
@Table(name = "clients")
public class Client {

  private Integer id;
  private String email;
  private String password;
  private String firstName;
  private String secondName;
  private ClientRole role;
  private Set<Statistic> statistics;

  public Client() {
  }

  private Client(Builder builder) {


    this.id = builder.id;
    this.email = builder.email;
    this.password = builder.password;
    this.firstName = builder.firstName;
    this.secondName = builder.secondName;
    this.role = builder.role;
    this.statistics = builder.statistics;

  }

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  @Column(name = "email")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Column(name = "password")
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Column(name = "firstName")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  @Column(name = "secondName")
  public String getSecondName() {
    return secondName;
  }

  public void setSecondName(String secondName) {
    this.secondName = secondName;
  }

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  public ClientRole getRole() {
    return role;
  }

  public void setRole(ClientRole role) {
    this.role = role;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
  @OrderBy
  public Set<Statistic> getStatistics() {
    return statistics;
  }

  public void setStatistics(Set<Statistic> statistics) {
    this.statistics = statistics;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;

    if (o == null || getClass() != o.getClass())
      return false;

    Client client = (Client)o;

    return new EqualsBuilder()
            .append(id, client.id)
            .append(email, client.email)
            .append(password,client.password)
            .append(firstName, client.firstName)
            .append(secondName,client.secondName)
            .append(role, client.role)
            .append(statistics,client.statistics)
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
            .append(statistics)
          .toHashCode();
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this)
            .append("id", id)
            .append("email", email)
            .append("firstName", firstName)
            .append("secondName", secondName)
            .append("role", role)
            .append("statistics",statistics)
          .toString();
  }

  public static class Builder {

    private final String email;
    private Integer id;
    private String password;
    private String firstName;
    private String secondName;
    private ClientRole role;
    private Set<Statistic> statistics;

    public Builder(final String email) {
      this.email = email;
    }

    public Builder setId(final Integer id) {
      this.id = id;
      return this;
    }

    public Builder setPassword(final String password) {
      this.password = password;
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

    public Builder setStatistics(Set<Statistic> statistics) {
      this.statistics = statistics;
      return this;
    }

    public Client build() {
      return new Client(this);
    }

  }

}
