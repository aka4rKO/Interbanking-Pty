import java.util.Scanner;

public class Transaction {

    //Declaring instance variables
    private BankAccount transferFromAcc;
    private BankAccount transferToAcc;
    private double transferAmount;
    Scanner sc = new Scanner(System.in);

    //Variable to check if all the conditions are met to complete the transfer
    boolean isTransferred = false;

    public Transaction(BankAccount transferFromAcc, BankAccount transferToAcc, double transferAmount) {
        this.transferFromAcc = transferFromAcc;
        this.transferToAcc = transferToAcc;
        this.transferAmount = transferAmount;
    }

    //Adding getter methods
    public BankAccount getTransferFromAcc() {
        return transferFromAcc;
    }

    public BankAccount getTransferToAcc() {
        return transferToAcc;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    //To transfer account balances
    public void transfer(){
        System.out.println("\nTransfer from account number " + this.transferFromAcc.getAccountNumber() +
                " to account number " + this.transferToAcc.getAccountNumber());

        //Checking if the transfer from account falls below $10 by the transfer
        if(this.transferFromAcc.getAccountBalance() - this.transferAmount < 0){
            System.out.println("The account number " + this.transferFromAcc.getAccountNumber() +
                    " cannot have a negative balance!!!");

        //Checking if the transfer from account falls below $10 but the transfer to account is less than the
        //federally insured amount
        }else if(this.transferFromAcc.getAccountBalance() - this.transferAmount < 10 &&
                !(this.transferToAcc.getAccountBalance() + this.transferAmount > 100000)){
            System.out.println("Warning! Balance in account number " + this.transferFromAcc.getAccountNumber() +
                    " falling below $10.00");
            isTransferred = true;

        //Checking if the transfer from account falls above $10 but the transfer to account is less than the
        //federally insured amount
        }else if(this.transferFromAcc.getAccountBalance() - this.transferAmount >= 10 &&
                !(this.transferToAcc.getAccountBalance() + this.transferAmount > 100000)){
            isTransferred = true;

        //If the transfer to account exceeds the federally isured amount
        }else{
            System.out.println("Warning! Account number " + this.transferToAcc.getAccountNumber() +
                    " has exceeded the federally insured amount.");
        }

        //If the above conditions are met the transfer is executed
        if(isTransferred){
            this.transferFromAcc.setAccountBalance(this.transferFromAcc.getAccountBalance()-this.transferAmount);
            this.transferToAcc.setAccountBalance(this.transferToAcc.getAccountBalance()+this.transferAmount);
            System.out.println("Transfer successful!");
        }else {
            System.out.println("Transfer unsuccessful!!!");
        }
    }

    //Method to revert the transfer
    public void rollback(){
        if(isTransferred){
            this.transferFromAcc.setAccountBalance(this.transferFromAcc.getAccountBalance()+this.transferAmount);
            this.transferToAcc.setAccountBalance(this.transferToAcc.getAccountBalance()-this.transferAmount);
            System.out.println("Transfer reverted successfully!");
        }else{
            System.out.println("There was no transfer to be reverted!!!");
        }
    }

}
