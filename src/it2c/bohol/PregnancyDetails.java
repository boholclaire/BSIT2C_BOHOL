
package it2c.bohol;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;


public class PregnancyDetails {

     public void prbirthing() {
        Scanner sc = new Scanner(System.in);
        String response;

        do {
            System.out.println("BIRTHING CENTER APP");
            System.out.println("1. ADD PREGNANCY DETAILS");
            System.out.println("2. VIEW PREGNANCY DETAILS");
            System.out.println("3. UPDATE PREGNANCY DETAILS");
            System.out.println("4. DELETE PREGNANCY DETAILS");
            System.out.println("5. EXIT");
            System.out.print("Enter action: ");
            
            int act = sc.nextInt();
            sc.nextLine(); 

            PregnancyDetails  pd = new PregnancyDetails();
            
            switch (act) {
                case 1:
                 
                   pd.addPregnancyDetails();
                  pd. viewPregnancyDetails();
                   
                    break;
                    
                case 2:
                  
                  pd.viewPregnancyDetails();
              
                    break;
                    
                case 3:
                    
                    pd.viewPregnancyDetails();
                    pd.updatePregnancyDetails();
                    pd.viewPregnancyDetails();
                    
                    break;
                    
                case 4:
                    
                    pd.viewPregnancyDetails();
                    pd.deletePregnancyDetails();
                    pd.viewPregnancyDetails();
                    
                    break;
                    
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

            System.out.print("Continue? (yes/no): ");
            response = sc.nextLine();
            
             if (response.equalsIgnoreCase("no")) {
                return; 
            }
            
        } while (response.equalsIgnoreCase("yes"));

        System.out.println("Thank you for using the Birthing Center App!");
    }

    public void addPregnancyDetails() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Patient ID: ");
        int patientID = sc.nextInt();
        sc.nextLine(); 
        
        if (!isValidPatient(patientID, conf)) {
            System.out.println("Patient ID does not exist.");
            return;
        }
       
        String dueDate;
        do {
            System.out.print("Enter Due Date (YYYY-MM-DD): ");
            dueDate = sc.nextLine();
        } while (!PregnancyDetails.isValidDate(dueDate));

        System.out.print("Enter Weight (in kg): ");
        double weight = sc.nextDouble();
        sc.nextLine(); 
        if (!PregnancyDetails.isValidWeight(weight)) {
            System.out.println("Invalid weight. Please enter a valid weight.");
            return;
        }

        System.out.print("Enter Medical History: ");
        String medicalHistory = sc.nextLine();

        System.out.print("Enter Allergies (if any): ");
        String allergies = sc.nextLine();

       String checkSql = "SELECT COUNT(*) FROM tbl_patient WHERE p_PatientID = ?";
        if (conf.getSingleValue(checkSql, patientID) == 0) {
            System.out.println("Patient ID does not exist.");
            return;
        }
        
        
        
        String sql = "INSERT INTO tbl_pregnancy (p_PatientID, pr_DueDate, pr_Weight, pr_MedicalHistory, pr_Allergies) VALUES ( ?, ?, ?, ?, ?)";
        conf.addRecord(sql, patientID, dueDate, weight, medicalHistory, allergies);

        System.out.println("Pregnancy details added successfully.");
    }

 
    public void viewPregnancyDetails() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        String query = "SELECT * FROM tbl_pregnancy";
        String[] hrds = {"PregnancyID", "PatientID", "DueDate", "Weight", "MedicalHistory", "Allergies"};
        String[] clmns = {"pr_PregnancyID", "p_PatientID", "pr_DueDate", "pr_Weight", "pr_MedicalHistory", "pr_Allergies"};
        conf.viewRecords(query, hrds, clmns);
    }

    public void updatePregnancyDetails() {
        Scanner sc = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Patient ID to update pregnancy details: ");
        int patientID = sc.nextInt();
        sc.nextLine();

        if (!isValidPatient(patientID, conf)) {
            System.out.println("Patient ID does not exist.");
            return;
        }

       String checkSql = "SELECT COUNT(*) FROM tbl_pregnancy WHERE p_PatientID = ?";
        if (conf.getSingleValue(checkSql, patientID) == 0) {
            System.out.println("No pregnancy details found for this Patient ID and Pregnancy ID.");
            return;
        }

     
        System.out.print("Enter new Due Date (YYYY-MM-DD): ");
        String newDueDate = sc.nextLine();
        System.out.print("Enter new Weight (in kg): ");
        double newWeight = sc.nextDouble();
        sc.nextLine(); 
          if (PregnancyDetails.isValidWeight(newWeight)) {
        } else {
              System.out.println("Invalid weight. Please enter a valid weight.");
              return;
        }
        System.out.print("Enter new Medical History: ");
        String newMedicalHistory = sc.nextLine();
        System.out.print("Enter new allergies: ");
        String  newAllergies = sc.nextLine();
        
     String qry = "UPDATE tbl_pregnancy SET pr_DueDate = ?, pr_Weight = ?, pr_MedicalHistory = ?, pr_Allergies = ? WHERE p_PatientID = ?";
     conf.updateRecord(qry, newDueDate, newWeight, newMedicalHistory, newAllergies, patientID);


     
  
}
           public void deletePregnancyDetails() {
          Scanner sc = new Scanner(System.in);
         config conf = new config();

        System.out.print("Enter Patient ID to delete pregnancy details: ");
       int patientID = sc.nextInt();
       sc.nextLine(); 

    if (!isValidPatient(patientID, conf)) {
        System.out.println("Patient ID does not exist.");
        return;
    }

    String checkSql = "SELECT COUNT(*) FROM tbl_pregnancy WHERE p_PatientID = ?";
    if (conf.getSingleValue(checkSql, patientID) == 0) {
        System.out.println("No pregnancy details found for this Patient ID.");
        return;
    }

    System.out.print("Are you sure you want to delete pregnancy details for Patient ID " + patientID + "? (yes/no): ");
    String confirmation = sc.nextLine();
    if (!confirmation.equalsIgnoreCase("yes")) {
        System.out.println("Deletion canceled.");
        return;
    }

    String sql = "DELETE FROM tbl_pregnancy WHERE p_PatientID = ?";
    try {
        conf.deleteRecord(sql,String.valueOf(patientID));
        System.out.println("Pregnancy details deleted successfully.");
    } catch (Exception e) {
        System.out.println("Error deleting pregnancy details: " + e.getMessage());
    
}


    }

    private boolean isValidPatient(int patientID, config conf) {
        String checkSql = "SELECT COUNT(*) FROM tbl_patient WHERE p_PatientID = ?";
        return conf.getSingleValue(checkSql, patientID) > 0;
    }
 private static boolean isValidDate(String dueDate) {
    try {
        LocalDate.parse(dueDate);
        return true; 
    } catch (DateTimeParseException e) {
        System.out.println("Invalid date format. Please use the format YYYY-MM-DD.");
        return false;
    }
}
 
  private static boolean isValidWeight(double weight) {
      if (weight <= 0) {
        System.out.println("Weight must be greater than zero.");
        return false;
      }
    if (weight < 30 || weight > 300) {
        System.out.println("Weight must be between 30kg and 300kg.");
        return false;
    }
    return true;
}
}