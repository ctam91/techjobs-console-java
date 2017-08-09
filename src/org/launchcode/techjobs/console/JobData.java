package org.launchcode.techjobs.console;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LaunchCode
 */
public class JobData {

    private static final String DATA_FILE = "resources/job_data.csv";
    private static Boolean isDataLoaded = false;

    private static ArrayList<HashMap<String, String>> allJobs;

    /**
     * Fetch list of all values from loaded data,
     * without duplicates, for a given column.
     *
     * @param field The column to retrieve values from
     * @return List of all of the values of the given field
     */
    public static ArrayList<String> findAll(String field) {

        // load data, if not already loaded
        loadData();

        // create new ArrayList of strings called 'values' to store our results
        ArrayList<String> values = new ArrayList<>();

        // for each HashMap (row) in our allJobs, find 'field' (our parameter).
        // if our arrayList 'values' does not contain that value, then add it
        for (HashMap<String, String> row : allJobs) {
            String aValue = row.get(field);

            if (!values.contains(aValue)) {
                values.add(aValue);
            }
        }

        return values;
    }

    public static ArrayList<HashMap<String, String>> findAll() {

        // load data, if not already loaded
        loadData();

        return allJobs;
    }

    /**
     * Returns results of search the jobs data by key/value, using
     * inclusion of the search term.
     *
     * For example, searching for employer "Enterprise" will include results
     * with "Enterprise Holdings, Inc".
     *
     * @param column   Column that should be searched.
     * @param value Value of teh field to search for
     * @return List of all jobs matching the criteria
     */
    public static ArrayList<HashMap<String, String>> findByColumnAndValue(String column, String value) {

        // load data, if not already loaded
        loadData();

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>();

        for (HashMap<String, String> row : allJobs) {

            String aValue = row.get(column);

            if (aValue.contains(value)) {
                jobs.add(row);
            }
        }

        return jobs;
    }

    /**
     * Read in data from a CSV file and store it in a list
     */
    private static void loadData() {

        // Only load data once
        if (isDataLoaded) {
            return;
        }

        try {                                   // if isDataLoaded = false, do the following

            // Open the CSV file and set up pull out column header info and records
            Reader in = new FileReader(DATA_FILE);                                          //read in the file called (DATA_FILE)
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);       // parse the file
            List<CSVRecord> records = parser.getRecords();                                  // create List named recrods with type CSVRecord and intialize it to content of parsed data
            Integer numberOfColumns = records.get(0).size();                                // finds size of first index in our file and assigns it to new Integer variable. The number of columns is 5.
            // create array called headers. Grab data from parser, only first row, take that data and return as Set of keys, change that to an Array, then create a new array with fixed length of 5 (depending on variable numberOfColumns)
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);

            allJobs = new ArrayList<>();

            // Put the records into a more friendly format.
            // for each record in records, create a new HashMap.
            // Then for each header, put it's key (header) and its value (content) into the newJob HashMap.
            // Finally, add the new HashMap to allJobs ArrayList and set isDataLoaded to true.
            for (CSVRecord record : records) {
                HashMap<String, String> newJob = new HashMap<>();

                for (String headerLabel : headers) {
                    newJob.put(headerLabel, record.get(headerLabel));
                }

                allJobs.add(newJob);
            }

            // flag the data as loaded, so we don't do it twice
            isDataLoaded = true;

        } catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
    }

}
