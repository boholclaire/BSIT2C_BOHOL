
package it2c.bohol;

import java.util.Scanner;


public class Patient {
    
      public void pbirthing(){
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
                    
                     System.out.println("Exiting...");
                    
                break;
                
                   default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            
          System.out.println("Do You Want to Continue? (yes/No): ");
            response = sc.next();
            
             if (response.equalsIgnoreCase("no")) {
                return; 
            }
            
        } while(response.equalsIgnoreCase("yes"));
        

             
    }
   public void addPatient() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("First Name: ");
        String fname = sc.nextLine();

        System.out.print("Last Name: ");
        String lname = sc.nextLine();

        String birth = getValidInput(sc, "Date of Birth (YYYY-MM-DD): ", this::isValidDate, "Invalid date format. Please try again.");

        String age = getValidInput(sc, "Age: ", this::isValidAge, "Invalid age. Please enter a valid positive number.");

        System.out.print("Address: ");
        String address = sc.nextLine();

        String contact = getValidInput(sc, "Contact Number: ", this::isValidContactNumber, "Invalid contact number. It must be exactly 11 digits.");

        String sql = "INSERT INTO tbl_Patient (p_fname, p_lname, p_dob, p_age, p_address, p_contactnumber) VALUES (?, ?, ?, ?, ?, ?)";
         conf.addRecord(sql, fname, lname, birth, age, address, contact);
     
    }

    public void viewPatient() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        String query = "SELECT * FROM tbl_Patient";
        String[] headers = {"PatientID", "First Name", "Last Name", "Date of Birth", "Age", "Address", "Contact Number"};
        String[] columns = {"p_PatientID", "p_fname", "p_lname", "p_dob", "p_age", "p_address", "p_contactnumber"};
        conf.viewRecords(query, headers, columns);
    }

    public void updatePatient() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.println("Enter ID to update: ");
        int id = sc.nextInt();
        sc.nextLine(); 

        while (conf.getSingleValue("SELECT p_PatientID FROM tbl_Patient WHERE p_PatientID = ?", id) == 0) {
            System.out.println("SELECTED ID DOES NOT EXIST. PLEASE TRY AGAIN:");
            id = sc.nextInt();
            sc.nextLine();
        }

        System.out.println("Enter new First Name: ");
        String ufname = sc.nextLine();
        System.out.println("Enter new Last Name: ");
        String ulname = sc.nextLine();

        String ubirth = getValidInput(sc, "Enter Date of Birth (YYYY-MM-DD): ", this::isValidDate, "Invalid date format. Please try again.");

        String uage = getValidInput(sc, "Enter new Age: ", this::isValidAge, "Invalid age. Please enter a valid positive number.");

        System.out.println("Enter new Address: ");
        String uaddress = sc.nextLine();

        String ucontact = getValidInput(sc, "Enter new Contact Number: ", this::isValidContactNumber, "Invalid contact number. It must be exactly 11 digits.");

        String query = "UPDATE tbl_Patient SET p_fname = ?, p_lname = ?, p_dob = ?, p_age = ?, p_address = ?, p_contactnumber = ? WHERE p_PatientID = ?";
        try {
            conf.updateRecord(query, ufname, ulname, ubirth, uage, uaddress, ucontact, id);
            System.out.println("Patient updated successfully.");
        } catch (Exception e) {
            System.out.println("Error updating patient: " + e.getMessage());
        }
    }

    public void deletePatient() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Patient ID to delete: ");
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

        String deleteSql = "DELETE FROM tbl_Patient WHERE p_PatientID = ?";
        try {
            conf.deleteRecord(deleteSql, String.valueOf(id));
            System.out.println("Patient deleted successfully.");
        } catch (Exception e) {
            System.out.println("Error deleting patient: " + e.getMessage());
        }
    }

    private String getValidInput(Scanner sc, String prompt, InputValidator validator, String errorMessage) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = sc.nextLine();
            if (validator.isValid(input)) {
                break;
            } else {
                System.out.println(errorMessage);
            }
        }
        return input;
    }

    private interface InputValidator {
        boolean isValid(String input);
    }

    private boolean isValidDate(String date) {
        return date.matches("^(\\d{4})-(\\d{2})-(\\d{2})$");
    }

    private boolean isValidAge(String age) {
        try {
            int ageInt = Integer.parseInt(age);
            return ageInt > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidContactNumber(String contact) {
        return contact.matches("\\d{11}");
    }
}


    
            
       
      
    
    
      

    
    
    

