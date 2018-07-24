import java.util.Scanner;

class InterBankingPty {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Please Login to continue");

        //Do while loop until the user inputs valid username "AND" password
        String custNameInput;
        char[] passwordInput;

        //Validating the authorized staff to use the main functionality
        do {
            System.out.println("Username: ");
            custNameInput = sc.next();

            System.out.println("Password: ");
            passwordInput = sc.next().toCharArray();

            //Calling the validating methods
            if (isUsernameValid(custNameInput) && isPasswordValid(passwordInput)){
                System.out.println("Sign in successful!");
            }else {
                System.out.println("\nUsername or Password is incorrect! Please re-enter.");
            }
        }while (!(isUsernameValid(custNameInput) && isPasswordValid(passwordInput)));

        //Reading from the file
        BankAccount.dataPersistency(false);

        System.out.println("Welcome to InterBanking Pty!");
        System.out.println("----------------------------");

        //Main program
        String in = menu(sc);
        while (in != "5"){

            switch (in){
                //Adding new bank account
                case "1":

                    //Creating the bank account from "enterAccountData" method
                    if(BankAccount.customerAccounts.size() < 10){
                        BankAccount.enterAccountData();
                    }else {
                        System.out.println("Cannot add more than 10 accounts!!!");
                    }
                    in = menu(sc);
                    break;

                //Displaying bank account information
                case "2":
                    if (BankAccount.customerAccounts.isEmpty()){
                        System.out.println("No bank accounts were added!");
                    }else{

                        //Calling display methods of each object on the array list
                        for(BankAccount i: BankAccount.customerAccounts){
                            System.out.println(i.displayAccount());
                        }
                    }
                    in = menu(sc);
                    break;

                //Transfer bank account balances between 2 accounts
                case "3":

                    //Checking if the list has at least 2 bank accounts in order to facilitate a transfer
                    if(BankAccount.customerAccounts.size() >= 2){
                        //Setting local variables
                        int accNumFrom = 0;
                        int accNumTo;
                        BankAccount accFrom = null;
                        BankAccount accTo = null;
                        double transferAmt = 0;
                        boolean flagValidate;

                        System.out.println("Input the account numbers for the transfer");
                        System.out.println("------------------------------------------");

                        //Receiving the account number of the "transfer from" account
                        //and checking if it is available
                        do{
                            flagValidate = false;
                            System.out.println("Account number(Transfer from): ");
                            if (sc.hasNextInt()){
                                accNumFrom = sc.nextInt();
                                for (BankAccount i: BankAccount.customerAccounts){
                                    if (i.getAccountNumber() == accNumFrom){
                                        accFrom = i;
                                        flagValidate = true;
                                        break;
                                    }
                                }
                                if (!flagValidate){
                                    System.out.println("Account does not exist!!!");
                                }
                            }else{
                                System.out.println("Invalid account number!");
                                sc.next();
                            }
                        }while (!flagValidate);

                        //Receiving the account number of the "transfer to" account
                        //and checking if it is available
                        do{
                            flagValidate = false;
                            System.out.println("Account number(Transfer to): ");
                            if (sc.hasNextInt()){
                                accNumTo = sc.nextInt();
                                for (BankAccount i: BankAccount.customerAccounts){
                                    if (i.getAccountNumber() == accNumTo && accNumTo != accNumFrom){
                                        accTo = i;
                                        flagValidate = true;
                                        break;
                                    }
                                }
                                if(accNumTo == accNumFrom){
                                    System.out.println("Cannot transfer amount to the same account!!!");
                                }else if (!flagValidate){
                                    System.out.println("Account does not exist!!!");
                                }
                            }else{
                                System.out.println("Invalid account number!");
                                sc.next();
                            }
                        }while (!flagValidate);

                        //Validating the amount to be transferred
                        do{
                            flagValidate = false;
                            System.out.println("Enter amount to be transferred: ");
                            if(sc.hasNextDouble()){
                                transferAmt = sc.nextDouble();
                                if(transferAmt >= 0){
                                    flagValidate = true;
                                    break;
                                }else {
                                    System.out.println("Transfer amount cannot be negative!!!");
                                }
                            }else {
                                System.out.println("Invalid input!!!");
                                sc.next();
                            }
                        }while (!flagValidate);

                        //Creating new transaction object to do the transfer
                        Transaction newTransaction = new Transaction(accFrom, accTo, transferAmt);

                        //Calling the transfer method
                        newTransaction.transfer();

                        //Checking if the user wants to undo or cancell the transfer
                        System.out.println("Do you want to undo the transfer?(Enter \"Yes\" to undo transfer)");
                        if(sc.next().trim().equalsIgnoreCase("yes")){
                            newTransaction.rollback();
                        }

                        in = menu(sc);
                        break;
                    }else{
                        System.out.println("There must be at least 2 bank accounts to do a transfer!!!");
                        in = menu(sc);
                        break;
                    }
                case "4":
                    if(BankAccount.customerAccounts.isEmpty()){
                        System.out.println("No accounts were added to produce report");
                    }else{
                        String title = "Bank accounts Report";
                        BankAccount.produceReport(title, BankAccount.customerAccounts);
                    }
                    in = menu(sc);
                    break;

                //Writing the bank account objects and terminating the program
                case "5":

                    //Writing to the file
                    BankAccount.dataPersistency(true);
                    System.exit(0);

                //If invalid selection is entered in the menu
                default:
                    System.out.println("Invalid selection!");
                    in = menu(sc);
            }
        }
    }

    //Checking if the username is valid
    private static boolean isUsernameValid(String name){
        boolean isValid;
        if(name.equals("Akram")){
            isValid = true;
        }else {
            isValid = false;
        }
        return isValid;
    }

    //Checking if the password is valid
    private static boolean isPasswordValid(char[] array){
        boolean isValid = true;
        char[] validPassword = {'1','2','3'};
        if (validPassword.length == array.length){
            for (int i = 0; i<array.length; i++) {
                if (array[i] != validPassword[i]){
                    isValid = false;
                    break;
                }
            }
        }else {
            isValid = false;
        }
        return isValid;
    }

    //Menu
    public static String menu(Scanner sc){
        System.out.println("\nEnter (1) to add a new bank account");
        System.out.println("Enter (2) to view bank account details");
        System.out.println("Enter (3) to transfer account balances");
        System.out.println("Enter (4) to generate report");
        System.out.println("Enter (5) to exit");
        String menuInput = sc.next().trim();
        return menuInput;
    }
}

