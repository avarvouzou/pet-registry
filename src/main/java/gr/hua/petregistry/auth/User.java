package gr.hua.petregistry.auth;

import gr.hua.petregistry.Municipality;
import gr.hua.petregistry.Pet;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private boolean enabled;

    @ManyToOne
    private Role role;

    @ManyToOne
    private Municipality municipality;

    @OneToMany(mappedBy = "owner")
    private Collection<Pet> pets;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public Role getRole() {
        return this.role;
    }

    public Municipality getMunicipality() {
        return this.municipality;
    }
}

