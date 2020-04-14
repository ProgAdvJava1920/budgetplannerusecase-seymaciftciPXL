package be.pxl.student.entity.JPA;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.DAO;
import be.pxl.student.entity.ExceptionClass.AccountException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountJPATest {

    DAO<Account, AccountException> dao;
    EntityManager entityManager;
    EntityManagerFactory entityManagerFactory;

    @BeforeEach
    void setup() {
        entityManagerFactory = Persistence.createEntityManagerFactory("budgetplanner_test");
        entityManager = entityManagerFactory.createEntityManager();

        dao = new AccountJPA(entityManager);
    }

    @AfterEach
    void tearDown() {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void create() {
        fail("not yet implemented");
    }

    @Test
    void getById() throws AccountException {
        Account account = dao.getById(1);
        Account expectedAccount = new Account(1, "geenDummyIBAN", "dummyName");

        assertEquals(expectedAccount, account);
    }

    @Test
    void getAll() throws AccountException {
        List<Account> accounts = dao.getAll();
        assertEquals(3, accounts.size());
    }

    @Test
    void update() throws AccountException {
        fail("not yet implemented");
    }

    @Test
    void delete() throws AccountException {
        Account expectedAccount = new Account(1, "dummyIBAN", "dummyName");
        dao.delete(expectedAccount);
        assertNull(this.dao.getById(1));
    }
}