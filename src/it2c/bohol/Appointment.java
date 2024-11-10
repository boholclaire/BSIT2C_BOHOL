
package it2c.bohol;


import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Appointment {
    
     
      public void atransaction(){
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
                    
                    
                break;
                
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
                
            }
        System.out.println("Continue? (yes/No): ");
            response = sc.nextLine();
        } while(response.equalsIgnoreCase("yes"));
        
        System.out.println("Thank you");
    };
    
     public void addAppointment() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();
         Patient p = new Patient();
         

        System.out.print("Patient ID: ");
        int patientID = sc.nextInt();
        
        if (!isValidPatient(patientID, conf)) {
            System.out.println("Patient ID does not exist.");
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
        
        String checkSql = "SELECT COUNT(*) FROM tbl_patient WHERE p_PatientID = ?";
        if (conf.getSingleValue(checkSql, patientID) == 0) {
            System.out.println("Patient ID does not exist.");
            return;
        }

   
        String sql = "INSERT INTO tbl_appointment (a_PatientID, a_date, a_time, a_type) VALUES (?, ?, ?, ?)";
        conf.addRecord(sql, patientID, date, time, type);
   
    }

    
 
     public void viewAppointment(){
         Scanner sc = new Scanner(System.in);
         config conf = new config();   
         
         String cqry = "SELECT * FROM tbl_appointment";
         String[] hrds = {"PatientID","Date","Time","Type"};
         String[]clmns = {"a_PatientID","a_date","a_time","a_type"};
         conf.viewRecords(cqry, hrds, clmns );
        }



    private void updateAppointment() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Patient ID to update: ");
        int patientID = sc.nextInt();
        sc.nextLine();  
        
        if (!isValidPatient(patientID, conf)) {
            System.out.println("Patient ID does not exist.");
            return;
        }

     
        String checkSql = "SELECT COUNT(*) FROM tbl_appointment WHERE a_PatientID = ?";
        if (conf.getSingleValue(checkSql, patientID) == 0) {
            System.out.println("Appointment not found for Patient ID " + patientID);
            return;
        }

  
        System.out.println("Enter new Date (YYYY-MM-DD): ");
        String date = sc.nextLine();
        System.out.println("Enter new Time (HH:MM): ");
        String time = sc.nextLine();
        System.out.println("Enter new Type of Appointment (e.g., Checkup, Consultation, etc.): ");
        String type = sc.nextLine();

        String qry = "UPDATE tbl_appointment SET a_date = ?, a_time = ?, a_type = ? WHERE a_PatientID = ?";
        conf.updateRecord(qry, date, time, type, patientID);

        System.out.println("Appointment updated successfully.");
    }

    
    public void deleteAppointment() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Patient ID to delete appointment: ");
        int patientID = sc.nextInt();
        
        if (!isValidPatient(patientID, conf)) {
            System.out.println("Patient ID does not exist.");
        }
      
        String checkSql = "SELECT COUNT(*) FROM tbl_appointment WHERE a_PatientID = ?";
        if (conf.getSingleValue(checkSql, patientID) == 0) {
            System.out.println("No appointment found for Patient ID " + patientID);
            return;
        }

        
        System.out.print("Are you sure you want to delete the appointment for Patient ID " + patientID + "? (yes/no): ");
        String confirmation = sc.next();
        if (!confirmation.equalsIgnoreCase("yes")) {
            System.out.println("Deletion canceled.");
            return;
        }

        String sql = "DELETE FROM tbl_appointment WHERE a_PatientID = ?";
        conf.deleteRecord(sql, String.valueOf(patientID));
        System.out.println("Appointment deleted successfully.");
    }

     private boolean isValidPatient(int patientID, config conf) {
        String checkSql = "SELECT COUNT(*) FROM tbl_patient WHERE p_PatientID = ?";
        return conf.getSingleValue(checkSql, patientID) > 0;
    
    }

    private boolean isValidDate(String date) {
        String dateRegex = "^(\\d{4})-(\\d{2})-(\\d{2})$";
        Pattern pattern = Pattern.compile(dateRegex);
        Matcher matcher = pattern.matcher(date);
        
            if (!matcher.matches()) {
        System.out.println("Invalid date format. Please try again. The correct format is YYYY-MM-DD.");
        return false;
            }
    return true;

    
    }

     private boolean isValidTime(String time) {
        String timeRegex = "^(\\d{2}):(\\d{2})$";
        Pattern pattern = Pattern.compile(timeRegex);
        Matcher matcher = pattern.matcher(time);
        
           if (!matcher.matches()) {
        System.out.println("Invalid time format. Please try again. The correct format is HH-MM.");
        return false;
            }
    return true;
    }
    
}