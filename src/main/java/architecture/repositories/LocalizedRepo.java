package architecture.repositories;

import architecture.domain.entities.Localised;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LocalizedRepo extends JpaRepository<Localised, Long> {

    @Query(value = "SELECT value(m) " +
            "FROM Localised l " +
            "JOIN l.strings m " +
            "WHERE  l.id=:id and KEY(m) like :countryCode")
    Object getValue(@Param(value = "countryCode") String countryCode, @Param(value = "id") Long id);
}
