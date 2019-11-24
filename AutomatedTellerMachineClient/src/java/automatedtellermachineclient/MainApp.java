/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automatedtellermachineclient;

import ejb.session.stateless.AtmCardSessionBeanRemote;
import entity.DepositAccount;
import java.text.NumberFormat;
import java.util.List;
import java.util.Scanner;
import util.exception.AtmCardNotFoundException;
import util.exception.InvalidAtmCardException;

/**
 *
 * @author dtjldamien
 */
public class MainApp {

    private AtmCardSessionBeanRemote atmCardSessionBeanRemote;
    private Long atmCardId;

    public MainApp() {
    }

    MainApp(AtmCardSessionBeanRemote atmCardSessionBeanRemote) {
        this();
        this.atmCardSessionBeanRemote = atmCardSessionBeanRemote;
    }

    public void runApp() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;
        while (true) {
            System.out.println("*** Welcome to RCBS - ATM ***\n");
            System.out.println("1: Insert ATM Card");
            System.out.println("2: Exit\n");
            response = 0;
            while (response < 1 || response > 2) {
                System.out.print("> ");
                response = scanner.nextInt();
                if (response == 1) {
                    try {
                        doInsertAtmCard();
                        menuMain();
                    } catch (InvalidAtmCardException ex) {
                        System.out.println("ATM Card is invalid! " + ex.getMessage() + "\n");
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

    private void doInsertAtmCard() throws InvalidAtmCardException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("RCBS :: Do Insert Atm Card");
        System.out.println("Enter ATM Card Id> ");
        this.atmCardId = scanner.nextLong();
        scanner.nextLine();
    }

    private void menuMain() {
        Scanner scanner = new Scanner(System.in);
        Integer response = 0;

        while (true) {
            System.out.println("*** Welcome to RCBS - ATM ***\n");
            System.out.println("Your are logged in as " + atmCardId + "\n");
            System.out.println("1: Change PIN");
            System.out.println("2: Enquire available balance");
            System.out.println("3: Logout\n");
            response = 0;
            while (response < 1 || response > 4) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    doChangePin();
                } else if (response == 2) {
                    doEnquireAvailableBalance();
                } else if (response == 3) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }
            if (response == 3) {
                break;
            }
        }
    }

    private void doChangePin() {
        try {
            Scanner scanner = new Scanner(System.in);
            String currPin = "";
            String newPin = "";
            System.out.println("*** RCBS - ATM : Change PIN ***\n");
            System.out.print("Enter current PIN> ");
            currPin = scanner.nextLine().trim();
            System.out.print("Enter new PIN> ");
            newPin = scanner.nextLine().trim();
            atmCardSessionBeanRemote.changePin(atmCardId, currPin, newPin);
            System.out.println("ATM card PIN changed!");
        } catch (AtmCardNotFoundException ex) {
            System.out.println("ATM Card not found!\n");
        }
    }

    private void doEnquireAvailableBalance() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("*** RCBS - ATM : Enquire Available Balance ***\n\n");
            List<DepositAccount> depositAccounts = atmCardSessionBeanRemote.retrieveDepositAccountsByAtmCardId(atmCardId);
            System.out.printf("%3s%15s%18s\n", "S/N", "Account Type", "Account Number");

            int sn = 0;

            for (DepositAccount depositAccount : depositAccounts) {
                ++sn;
                System.out.printf("%3s%15s%18s\n", sn, depositAccount.getAccountType().toString(), depositAccount.getAccountNumber());
            }

            System.out.println("------------------------");
            System.out.print("Account to Enquire > ");
            int response = scanner.nextInt();

            if (response >= 1 && response <= sn) {
                System.out.println("Available balance is " + NumberFormat.getCurrencyInstance().format(depositAccounts.get(response - 1).getAvailableBalance()));
            } else {
                System.out.println("Invalid option!\n");
            }
        } catch (AtmCardNotFoundException ex) {
            System.out.println("Invalid ATM card!\n");
        }
    }
}
