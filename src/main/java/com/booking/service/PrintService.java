package com.booking.service;

import java.util.List;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;

public class PrintService {
    public static void printMenu(String title, String[] menuArr) {
        int num = 1;
        System.out.println(title);
        for (int i = 0; i < menuArr.length; i++) {
            if (i == (menuArr.length - 1)) {
                num = 0;
            }
            System.out.println(num + ". " + menuArr[i]);
            num++;
        }
    }
    private static String printServices(List<Service> serviceList) {
        StringBuilder result = new StringBuilder();
        for (Service service : serviceList) {
            result.append(service.getServiceName()).append(", ");
        }
        return result.toString();
    }

    // Function yang dibuat hanya sebgai contoh bisa disesuaikan kembali
    public static void showRecentReservation(List<Reservation> reservationList) {
        int num = 1;
        System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                "No.", "ID", "Nama Customer", "Service", "Biaya Service", "Pegawai", "Workstage");
        System.out.println("+========================================================================================+");
        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equalsIgnoreCase("Waiting") || reservation.getWorkstage().equalsIgnoreCase("In process")) {
                System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                        num, reservation.getReservationId(), reservation.getCustomer().getName(), printServices(reservation.getServices()), reservation.getReservationPrice(), reservation.getEmployee().getName(), reservation.getWorkstage());
                num++;
            }
        }
    }

    public static void showAllCustomer(List<Person> customerList) {
        System.out.println("Daftar Pelanggan:");
        for (Person person : customerList) {
            if (person instanceof Customer) {
                System.out.println(person.getName() + " - Wallet: " + ((Customer) person).getWallet());
            } 
        }
    }

    public static void showAllEmployee(List<Person> personList) {
        System.out.println("Daftar Karyawan:");
        for (Person person : personList) {
            if (person instanceof Employee) {
                System.out.println(person.getName() + " - Pengalaman: " + ((Employee) person).getExperience() + " tahun");
            }
        }
    }

    public static void showHistoryReservation(List<Reservation> reservationList) {
        System.out.println("Riwayat Reservasi:");
        for (Reservation reservation : reservationList) {
            System.out.println("ID Reservasi: " + reservation.getReservationId());
            System.out.println("Nama Pelanggan: " + reservation.getCustomer().getName());
            System.out.println("Nama Karyawan: " + reservation.getEmployee().getName());
            System.out.println("Biaya Reservasi: " + reservation.getReservationPrice());
            System.out.println("Status: " + reservation.getWorkstage());
            System.out.println("--------------------------------------");
        }
    }
}
