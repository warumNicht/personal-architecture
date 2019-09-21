package architecture.repositories;

import architecture.domain.CountryCodes;
import architecture.domain.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepo extends JpaRepository<Article, Long> {
    @Query(value = "SELECT value(m) " +
            "FROM Article a " +
            "JOIN a.localContent m " +
            "WHERE  a.id=:id and KEY(m) = :countryCode or  KEY(m) = 'BG' ")
    Object[] getValue(@Param(value = "countryCode") CountryCodes countryCode, @Param(value = "id") Long id);

    @Query(value = "SELECT a.id, a.date, value(m) " +
            "FROM Article a " +
            "JOIN a.localContent m " +
            "WHERE  KEY(m) = :countryCode OR KEY(m) = :defaultCode " +
            "GROUP BY a.id " +
            "ORDER BY KEY(m) DESC" )
    Object[] getAll(@Param(value = "countryCode") CountryCodes countryCode, @Param(value = "defaultCode") CountryCodes defaultCode);

    @Query(value = "SELECT a.id, a.date, value(m)" +
            "FROM Article a " +
            "JOIN a.localContent m " +
            "WHERE key(m) = ( SELECT max(key(n)) FROM Article  b " +
            "JOIN b.localContent n " +
            "WHERE a.id=b.id AND (KEY(n) = :countryCode OR KEY(n) = :defaultCode )) " )
    Object[] getAllNestedSelect(@Param(value = "countryCode") CountryCodes countryCode, @Param(value = "defaultCode") CountryCodes defaultCode);

    @Query(value = "SELECT a.id, max(key(m)) as ma, value(n) " +
            "FROM Article a, Article b " +
            "JOIN a.localContent m " +
            "JOIN b.localContent n " +
            "WHERE  (KEY(m) = :countryCode OR KEY(m) = :defaultCode) and ma=key(n) " +
            "GROUP BY a.id " )
    Object[] getAllMax(@Param(value = "countryCode") CountryCodes countryCode, @Param(value = "defaultCode") CountryCodes defaultCode);
}
