import java.io.Serializable;

public class CheckingAccountWithInterest extends CheckingAccount implements Serializable{

    //Overloaded constructors
    public CheckingAccountWithInterest() {
    }

    public CheckingAccountWithInterest(String customerName, char[] password,int accountNumber,
                                       double accountBalance, BankBranch homeBranch
                                        , double monthlyFee, int checksAllowedPerMonth, String accType) {
        super(customerName, password, accountNumber, accountBalance, homeBranch, 0.02,
                monthlyFee, checksAllowedPerMonth, accType);
    }

    //To view the checking account with interest details
    public String displayAccount(){
        String details = super.displayAccount();
        return  details + "Annual interest: " + String.format("%.2f%%", getInterestRate()) + "\n";
    }

}
