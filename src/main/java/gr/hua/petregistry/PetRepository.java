package gr.hua.petregistry;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface PetRepository extends CrudRepository<Pet, Long> {
    Iterable<Pet> findByStatus(String status);

    Iterable<Pet> findByReviewerId(Long reviewerId);

    Iterable<Pet> findByOwnerId(Long ownerId);

    @Query("select p from User u inner join Pet p on p.owner.id = u.id where u.municipality.id = ?1 and p.status = ?2")
    Iterable<Pet> findByUserMunicipalityIdAndByStatus(Long municipalityId, String status);
}
