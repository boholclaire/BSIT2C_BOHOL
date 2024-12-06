
package it2c.bohol;


import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Appointment {
    
     
      public void abirthing(){
     Scanner sc = new Scanner(System.in);
     String response;
     do{
          System.out.println("BIRTHING CENTER APP");
            
            System.out.println("1. ADD APPOINTMENT");
            System.out.println("2. VIEW APPOINTMENT");
            System.out.println("3. UPDATE APPOINTMENT");      
            System.out.println("4. DELETE  APPOINTMENT");
             System.out.println("5. EXIT");
        
             System.out.println("Enter action");           
            int  act = sc.nextInt();
            sc.nextLine();
       
            Appointment  a = new Appointment();
            
            switch(act){
                case 1:         
                    
                    a.addAppointment();
                   a.viewAppointment();     
             
               break;
                
                case 2:  
                  
                    a.viewAppointment();
                    
                break;
                
                case 3:        
                    
                 a.viewAppointment();
                 a.updateAppointment();
                 a.viewAppointment();
                 
                break;
                
                case 4:        
                    
                      a.viewAppointment();
                     a.deleteAppointment();
                     a.viewAppointment();          
                               
                break;
                
                case 5:
                    
                     System.out.println("Exiting...");
                    
                break;
                
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
                
            }
        System.out.println("Continue? (yes/No): ");
            response = sc.nextLine();
            
             if (response.equalsIgnoreCase("no")) {
                return;  
            }
        } while(response.equalsIgnoreCase("yes"));
        
        System.out.println("Thank you");
    };
    
     public void addAppointment() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Patient ID: ");
        int patientID = sc.nextInt();
        if (!isValidPatient(patientID, conf)) {
            System.out.println("Patient ID does not exist.");
            return;
        }

        System.out.print("Pregnancy ID: ");
        int pregnancyID = sc.nextInt();
        if (!isValidPregnancy(pregnancyID, conf)) {
            System.out.println("Pregnancy ID does not exist.");
            return;
        }

        String date;
        do {
            System.out.print("Date (YYYY-MM-DD): ");
            date = sc.next();
        } while (!isValidDate(date)); 
        
        String time;
        do {
            System.out.print("Time (HH:MM): ");
            time = sc.next();
        } while (!isValidTime(time)); 

        String type;
        do {
            System.out.print("Type of Appointment (e.g., Checkup, Consultation, etc.): ");
            type = sc.next();
        } while (type.trim().isEmpty()); 

        String sql = "INSERT INTO tbl_appointment (p_PatientID, pr_PregnancyID, a_date, a_time, a_type) VALUES (?, ?, ?, ?, ?)";
        conf.addRecord(sql, patientID, pregnancyID, date, time, type);
        System.out.println("Appointment added successfully.");
    }

   
    public void viewAppointment() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();   

        String query = "SELECT * FROM tbl_appointment";
        String[] headers = {"PatientID", "PregnancyID", "Date", "Time", "Type"};
        String[] columns = {"p_PatientID", "pr_PregnancyID", "a_date", "a_time", "a_type"};
        conf.viewRecords(query, headers, columns);
    }

    public void updateAppointment() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Patient ID to update: ");
        int patientID = sc.nextInt();
        sc.nextLine();  

        if (!isValidPatient(patientID, conf)) {
            System.out.println("Patient ID does not exist.");
            return;
        }

        System.out.print("Enter Pregnancy ID to update appointment: ");
        int pregnancyID = sc.nextInt();
        sc.nextLine();

        if (!isValidPregnancy(pregnancyID, conf)) {
            System.out.println("Pregnancy ID does not exist.");
            return;
        }

        String checkSql = "SELECT COUNT(*) FROM tbl_appointment WHERE p_PatientID = ? AND pr_PregnancyID = ?";
        if (conf.getSingleValue(checkSql, patientID, pregnancyID) == 0) {
            System.out.println("No appointment found for Patient ID " + patientID + " and Pregnancy ID " + pregnancyID);
            return;
        }

        System.out.println("Enter new Date (YYYY-MM-DD): ");
        String date = sc.nextLine();
        System.out.println("Enter new Time (HH:MM): ");
        String time = sc.nextLine();
        System.out.println("Enter new Type of Appointment (e.g., Checkup, Consultation, etc.): ");
        String type = sc.nextLine();

        String query = "UPDATE tbl_appointment SET a_date = ?, a_time = ?, a_type = ? WHERE p_PatientID = ? AND pr_PregnancyID = ?";
        conf.updateRecord(query, date, time, type, patientID, pregnancyID);

        System.out.println("Appointment updated successfully.");
    }

    public void deleteAppointment() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Patient ID to delete appointment: ");
        int patientID = sc.nextInt();
        sc.nextLine();

        if (!isValidPatient(patientID, conf)) {
            System.out.println("Patient ID does not exist.");
            return;
        }

        System.out.print("Enter Pregnancy ID to delete appointment: ");
        int pregnancyID = sc.nextInt();
        sc.nextLine();

        if (!isValidPregnancy(pregnancyID, conf)) {
            System.out.println("Pregnancy ID does not exist.");
            return;
        }

        String checkSql = "SELECT COUNT(*) FROM tbl_appointment WHERE p_PatientID = ? AND pr_PregnancyID = ?";
        if (conf.getSingleValue(checkSql, patientID, pregnancyID) == 0) {
            System.out.println("No appointment found for Patient ID " + patientID + " and Pregnancy ID " + pregnancyID);
            return;
        }

        System.out.print("Are you sure you want to delete the appointment for Patient ID " + patientID + " and Pregnancy ID " + pregnancyID + "? (yes/no): ");
        String confirmation = sc.nextLine();
        if (!confirmation.equalsIgnoreCase("yes")) {
            System.out.println("Deletion canceled.");
            return;
        }

        String sql = "DELETE FROM tbl_appointment WHERE p_PatientID = ? AND pr_PregnancyID = ?";
        conf.deleteRecord(sql, String.valueOf(patientID), String.valueOf(pregnancyID));

        System.out.println("Appointment deleted successfully.");
    }

    private boolean isValidPatient(int patientID, config conf) {
        String checkSql = "SELECT COUNT(*) FROM tbl_patient WHERE p_PatientID = ?";
        return conf.getSingleValue(checkSql, patientID) > 0;
    }

    private boolean isValidPregnancy(int pregnancyID, config conf) {
        String checkSql = "SELECT COUNT(*) FROM tbl_pregnancy WHERE pr_PregnancyID = ?";
        return conf.getSingleValue(checkSql, pregnancyID) > 0;
    }

    private boolean isValidDate(String date) {
        String dateRegex = "^(\\d{4})-(\\d{2})-(\\d{2})$";
        Pattern pattern = Pattern.compile(dateRegex);
        Matcher matcher = pattern.matcher(date);

        return matcher.matches();
    }

    private boolean isValidTime(String time) {
        String timeRegex = "^(\\d{2}):(\\d{2})$";
        Pattern pattern = Pattern.compile(timeRegex);
        Matcher matcher = pattern.matcher(time);

        return matcher.matches();
    }
}
    
