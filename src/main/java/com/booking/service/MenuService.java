package com.booking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;
import com.booking.repositories.PersonRepository;
import com.booking.repositories.ServiceRepository;

public class MenuService {
    private static List<Person> personList = PersonRepository.getAllPerson();
    private static List<Service> serviceList = ServiceRepository.getAllService();
    private static List<Reservation> reservationList = new ArrayList<>();
    private static Scanner input = new Scanner(System.in);

    public static void mainMenu() {
        String[] mainMenuArr = { "Show Data", "Create Reservation", "Complete/cancel reservation", "Exit" };
        String[] subMenuArr = { "Recent Reservation", "Show Customer", "Show Available Employee", "Back to main menu" };

        int optionMainMenu;
        int optionSubMenu;

        boolean backToMainMenu = false;
        boolean backToSubMenu = false;
        do {
            PrintService.printMenu("Main Menu", mainMenuArr);
            optionMainMenu = Integer.valueOf(input.nextLine());
            switch (optionMainMenu) {
                case 1:
                    do {
                        PrintService.printMenu("Show Data", subMenuArr);
                        optionSubMenu = Integer.valueOf(input.nextLine());
                        // Sub menu - menu 1
                        switch (optionSubMenu) {
                            case 1:
                                PrintService.showRecentReservation(reservationList);
                                break;
                            case 2:
                                PrintService.showAllCustomer(personList);
                                break;
                            case 3:
                                PrintService.showAllEmployee(personList);
                                break;
                            case 4:
                                PrintService.showHistoryReservation(reservationList);
                                break;
                            case 0:
                                backToSubMenu = true;
                        }
                    } while (!backToSubMenu);
                    break;
                case 2:
                    createReservation();
                    break;
                case 3:
                    updateReservationWorkstage(reservationList);
                    break;
                case 0:
                    backToMainMenu = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please choose again.");
            }
        } while (!backToMainMenu);

    }

    private static void updateReservationWorkstage(List<Reservation> reservationList) {
        System.out.println("=== Update Reservation Workstage ===");
        System.out.print("Reservation ID: ");
        String reservationId = input.nextLine();

        System.out.print("New Workstage (Finish/Cancel): ");
        String newWorkstage = input.nextLine();

        ReservationService.updateReservationWorkstage(reservationId, newWorkstage, reservationList);
    }

    private static void createReservation() {
        System.out.println("=== Create Reservation ===");
        System.out.print("Customer ID: ");
        String customerId = input.nextLine();
        Customer customer = ReservationService.getCustomerByCustomerId(getCustomerList(personList), customerId);
        if (customer == null) {
            System.out.println("Customer tidak ditemukan.");
            return;
        }

        System.out.print("Employee ID: ");
        String employeeId = input.nextLine();
        Employee employee = getEmployeeById(employeeId);
        if (employee == null) {
            System.out.println("Employee tidak ditemukan.");
            return;
        }

        List<Service> chosenServices = selectServices();
        if (chosenServices.isEmpty()) {
            System.out.println("Pilih setidaknya satu service.");
            return;
        }

        String reservationId = generateReservationId();
        Reservation reservation = ReservationService.createReservation(reservationId, customer, employee,
                chosenServices, "In Process");
        reservationList.add(reservation);

        System.out.println("Reservasi berhasil dibuat dengan ID: " + reservationId);
    }

    private static List<Customer> getCustomerList(List<Person> personList) {
        List<Customer> customers = new ArrayList<>();
        for (Person person : personList) {
            if (person instanceof Customer) {
                customers.add((Customer) person);
            }
        }
        return customers;
    }

    private static Employee getEmployeeById(String employeeId) {
        for (Person person : personList) {
            if (person instanceof Employee && person.getId().equals(employeeId)) {
                return (Employee) person;
            }
        }
        return null;
    }

    private static List<Service> selectServices() {
        List<Service> chosenServices = new ArrayList<>();
        String choice;
        do {
            System.out.print("Service ID (0 to finish): ");
            choice = input.nextLine();
            if (!choice.equals("0")) {
                Service service = getServiceById(choice);
                if (service != null && !chosenServices.contains(service)) {
                    chosenServices.add(service);
                } else if (service == null) {
                    System.out.println("Service tidak ditemukan.");
                } else {
                    System.out.println("Service sudah dipilih.");
                }
            }
        } while (!choice.equals("0"));
        return chosenServices;
    }

    private static Service getServiceById(String serviceId) {
        for (Service service : serviceList) {
            if (service.getServiceId().equals(serviceId)) {
                return service;
            }
        }
        return null;
    }

    private static String generateReservationId() {
        return "RES-001";
    }
}
