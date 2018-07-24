import java.io.Serializable;

public class SavingsAccount extends BankAccount implements Serializable {

    //Overloaded constructors
    public SavingsAccount(){

    }

    public SavingsAccount(String customerName, char[] password, int accountNumber, double accountBalance,
                          BankBranch homeBranch, double interestRate, String accType) {
        super(customerName, password, accountNumber, accountBalance, homeBranch, 3, accType);
    }

    public SavingsAccount(double interestRate){
        super.setInterestRate(interestRate);
    }

    //To view the savings account details
    public String displayAccount(){
        return super.displayAccount() +
                "\nAnnual interest: " + String.format("%.1f%%", super.getInterestRate()) + "\n";
    }

}
