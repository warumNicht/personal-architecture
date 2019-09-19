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
            "WHERE  a.id=:id and KEY(m) like :countryCode")
    Object getValue(@Param(value = "countryCode") CountryCodes countryCode, @Param(value = "id") Long id);
}
