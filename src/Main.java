import java.util.Scanner;



class Main{
    Scanner sc = new Scanner(System.in);

    boolean isLogin(){
        System.out.println("Enter The gmail");
        String gmail = sc.nextLine();

        System.out.println("Enter The password");
        String password = sc.nextLine();

        if(gmail.equals("chetan@gmail.com")){
            if(password.equals("chetan123")){
                return false;
            }
        }else{
            System.out.println("Incorrect Gmail");
        }
        return true;
    }
    public static void main(String[] args){
        Main n = new Main();

        while(n.isLogin()){
            System.out.println("hii");
            
        }

        System.out.println("wfigwifbiwegfwu9");
    }
}