package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BudgetPlannerMapperTest {
    BudgetPlannerMapper mapper;
    Path testCsvFile = Paths.get( "src/test/resources/account_payments_test.csv" );
    List<String> accountLines;
    String testDataLine = "Jos,BE69771770897312,BE17795215960626,Thu Feb 13 05:47:35 CET 2020,265.8,EUR,Ut ut necessitatibus itaque ullam.";

    @BeforeEach
    void setUp() throws BudgetPlannerException {
        mapper = new BudgetPlannerMapper();
        accountLines = BudgetPlannerImporter.readCsvFile( testCsvFile );
    }

    @Test
    public void it_should_return_non_empty_list() throws ParseException {
        assertFalse( mapper.mapAccounts( accountLines ).isEmpty() );
    }

    @Test
    void it_should_map_to_account_list_with_1_account() throws ParseException {
        List<Account> accountList = mapper.mapAccounts( accountLines );
        assertEquals( 1, accountList.size(), "it should have 1 account" );
    }

    @Test
    void it_should_map_to_account_list_with_1_account_with_2_payments() throws ParseException {
        List<Account> accountList = mapper.mapAccounts( accountLines );
        assertEquals( 2, accountList.get( 0 ).getPayments().size(), "account should have 2 payments" );
    }

    @Test
    void it_should_map_line_to_account_object() throws ParseException {
        Account expectedAccount = new Account( "Jos", "BE69771770897312" );
        Account lineToAccount = mapper.mapDataLineToAccount( testDataLine );
        mapper.mapDataLineToAccount( testDataLine );
        assertEquals( expectedAccount, lineToAccount ); //  //vergelijking met toString:  --> equals methode implementeren = assertTrue(expectedAccount.equals(lineToAccount));
    }

    @Test
    public void it_should_map_line_to_payment() throws Exception {
        Payment expectedPayment = new Payment(
                "BE17795215960626",
                mapper.convertToDate( "Thu Feb 13 05:47:35 CET 2020" ),
                265.8f,
                "EUR",
                "Ut ut necessitatibus itaque ullam."
        );

        //throw new Exception( "not yet implemented" );
        //fail("still on it");

        Payment actualPayment = mapper.mapItemsToPayment(testDataLine.split( "," ));
        assertEquals( expectedPayment, actualPayment );

    }

    @Test
    void it_should_convert_date() throws ParseException {
        // "EEE MMM d HH:mm:ss z yyyy"
        String testDate = "Thu Feb 13 05:47:35 CET 2020";
        Date date = mapper.convertToDate( testDate );
        // we kunnen nu van de aangemaakte date terug naar een string gaan om te testen, we maken hiervoor een methode
        String dateToString = mapper.convertDateToString( date );
        assertEquals( testDate, dateToString );
    }
}