package org.launchcode.techjobs.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    // Create new Scanner object called 'in'
    private static Scanner in = new Scanner(System.in);

    // create main method, which takes in an array of Strings as a parameter
    public static void main(String[] args) {

        // Initialize HashMap called columnChoices with key value pairs.
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // Top-level menu options. Initialize HashMap called actionChoices with key value pairs.
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        // Print out welcome message
        System.out.println("Welcome to LaunchCode's TechJobs App!");

        // Allow the user to search until they manually quit
        while (true) {

            String actionChoice = getUserSelection("View jobs by:", actionChoices);

            if (actionChoice.equals("list")) {

                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {
                    printJobs(JobData.findAll());
                } else {

                    ArrayList<String> results = JobData.findAll(columnChoice);

                    System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

                    // Print list of skills, employers, etc
                    for (String item : results) {
                        System.out.println(item);
                    }
                }

            } else { // choice is "search"

                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);

                // What is their search term?
                System.out.println("\nSearch term: ");
                String searchTerm = in.nextLine();

                if (searchField.equals("all")) {
                    printJobs(JobData.findByValue(searchTerm.toLowerCase()));
                } else {
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm.toLowerCase()));
                }
            }
        }
    }

    /**
     * @param menuHeader either "View jobs by:" or "Search by:", depending on user input
     * @param choices includes the options/columns available , depending on user input
     *@return key of the selected item from the choices Dictionary
     */
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {

        Integer choiceIdx;
        Boolean validChoice = false;                                // Do While loop will keep going until boolean is True (user input is valid)
        String[] choiceKeys = new String[choices.size()];           // create an array, which has the same size as our choices HashMap

        // Put the choices in an ordered structure (choiceKeys) so we can associate an integer with each one
        Integer i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {

            System.out.println("\n" + menuHeader);

            // For each option in our choiceKey array, print out the integer and option
            for (Integer j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            // Grab user input and store it in choiceIdx variable
            choiceIdx = in.nextInt();
            in.nextLine();

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {  // if user input is less than 0 or greater than the array length, print an error.
                System.out.println("Invalid choice. Try again.");
            } else {                                                  // if user input is valid, assign true to validChoice and move on to return statement
                validChoice = true;
            }

        } while (!validChoice);                                      // keep looping until validChoice is true

        return choiceKeys[choiceIdx];                               // return content of choice. This String corresponds to the chosen key (from choices, which will be either actionChoices or columnChoices).
    }

    /**
     * Print a job search results
     * @param someJobs ArrayList of results
     */
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {
        String message = "\n" + someJobs.size() + " results were found.\n\n*****\n\n";
        if (someJobs.size() == 0) {
            message += "No results were found. Please try a different search";
        } else {
            for (HashMap<String, String> job : someJobs) {
                for (String key : job.keySet()) {
                    String value = job.get(key);
                    message += key + ": " + value + "\n";
                }
                message += "\n*****\n\n";
            }
        }
        System.out.println(message);

    }
}
