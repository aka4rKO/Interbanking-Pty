import java.io.Serializable;

public class BankBranch implements Serializable{

    private int BSBNumber;
    private String address;
    private int postCode;

    //Constructor for the bank branch class
    public BankBranch(int BSBNumber, String address, int postCode) {
        this.BSBNumber = BSBNumber;
        this.address = address;
        this.postCode = postCode;
    }

    //Getters for the bank branch class
    public int getBSBNumber() {
        return BSBNumber;
    }

    public String getAddress() {
        return address;
    }

    public int getPostCode() {
        return postCode;
    }

    @Override
    public String toString() {
        return "\tBranch details: \n" +
                "\t\tBSB Number: " + BSBNumber +
                "\n\t\tAddress: " + address +
                "\n\t\tPostcode: " + postCode;
    }
}
