package gr.hua.petregistry.auth;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.annotation.Secured;

@Secured("ROLE_ADMIN")
@RepositoryRestResource(exported = true, collectionResourceRel = "users", path = "users")
public interface UserRepositoryRest extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
