package sq.vk.dto.Client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import sq.vk.domain.client.ClientRole;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
public class ClientDto {

    private String password;
    private Integer id;
    private String email;
    private String firstName;
    private String secondName;
    private String role;

    private ClientDto(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.password = builder.password;
        this.firstName = builder.firstName;
        this.secondName = builder.secondName;
        this.role = builder.role.getClientRole();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @JsonIgnore
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ClientDto clientDto = (ClientDto) o;

        return new EqualsBuilder()
                .append(id, clientDto.id)
                .append(email, clientDto.email)
                .append(password, clientDto.password)
                .append(firstName, clientDto.firstName)
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
        return "ClientDto{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", role=" + role +
                '}';
    }

    public static class Builder {

        private Integer id;
        private String email;
        private String password;
        private String firstName;
        private String secondName;
        private ClientRole role;

        public Builder(String email) {
            this.email = email;
        }

        public Builder setId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setSecondName(String secondName) {
            this.secondName = secondName;
            return this;
        }

        public Builder setRole(ClientRole role) {
            this.role = role;
            return this;
        }

        public ClientDto build() {
            return new ClientDto(this);
        }
    }
}
