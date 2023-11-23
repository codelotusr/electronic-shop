package com.coursework.eshop;

import com.coursework.eshop.model.Address;
import com.coursework.eshop.model.Card;
import com.coursework.eshop.model.Customer;

import java.time.LocalDate;
import java.util.Scanner;

public class Start {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        var cmd = 0;

        while (cmd != 3) {
            System.out.println("Sveiki, pasirinkite, ka norite daryti:");
            System.out.println("""
                    1 - User
                    2 - Warehouse
                    3 - Quit
                    """);

            cmd = scanner.nextInt();
            scanner.nextLine();

            switch (cmd) {
                case 1:
                    System.out.println("create customer: username;password;b-date(yyyy-mm-dd);first-name;last-name;address(country;city;street;house-number);card(number;card-holder;expiration-date(yyyy-mm-dd);cvv);");
                    String values = scanner.nextLine();
                    String[] info = values.split(";");
                    Customer customer = new Customer(info[0], info[1], LocalDate.parse(info[2]), info[3], info[4], new Address(info[5], info[6], info[7], info[8]), new Card(info[9], info[10], LocalDate.parse(info[11]), info[12]));
                    System.out.println(customer);
                    break;
                case 2:
                    break;
                default:
                    System.out.println("Pasirinkite viena is galimu veiksmu");
                    break;
            }

            cmd = scanner.nextInt();
        }
    }
}
