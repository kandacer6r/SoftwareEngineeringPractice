package edu.ithaca.dragon.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email) && isAmountValid(startingBalance)) {
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email +"or starting balance" +startingBalance+ " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * If the amount is non-negative and smaller than the balance, the balance will decrease
     * the given amount. If the num is negative then an exception is thrown or if the amount
     * trying to be taken out is
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if(!isAmountValid(amount)){//uses is valid to check if amount is valid or not
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if(amount>balance){//if the amount is bigger than the balance then throw exception
            throw new InsufficientFundsException("Cannot withdraw more than available in account");
        }
        balance -= amount;

    }
    public static boolean isAmountValid(double amount){
        if(amount<0)
            return false;
        String strAmount = Double.toString(amount);
        if(strAmount.indexOf('.')!=-1) {
            String decimalPlaces = strAmount.substring(strAmount.indexOf('.'));
            if(decimalPlaces.length()>3)//if the string from '.' has a length higher than three then there are other valid numbers beyond the two decimal places
                return false;
        }
        return true;
    }

    /**
     * Increases the balance by a valid amount provided by the user
     * @param amount checks if amount is valid and then deposits this amount into balance(if valid)
     */

    public void Deposit(double amount){
        if(!isAmountValid(amount)){//checks if the amount given is valid or not
            throw new IllegalArgumentException("Amount is invalid");
        }
        else
            balance+=amount;
    }

    /**
     * Transfers a valid amount (that isn't less than your balance) to another account
     * @param transferee the email account the user want to transfer the money to
     * @param amount is transferred if amount is valid and isn't greater than the balance
     */
    public void Transfer(String transferee,double amount)throws InsufficientFundsException{
        if(transferee.equals(email)){//the user cannot transfer money to themselves
            throw new IllegalArgumentException("Can't transfer money to yourself. Use deposit option");
        }
        if(!isEmailValid(transferee)){//checks the validity of the email given by the user
            throw new IllegalArgumentException("Invalid mailing account");
        }
        withdraw(amount);
    }

    public static boolean isEmailValid(String email){
        if (email.indexOf('@') == -1){ //Checks for '@'
            return false;
        }
        if (email.indexOf('.') == 0 || email.indexOf('.') == -1){ //checks for '.' but not at beginning
            return false;
        }
        if (email.indexOf('#') != -1){ //Checks for an illegal character
            return false;
        }
        for (int i=0; i<email.length(); i++){ //checks entire email for any occurrence of '..' which is illegal
            if(email.charAt(i) == '.'){
                if(email.charAt(i+1) == '.'){
                    return false;
                }
            }
        }
        String[] split = email.split("@");
        if (split.length > 2){
            return false;
        }
        String prefix  = split[0];
        if (prefix.charAt(prefix.length()-1) == '.'||prefix.charAt(prefix.length()-1) == '-'||prefix.charAt(prefix.length()-1) == '_'){ //checks that '.', '-', or '_' are not at end of prefix
            return false;
        }
        String backHalf = split[1];
        String[] newSplit = backHalf.split("\\.", 0);
        if (newSplit.length > 2){
            return false;
        }
        if (newSplit[0].length() < 1){
            return false;
        }
        if (split[1].length() < 2){
            return false;
        }
        if (!email.endsWith(".com"))
            return false;
        else {
            return true;
        }
    }
}
