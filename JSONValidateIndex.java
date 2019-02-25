//This file is the class for JSONValidateIndex method and is called in my sak file

import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class JSONValidateIndex {
    protected String requestURL;
    protected ArrayList<String> urlContent;
    protected ArrayList<String> urlsFound;
    private String firstName = "";
    private String lastName = "";
    private String firstNameError = null;
    private String lastNameError = null;
    private String validationMsg;
    private String passedValidation = null;
    
    //default constructor
    JSONValidateIndex() {
        requestURL = "";
        urlContent = new ArrayList<String>();
        urlsFound = new ArrayList<String>();
    }

    //non-default constructor
    JSONValidateIndex(String urlIn) {
        requestURL = urlIn;
        urlContent = new ArrayList<String>();
        urlsFound = new ArrayList<String>();
    }

    public Boolean readAllURL() {
        return readAllURL(requestURL);
    }

    //read data from request
    public Boolean readAllURL(String urlIn) {
        requestURL = urlIn;
        Boolean returnValue = false; 
        try {
            URL myURL = new URL(requestURL);
            URLConnection myURLConnection = myURL.openConnection();
            BufferedReader readerIn = new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));
            String inputLine;
            while ((inputLine = readerIn.readLine()) != null) {
                urlContent.add(inputLine);
                //parse main file and look for "http" inside the content to identify and store additonal URLs in order to make requests to them down the line.
                //This uses regex to find a patter of URL with line below
                String regexUrlPattern = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

                Pattern urlPattern = Pattern.compile(regexUrlPattern);
                Matcher urlMatcher = urlPattern.matcher(inputLine);
                while (urlMatcher.find()){
                    urlsFound.add(urlMatcher.group());
                }
                returnValue = true;
            }
            readerIn.close();
        }
        catch (IOException e) {
            returnValue = false;
            System.out.println("Invalid URL. Please try again.");
        }
        
        return returnValue;
    }

    //additional http requests from source URL
    public Boolean additonalURLRequests() {
        Boolean returnValue = true;
        for (String s : urlsFound) {
            JSONValidateIndex additionalRequest = new JSONValidateIndex(s);
            if (additionalRequest.readAllURL()) {
                System.out.println(additionalRequest);
            } else {
                returnValue = false;
            }
        }
        return returnValue;
    }

    //method to parse JSON files to find FIrst name and last Name 
    public void ParseJSON(String stringIn) {
        for (String dataString : urlContent) {
            //split up data from the additional requests so we can identify first and last names
            String[] jsonData = dataString.split("\"");

            if (jsonData.length > 3) {
                if (jsonData[1].equals("firstName")) {
                    firstName = jsonData[3];
                }
                if (jsonData[1].equals("lastName")) {
                    lastName = jsonData[3];
                }
            }
        }    
    }

    public void Validate(String stringIn) {
        if (urlContent.size() < 1) {
            System.out.println("Critical Error: No content found from requestURL\n");
        }
        int firstNameLength = firstName.length();
        int lastNameLength = lastName.length();

        //if first name fails then update msg for it 
        if (firstNameLength < 2) {
            firstNameError = "Error - no firstName found in content or it is of insufficient length.";
            passedValidation = "\nData Validation Failed for: " + requestURL;
        }
        //if last name fails update msg for it
        if (lastNameLength < 2) {   
            lastNameError = "Error - no lastName found in content or it is of insufficient length.";    
            passedValidation = "\nData Validation Failed for: " + requestURL;   
        }
        //if both pass then all is well, and update pass msg for it
        if (firstNameLength > 2 && lastNameLength > 2){
            passedValidation = "\nData Validation Passed for: " + requestURL;
        }
        return;
    }

    public String toString() {
        String returnValue = "";
        for (String string : urlContent) {
            validationMsg = "Validating: " + requestURL + "\n";
            ParseJSON(string);
            Validate(string);
            if(firstNameError == null && lastNameError == null ){
                returnValue =  validationMsg + "First Name: " + firstName + "\nLast Name: " + lastName + passedValidation + "\n\n";
            }
           else if (firstNameError != null || lastNameError != null){
               returnValue =  validationMsg + "First Name: " + firstNameError + "\nLast Name: " + lastNameError + passedValidation + "\n\n";
           }
        }
        additonalURLRequests();
        return returnValue;
    }
}