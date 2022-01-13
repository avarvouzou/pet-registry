package gr.hua.petregistry;

import gr.hua.petregistry.auth.CustomAuthUser;
import gr.hua.petregistry.auth.User;
import gr.hua.petregistry.auth.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/pets")
public class PetsController {

    @Autowired
    private PetRepository petRepo;

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepo;

    @GetMapping
    public Iterable<Pet> index(@AuthenticationPrincipal CustomAuthUser user, @RequestParam Map<String, String> queryParameters) {
        Collection<GrantedAuthority> authorities = user.getAuthorities();

        String status = queryParameters.getOrDefault("status", "");
        String sameMunicipality = queryParameters.getOrDefault("same_municipality", "");
        String own = queryParameters.getOrDefault("own", "");

        // Here we can check if multiple query params are set and throw validation error.
        // Below we could also use the criteria approach and create a criteria object based on query parameters.

        if (this.hasAuthority(authorities, "CAN_VIEW_ALL_PETS")) {
            return this.petRepo.findAll();
        }

        if (own.equals("true") && this.hasAuthority(authorities, "CAN_VIEW_OWN_PETS")) {
            return this.petRepo.findByOwnerId(user.getUserId());
        }

        if (status.equals("PENDING") && this.hasAuthority(authorities, "CAN_VIEW_PENDING_PETS")) {
            return this.petRepo.findByStatus("PENDING");
        }

        if (status.equals("REVIEWED") &&this.hasAuthority(authorities, "CAN_REVIEW_PET")) {
            return this.petRepo.findByReviewerId(user.getUserId());
        }

        if (sameMunicipality.equals("true") && this.hasAuthority(authorities, "CAN_VIEW_APPROVED_MUNICIPALITY_PETS")) {
            User currentUser = this.userRepo.findById(user.getUserId()).get();
            return this.petRepo.findByUserMunicipalityIdAndByStatus(currentUser.getMunicipality().getId(), "APPROVED");
        }

        throw new AccessDeniedException("Forbidden");
    }

    @GetMapping("/{id}")
    public Pet view(@PathVariable("id") Long petId, @AuthenticationPrincipal CustomAuthUser authUser) {
        Optional<Pet> optPet = this.petRepo.findById(petId);
        if (optPet.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
        }

        User currentUser = this.userRepo.findById(authUser.getUserId()).get();
        Pet pet = optPet.get();

        Collection<GrantedAuthority> authorities = authUser.getAuthorities();

        if (this.hasAuthority(authorities, "CAN_VIEW_ALL_PETS")) {
            return pet;
        }

        if (
            this.hasAuthority(authorities, "CAN_VIEW_OWN_PETS") &&
            pet.getOwner().getId().equals(authUser.getUserId())
        ) {
            return pet;
        }

        if (
            this.hasAuthority(authorities, "CAN_VIEW_PENDING_PETS") &&
            pet.getStatus().equals("PENDING")
        ) {
            return pet;
        }

        if (
            this.hasAuthority(authorities, "CAN_VIEW_APPROVED_MUNICIPALITY_PETS") &&
            pet.getStatus().equals("APPROVED") && pet.getOwner().getMunicipality().getName().equals(currentUser.getMunicipality().getName())
        ) {
            return pet;
        }

        throw new AccessDeniedException("Forbidden");
    }

    @PostMapping
    public Pet create(@RequestBody CreatePetDTO cpDTO, @AuthenticationPrincipal CustomAuthUser authUser) {
        User dbUser = new User();
        dbUser.setId(authUser.getUserId());

        Pet pet = new Pet();
        pet.setName(cpDTO.getName());
        pet.setType(cpDTO.getType());
        pet.setSex(cpDTO.getSex());
        pet.setBirthday(cpDTO.getBirthday());
        pet.setBreed(cpDTO.getBreed());
        pet.setMicrochipNumber(cpDTO.getMicrochipNumber());
        pet.setOwner(dbUser);
        pet.setStatus("PENDING");

        return this.petRepo.save(pet);
    }

    @PutMapping("/{id}/review")
    public Pet review(@RequestBody ReviewPetDTO rpDTO, @PathVariable("id") long petId, @AuthenticationPrincipal CustomAuthUser authUser) {
        Optional<Pet> optPet = this.petRepo.findById(petId);
        if (optPet.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
        }

        User dbUser = new User();
        dbUser.setId(authUser.getUserId());

        Pet pet = optPet.get();
        pet.setStatus(rpDTO.getStatus());
        pet.setMedicalHistory(rpDTO.getMedicalHistory());
        pet.setReviewer(dbUser);

        return this.petRepo.save(pet);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long petId, @AuthenticationPrincipal CustomAuthUser authUser) {
        Optional<Pet> optPet = this.petRepo.findById(petId);
        if (optPet.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
        }

        Collection<GrantedAuthority> authorities = authUser.getAuthorities();
        Pet pet = optPet.get();
        if (this.hasAuthority(authorities, "CAN_DELETE_OWN_PETS") && pet.getOwner().getId().equals(authUser.getUserId())) {
            this.petRepo.deleteById(petId);
            return;
        }

        if (this.hasAuthority(authorities, "CAN_DELETE_ANY_PET")) {
            this.petRepo.deleteById(petId);
            return;
        }

        throw new AccessDeniedException("Forbidden");
    }

    private boolean hasAuthority(Collection<? extends GrantedAuthority> authorities, String authority) {
        return authorities
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(a -> a.equals(authority));
    }

}