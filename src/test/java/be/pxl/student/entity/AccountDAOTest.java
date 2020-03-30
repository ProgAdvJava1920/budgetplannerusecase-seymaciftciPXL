package be.pxl.student.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountDAOImplTest {

    private static final String DB_URL = "jdbc:h2:mem:test;MODE=MySql;INIT=RUNSCRIPT FROM'classpath:BudgetPlannerTest.sql'";

    @Test
    void create() {
        fail("not yet implemented");
    }

    @Test
    void should_return_account_with_id_1() throws AccountException {
        AccountDAO dao = new AccountDAO( DB_URL );
        Account account = dao.getById( 1 );
        Account expected = new Account( 1, "dummyIBAN", "dummyName" );
        assertEquals( expected, account );
    }

    @Test
    void getAll() {
        fail("not yet implemented");
    }

    @Test
    void update() {
        fail("not yet implemented");
    }

    @Test
    void delete() {
        fail("not yet implemented");
    }
}