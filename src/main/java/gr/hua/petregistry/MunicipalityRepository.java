package gr.hua.petregistry;

import org.springframework.data.repository.Repository;

public interface MunicipalityRepository extends Repository<Municipality, Long> {
    Iterable<Municipality> findAll(); ;
}
