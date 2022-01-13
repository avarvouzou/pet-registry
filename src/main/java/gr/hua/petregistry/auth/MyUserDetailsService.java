package gr.hua.petregistry.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("userDetailsService")
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            System.out.println("NO USER FOUND");
            return new CustomAuthUser(
                    " ", " ", true, true, true, true, new ArrayList<>(), 0L);
        }

        return new CustomAuthUser(
                user.getUsername(), user.getPassword(), user.isEnabled(), true, true,
                true, getAuthorities(user.getRole()), user.getId());
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Role role) {

        return getGrantedAuthorities(getPrivileges(role));
    }

    private List<String> getPrivileges(Role role) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();

        privileges.add(role.getName());
        collection.addAll(role.getPrivileges());

        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}

