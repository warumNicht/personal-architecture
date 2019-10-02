package architecture.repositories;

import architecture.domain.CountryCodes;
import architecture.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT a.id, a.date, value(m)" +
            "FROM Article a " +
            "JOIN a.localContent m " +
            "ON key(m) = ( SELECT max(key(n)) FROM Article  b " +
            "JOIN b.localContent n " +
            "WHERE a.id=b.id AND (KEY(n) = :countryCode OR KEY(n) = :defaultCode )) " )
    Object[] getAllNestedSelect(@Param(value = "countryCode") CountryCodes countryCode, @Param(value = "defaultCode") CountryCodes defaultCode);

    @Query(value = "SELECT c.id, value(m) FROM Category c " +
            "JOIN c.localCategoryNames m " +
            "ON key(m) = ( SELECT max(key(n)) FROM Category b " +
            "JOIN b.localCategoryNames n " +
            "WHERE c.id=b.id AND (KEY(n) = :countryCode OR KEY(n) = :defaultCode ))")
    Object[] getAllCategoriesByLocale(@Param(value = "countryCode") CountryCodes countryCode, @Param(value = "defaultCode") CountryCodes defaultCode);
}
