package be.pxl.student.entity.JPA;

import be.pxl.student.entity.AccountDAO;
import be.pxl.student.entity.DomainClass.Account;
import be.pxl.student.entity.ExceptionClass.AccountException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.List;

public class AccountDaoJPA implements AccountDAO {
    private String persistenceUnitName = "budgetplanner_pu";
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
    private EntityManager entityManager = entityManagerFactory.createEntityManager();

    @Override
    public Account create(Account account) throws AccountException {
        return super.create( account );
    }

    @Override
    public Account getById(int id) throws AccountException {
        return super.getById( id );
    }

    @Override
    public List<Account> getAll() throws AccountException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        tx.begin();

        List<Account> accountList = entityManager.createQuery("select ac from account").getResultList();
        tx.commit();
        entityManager.close();

        return accountList;
    }

    @Override
    public Account update(Account account) throws AccountException {
        return super.update( account );
    }

    @Override
    public Account delete(Account account) throws AccountException {
        return super.delete( account );
    }
}
