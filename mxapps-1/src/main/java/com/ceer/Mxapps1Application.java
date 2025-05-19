package com.ceer;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ceer.email.GEmailSender;
import com.ceer.email.JsonDataChecker;
import com.ceer.email.JsonToExcelConverter;

@SpringBootApplication
public class Mxapps1Application {

    public static void main(String[] args) {
        SpringApplication.run(Mxapps1Application.class, args);

        // URL to check for JSON data
        String urlToCheck = "https://jsonplaceholder.typicode.com/posts";
        String excelFilePath = "output.xlsx"; // Specify the desired file path

        try {
            JsonDataChecker jsonDataChecker = new JsonDataChecker();
            GEmailSender gEmailSender = new GEmailSender();

            // Retrieve JSON data
            String jsonData = jsonDataChecker.getJsonData(urlToCheck);

            if (jsonData != null && !jsonData.isEmpty()) {
                System.out.println("JSON Data Found: " + jsonData);

                // Convert JSON to Excel
                File excelFile = JsonToExcelConverter.convertJsonToExcel(jsonData, excelFilePath);

                if (excelFile != null) { // Check if file creation was successful
                    // Email details
                    String from = "";//add the sender email here
                    String to = "";//add receiver's email here
                    String subject = "New JSON Data Available!";
                    String text = "New JSON data has been found at the URL: " + urlToCheck
                            + "\n\nAttached is the Excel file containing the data.";

                    // Send email with attachment
                    boolean emailSent = gEmailSender.sendEmailWithAttachment(to, from, subject, text, excelFile);

                    if (emailSent) {
                        System.out.println("Email sent successfully with attachment.");
                    } else {
                        System.out.println("Failed to send email.");
                    }
                } else {
                    System.out.println("Failed to create Excel file.");
                }
            } else {
                System.out.println("No JSON data found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

