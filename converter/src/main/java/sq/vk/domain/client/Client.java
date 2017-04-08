package sq.vk.domain.client;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

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

    public Client() {
    }

    private Client(Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.password = builder.password;
        this.firstName = builder.firstName;
        this.secondName = builder.secondName;
        this.role = builder.role;
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
    @NotBlank(message = "Email should not be blank.")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "password")
    @NotBlank(message = "Password should not be blank.")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "firstName")
    @NotBlank(message = "Firstname should not be blank.")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "secondName")
    @NotBlank(message = "Secondname should not be blank.")
    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    @Enumerated(EnumType.STRING)
    public ClientRole getRole() {
        return role;
    }

    public void setRole(ClientRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        return new EqualsBuilder()
                .append(id, client.id)
                .append(email, client.email)
                .append(password, client.password)
                .append(firstName, client.firstName)
                .append(secondName, client.secondName)
                .append(role, client.role)
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

        public Client build() {
            return new Client(this);
        }
    }

}
