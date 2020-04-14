package be.pxl.student.util;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BudgetPlannerMapper {
    public static final String DATE_PATTERN = "EEE MMM d HH:mm:ss z yyyy"; // DATEPATTERN : EEE staat voor dag MMM voor maand d voor dagnummer, de uren, z voor ZONE, yyyy voor de jaren
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat( DATE_PATTERN, Locale.US );
    private static final int CSV_ITEM_COUNT = 7;

    private Map<String, Account> accountMap = new HashMap<>(  );

    public List<Account> mapAccounts(List<String> accountLines) {
        //TODO implement mapping account
        List<Account> accountList = new ArrayList<>();
        for (String line : accountLines) {
            try { // slechte lijnen overslaan en we gaan catchen
                Account account = mapDataLineToAccount( line );
                accountMap.putIfAbsent( account.getIBAN(), account );
            } catch (ParseException | BudgetPlannerException e) {
                System.err.printf("Could not parse line [%s]",line); //haakjes zodat we weten wanneer de waarde eindigt
            }
        }
        return new ArrayList<Account>( accountMap.values());
    }

    public Account mapDataLineToAccount(String line) throws BudgetPlannerException, ParseException {
        String[] items = line.split( "," );

        if(items.length != CSV_ITEM_COUNT){
            throw new BudgetPlannerException( String.format("Invalid line. Expected %d fields. Found %s", CSV_ITEM_COUNT, items.length));
        }

        String name = items[0];
        String IBAN = items[1];

        Account account = accountMap.getOrDefault( IBAN, new Account( name, IBAN)); //maakt nieuwe account aan als die niet bestaat
        Payment payment = mapItemsToPayment( items );
        account.getPayments().add( payment );

        return account;
    }

    public Date convertToDate(String dateString) throws ParseException {
        return SIMPLE_DATE_FORMAT.parse( dateString );
        //van string naar date
    }

    public String convertDateToString(Date date){
        return SIMPLE_DATE_FORMAT.format(date);
    }

    public Payment mapItemsToPayment(String[] items) throws ParseException {
        //van een item array een payment maken
        return new Payment(
                items[2],                       //IBAN
                convertToDate(items[3]),        //Transaction date
                Float.parseFloat(items[4]),     //Amount
                items[5],                       //Currency
                items[6]                        //Detail
        );
        //Geen return null gebruiken, maar onderstaande code
        //throw new RuntimeException( "not yet implemented" );
    }
}
