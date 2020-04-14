package be.pxl.student.entity.JPA;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.ExceptionClass.AccountException;
import be.pxl.student.entity.DAO;
import org.hibernate.cfg.NotYetImplementedException;

import javax.persistence.*;

import java.util.List;

public class AccountJPA implements DAO<Account, AccountException> {
    private String persistenceUnitName = "budgetplanner_pu";
    EntityManager entitymanager;

    public AccountJPA(EntityManager entitymanager) {
        this.entitymanager = entitymanager;
    }

    @Override
    public Account create(Account account) throws AccountException {
        /*EntityManagerFactory entityManagerFactory = null;
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
        }*/
        throw new NotYetImplementedException(  );
    }

    @Override
    public Account getById(int id) throws AccountException {
        return entitymanager.find( Account.class, id );
    }

    @Override
    public List<Account> getAll() throws AccountException {
        TypedQuery<Account> query = entitymanager.createNamedQuery( "findAll", Account.class );
        return query.getResultList();
    }

    @Override
    public Account update(Account account) throws AccountException {
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory( persistenceUnitName );
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();
            entityManager.merge( account );
            entityManager.getTransaction().commit();

            return account;
        } finally {
            if (entityManager != null) entityManager.close();
            if (entityManagerFactory != null) entityManagerFactory.close();
        }
    }

    @Override
    public Account delete(Account account) throws AccountException {
        Account attachedAccount = entitymanager.find( Account.class, account.getId() );
        entitymanager.remove( attachedAccount );
        return attachedAccount;
    }
}
