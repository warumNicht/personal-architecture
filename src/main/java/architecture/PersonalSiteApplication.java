package architecture;

import architecture.config.AppBeansConfiguration;
import architecture.domain.entities.Category;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@SpringBootApplication
public class PersonalSiteApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppBeansConfiguration.class);
        EntityManagerFactory emf = context.getBean(EntityManagerFactory.class);
        EntityManager em = emf.createEntityManager();
        seed(em, emf);
//        em.getTransaction().begin();
//        Category category = em.find(Category.class, 1L);
//
//        em.getTransaction().commit();
//        em.close();

        SpringApplication.run(PersonalSiteApplication.class, args);
    }

    private static void seed(EntityManager em, EntityManagerFactory emf){
        em.getTransaction().begin();

        Category category = new Category();
        em.persist(category);
        category.setName("EN", "Business");
        category.setDescription("EN", "This is the business category");
        category.setName("FR", "La Business");
        category.setDescription("FR", "Ici es la Business");

        em.flush();

        System.out.println(category.getDescription("EN"));
        System.out.println(category.getName("FR"));

        Category c2 = new Category();
        em.persist(c2);
        c2.setDescription("EN", "Second Description");
        c2.setName("EN", "Second Name");
        c2.setDescription("DE", "Zwei  Description");
        c2.setName("DE", "Zwei  Name");

        em.flush();
        //em.remove(category);
        em.getTransaction().commit();
        em.close();
        emf.close();
    }

}
