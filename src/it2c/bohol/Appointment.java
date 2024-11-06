
package it2c.bohol;


import java.util.Scanner;



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
    
     public void addAppointment(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
         System.out.print("First Name: ");
        String fname = sc.next();
        System.out.print("Last Name: ");
        String lname = sc.next();
       System.out.print("Date (YYYY-MM-DD): ");
        String date = sc.next();
        System.out.print("Time (HH:MM): ");
        String time = sc.next();
        System.out.print("Types of Appointment(e.g., Checkup, Consultation, etc.): ");
        String type = sc.next();

        String sql = "INSERT INTO tbl_appointment (a_fname, a_lname, a_date, a_time, a_type) VALUES (?, ?, ?, ?, ?)";
        conf.addRecord(sql, fname, lname,date, time, type);
 
      
    }
     
    
        public void viewAppointment(){
         Scanner sc = new Scanner(System.in);
         config conf = new config();   
         
         String cqry = "SELECT * FROM tbl_appointment";
         String[] hrds = {"PatientID","First Name","Last Name","Date","Time","Type"};
         String[]clmns = {"a_PatientID","a_fname","a_lname","a_date","a_time","a_type"};
         conf.viewRecords(cqry, hrds, clmns );
        }
        
        private void updateAppointment(){
            
        Scanner sc = new Scanner(System.in);
        config conf = new config();
            System.out.println("Enter ID to update");     
            int id = sc.nextInt();
             sc.nextLine();
             
          String checkSql = "SELECT a_PatientID FROM tbl_appointment WHERE a_PatientID = ?";
        if (conf.getSingleValue(checkSql, id) == 0) {
            System.out.println("Appointment ID does not exist. Please try again.");
            return;
        }
            
         System.out.println("Enter new First Name:");
         String ufname = sc.next();
         System.out.println("Enter new Last Name:");
         String ulname = sc.next();
         System.out.print("Date (YYYY-MM-DD): ");
         String udate = sc.next();
        System.out.print("Enter new Time (HH:MM): ");
        String utime = sc.nextLine();
        System.out.println("Enter new Type of Appointment(e.g., Checkup, Consultation, etc.):");
         String utype= sc.next();
      
      String qry = "UPDATE tbl_appointment SET a_fname = ?,a_lname = ?,a_date= ?, a_time = ?, a_type = ? WHERE a_PatientID= ?";
      conf.updateRecord(qry, ufname, ulname, udate, utime, utype ,id);
        }
      
      
      public void deleteAppointment(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
   
            System.out.print("Enter Patient ID to delete:");
            int id = sc.nextInt();
            
             String checkSql = "SELECT a_PatientID FROM tbl_appointment WHERE a_PatientID = ?";
        if (conf.getSingleValue(checkSql, id) == 0) {
            System.out.println("Patient ID does not exist. Please try again with a valid ID.");
            return;
        }
        
     
        System.out.print("Are you sure you want to delete patient with ID " + id + "? (yes/no): ");
        String confirmation = sc.next();
        
        if (!confirmation.equalsIgnoreCase("yes")) {
            System.out.println("Deletion canceled.");
            return; 
        }
         String sql;
          sql = "DELETE FROM tbl_appointment WHERE a_PatientID= ?";
           conf.deleteRecord(sql,String.valueOf(id));
      
    
    
      }
   
}
