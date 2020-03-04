package be.pxl.student.util;

import be.pxl.student.BudgetPlanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Util class to import csv file
 */
public class BudgetPlannerImporter {

     public static List<String> readCsvFile(Path csvPath) throws BudgetPlannerException { //de developer gaat de fout afhandelen wij niet omdat het niet kunnen afprinten omdat we geen main gebruiken
         List<String> csvLines = new ArrayList<>(  );

         try(BufferedReader reader = Files.newBufferedReader( csvPath )){
             String line = reader.readLine(); //ignore first line
             while ((line = reader.readLine()) != null){
                 csvLines.add( line );
             }
         } catch (IOException | NullPointerException ex) {
             throw new BudgetPlannerException("Something went wrong reading csv file", ex);
         }

         return csvLines;
     }
}
