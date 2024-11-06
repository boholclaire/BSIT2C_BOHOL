
package it2c.bohol;

import java.util.Scanner;


public class Patient {
    
      public void ptransaction(){
     Scanner sc = new Scanner(System.in);
     String response;
     do{
          System.out.println("BIRTHING CENTER APP");
            
            System.out.println("1. ADD PATIENT");
            System.out.println("2. VIEW PATIENT ");
            System.out.println("3. UPDATE PATIENT");      
            System.out.println("4. DELETE PATIENT");
             System.out.println("5. EXIT");
        
             System.out.println("Enter action");           
            int  act = sc.nextInt();
       
            Patient  p = new Patient();
            
            switch(act){
                case 1:         
                    p.addPatient();
                   p.viewPatient();     
             
               break;
                
                case 2:  
                  
                    p.viewPatient();
                    
                break;
                
                case 3:        
                    
                 p.viewPatient();
                 p.updatePatient();
                 p.viewPatient();
                 
                break;
                
                case 4:        
                    
                      p.viewPatient();
                     p.deletePatient();
                     p.viewPatient();          
                               
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
             
    }
    
     public void addPatient(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
        
         System.out.print("First Name: ");
        String fname = sc.next();
        System.out.print("Last Name: ");
        String lname = sc.next();
        System.out.print("Date of Birth(YYYY-MM-DD):");
        String birth = sc.next();
        System.out.print("Age:");
        String  age = sc.next();
        System.out.print("Address: ");
        String address = sc.next();
        System.out.print("Contact Number: ");
        String contact = sc.next();

        String sql = "INSERT INTO tbl_Patient (p_fname, p_lname,p_dob, p_age, p_address, p_contactnumber) VALUES (?, ?, ?, ?, ?,?)";
        conf.addRecord(sql, fname, lname,birth,age, address, contact);
      
      
    }
     
    
        public void viewPatient(){
         Scanner sc = new Scanner(System.in);
         config conf = new config();   
         
         String cqry = "SELECT * FROM tbl_Patient";
         String[] hrds = {"PatientID","First Name","Last Name","Date of Birth","Age","Address","Contact Number"};
         String[]clmns = {"p_PatientID","p_fname","p_lname","p_dob","p_age","p_address","p_contactnumber"};
         conf.viewRecords(cqry, hrds, clmns );
        }
        
        private void updatePatient(){
            
        Scanner sc = new Scanner(System.in);
        config conf = new config();
            System.out.println("Enter ID to update");  
            int id = sc.nextInt();

             
       while(conf.getSingleValue("SELECT p_PatientID FROM tbl_Patient WHERE p_PatientID = ?",  id)  == 0){
            System.out.println("SELECTED ID DOESNT EXIST");
            System.out.println("PLEASE TRY AGAIN:");
            id = sc.nextInt();
    }
         System.out.println("Enter new First Name:");
         String ufname = sc.next();
         System.out.println("Enter new Last Name:");
         String ulname = sc.next();
         System.out.print("Enter new Date of Birth(YYYY-MM-DD):");
         String ubirth = sc.next();
         System.out.println("Enter new Age:");
         String uage = sc.next();
         System.out.println("Enter new Address:");
         String uaddress = sc.next();
         System.out.println("Enter new Contact Number:");
         String ucontact = sc.next();
      
      String qry = "UPDATE tbl_Patient SET p_fname = ?,p_lname = ?,p_dob=?,p_age = ?, p_address = ?, p_contactnumber = ? WHERE p_PatientID= ?";
      conf.updateRecord(qry, ufname, ulname, ubirth, uage, uaddress, ucontact,id );
      
        
        }
        
      public void deletePatient(){
        Scanner sc = new Scanner(System.in);
        config conf = new config();
   
            System.out.print("Enter Patient ID to delete:");
            int id = sc.nextInt();
            
            String checkSql = "SELECT p_PatientID FROM tbl_Patient WHERE p_PatientID = ?";
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
          sql = "DELETE FROM tbl_Patient WHERE p_PatientID= ?";
           conf.deleteRecord(sql,String.valueOf(id));
 
        }
    }
            
       
      
    
    
      

    
    
    

