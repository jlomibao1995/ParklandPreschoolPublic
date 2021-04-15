package services;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.persistence.NoResultException;
import persistence.AccountDBBroker;
import problemdomain.Account;

/**
 * Accesses preschool database and manages the Account table
 *
 * @author Jean Lomibao, Ethan Foster
 */
public class AccountService {

    String PROVINCE = "AB";
    String CITY = "Calgary";

    /**
     * Retrieves the account with the corresponding id
     *
     * @param accountId id associated with the account
     * @return Account object with account information
     * @throws java.lang.Exception thrown when a database error occurs
     */
    public Account getAccount(String accountId) throws Exception {
        AccountDBBroker accountDB = new AccountDBBroker();
        Account account = accountDB.getAccount(Integer.parseInt(accountId));
        return account;
    }

    /**
     * Checks the user input and creates an inactive guardian account that is
     * saved to the database. If the account is saved successfully an email is
     * sent to the user to activate their account.
     *
     * @param firstName User's first name
     * @param lastName User's last name
     * @param address User's address
     * @param postalCode User's postal code
     * @param email User's email
     * @param homePhone User's home phone
     * @param workPhone User's work phone
     * @param cellPhone User's cell phone
     * @param password User's password
     * @param confirmPassword Used to make sure passwords match
     * @param path path to find the email template
     * @param url url for activating account
     * @throws Exception thrown when a database error occurs or input is invalid
     */
    public void registerNewAccount(String firstName, String lastName, String address,
            String postalCode, String email, String homePhone, String workPhone,
            String cellPhone, String password, String confirmPassword, String path,
            String url) throws Exception {
        //check input for special characters and formatting
        validateInputs(firstName, lastName,address, postalCode, homePhone, 
                cellPhone, workPhone, password);

        //check if passwords match
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords don't match");
        }
        
        //salt and hash password
        String salt = PasswordUtil.getSalt();
        String hashPassword = PasswordUtil.hashAndSaltPassword(password, salt);

        //create account object
        AccountDBBroker accountDB = new AccountDBBroker();
        Account account = new Account(0, email, hashPassword, firstName, lastName,
                address, CITY, PROVINCE, postalCode.toUpperCase(), homePhone, 'G', false, salt);

        account.setCellphoneNumber(cellPhone);
        account.setWorkPhoneNumber(workPhone);
        account.setCreationDate(new Date());

        //set uuid for activation link
        String uuid = UUID.randomUUID().toString();
        account.setActivateAccountUuid(uuid);

        //save account info to database
        //send error message if email already exists
        try {
            accountDB.insertAccount(account);
        } catch (IllegalStateException ex) {
            throw new IllegalArgumentException("A user with that email already exists");
        }

        //if account is saved to the database successfully send the activation email
        //set up email
        String subject = "Parkland Preschool Activate Account";
        String template = path + "/emailtemplates/activate.html";
        String link = url + "?uuid=" + uuid;

        HashMap<String, String> tags = new HashMap<>();
        tags.put("firstname", account.getAccountFirstName());
        tags.put("lastname", account.getAccountLastName());
        tags.put("link", link);

        //send email
        GmailService.sendMail(email, subject, template, tags);
    }

    /**
     * Creates an account by the specified account type, used for accounts
     * created by the admin
     *
     * @param firstName User's first name
     * @param lastName User's last name
     * @param address User's address
     * @param postalCode User's postal code
     * @param email User's email
     * @param homePhone User's home phone
     * @param workPhone User's work phone
     * @param cellPhone User's cell phone
     * @param password User's password
     * @param confirmPassword Used to make sure passwords match
     * @param accountType account type of the user
     * @throws Exception thrown when a database error occurs or invalid
     * parameters are given
     */
    public void createAccount(String firstName, String lastName, String address,
            String postalCode, String email, String homePhone, String cellPhone,
            String workPhone, String password, String confirmPassword, String accountType) throws Exception {
        //check input for special characters and formatting
        validateInputs(firstName, lastName, address, postalCode, homePhone, cellPhone,
                workPhone, password);

        //check if passwords match
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords don't match");
        }
        
        //salt and hash password
        String salt = PasswordUtil.getSalt();
        String hashPassword = PasswordUtil.hashAndSaltPassword(password, salt);

        //create account object
        AccountDBBroker accountDB = new AccountDBBroker();
        Account account = new Account(0, email, hashPassword, firstName, lastName,
                address, CITY, PROVINCE, postalCode.toUpperCase(), homePhone, 
                accountType.charAt(0), true, salt);

        account.setCellphoneNumber(cellPhone);
        account.setWorkPhoneNumber(workPhone);
        account.setCreationDate(new Date());

        //save account info to database
        //send error message if email already exists
        try {
            accountDB.insertAccount(account);
        } catch (IllegalStateException ex) {
            throw new IllegalArgumentException("A user with that email already exists");
        }
    }

    /**
     * Checks the format and pattern for account information
     *
     * @param firstName first name to be checked
     * @param lastName last name to be checked
     * @param postalCode postal code to be checked
     * @param homePhone phone number to be checked
     * @param cellPhone phone number to be checked
     * @param workPhone phone number to be checked
     * @param password password to be checked
     */
    private void validateInputs(String firstName, String lastName, String address, String postalCode,
            String homePhone, String cellPhone, String workPhone, String password) {
        inputValidator(firstName, "[a-zA-Z-\\s]+", "Your name can't contain special characters");
        inputValidator(lastName, "[a-zA-Z-\\s]+", "Your name can't contain special characters");
        inputValidator(address, "[a-zA-z0-9-\\.\\s]+", "Your address can't contain special characters");
        inputValidator(postalCode, "[A-Za-z][0-9][A-Za-z] [0-9][A-Za-z][0-9]", "Your postal code doesn't match the required format");
        inputValidator(homePhone, "^\\(\\d{3}\\)\\s\\d{3}-\\d{4}", "Your phone number doesn't match the required format");
        inputValidator(cellPhone, "^\\(\\d{3}\\)\\s\\d{3}-\\d{4}", "Your phone number doesn't match the required format");
        inputValidator(workPhone, "^\\(\\d{3}\\)\\s\\d{3}-\\d{4}", "Your phone number doesn't match the required format");
        
        if (password != null){
            inputValidator(password, "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", "Your password doesn't match the required format");
        }        
    }

    /**
     * Checks if user info is valid using a regular expression
     *
     * @param input user input to check
     * @param regExp regular expression to check the input against
     * @param message message to display if input is not valid
     */
    public void inputValidator(String input, String regExp, String message) {
        if (!input.matches(regExp)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Retrieves the right account object if the email and password matches
     *
     * @param email email user entered
     * @param password password user entered
     * @return Account object if the password matches the email
     * @throws Exception thrown when a database error occurs
     */
    public Account login(String email, String password) throws Exception {
        AccountDBBroker accountDB = new AccountDBBroker();

        //if the entries are null return null right away
        if (email.equals("") || password.equals("") || email == null || password == null) {
            return null;
        }

        email = email.replaceAll("[/\\<>=?;#%^()\'\"]", "");
        password = password.replaceAll("[/\\<>=?;#%^()\'\"]", "");

        //grab the account object from the database
        Account account = accountDB.getAccountByEmail(email);
        String salt = account.getSalt();
        String hashAndSalt = PasswordUtil.hashAndSaltPassword(password, salt);

        //check if the password matches the email then return the account object if it does
        //otherwise return null
        if (account.getPassword().equals(hashAndSalt) && account.getAccountStatus()) {
            //update last login date
            account.setLastLoginDate(new Date());
            accountDB.updateAccount(account);
            return account;
        }
        return null;
    }

    /**
     * Sends link for resetting the user's password
     *
     * @param email email user entered
     * @param url link the user will be directed to change their password
     * @param path path to located email templates
     * @throws java.lang.Exception thrown when a database error occurs
     */
    public void sendResetPasswordLink(String email, String url, String path) throws Exception {
        //grab the account object and give it a reset uuid
        AccountDBBroker accountDB = new AccountDBBroker();
        Account account = null;
        try {
            account = accountDB.getAccountByEmail(email);
        } catch (NoResultException ex){
            throw new IllegalArgumentException("Email is not linked to existing account");
        }
        
        String uuid = UUID.randomUUID().toString();
        account.setResetPasswordUuid(uuid);
        accountDB.updateAccount(account);

        //prepare the email templates 
        String subject = "Account password reset";
        String template = path + "/emailtemplates/reset.html";
        String link = url + "?uuid=" + uuid;

        //create a map for information place on the email
        HashMap<String, String> tags = new HashMap<>();
        tags.put("firstname", account.getAccountFirstName());
        tags.put("lastname", account.getAccountLastName());
        tags.put("link", link);

        //send the reset link
        GmailService.sendMail(email, subject, template, tags);
    }

    /**
     * Resets the password using the uuid sent by email
     *
     * @param uuid unique id to identify the account
     * @param newPassword password to change to
     * @param confirmPassword same password entered by the user
     * @throws Exception thrown when a database error occurs
     */
    public void resetPassword(String uuid, String newPassword, String confirmPassword) throws Exception {
        //get the right account with the uuid
        AccountDBBroker accountDB = new AccountDBBroker();
        Account account = accountDB.getAccountByResetUUID(uuid);
        
        inputValidator(newPassword, "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", "Your password doesn't match the required format");

        //continue only if both passwords match
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords don't match");
        }
        
        //salt and hash password
        String salt = account.getSalt();
        String hashPassword = PasswordUtil.hashAndSaltPassword(newPassword, salt);

        //change the password and remove the reset uuid
        //save changes to the database
        account.setPassword(hashPassword);
        account.setResetPasswordUuid(null);
        accountDB.updateAccount(account);
    }

    /**
     * Retrieve all accounts in the persistence layer
     *
     * @return a list of account objects
     * @throws Exception thrown when a database error occurs
     */
    public List<Account> getAllAccounts() throws Exception {
        AccountDBBroker accountBroker = new AccountDBBroker();
        return accountBroker.getAllAccounts();
    }

    /**
     * Update an account
     *
     * @param account to update
     * @return true if success
     * @throws Exception if database can't commit
     */
    public boolean updateAccount(Account account) throws Exception {
        return new AccountDBBroker().updateAccount(account);
    }

    /**
     * Account is update with the provided parameters
     *
     * @param accountId ID of the account being updated
     * @param firstName first name of the account
     * @param lastName last name of the account
     * @param address address of the account
     * @param postalCode postal code of the account
     * @param email email of the account
     * @param homePhone home phone of the account
     * @param workPhone work phone of the account
     * @param cellPhone cell phone of the account
     * @param password password of the account
     * @param confirmPassword to confirm the password
     * @param accountType type of the account
     * @throws Exception thrown when a database error occurs or invalid
     * parameters are given
     */
    public void updateAccount(String accountId, String firstName, String lastName, String address,
            String postalCode, String email, String homePhone, String workPhone,
            String cellPhone, String password, String confirmPassword, String accountType) throws Exception {
        //check input for special characters and formatting
        if (password != null && password.equals("")){
            password = null;
        }
        validateInputs(firstName, lastName, address, postalCode, homePhone, cellPhone,
                workPhone, password);

        //check if passwords match
        if (password != null && !password.equals("") && !password.equals(confirmPassword)) {
            throw new IllegalArgumentException("Passwords don't match");
        }

        //get account associated with the user
        AccountDBBroker accountDB = new AccountDBBroker();
        Account account = accountDB.getAccount(Integer.parseInt(accountId));

        if (accountType != null && !accountType.equals("") && account.getAccountType() == 'G'
                && account.getAccountType() != accountType.charAt(0)) {
            throw new IllegalArgumentException("Guardian account can't be changed to a staff account");
        }

        //change account information 
        account.setAccountFirstName(firstName);
        account.setAccountLastName(lastName);
        account.setAccountAddress(address);
        account.setAccountPostalCode(postalCode);
        account.setEmail(email);
        account.setAccountPhoneNumber(homePhone);
        account.setWorkPhoneNumber(workPhone);
        account.setCellphoneNumber(cellPhone);

        if (accountType != null && !accountType.equals("") ) {
            account.setAccountType(accountType.charAt(0));
        }

        //change password if user entered on confirm password form
        if (password != null && !password.equals("")) {
            //salt and hash password
        String salt = account.getSalt();
        String hashPassword = PasswordUtil.hashAndSaltPassword(password, salt);
            account.setPassword(hashPassword);
        }

        //save changes to database
        //send error message if email already exists
        try {
            accountDB.updateAccount(account);
        } catch (IllegalStateException ex) {
            throw new IllegalArgumentException("A user with that email already exists");
        }
    }

    /**
     * Delete an account
     *
     * @param account to delete
     * @throws Exception if the database can't delete
     */
    public void removeAccount(Account account) throws Exception {
        new AccountDBBroker().deleteAccount(account);
    }

    /**
     * Activates the account status to true and removes the activate uuid
     *
     * @param uuid unique id to find account that needs to be activated
     * @throws Exception thrown when a database error occurs
     */
    public void activateUser(String uuid) throws Exception {
        AccountDBBroker accountDB = new AccountDBBroker();
        Account account = accountDB.getAccountByActivateUUID(uuid);

        account.setAccountStatus(true);
        account.setActivateAccountUuid("");
        accountDB.updateAccount(account);
    }

    /**
     * Goes through all accounts and removes account entry that doesn't match
     * key
     *
     * @param key account name to look for
     * @return list of Account objects
     * @throws java.lang.Exception thrown when a database error occurs
     */
    public List<Account> getAccounts(String key) throws Exception {
        List<Account> allAccounts = getAllAccounts();

        //if the key is not null or an empty string look for a match in the list of accounts
        if (key != null && !key.equals("")) {
            Iterator<Account> iter = allAccounts.iterator();
            while (iter.hasNext()) {
                Account current = iter.next();
                String name = current.getAccountFirstName().toLowerCase() + " " + current.getAccountLastName().toLowerCase();

                if (!name.contains(key)) {
                    iter.remove();
                }
            }
        }

        return allAccounts;
    }

    /**
     * Retrieves a list of accounts according to the period provided
     *
     * @param period period that the account has been new
     * @param active true if were retrieving active accounts
     * @return List of Account objects
     * @throws java.lang.Exception thrown when a database error occurs
     */
    public List<Account> getAccountsByTimePeriod(String period, boolean active) throws Exception {
        //grab list of all accounts from the database
        AccountDBBroker accountDB = new AccountDBBroker();
        List<Account> accounts;

        if (active) {
            accounts = accountDB.getAccountsByStatus(active);
        } else {
            accounts = accountDB.getAllAccounts();
        }

        LocalDate dateToCompare; //holds the date to be compared agains the creation date

        //determine the period and get the date to compare according to that
        if (period.equals("week")) {
            dateToCompare = LocalDate.now().minusDays(7);
        } else {
            dateToCompare = LocalDate.now().minusMonths(1);
        }

        //grab the dates that match 
        Iterator<Account> iter = accounts.iterator();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        while (iter.hasNext()) {
            String date;

            try {
                if (active) {
                    date = format.format(iter.next().getLastLoginDate());
                } else {
                    date = format.format(iter.next().getCreationDate());
                }

                LocalDate accountDate = LocalDate.parse(date);

                if (dateToCompare.isAfter(accountDate)) {
                    iter.remove();
                }
            } catch (NullPointerException ex) {
                iter.remove();
            }
        }

        return accounts;
    }
}
