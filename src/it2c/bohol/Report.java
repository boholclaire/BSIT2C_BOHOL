
package it2c.bohol;

import java.sql.*;
import java.util.Scanner;

public class Report {
    Scanner input = new Scanner(System.in);
    config conf = new config();
    Patient p = new Patient();
    Appointment a = new Appointment();
    PregnancyDetails pd = new PregnancyDetails();


      public void report_type() {
        boolean exit = true;
        do {
            System.out.println("+----------------------------------------------------------------------------------------------------+");
            System.out.printf("|%-25s%-50s%-25s|\n", "", "**Report**", "");
            System.out.printf("|%-5s%-95s|\n", "", "1. General Report");
            System.out.printf("|%-5s%-95s|\n", "", "2. Individual Report");
            System.out.printf("|%-5s%-95s|\n", "", "3. Exit");
            System.out.printf("|%-5sEnter Choice: ", "");
            int choice;
            while (true) {
                try {
                    choice = input.nextInt();
                    if (choice > 0 && choice < 4) {
                        break;
                    } else {
                        System.out.printf("|%-5sEnter Choice Again: ", "");
                    }
                } catch (Exception e) {
                    input.next();
                    System.out.printf("|%-5sEnter Choice Again: ", "");
                }
            }
            switch (choice) {
                case 1:
                    generalReport();
                    break;
                case 2:
                    p.viewPatient();
                    individualReport();
                    break;
                default:
                    exit = false;
                    break;
            }
        } while (exit);
    }

   private void generalReport() {
        System.out.println("+----------------------------------------------------------------------------------------------------+");
        System.out.printf("|%-25s%-50s%-25s|\n", "", "**Patients**", "");
        p.viewPatient();
        System.out.println("+----------------------------------------------------------------------------------------------------+");
       
        System.out.printf("|%-25s%-50s%-25s|\n", "", "**Pregnancy Details**", "");
        pd.viewPregnancyDetails();
        System.out.println("+----------------------------------------------------------------------------------------------------+");
       
        System.out.printf("|%-25s%-50s%-25s|\n", "", "**Appointment History**", "");
        a.viewAppointment();
    }

    private void individualReport() {
        boolean exit = true;
        System.out.println("+----------------------------------------------------------------------------------------------------+");
        System.out.printf("|%-25s%-50s%-25s|\n", "", "**Individual Report**", "");
        System.out.printf("|%-25s%-50s%-25s|\n", "", "**!Enter 0 in ID to Exit!**", "");
        System.out.print("|\tEnter ID to View: ");

        int id;
        while (true) {
            try {
                id = input.nextInt();
                if (doesIDexists(id, conf)) {
                    break;
                } else if (id == 0) {
                    exit = false;
                    break;
                } else {
                    System.out.print("|\tEnter ID to View Again: ");
                }
            } catch (Exception e) {
                input.next();
                System.out.print("|\tEnter ID to View Again: ");
            }
        }

        if (exit) {
            try {
                String patientSQL = "SELECT p_fname, p_lname, p_dob, p_age, p_address, p_contactnumber FROM tbl_Patient WHERE p_PatientID = ?";
                PreparedStatement patientStmt = conf.connectDB().prepareStatement(patientSQL);
                patientStmt.setInt(1, id);
                ResultSet patientRs = patientStmt.executeQuery();

                if (patientRs.next()) {
                    System.out.println("+----------------------------------------------------------------------------------------------------+");
                    System.out.printf("|%-25s%-50s%-25s|\n", "", "Individual Patient Information", "");
                    System.out.printf("|%-15s: %-83s|\n", "First Name", patientRs.getString("p_fname"));
                    System.out.printf("|%-15s: %-83s|\n", "Last Name", patientRs.getString("p_lname"));
                    System.out.printf("|%-15s: %-83s|\n", "Date of Birth", patientRs.getString("p_dob"));
                    System.out.printf("|%-15s: %-83s|\n", "Age", patientRs.getString("p_age"));
                    System.out.printf("|%-15s: %-83s|\n", "Address", patientRs.getString("p_address"));
                    System.out.printf("|%-15s: %-83s|\n", "Contact Number", patientRs.getString("p_contactnumber"));
                    System.out.println("+----------------------------------------------------------------------------------------------------+");

                    String pregnancySQL = "SELECT pr_DueDate, pr_Weight, pr_MedicalHistory, pr_Allergies FROM tbl_pregnancy WHERE p_PatientID = ?";
                    PreparedStatement pregnancyStmt = conf.connectDB().prepareStatement(pregnancySQL);
                    pregnancyStmt.setInt(1, id);
                    ResultSet pregnancyRs = pregnancyStmt.executeQuery();

                    System.out.printf("|%-25s%-50s%-25s|\n", "", "**Pregnancy Details**", "");
                    System.out.println("+-----------------------------------------------------------------------------------------------------+");
                    System.out.printf("| %-22s | %-22s | %-22s | %-23s |\n", "Due Date", "Weight", "Medical History", "Allergies");
                    System.out.println("+-----------------------------------------------------------------------------------------------------+");

                    boolean hasPregnancyDetails = false;
                    while (pregnancyRs.next()) {
                        hasPregnancyDetails = true;
                        System.out.printf("| %-22s | %-22s | %-22s | %-23s |\n",
                                pregnancyRs.getString("pr_DueDate"),
                                pregnancyRs.getString("pr_Weight"),
                                pregnancyRs.getString("pr_MedicalHistory"),
                                pregnancyRs.getString("pr_Allergies"));
                    }

                    if (!hasPregnancyDetails) {
                        System.out.printf("|%-25s%-50s%-25s|\n", "", "!!No Pregnancy History!!", "");
                    }

                    System.out.println("+--------------------------------------------------------------------------------------------------------+");

                    String appointmentSQL = "SELECT a_date, a_time, a_type FROM tbl_appointment WHERE p_PatientID = ?";
                    PreparedStatement appointmentStmt = conf.connectDB().prepareStatement(appointmentSQL);
                    appointmentStmt.setInt(1, id);
                    ResultSet appointmentRs = appointmentStmt.executeQuery();

                    System.out.printf("|%-25s%-50s%-25s|\n", "", "**Appointment History**", "");
                    System.out.println("+----------------------------------------------------------------------------------------------------------+");
                    System.out.printf("| %-22s | %-22s | %-22s |\n", "Date", "Time", "Type");
                    System.out.println("+----------------------------------------------------------------------------------------------------------+");

                    boolean hasAppointments = false;
                    while (appointmentRs.next()) {
                        hasAppointments = true;
                        System.out.printf("| %-22s | %-22s | %-22s |\n",
                                appointmentRs.getString("a_date"),
                                appointmentRs.getString("a_time"),
                                appointmentRs.getString("a_type"));
                    }

                    if (!hasAppointments) {
                        System.out.printf("|%-25s%-50s%-25s|\n", "", "!!No Appointment History!!", "");
                    }

                    System.out.println("+-----------------------------------------------------------------------------------------------------------+");

                    patientRs.close();
                    pregnancyRs.close();
                    appointmentRs.close();
                    patientStmt.close();
                    pregnancyStmt.close();
                    appointmentStmt.close();
                } else {
                    System.out.println("|\tNo record found for ID: " + id + " |");
                }
            } catch (Exception e) {
                System.out.println("|\tError retrieving data: " + e.getMessage() + " |");
            }
        }
    }

    private boolean doesIDexists(int id, config conf) {
        String query = "SELECT COUNT(*) FROM tbl_Patient WHERE p_PatientID = ?";
        try (Connection conn = conf.connectDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("|\tError checking Patient ID: " + e.getMessage());
        }
        return false;
    }
}

   



