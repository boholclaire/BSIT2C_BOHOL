
package it2c.bohol;


import java.util.Scanner;

public class Report {
    
  Scanner input = new Scanner(System.in);
   config conf = new config();
   Patient  p = new Patient();
   Appointment  a = new Appointment();
    private String cqry;

 public void report_type(){
 Scanner sc = new Scanner(System.in);
 String response;
  
  do{
     System.out.println("+----------------------------------------------------------------------------------------------------+");
         System.out.println("1. General Report");
            System.out.println("2. Generate Report for a Specific Patient");
            System.out.println("3. Generate Report for a Specific Appointment");
            System.out.println("4. Exit");
            System.out.printf("|%-5sEnter Choice: ","");
            int choice;
            while(true){
                try{
                    choice = input.nextInt();
                    if(choice > 0 &&  choice < 4 ){
                        break;
                    }else{
                        System.out.printf("Enter choice age:");
                    }
                }catch(Exception e){
                    input.next();
                    System.out.printf("Enter choice again:");
                }
            }
            
            
        switch(choice){
            case 1:
                generalReport();
                break;
                
            case 2:
                 p.viewPatient();
                 generatePatientReport();
                 
                 break;
                 
            case 3:
                a.viewAppointment();
                generateAppointmentReport();
                
                break;
                
            case 4:
                System.out.println("Exiting Report.....");
                return;
    
        }
        System.out.println("Do you want to generate another report? (yes/no)");
        response = sc.next();
    }while(response.equalsIgnoreCase("yes"));
 }
 private void generalReport() {
 
 Scanner sc = new Scanner(System.in);
 config conf = new config();
 
  String qry = "SELECT "
               + "COUNT(DISTINCT p.PatientID) AS TotalPatients, "
               + "COUNT(a.AppointmentID) AS TotalAppointments "
               + "FROM tbl_Patient p "
               + "LEFT JOIN tbl_Appointment a ON p.PatientID = a.PatientID";

    System.out.println("General Report: Summary of Patients and Appointments");
    String[] hrds = {"Total Patients", "Total Appointments"};
    String[] clmns = {"TotalPatients", "TotalAppointments"};
 conf.viewRecords(cqry, hrds, clmns );
  }
 public void generatePatientReport() {
    config conf = new config();
    Scanner sc = new Scanner(System.in);

    System.out.print("Enter Patient ID: ");
    int patientID = sc.nextInt();

    String qry = "SELECT p.PatientID, p.FirstName, p.LastName, p.DOB,p.Age,p.address,p.ContactNumber, "
               + "a.AppointmentID, a.AppointmentDate, a.AppointmentTime, a.AppointmentType "
               + "FROM tbl_Patient p "
               + "LEFT JOIN tbl_Appointment a ON p.PatientID = a.PatientID "
               + "WHERE p.PatientID = ?";

    System.out.println("Specific Report: Transactions for Patient ID " + patientID);
    String[] hrds = {"Patient ID", "First Name", "Last Name", "DOB", "Age","Address","Contact Number", "Appointment ID", "Patient","Appointment Date", "Appointment Time", "Appointment Type"};
    String[] clmns = {"p_PatientID", "p_fname", "p_lname", "p_dob", "p_age","p_address","p_contactnumber", "a_appointmentID", "a_patienID","a_date", "a_time", "a_type"};
    conf.viewRecord(qry, hrds, clmns, patientID);
}
   
public void generateAppointmentReport() {
    config conf = new config();
    Scanner sc = new Scanner(System.in);

    System.out.print("Enter Appointment ID: ");
    int appointmentID = sc.nextInt();

    String qry = "SELECT a.AppointmentID, p.FirstName, p.LastName, p.DOB, p.ContactNumber, "
               + "a.AppointmentDate, a.AppointmentTime, a.AppointmentType "
               + "FROM tbl_Appointment a "
               + "LEFT JOIN tbl_Patient p ON a.PatientID = p.PatientID "
               + "WHERE a.AppointmentID = ?";

    System.out.println("Specific Report: Details for Appointment ID " + appointmentID);
    String[] hrds = {"Appointment ID", "First Name", "Last Name", "DOB", "Contact Number", "Appointment Date", "Appointment Time", "Appointment Type"};
    String[] clmns = {"AppointmentID", "FirstName", "LastName", "DOB", "ContactNumber", "AppointmentDate", "AppointmentTime", "AppointmentType"};
    conf.viewRecord(qry, hrds, clmns, appointmentID);
}
 
}
