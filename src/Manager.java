import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class Manager {
    Scanner sc = new Scanner(System.in);
    static Connection con;
    
    static{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded Sucessull");
            
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Employee","root","1234");
            System.out.println("Connection build Sucessful");
        }catch(SQLException e){
            e.printStackTrace();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    void addEmployee() throws Exception{

        System.out.println("Enter The Employee name");
        String empName = sc.nextLine();

        System.out.println("Enter The Employee Email");
        String empEmail = sc.nextLine();
        empEmail = empEmail.toLowerCase();

        System.out.println("Enter The Pass word");
        String password = sc.nextLine();

        System.out.println("Enter The Employee Task ");
        String empTask = sc.nextLine();

        int empPercentages = 0;
        String empStatus = "Incomplete";

        String query = "INSERT INTO empinformation(name,email,password,task,emp_percentages,status) VAlues(?,?,?,?,?,?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1,empName);
        ps.setString(2,empEmail);
        ps.setString(3,password);
        ps.setString(4,empTask);
        ps.setInt(5,empPercentages);
        ps.setString(6,empStatus);

        int i = ps.executeUpdate();
        

        if(i > 0){
            System.out.println("Emplyess information inserted");
        }else {
            System.out.println("Emp information is not inserted");
        }
        

    }

    void showAllEmployee() throws Exception{
        String query = "SELECT * FROM empinformation";
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);

        System.out.println("ID |     name   |     email   | Password|        Task      | Task percentages | sattus");
        while(rs.next()){
            System.out.println(
                rs.getInt(1) +"  "+ rs.getString(2) +
                "  "+rs.getString(3)+"  "+ rs.getString(4) +"  "
                + rs.getString(5)+"  "+rs.getInt(6) +"  " +rs.getString(7) 
            );
            
        }
    }

    void deleteEmployee() throws Exception{
        
        System.out.println("Enter The manager Gmail id");
        String managerId = sc.nextLine();
        System.out.println("Enter The password");
        String password = sc.nextLine();

        if(managerId.equals("manager@gmail.com") && password.equals("manager123")){
            
            System.out.println("Enter The Employee gmail");
            sc.nextLine();
            String empGmail = sc.nextLine();

            String query = "DELETE FROM empinformation WHERE name = '"+empGmail+"'";
            PreparedStatement ps = con.prepareStatement(query);
            int i = ps.executeUpdate();
            if(i > 0){
                System.out.println("Employ Deleted Successfull");
            }else{
                System.out.println("Employee does not found");
            }


        }
        else if(!(managerId.equals("manager@gmail.com")) && password.equals("manager123")){
            System.out.println("Wrong manager gmail");
        }
        else if(managerId.equals("manager@gmail.com") && !(password.equals("manager123"))){
            System.out.println("Wrong password");
        }
        else{
            System.out.println("You entered wrong manager id and password");
        }
       

    }


    void reAssignTask() throws Exception {
       
        System.out.println("Enter The Employee gmail name");
        String empEmail = sc.nextLine();

        System.out.println("Enter The new Task");
        String newTask = sc.nextLine();

        String query = "UPDATE empinformation SET emp_percentages = 0 , task = '"+newTask+"' , status = 'Incomplete' WHERE email = '"+empEmail+"'";
        PreparedStatement ps = con.prepareStatement(query);

        int i = ps.executeUpdate();
        if(i > 0){
            System.out.println("Task Reassign successful");
        }else{
            System.out.println("Employee is not found");
        }
    }


        void showAllTask() throws Exception{
            String query = "SElect name,task,emp_percentages,status From empinformation";

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            System.out.println("Name | task | complete |status");
            while(rs.next()){
                System.out.println(rs.getString(1)+"  "+
            rs.getString(2) +"  "+ rs.getInt(3)+"  "+
            rs.getString(4));
            }
        }


        void changeManagerPassword() throws Exception{

            System.out.println("Enter The Manager gmail");
            String managerGmailForPasswordChange = sc.nextLine();
            String managerGmailFromDatabase = "";


            System.out.println("Enter The Manager Old Password");
            String managerOldPasswordForPasswordChange = sc.nextLine();
            String managerOldPasswordFromDatabase = "";

            String query = "Select email, manager_password FROM managerpassword";

            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                managerGmailFromDatabase = rs.getString(1);
                managerOldPasswordFromDatabase = rs.getString(2);
            }

            if(managerGmailForPasswordChange.equals(managerGmailFromDatabase) &&
             managerOldPasswordForPasswordChange.equals(managerOldPasswordFromDatabase)){

                System.out.println("Enter The New Password");
                String newPasswordForChange1 = sc.nextLine();

                System.out.println("Re Enter The New Password");
                String newPasswordForChange2 = sc.nextLine();

                if(newPasswordForChange1.equals(newPasswordForChange2)){

                    String SQLQuery = "Update managerpassword SET manager_password = ? WHERE email = ?;";

                    PreparedStatement pst = con.prepareStatement(SQLQuery);

                    pst.setString(1,newPasswordForChange1);
                    pst.setString(2,managerGmailForPasswordChange);

                    int i = pst.executeUpdate();

                    if(i > 0){
                        System.out.println("Password Update Sucessful");
                    }else{
                        System.out.println("Password Update Failed");

                    }
                }else{
                    System.out.println("Password Does not matched");
                }
            }else{
                System.out.println("Login failed");
            }





        }


    public static void main(String[] args) throws Exception{

        Manager m = new Manager();

        Scanner scan = new Scanner(System.in);

        System.out.println("--- Welcome ---");
        System.out.println("Login as ?");
        System.out.println("1. Manager");
        System.out.println("2. Employee");
        int choice = 0;

        try{
             choice= scan.nextInt();
        }catch(Exception w){
            System.out.println("Please Give the Integer Input");
            choice = scan.nextInt();
        }

        switch(choice){
                case 1: 

                        System.out.println("Enter The Gmail");
                        scan.nextLine();
                        String managerEmailForLogin = scan.nextLine();

                        System.out.println("Enter The Password");
                        String managerPasswordForLogin = scan.nextLine();
                        String managerPasswordFormDatabase = "";

                        String query = "Select manager_password From managerpassword;";
                        PreparedStatement ps = con.prepareStatement(query);

                        ResultSet rs = ps.executeQuery();

                        if(rs.next()){
                            managerPasswordFormDatabase = rs.getString(1);
                        }

                        if(managerEmailForLogin.equals("manager@gmail.com")  && managerPasswordForLogin.equals(managerPasswordFormDatabase)){
                            
                        System.out.println("==== Welcome Manager ====");
                        boolean exit = false;

                        while(!exit){
                            System.out.println("1. Add Employee");
                            System.out.println("2. Show All Employee");
                            System.out.println("3. delete Employee");
                            System.out.println("4. Re Assign Task");
                            System.out.println("5. Show All Task");
                            System.out.println("6. For Change Password");
                            System.out.println("7. For Exit");
                            int managerChoose ;


                            try {
                                managerChoose = scan.nextInt();
                            } catch (Exception e) {
                                System.out.println("you can only choose the interger");
                                managerChoose = scan.nextInt();
                            }


                            switch(managerChoose){
                                case 1 :
                                    System.out.println();
                                    m.addEmployee();
                                    System.out.println();
                                    break;

                                case 2:
                                    System.out.println();
                                    m.showAllEmployee();
                                    System.out.println();
                                    break;

                                case 3:
                                    System.out.println();
                                    m.deleteEmployee();
                                    System.out.println();
                                    break;

                                case 4:
                                    System.out.println();
                                    m.reAssignTask();
                                    System.out.println();
                                    break;

                                case 5:
                                    System.out.println();
                                    m.showAllTask();
                                    System.out.println();
                                    break;

                                case 6:
                                    System.out.println();
                                    m.changeManagerPassword();
                                    System.out.println();
                                    break;

                                case 7:
                                    System.out.println();
                                    System.out.println("Thakyou for the use ");
                                    exit = true;
                                    break;

                                default :
                                    System.out.println();
                                    System.out.println("Enter The Number Between the 1 and 6");
                                    System.out.println();
                                    break;

                                
                            }

                        }
                    }else{
                        System.out.println("Login Failed");
                    }
                    

                    

                    case 2:
                        m.empLogin();
                        
                        break;






        }
  
    }

            private int empId ;
            private String empName;
            private String empEmail;
            private String empPassword;
            private String empTask;
            private int empPercentages;
            private String empStatus;

            void empLogin() throws Exception{
          
            System.out.println("Enter The your gmail");
            String empEnterGmail = sc.nextLine();

            System.out.println("Enter The  Password");
            String empEnterPassword = sc.nextLine();
            
            String query = "SELECT * FROM empinformation WHERE email = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,empEnterGmail);
            ps.setString(2,empEnterPassword);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
            empId = rs.getInt(1);
            empName = rs.getString(2);
            empEmail = rs.getString(3);
            this.empPassword = rs.getString(4);
            empTask = rs.getString(5);
            empPercentages = rs.getInt(6);
            empStatus = rs.getString(7);

            boolean isExit = false;

            while(!isExit){

            

            System.out.println("1. View Details");
            System.out.println("2. view My Task");
            System.out.println("3. Change My Password");
            System.out.println("4. Update My Task");
            System.out.println("5. Exit");
            int empChoice;
            try {
                empChoice = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Enter Only the Integer");
                empChoice = sc.nextInt();
            }


            switch(empChoice){
                case 1:
                    viewDetails();
                    break;

                case 2: 
                    viewEmpTask();
                    break;

                case 3:
                    changePassword();
                    break;

                case 4:
                    updateMyTask();
                    break;

                case 5:
                    System.out.println("Thankyou for the use");
                    isExit = true;
                    break;


                default :
                    System.out.println("Enter The Number between 1 and 5");

                    break;


            }
            
        }
    }
    }


        void viewDetails(){
            System.out.println("Id: "+this.empId);
            System.out.println("Name: "+this.empName);
            System.out.println("Email: "+this.empEmail);
            System.out.println("Password: "+this.empPassword);
            System.out.println("Task complete Percentages: "+this.empPercentages);
            System.out.println("Status: "+this.empStatus);
        }


        void viewEmpTask(){
            System.out.println("==Task==");
            System.out.println("Task: "+this.empTask);
            System.out.println("Task complete percentages: "+this.empPercentages);
            System.out.println("Task Status: "+this.empStatus);
        }

        void changePassword() throws Exception{
        
            System.out.println("Enter The Gmail");
            sc.nextLine();
            String empPassGmail = sc.nextLine();

            
            if(empPassGmail.equals(empEmail)){
                System.out.println("Enter The Password");
                
                 String empPassPassword = sc.nextLine();

                if(empPassPassword.equals(empPassword)){

                    System.out.println("Enter The New Password");
                    String newPassPassword1 = sc.nextLine();
                    System.out.println("Re Enter The new password");
                    String newPassPassword2 = sc.nextLine();

                    if(newPassPassword1.equals(newPassPassword2)){
                        String query = "UPDATE empinformation SET password = ? WHERE email = ?;";
                        PreparedStatement ps = con.prepareStatement(query);
                        ps.setString(1,newPassPassword1);
                        ps.setString(2,empPassGmail);

                        int i = ps.executeUpdate();

                        if(i > 0){
                            System.out.println("Password Change Sucessfull");
                        }else{
                            System.out.println("Password doesn't matched");
                        }



                    }else{
                        System.out.println("Password does not matched");
                    }

                }else{
                    System.out.println("Wrong Password");
                }
            }else{
                System.out.println("Wrong Employee Gmail");
            }
        }
        

        void updateMyTask() throws Exception{
            System.out.println("Enter Your Progress percentages");
            int empProgressPercentages = sc.nextInt();
            int oldEmpProgressPercentage = 0;

            String query = "SELECT emp_percentages FROM empinformation WHERE email = ?;";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,this.empEmail);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                    oldEmpProgressPercentage = rs.getInt(1);
                }

               // System.out.println(oldEmpProgressPercentage);

            if(empProgressPercentages > 100 || empProgressPercentages < oldEmpProgressPercentage){
                System.out.println("Wrong Percentages Entered");

            }else{
                String querySql = "UPDATE empinformation Set emp_percentages = ? WHERE email = ?;";
                PreparedStatement psa = con.prepareStatement(querySql);

                psa.setInt(1,empProgressPercentages);
                psa.setString(2,this.empEmail);

                int i = psa.executeUpdate();

                if(i  > 0){
                    System.out.println("Your Task is Updated");
                }else{
                    System.out.println("Failed to update the task");
                }

                if(oldEmpProgressPercentage == 100)
                {
                    String sqlQuery = "Update empinformation Set Status = 'Complete' Where email = ?";
                    PreparedStatement psdg = con.prepareStatement(sqlQuery);
                    psdg.setString(1,this.empEmail);

                    int j = psdg.executeUpdate();

                    if(j > 0){
                        System.out.println("Status Update Sucessfully");
                    }else{
                        System.out.println("Status Did not updated");
                    }
                }

        }
    }

}