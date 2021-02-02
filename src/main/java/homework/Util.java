package homework;

import entities.Address;
import entities.Town;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public final class Util {
    EntityManager entityManager;


    public Util(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @SuppressWarnings("unchecked")
    public  <T> T findEntityByName(Class<?> entityClass, String columnName, String entityName) {
        T entity;
        try {
            entity = (T) this.entityManager
                    .createQuery(String.format(Queries.FIND_ENTITY_BY_NAME, entityClass.getSimpleName(), columnName))
                    .setParameter("name", entityName)
                    .getSingleResult();
        } catch (NullPointerException | NoResultException e) {
            return null;
        }
        return entity;
    }

    public Address createAddressIfNotExist(){
        String textAddress = "Vitoshka 15";

        Address address = findEntityByName(Address.class, "text", textAddress);

        if(address == null){
            address = new Address();
            address.setText(textAddress);
            address.setTown(findEntityByName(Town.class, "name", "Sofia"));
            System.out.println();
            this.entityManager.getTransaction().begin();
            this.entityManager.persist(address);
            this.entityManager.getTransaction().commit();
        }

        return address;
    }
}
