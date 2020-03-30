package be.pxl.student.entity.JPA;

import be.pxl.student.entity.DomainClass.Account;
import be.pxl.student.entity.ExceptionClass.AccountException;
import be.pxl.student.entity.JDBC.DAO;

import javax.persistence.*;

import java.util.List;

public class AccountJPA implements DAO<Account, AccountException> {
    private String persistenceUnitName = "budgetplanner_pu";

    @Override
    public Account create(Account account) throws AccountException {
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        try{
            entityManagerFactory = Persistence.createEntityManagerFactory( persistenceUnitName );
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            //id niet meegeven doordat de id autom. genereerd wordt Message message = new Message( 4, "some text4" );
            Account newAccount = account;

            entityManager.persist( newAccount );
            entityManager.getTransaction().commit();

            return newAccount;
        } finally {
            if(entityManager!= null) entityManager.close();
            if(entityManagerFactory != null) entityManagerFactory.close();
        }
    }

    @Override
    public Account getById(int id) throws AccountException {
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try{
            entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
            entityManager = entityManagerFactory.createEntityManager();

            return entityManager.find( Account.class, id );

        } finally {
            if(entityManager != null) entityManager.close();
            if(entityManagerFactory != null)entityManagerFactory.close();
        }
    }

    @Override
    public List<Account> getAll() throws AccountException {
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try{
            entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
            entityManager = entityManagerFactory.createEntityManager();

            return entityManager.createQuery("SELECT ac FROM account", Account.class).getResultList(); // geeft een list terug met Account objecten

        } finally {
            if(entityManager != null) entityManager.close();
            if(entityManagerFactory != null)entityManagerFactory.close();
        }
    }

    @Override
    public Account update(Account account) throws AccountException {
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();
            entityManager.merge( account );
            entityManager.getTransaction().commit();

            return account;
        }
        finally {
            if (entityManager != null) entityManager.close();
            if (entityManagerFactory != null) entityManagerFactory.close();
        }
    }

    @Override
    public Account delete(Account account) throws AccountException {
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();
            entityManager.remove( account );
            entityManager.getTransaction().commit();

            return account;
        }
        finally {
            if (entityManager != null) entityManager.close();
            if (entityManagerFactory != null) entityManagerFactory.close();
        }
    }
}
