
package it2c.bohol;

import java.util.Scanner;


public class MedicalRecord {
    
     public void mtransaction(){
     Scanner sc = new Scanner(System.in);
     String response;
     do{
          System.out.println("BIRTHING CENTER APP");
            
            System.out.println("1. ADD MEDICAL RECORDS");
            System.out.println("2. VIEW MEDICAL RECORDS");
            System.out.println("3. UPDATE MEDICAL RECORDS");      
            System.out.println("4. DELETE MEDICAL RECORDS");
             System.out.println("5. EXIT");
        
             System.out.println("Enter action");           
            int  act = sc.nextInt();
       
            MedicalRecord  m = new MedicalRecord ();
            
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
                
            }
            
            System.out.println("Continue? (yes/No)");
                response = sc.next();
         
            
            
            
        }while(response.equals("yes"));
        System.out.println("Thankyou");
                  
      
     
    System.out.println("Do you want to continue? (yes/no):");
     response = sc.next();
    while(response.equalsIgnoreCase("yes"));
             
    }
    
     public void addMedicalRecord(){
      config conf = new config();
      Scanner sc = new Scanner(System.in);
      Patient  p = new Patient();
      p.viewPatient(); 
      
      System.out.print("");
      
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
            
         System.out.println("Enter new First Name:");
         String ufname = sc.next();
         System.out.println("Enter new Last Name:");
         String ulname = sc.next();
         System.out.println("Enter new Date:");
         String udate = sc.next();
         System.out.println("Enter new Time:");
         String utime = sc.next();
         System.out.println("Enter new Type of Appointment:");
         String utype= sc.next();
      
      String qry = "UPDATE tbl_appointment SET a_fname = ?,a_lname = ?,a_date= ?, a_time = ?, a_type = ? WHERE a_PatientID= ?";
      conf.updateRecord(qry, ufname, ulname, udate, utime, utype ,id);
        }
      
      
      public void deleteAppointment(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
   
            System.out.print("Enter Patient ID to delete:");
            int id = sc.nextInt();
            
           String sql = "DELETE FROM tbl_appointment WHERE a_PatientID= ?";
           conf.deleteRecord(sql,String.valueOf(id));
      
    
    
      }
     
    
    
}
