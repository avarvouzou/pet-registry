package gr.hua.petregistry.auth;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.annotation.Secured;

@Secured("ROLE_ADMIN")
public interface PrivilegeRepository extends CrudRepository<Privilege, Long> { }
