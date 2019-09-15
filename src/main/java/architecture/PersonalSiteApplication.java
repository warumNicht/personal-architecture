package architecture;

import architecture.config.AppBeansConfiguration;
import architecture.domain.entities.Category;
import architecture.repositories.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@SpringBootApplication
public class PersonalSiteApplication {
    public static void main(String[] args) {

        SpringApplication.run(PersonalSiteApplication.class, args);
    }


}
