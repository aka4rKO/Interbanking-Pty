import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class BankAccount implements Serializable {

    //Declaring instance variables
    private int accountNumber;
    private double accountBalance;
    private String customerName;
    private char[] password;
    private BankBranch homeBranch;
    private String accType;
    private double interestRate;
    static ArrayList<BankAccount> customerAccounts = new ArrayList<BankAccount>();

    static Scanner sc = new Scanner(System.in);

    //Overloaded constructors
    public BankAccount(){}

    public BankAccount(String customerName, char[] password, int accountNumber, BankBranch homeBranch, String accType) {
        this(customerName, password, accountNumber,0,homeBranch, 0, accType);
    }

    public BankAccount(String customerName, char[] password, int accountNumber, BankBranch homeBranch,
                       double interestRate, String accType) {
        this(customerName, password, accountNumber,0,homeBranch,interestRate, accType);
    }

    public BankAccount(String customerName, char[] password, int accountNumber,
                       double accountBalance, BankBranch homeBranch, String accType) {
        this(customerName, password, accountNumber,accountBalance,homeBranch,0, accType);
    }

    public BankAccount(String customerName, char[] password, int accountNumber,
                       double accountBalance, BankBranch homeBranch, double interestRate, String accType) {
        this.customerName = customerName;
        this.password = password;
        this.accountNumber = accountNumber;
        this.accountBalance = accountBalance;
        this.homeBranch = homeBranch;
        this.interestRate = interestRate;
        this.accType = accType;
    }

    //Adding getter methods to the class
    public int getAccountNumber() {
        return accountNumber;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAccType() {
        return accType;
    }

    public char[] getPassword() {
        return password;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public BankBranch getHomeBranch() {
        return homeBranch;
    }

    //Adding setter methods to the class
    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    //To add new customer
    public static void enterAccountData(){

        System.out.println("Please enter the following details to create a new customer bank account");

        int BSBNumber;
        String address;
        int postCode;

        System.out.println("Enter branch details to continue");
        System.out.println("--------------------------------\n");

        //Validating BSB Number
        do{
            System.out.println("Enter BSB Number: ");
            if(sc.hasNextInt()){

                //Validating BSB Number
                BSBNumber = sc.nextInt();
                break;
            }else {
                System.out.println("BSB Number must be an integer!!!");
                sc.next();
            }
        }while(true);

        //Validating address
        do{
            System.out.println("Enter Branch location: ");
            if(!(sc.hasNextInt() || sc.hasNextDouble())){

                //Validating address
                address = sc.next();
                break;
            }else {
                System.out.println("Branch location must not be an integer!!!");
                sc.next();
            }
        }while(true);

        //Validating postcode
        do{
            System.out.println("Enter Postcode: ");
            if(sc.hasNextInt()){

                //Validating BSB Number
                postCode = sc.nextInt();
                break;
            }else {
                System.out.println("Postcode must be an integer!!!");
                sc.next();
            }
        }while(true);

        //Initializing the bank branch object
        BankBranch branch = new BankBranch(BSBNumber, address, postCode);

        int accNum;

        //Automatically generating account number between 1000 and 9999
        if(BankAccount.customerAccounts.isEmpty()){
            accNum = 1000;
        }else {

            //Getting the last account number which is stored
            int temp = BankAccount.customerAccounts.get(BankAccount.customerAccounts.size() - 1).getAccountNumber();
            accNum = temp + 1;
        }

        System.out.println("Enter the customer name: ");

        while ((sc.hasNextInt()) || (sc.hasNextDouble())){
            System.out.println("Please enter valid customer name!");
            System.out.println("Enter the customer name: ");
            sc.next();
        }

        //Customer name
        String custName = sc.next();

        System.out.println("Enter a password for the customer: ");

        //Customer password
        char[] password = sc.next().toCharArray();

        System.out.println("Enter account balance: ");
        double accBal;

        //The prompt continues until the user inputs an integer or double greater than 0
        while (true){
            if (sc.hasNextDouble()){
                double amount = sc.nextDouble();
                if (amount > 0){

                    if (amount > 99999){
                        System.out.println("Warning! Account number " + accNum +
                                " cannot exceed the federally insured amount.");
                        System.out.println("Enter account balance: ");
                    }else{
                        //Account balance
                        accBal = amount;
                        break;
                    }
                } else {
                    System.out.println("Amount cannot be 0 or less than 0!!!");
                    System.out.println("Enter account balance: ");
                }
            } else if(!(sc.hasNextDouble())){
                System.out.println("Invalid amount!");
                System.out.println("Enter account balance: ");
                sc.next();
            }
        }

        objectCreation(branch, accNum, accBal, custName, password, 0);
    }

    //To view the customers
    public String displayAccount(){
        return "Account number: " + accountNumber +
                "\nCustomer name: " + customerName +
                "\nCustomer password: " + Arrays.toString(password) +
                "\n" + homeBranch +
                "\nAccount balance: " + String.format("$%.2f",accountBalance);
    }

    //Method to write bank account objects to a file
    public static void dataPersistency(Boolean isRead){
        if(!isRead){
            FileInputStream fileInputStream = null;
            ObjectInputStream objectInputStream = null;
            try {
                fileInputStream = new FileInputStream(new File("Bank Account objects.txt"));
                objectInputStream = new ObjectInputStream(fileInputStream);
                while(fileInputStream.available() != 0){
                    Object obj = objectInputStream.readObject();
                    BankAccount.customerAccounts.add((BankAccount)obj);
                }
            }catch (FileNotFoundException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            }catch (ClassNotFoundException e) {
                e.printStackTrace();
            }finally {
                try{
                    fileInputStream.close();
                    objectInputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }else {
            FileOutputStream fileOutputStream = null;
            ObjectOutputStream objectOutputStream = null;

            try {
                fileOutputStream = new FileOutputStream(new File("Bank Account objects.txt"));
                objectOutputStream = new ObjectOutputStream(fileOutputStream);
                for(BankAccount i: customerAccounts){
                    objectOutputStream.writeObject(i);
                }
            }catch (FileNotFoundException e){
                System.out.println("File could not be found!!!");
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                try{
                    fileOutputStream.close();
                    objectOutputStream.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    //Object Creation method
    public static void objectCreation(BankBranch branch,int accNum,double accBal,String custName,
                                       char[] password, double interestRate){
        double monthlyFee;
        int checksAllowedPerMonth;

        //Calling the submenu
        String selection = subMenu();
        while(true){
            switch (selection){
                case "1":

                    //Creating a "savingsAccount" account object and saving it to the arraylist
                    SavingsAccount savingsAccount = new SavingsAccount(custName, password,
                            accNum, accBal, branch, interestRate, "SA");
                    if(customerAccounts.size() < 10){
                        customerAccounts.add(savingsAccount);
                        System.out.println("Savings Account added successfully...");
                    }else {
                        System.out.println("Cannot create more than 10 accounts!!!");
                    }
                    break;
                case "2":

                    //Getting input for monthly fee
                    do{
                        System.out.println("Enter monthly fee: ");
                        if(sc.hasNextDouble()){
                            monthlyFee = sc.nextDouble();
                            if(monthlyFee >= 0){
                                break;
                            }else {
                                System.out.println("Monthly fee cannot be negative!");
                            }
                        }else {
                            System.out.println("Invalid input!");
                            sc.next();
                        }
                    }while(true);

                    //Getting input for checks allowed per month
                    do{
                        System.out.println("Enter number of checks allowed per month: ");
                        if(sc.hasNextInt()){
                            checksAllowedPerMonth = sc.nextInt();
                            if(checksAllowedPerMonth >= 0){
                                break;
                            }else {
                                System.out.println("Checks allowed per month cannot be negative!");
                            }
                        }else {
                            System.out.println("Invalid input!");
                            sc.next();
                        }
                    }while(true);

                    //Creating a "CheckingAccount" account object and saving it to the arraylist
                    CheckingAccount checkingAccount = new CheckingAccount(custName, password, accNum, accBal, branch,
                            interestRate, monthlyFee, checksAllowedPerMonth, "CA");
                    if(customerAccounts.size() < 10){
                        customerAccounts.add(checkingAccount);
                        System.out.println("Checking Account added successfully...");
                    }else {
                        System.out.println("Cannot create more than 10 accounts!!!");
                    }
                    break;
                case "3":

                    //Getting input for monthly fee
                    do{
                        System.out.println("Enter monthly fee: ");
                        if(sc.hasNextDouble()){
                            monthlyFee = sc.nextDouble();
                            if(monthlyFee >= 0){
                                break;
                            }else {
                                System.out.println("Monthly fee cannot be negative!");
                            }
                        }else {
                            System.out.println("Invalid input!");
                            sc.next();
                        }
                    }while(true);

                    //Getting input for checks allowed per month
                    do{
                        System.out.println("Enter number of checks allowed per month: ");
                        if(sc.hasNextInt()){
                            checksAllowedPerMonth = sc.nextInt();
                            if(checksAllowedPerMonth >= 0){
                                break;
                            }else {
                                System.out.println("Checks allowed per month cannot be negative!");
                            }
                        }else {
                            System.out.println("Invalid input!");
                            sc.next();
                        }
                    }while(true);

                    //Creating a "CheckingAccountWithInterest" account object and saving it to the arraylist
                    BankAccount checkingAccountWithInterest = new CheckingAccountWithInterest
                            (custName, password, accNum, accBal, branch, monthlyFee,
                                    checksAllowedPerMonth, "CAWI");
                    if(customerAccounts.size() < 10){
                        customerAccounts.add(checkingAccountWithInterest);
                        System.out.println("Checking Account with Interest added successfully...");
                    }else {
                        System.out.println("Cannot create more than 10 accounts!!!");
                    }
                    break;
                default:
                    System.out.println("Invalid selection!");
                    selection = subMenu();
            }
            break;
        }
    }

    //Method to produce report after formatting properly
    public static void produceReport(String title, List<BankAccount> list){
        System.out.println("---------------Start of Report---------------");
        System.out.println("---------------------------------------------\n");
        System.out.println(title);
        System.out.println("--------------------\n");
        System.out.println("Account type abbreviations");
        System.out.println("SA = Savings Account\nCA = Checking Account\nCAWI = Checking Account with Interest\n\n");

        System.out.printf("%12s | %14s | %15s | %15s | %15s | %13s %n","Account Type", "Account Number",
                "Customer Name","Account Balance", "Branch", "Interest rate");
        System.out.println("-------------------------------------------------------------" +
                "--------------------------------------");
        for(BankAccount i: list){
            System.out.printf("%12s | %14d | %15s | %15s | %15s |         %.2f%% %n",i.getAccType(),
                    i.getAccountNumber(), i.getCustomerName()
            , String.format("$%.2f",i.getAccountBalance()), i.getHomeBranch().getAddress(), i.getInterestRate());
        }

        System.out.println("\n\n---------------End of Report---------------");
        System.out.println("-------------------------------------------");
    }

    //Submenu
    public static String subMenu(){
        System.out.println("\nEnter (1) to create Savings Account");
        System.out.println("Enter (2) to create Checking Account");
        System.out.println("Enter (3) to create Checking Account with Interest");
        String option = sc.next().trim();
        return option;
    }
}
