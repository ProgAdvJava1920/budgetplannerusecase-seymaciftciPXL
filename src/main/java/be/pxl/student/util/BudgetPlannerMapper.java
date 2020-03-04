package be.pxl.student.util;

import be.pxl.student.entity.Account;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BudgetPlannerMapper {
    public static final String DATE_PATTERN = "EEE MMM d HH:mm:ss z yyyy"; // DATEPATTERN : EEE staat voor dag MMM voor maand d voor dagnummer, de uren, z voor ZONE, yyyy voor de jaren
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat( DATE_PATTERN, Locale.US );
    public List<Account> accountList = new ArrayList<>();

    public List<Account> mapAccounts(List<String> accountLines) {
        //TODO implement mapping account
        List<Account> accountList = new ArrayList<>();
        for (String line : accountLines) {
            Account account = mapDataLineToAccount( line );
            if (!accountList.contains( account )) {
                accountList.add( account );
            }
        }
        return accountList;
    }

    public Account mapDataLineToAccount(String line) {
        String[] items = line.split( "," );

        String name = items[0];
        String IBAN = items[1];

        return new Account( name, IBAN );
    }


    public Date convertToDate(String dateString) throws ParseException {
        return SIMPLE_DATE_FORMAT.parse( dateString );
        //van string naar date
    }

    public String convertDateToString(Date date){
        return SIMPLE_DATE_FORMAT.format(date);
    }
}
