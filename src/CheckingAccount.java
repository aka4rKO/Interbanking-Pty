import java.io.Serializable;

public class CheckingAccount extends BankAccount implements Serializable {

    private double monthlyFee;
    private int checksAllowedPerMonth;

    //Overloaded constructors
    public CheckingAccount(){

    }

    public CheckingAccount(String customerName, char[] password, int accountNumber,
                           double accountBalance, BankBranch homeBranch,
                           double interestRate, double monthlyFee, int checksAllowedPerMonth, String accType) {
        super(customerName, password, accountNumber, accountBalance, homeBranch, interestRate, accType);
        this.monthlyFee = monthlyFee;
        this.checksAllowedPerMonth = checksAllowedPerMonth;
    }

    //To view the checking account details
    public String displayAccount(){
        String details = super.displayAccount();
        return  details + "\nMonthly fee: " + String.format("$%.2f",monthlyFee) +
                "\nChecks Allowed Per Month: " + checksAllowedPerMonth + "\n";
    }

}
