package com.example.validation;


import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidationService {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private final Date fechaMinima = new Date(1980-01-01);

    private final String[] jobTitle = {"Haematologist", "Phytotherapist", "Building surveyor", "Insurance account manager",
            "Educational psychologist"};
    private final String[] reportType = {"Near Miss", "Lost Time", "First Aid"};
    public boolean validationFileCSV(String[] strings){
            return validationCSV(strings);

    }
    public boolean validationFileExcel(String[] strings){
        return isValidationExcel(strings);
    }

    private boolean isValidationExcel(String[] strings) {
        return isInjuryLocation(strings[0]) && isReportType(strings[1]);
    }

    private boolean validationCSV(String [] strings){
        return isValidEmail(strings[0]) && isDateValid(strings[1]) && isJobTitleValid(strings[2]);
    }

    private boolean isValidEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isDateValid (String dateString){

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateString);

            return date.after(fechaMinima);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isJobTitleValid (String title){
        int compare = 0;
        for (String titleJob: jobTitle) {
            if (title.equals(titleJob))
                compare++;
        }
        return compare == 1;
    }
    private boolean isInjuryLocation (String injuryLocation){
        return !injuryLocation.equals("N/A");
    }

    private boolean isReportType (String reportType){
        int compare = 0;
        for (String report : this.reportType) {
            if (report.equals(reportType))
                compare++;
        }
        return compare == 1;
    }
}
