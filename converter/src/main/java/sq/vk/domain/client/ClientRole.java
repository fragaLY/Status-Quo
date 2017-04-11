package sq.vk.domain.client;

import java.util.Arrays;
import java.util.Optional;

/**
 * Created by Vadzim Kavalkou on 22.03.2017.
 */
public enum ClientRole {

    DEVELOPER("ROLE_DEVELOPER"), ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private final String role;

    ClientRole(String userRole) {
        this.role = userRole;
    }

    public static ClientRole getClientRoleByHisRole(String role_) {
        Optional<ClientRole> cRole = Arrays.stream(values())
                .parallel()
                .filter(clientRole -> clientRole.getClientRole().equals(role_))
                .findFirst();

        if (cRole.isPresent()) {
            return cRole.get();
        } else {
            throw new RuntimeException("ClientRole with '" + role_ + "' was not found.");
        }
    }

    public String getClientRole() {
        return role;
    }
}
