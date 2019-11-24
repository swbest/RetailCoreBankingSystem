/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tellerterminalclient;

import ejb.session.stateless.AtmCardSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.DepositAccountSessionBeanRemote;
import entity.AtmCard;
import entity.Customer;
import entity.DepositAccount;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import util.enumeration.DepositAccountType;
import util.exception.AtmCardNumberExistException;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerUsernameExistException;
import util.exception.DepositAccountExistException;
import util.exception.InvalidLoginException;
import util.exception.UnknownPersistenceException;

/**
 *
 * @author dtjldamien
 */
public class MainApp {

    private CustomerSessionBeanRemote customerSessionBeanRemote;
    private DepositAccountSessionBeanRemote depositAccountSessionBeanRemote;
    private AtmCardSessionBeanRemote atmCardSessionBeanRemote;

    public MainApp() {
    }

    MainApp(CustomerSessionBeanRemote customerSessionBeanRemote, DepositAccountSessionBeanRemote depositAccountSessionBeanRemote, AtmCardSessionBeanRemote atmCardSessionBeanRemote) {
        this();
        this.customerSessionBeanRemote = customerSessionBeanRemote;
        this.depositAccountSessionBeanRemote = depositAccountSessionBeanRemote;
        this.atmCardSessionBeanRemote = atmCardSessionBeanRemote;
    }

    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to Retail Core Banking System - Teller Terminal ***\n");
            System.out.println("1: Login");
            System.out.println("2: Exit\n");
            response = 0;
            while (response < 1 || response > 2) {
                System.out.print("> ");
                response = scanner.nextInt();
                if (response == 1) {
                    try {
                        doLogin();
                        System.out.println("Login successful!\n");
                        menuMain();
                    } catch (InvalidLoginException ex) {
                        System.out.println("Invalid login credentials " + ex.getMessage());
                    }
                } else if (response == 2) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            if (response == 2) {
                break;
            }
        }
    }

    private void doLogin() throws InvalidLoginException {
        Scanner scanner = new Scanner(System.in);
        String username;
        String password = "";
        System.out.println("*** RCBS - Teller Terminal : Login ***\n");
        System.out.print("Enter username> ");
        username = scanner.nextLine().trim();
        System.out.print("Enter password> ");
        password = scanner.nextLine().trim();

        if (username.length() > 0 && password.length() > 0) {
        } else {
            throw new InvalidLoginException("Missing login credential!");
        }
    }

    private void menuMain() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        while (true) {
            System.out.println("*** Welcome to RCBS - Teller Terminal ***\n");
            System.out.println("You are logged in\n");
            System.out.println("1: Create Customer");
            System.out.println("2: Open Deposit Account");
            System.out.println("3: Issue ATM Card");
            System.out.println("4: Logout\n");
            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    doCreateCustomer();
                } else if (response == 2) {
                    doOpenDepositAccount();
                } else if (response == 3) {
                    doIssueAtmCard();
                } else if (response == 4) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 4) {
                break;
            }
        }
    }

    private void doCreateCustomer() {
        try {
            Scanner scanner = new Scanner(System.in);
            Customer newCustomer = new Customer();
            System.out.println("*** Retail Core Banking System :: Create Customer ***\n");
            System.out.print("Enter first name> ");
            newCustomer.setFirstName(scanner.nextLine().trim());
            System.out.print("Enter last name> ");
            newCustomer.setLastName(scanner.nextLine().trim());
            System.out.print("Enter identification number> ");
            newCustomer.setIdentificationNumber(scanner.nextLine().trim());
            System.out.print("Enter contact number> ");
            newCustomer.setContactNumber(scanner.nextLine().trim());
            System.out.print("Enter address line 1> ");
            newCustomer.setAddressLine1(scanner.nextLine().trim());
            System.out.print("Enter address line 2> ");
            newCustomer.setAddressLine2(scanner.nextLine().trim());
            System.out.print("Enter postal code> ");
            newCustomer.setPostalCode(scanner.nextLine().trim());
            newCustomer = customerSessionBeanRemote.createNewCustomer(newCustomer);
        } catch (Exception ex) {
            System.out.println("Error occured when creating the new customer " + ex.getMessage());
        }
    }

    private void doOpenDepositAccount() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("*** Retail Core Banking System :: Open new deposit account ***\n");
            System.out.println("Enter Customer ID> ");
            Long customerId = scanner.nextLong();
            Customer customer = customerSessionBeanRemote.retrieveCustomerByCustomerId(customerId);
            System.out.println("Open deposit account for " + customer.getFullName() + "\n");
            DepositAccount newDepositAccount = new DepositAccount();
            newDepositAccount.setEnabled(true);
            scanner.nextLine();
            System.out.println("Enter account number> ");
            newDepositAccount.setAccountNumber(scanner.nextLine().trim());
            System.out.println("Select account type (1: Savings, 2: Current)> ");
            Integer accountTypeInt = scanner.nextInt();
            while (true) {
                if (accountTypeInt == 1) {
                    newDepositAccount.setAccountType(DepositAccountType.values()[accountTypeInt - 1]);
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            System.out.println("Enter initial deposit amount> $");
            BigDecimal entered = scanner.nextBigDecimal();
            newDepositAccount.setAvailableBalance(entered);
            System.out.println("You entered " + entered);
            newDepositAccount.setLedgerBalance(newDepositAccount.getAvailableBalance());
            newDepositAccount = depositAccountSessionBeanRemote.openNewDepositAccount(newDepositAccount, customerId);
            System.out.println("Successfully created new deposit account " + newDepositAccount.getAccountNumber() + "!\n");
        } catch (CustomerNotFoundException ex) {
            System.out.println(ex.getMessage() + "!\n");
        } catch (UnknownPersistenceException ex) {
            System.out.println(ex.getMessage() + "!\n");
        } catch (DepositAccountExistException ex) {
            System.out.println("Deposit Account already exists! " + ex.getMessage() + "!\n");
        }
    }

    private void doIssueAtmCard() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("*** Retail Core Banking System :: Issue new ATM Card ***\n");
            System.out.println("Enter Customer Id> ");
            Long customerId = scanner.nextLong();
            scanner.nextLine(); // nextlong after nextline
            Customer customer = customerSessionBeanRemote.retrieveCustomerByCustomerId(customerId);
            if (customer.getAtmCard() != null) {
                System.out.println("Do you want to replace the existing ATM Card? (Enter 'Y' to remove)> ");
                String input = scanner.nextLine().trim();
                if (input.equals("Y")) {
                    atmCardSessionBeanRemote.deleteAtmCard(customer.getAtmCard().getAtmCardId());
                    System.out.println("Current ATM Card not removed");
                } else {
                    System.out.println("Current ATM Card not removed");
                    return;
                }
            }

            AtmCard newAtmCard = new AtmCard();
            System.out.println("Enter card number> ");
            newAtmCard.setCardNumber(scanner.nextLine().trim());
            System.out.println("Enter name on ATM Card> ");
            newAtmCard.setNameOnCard(scanner.nextLine().trim());
            System.out.println("Enter PIN> ");
            newAtmCard.setPin(scanner.nextLine().trim());
            List<Long> depositAccountIds = new ArrayList<>();
            System.out.println("Link existing deposit accounts to the new ATM card");
            for (DepositAccount depositAccount : customer.getDepositAccounts()) {
                System.out.println("Link account " + depositAccount.getAccountNumber() + "? (Enter 'Y' to link)> ");
                String input = scanner.nextLine().trim();
                if (input.equals("Y")) {
                    depositAccountIds.add(depositAccount.getDepositAccountId());
                }
            }
            if (!depositAccountIds.isEmpty()) {
                newAtmCard.setEnabled(true);
                newAtmCard = atmCardSessionBeanRemote.issueNewAtmCard(newAtmCard, customerId, depositAccountIds);
                System.out.println("New ATM card created successfully!: " + newAtmCard.getAtmCardId() + "\n");
            } else {
                System.out.println("An ATM card must be linked to at least one deposit account, unable to create new ATM card!\n");
            }
        } catch (Exception ex) {
            System.out.println("An error occured while creating the new ATM Card : " + ex.getMessage() + "!\n");
        }
    }
}
