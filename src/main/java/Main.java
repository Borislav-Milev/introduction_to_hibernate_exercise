import homework.Engine;
import homework.Manager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("soft_uni");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        EntityManager entityManager = emf.createEntityManager();

        Manager manager = new Manager(entityManager);
        Engine engine = new Engine(manager, reader);
        engine.run();
    }
}
