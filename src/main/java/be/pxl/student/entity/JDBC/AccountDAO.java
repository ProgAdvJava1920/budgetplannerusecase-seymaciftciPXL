package be.pxl.student.entity.JDBC;

import be.pxl.student.entity.DomainClass.Account;
import be.pxl.student.entity.ExceptionClass.AccountException;
import be.pxl.student.entity.ExceptionClass.AccountNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//public class AccountDAOImpl implements AccountDAO {
public class AccountDAO implements DAO<Account, AccountException> {

    public static final String DELETE_STATEMENT = "DELETE FROM ACCOUNT WHERE id = ?";
    public static final String UPDATE_STATEMENT = "UPDATE Account SET name = ?, IBAN = ? where id = ?";
    private static Logger logger = LogManager.getLogger( AccountDAO.class );

    public static final String SELECT_BY_ID = "select * from Account where id = ?";
    private static final String SELECT_ALL = "select * from Account";

    private DAOManager manager;

    public AccountDAO(DAOManager manager) {
        this.manager = manager;
    }

    @Override
    public Account create(Account account) throws AccountException {
        try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement( "INSERT INTO Account(`IBAN`, `name`) VALUES (?, ?)" )) {
            //preparedStatement.setInt( 1, account.getId());
            preparedStatement.setString( 1, account.getIBAN() );
            preparedStatement.setString( 2, account.getName() );

            int result = preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

            if (generatedKeys.first()) {
                int accountId = generatedKeys.getInt( 1 );
                account.setId( accountId );
            }

            if (result == 1) {
                return account;
            }

            manager.commit();

        } catch (SQLException e) {
            manager.rollback( e );
            throw new AccountException( String.format( "Error creating account [%s]", account ), e );
        }

        throw new AccountException( "could not create account" );
    }

    @Override
    public Account getById(int id) throws AccountException {

        try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement( SELECT_BY_ID )) {
            preparedStatement.setInt( 1, id ); //telt vanaf 1
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.first()) {
                return new Account(
                        resultSet.getInt( "id" ),
                        resultSet.getString( "IBAN" ),
                        resultSet.getString( "name" )
                );
            } else {
                //return new Account(); // Een lege account
                throw new AccountNotFoundException( String.format( "Account with id [%d] not found", id ) );
            }
        } catch (SQLException sqlException) {
            throw new AccountException( String.format( "Exception while retrieving account with id [%d]", id ), sqlException );
        }
    }

    @Override
    public List<Account> getAll() throws AccountException {
        List<Account> accountsList = new ArrayList<>();
        try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement( SELECT_ALL )) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accountsList.add(
                        new Account(
                                resultSet.getInt( "id" ),
                                resultSet.getString( "IBAN" ),
                                resultSet.getString( "name" )
                        )
                );
            }
        } catch (SQLException e) {
            throw new AccountException( "Error retrieving accounts", e );
        }

        return accountsList;
    }


    //written by Seyma
    @Override
    public Account update(Account account) throws AccountException {
        try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement( UPDATE_STATEMENT )) {
            preparedStatement.setString( 1, account.getName() ); //telt vanaf 1
            preparedStatement.setString( 2, account.getIBAN() );
            preparedStatement.setInt( 3, account.getId() );
            int resultSet = preparedStatement.executeUpdate();

            return account;
        } catch (SQLException sqlException) {
            throw new AccountException( String.format( "Exception while updating account with id [%d]", account.getId() ), sqlException );
        }
    }

    //written by Seyma
    @Override
    public Account delete(Account account) throws AccountException {

        try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement( DELETE_STATEMENT )) {
            preparedStatement.setInt( 1, account.getId() ); //telt vanaf 1
            int resultSet = preparedStatement.executeUpdate();
            return account;
        } catch (SQLException sqlException) {
            throw new AccountException( String.format( "Exception while deleting account with id [%d]", account.getId() ), sqlException );
        }


    }
}
