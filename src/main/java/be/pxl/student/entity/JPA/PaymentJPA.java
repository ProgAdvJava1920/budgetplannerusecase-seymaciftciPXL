package be.pxl.student.entity.JPA;

import be.pxl.student.entity.DomainClass.Payment;
import be.pxl.student.entity.ExceptionClass.PaymentException;
import be.pxl.student.entity.JDBC.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class PaymentJPA implements DAO<Payment, PaymentException> {
    private String persistenceUnitName = "budgetplanner_pu";

    @Override
    public Payment create(Payment payment) throws PaymentException {
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;

        try {
            entityManagerFactory = Persistence.createEntityManagerFactory( persistenceUnitName );
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            //id niet meegeven doordat de id autom. genereerd wordt Message message = new Message( 4, "some text4" );
            Payment newPayment = payment;

            entityManager.persist( newPayment );
            entityManager.getTransaction().commit();

            return newPayment;
        } finally {
            if (entityManager != null) entityManager.close();
            if (entityManagerFactory != null) entityManagerFactory.close();
        }
    }

    @Override
    public Payment getById(int id) throws PaymentException {
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try{
            entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
            entityManager = entityManagerFactory.createEntityManager();

            return entityManager.find( Payment.class, id );

        } finally {
            if(entityManager != null) entityManager.close();
            if(entityManagerFactory != null)entityManagerFactory.close();
        }
    }

    @Override
    public List<Payment> getAll() throws PaymentException {
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try{
            entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
            entityManager = entityManagerFactory.createEntityManager();

            return entityManager.createQuery("SELECT pm FROM payment", Payment.class).getResultList(); // geeft een list terug met Account objecten

        } finally {
            if(entityManager != null) entityManager.close();
            if(entityManagerFactory != null)entityManagerFactory.close();
        }
    }

    @Override
    public Payment update(Payment payment) throws PaymentException {
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();
            entityManager.merge( payment );
            entityManager.getTransaction().commit();

            return payment;
        }
        finally {
            if (entityManager != null) entityManager.close();
            if (entityManagerFactory != null) entityManagerFactory.close();
        }
    }

    @Override
    public Payment delete(Payment payment) throws PaymentException {
        EntityManagerFactory entityManagerFactory = null;
        EntityManager entityManager = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
            entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();
            entityManager.remove( payment );
            entityManager.getTransaction().commit();

            return payment;
        }
        finally {
            if (entityManager != null) entityManager.close();
            if (entityManagerFactory != null) entityManagerFactory.close();
        }
    }
}
