package be.pxl.student.entity.JDBC;

import be.pxl.student.entity.ExceptionClass.PaymentException;
import be.pxl.student.entity.Payment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentDAOTest {

    private static final String DB_URL = "jdbc:h2:mem:test;MODE=MySql;INIT=RUNSCRIPT FROM'classpath:BudgetPlannerTest.sql'";

    DAOManager manager;
    PaymentDAO dao;

    @BeforeEach
    void setUp() {
        manager = new DAOManager( DB_URL );
        dao = new PaymentDAO(manager);
    }

    @AfterEach
    void tearDown() {
        manager.close();
    }

    @Test
    void it_should_create_1_new_payment() throws PaymentException {
        //fail("not yet implemented");
        Payment newPayment = new Payment( "Random IBAN", new Date(  ), 5.95f, "EUR", "terugbetaling broodje" );
        dao.create( newPayment );
        assertEquals( dao.getById( newPayment.getAccountID() ), newPayment );
    }

    @Test
    void it_should_return_payment_with_id_1() throws PaymentException {
        //fail("not yet implemented");
        Payment payment = dao.getById( 1 );
        Payment expected = new Payment( 1, 1, 2, "dummy IBAN", new Date(  ), 0.01f, "EUR", "dummy detail" );
        assertEquals( expected, payment );
    }

    @Test
    void it_should_return_2_payments() throws PaymentException {
        //fail("not yet implemented");
        List<Payment> payments = dao.getAll();
        assertEquals( 3, payments.size() );

    }

    @Test
    void it_should_return_updated_payment_with_new_data() throws PaymentException {
        //fail("not yet implemented");
        Payment payment = dao.getById( 1 );
        payment.setAmount( 500.89f );
        Payment updatedPayment = dao.update( payment );
        assertEquals( updatedPayment.getAmount(), 500.89f );
    }

    @Test
    void it_should_return_deleted_payment() throws PaymentException {
        //fail("not yet implemented");
        Payment payment = dao.getById( 1 );
        Payment deletedPayment = dao.delete( payment );
        assertEquals( deletedPayment, payment );
    }
}