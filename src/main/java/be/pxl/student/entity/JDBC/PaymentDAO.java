package be.pxl.student.entity;

import be.pxl.student.entity.DomainClass.Payment;
import be.pxl.student.entity.ExceptionClass.PaymentException;
import be.pxl.student.entity.ExceptionClass.PaymentNotFoundException;
import be.pxl.student.entity.JDBC.DAO;
import be.pxl.student.entity.JDBC.DAOManager;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// ALLES ZELF GESCHRVEEN
public class PaymentDAO implements DAO<Payment, PaymentException> {
    public static final String INSERT_STATEMENT_PAYMENT = "INSERT INTO Payment (accountId, counterAccountId, IBAN, date, amount, currency, detail) VALUES (?, ?, ?, ?, ?, ?, ?)";
    public static final String SELECT_BY_ID = "select * from Payment where id = ?";
    private static final String UPDATE_STATEMENT = "update Payment set accountId, counterAccountId, IBAN, date, amount, currency, detail" ;
    private static final String SELECT_ALL = "select * from Payment" ;
    private static final String DELETE_STATEMENT = "delete from Payment where id = ?";
    private DAOManager manager;

    public PaymentDAO(DAOManager manager) {
        this.manager = manager;
    }

    @Override
    public Payment create(Payment payment) throws PaymentException {
        //throw new PaymentException( "not yet implemented" );

        try(PreparedStatement pst = manager.getConnection().prepareStatement( INSERT_STATEMENT_PAYMENT )){
            //preparedStatement.setInt( 1, account.getId());
            pst.setInt( 1, payment.getAccountID() );
            pst.setInt( 2, payment.getCounterAccountID() );
            pst.setString( 3, payment.getIBAN() );
            pst.setDate( 4, (Date) payment.getDate() );
            pst.setFloat( 5, payment.getAmount() );
            pst.setString( 6, payment.getCurrency() );
            pst.setString( 7, payment.getDetail() );

            int result = pst.executeUpdate();

            ResultSet generatedKeys = pst.getGeneratedKeys();

            if(generatedKeys.first()){
                int paymentId = generatedKeys.getInt( 1 );
                payment.setId( paymentId );
            }

            if(result == 1){
                return payment;
            }

            manager.commit();

        } catch (SQLException e) {
            manager.rollback( e );
            throw new PaymentException( String.format( "Error creating payment [%s]", payment), e);
        }

        throw new PaymentException( "could not create payment" );
    }

    @Override
    public Payment getById(int id) throws PaymentException {
        //throw new PaymentException( "not yet implemented" );
        try (PreparedStatement pst = manager.getConnection().prepareStatement( SELECT_BY_ID )){
            pst.setInt( 1, id );
            ResultSet resultSet = pst.executeQuery();
            if(resultSet.first()) {
                return new Payment(
                        resultSet.getInt( "id" ),
                        resultSet.getInt( "accountId" ),
                        resultSet.getInt( "counterAccountId" ),
                        resultSet.getString( "IBAN" ),
                        resultSet.getDate( "date" ),
                        resultSet.getFloat( "amount" ),
                        resultSet.getString( "currency" ),
                        resultSet.getString( "details" )
                );
            } else {
                throw new PaymentNotFoundException(String.format( "Payment with id [%d] not found", id ));
            }
        } catch (SQLException e) {
            throw new PaymentException( String.format( "Exception while retrieving payment with id [%d]", id ), e );
        }
    }

    @Override
    public List<Payment> getAll() throws PaymentException {
        List<Payment> paymentList = new ArrayList<>();
        try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement( SELECT_ALL )) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                paymentList.add(
                        new Payment(
                                resultSet.getInt( "id" ),
                                resultSet.getInt( "accountId" ),
                                resultSet.getInt( "counterAccountId" ),
                                resultSet.getString( "IBAN" ),
                                resultSet.getDate( "date" ),
                                resultSet.getFloat( "amount" ),
                                resultSet.getString( "currency" ),
                                resultSet.getString( "details" )
                        )
                );
            }
        } catch (SQLException e) {
            throw new PaymentException( "Error retrieving accounts", e );
        }

        return paymentList;
    }

    @Override
    public Payment update(Payment payment) throws PaymentException {
        //throw new PaymentException( "not yet implemented" );
        try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement( UPDATE_STATEMENT )) {
            preparedStatement.setString( 1,"accountId" );
            preparedStatement.setString( 2,"counterAccountId" );
            preparedStatement.setString( 3,"IBAN" );
            preparedStatement.setDate( 4, Date.valueOf( "date" ) );
            preparedStatement.setFloat( 5, Float.parseFloat( "amount" ) );
            preparedStatement.setString( 6,"currency" );
            preparedStatement.setString( 7,"details" );

            int resultSet = preparedStatement.executeUpdate();

            return payment;
        } catch (SQLException sqlException) {
            throw new PaymentException( String.format( "Exception while updating payment with id [%d]", payment.getId() ), sqlException );
        }
    }

    @Override
    public Payment delete(Payment payment) throws PaymentException {
        //throw new PaymentException( "not yet implemented" );
        try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement( DELETE_STATEMENT )) {
            preparedStatement.setInt( 1, payment.getId() ); //telt vanaf 1
            int resultSet = preparedStatement.executeUpdate();
            return payment;
        } catch (SQLException sqlException) {
            throw new PaymentException( String.format( "Exception while deleting payment with id [%d]", payment.getId() ), sqlException );
        }
    }
}
