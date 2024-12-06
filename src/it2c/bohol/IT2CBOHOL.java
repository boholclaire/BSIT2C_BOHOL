
package it2c.bohol;

import java.util.Scanner;


public class IT2CBOHOL {

  
    public static void main(String[] args) {
      Scanner sc = new Scanner (System.in);
        String response;
        
        do{
            System.out.println("");
            System.out.println("BIRTHING CENTER APP");
            
            System.out.println("1. REGISTRATION");
            System.out.println("2. PREGNANCY DETAILS");
            System.out.println("3. APPOINTMENT");
            System.out.println("4. REPORT");
            System.out.println("5. Exit");      
            
            System.out.println("Enter action:");        
            int  action = sc.nextInt();
            
            switch(action){
                
                case 1:
                                Patient p = new Patient();
                                   p.pbirthing();
               break;
                
                case 2: 
                            
                              PregnancyDetails  pd = new PregnancyDetails();
                                 pd.prbirthing();
                          
                break;
                
                case 3:  
                       
                               Appointment  a = new Appointment();
                                    a.abirthing();
                      
                break;
                
                case 4:
                    
                                  Report r = new Report();
                                      r.report_type();
                
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
            
        } while(response.equalsIgnoreCase("yes"));
        
        System.out.println("Thank you");
    }
}

    
    

