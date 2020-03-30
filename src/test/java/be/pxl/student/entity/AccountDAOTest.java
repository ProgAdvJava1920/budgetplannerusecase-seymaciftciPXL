package be.pxl.student.entity;

import be.pxl.student.entity.DomainClass.Account;
import be.pxl.student.entity.ExceptionClass.AccountException;
import be.pxl.student.entity.JDBC.AccountDAO;
import be.pxl.student.entity.JDBC.DAOManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AccountDAOTest {

    private static final String DB_URL = "jdbc:h2:mem:test;MODE=MySql;INIT=RUNSCRIPT FROM'classpath:BudgetPlannerTest.sql'";

    DAOManager manager;
    AccountDAO dao;

    @BeforeEach
    void setUp() {
        manager = new DAOManager(DB_URL);
        dao = new AccountDAO( manager );
    }

    @AfterEach
    void tearDown() {
        manager.close();
    }

    @Test
    void create() throws AccountException {
        Account newAccount = new Account( "Seyma Ciftci", "Random IBAN" );
        dao.create( newAccount );
        assertEquals( dao.getById( newAccount.getId() ), newAccount );
    }

    @Test
    void it_should_return_2_items() throws AccountException {
        List<Account> accounts = dao.getAll();
        assertEquals( 2, accounts.size());
    }

    @Test
    void it_should_return_account_with_id_1() throws AccountException {
        Account account = dao.getById( 1 );
        Account expected = new Account( 1, "dummyIBAN", "dummyName" );
        assertEquals( expected, account );
    }

    //written by Seyma
    @Test
    void it_should_return_updated_account_with_new_data() throws AccountException {
        //fail("not yet implemented");
        Account account = dao.getById( 1 );
        account.setName( "updatedSeyma" );
        Account updatedAccount = dao.update( account );
        assertEquals( account.getName(), "updatedSeyma" );
    }

    //written by Seyma
    @Test
    void it_should_return_deleted_account() throws AccountException {
        //fail("not yet implemented");
        Account account = dao.getById( 1 );
        Account deletedAccount = dao.delete( account );
        assertEquals( account, deletedAccount );
    }
}