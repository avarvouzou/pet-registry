package gr.hua.petregistry;

import gr.hua.petregistry.auth.User;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "municipalities")
public class Municipality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "municipality")
    private Collection<User> users;

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
