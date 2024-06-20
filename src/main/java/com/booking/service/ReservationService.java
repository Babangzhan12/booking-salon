package com.booking.service;

import java.util.List;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Reservation;
import com.booking.models.Service;

public class ReservationService {
    public static Reservation createReservation(String reservationId, Customer customer, Employee employee,
            List<Service> services, String workstage) {
        // Buat objek Reservation baru
        Reservation reservation = new Reservation(reservationId, customer, employee, services, workstage);
        // Hitung harga reservasi
        reservation.setReservationPrice(calculateReservationPrice(customer, services));
        return reservation;
    }

    public static Customer getCustomerByCustomerId(List<Customer> customerList, String customerId) {
        return customerList.stream()
                .filter(customer -> customer.getId().equals(customerId))
                .findFirst()
                .orElse(null);
    }

    public static void updateReservationWorkstage(Reservation reservation, String newWorkstage) {
        reservation.setWorkstage(newWorkstage);
        System.out.println("Workstage updated successfully for reservation with ID: " + reservation.getReservationId());
    }

    public static void updateReservationWorkstage(String reservationId, String newWorkstage, List<Reservation> reservationList) {
        Reservation reservation = findReservationById(reservationId, reservationList);
        if (reservation == null) {
            System.out.println("Reservation not found.");
            return;
        }
        
        // Check if the reservation is in process
        if (!reservation.getWorkstage().equalsIgnoreCase("In Process")) {
            System.out.println("Cannot update workstage for reservation that is not in process.");
            return;
        }
        
        // Update the workstage
        reservation.setWorkstage(newWorkstage);
        System.out.println("Workstage updated successfully for reservation with ID: " + reservationId);
    }

    private static Reservation findReservationById(String reservationId, List<Reservation> reservationList) {
        return reservationList.stream()
                .filter(reservation -> reservation.getReservationId().equals(reservationId))
                .findFirst()
                .orElse(null);
    }

    // Silahkan tambahkan function lain, dan ubah function diatas sesuai kebutuhan
    private static double calculateReservationPrice(Customer customer, List<Service> services) {
        // Implementasi logika perhitungan harga reservasi sesuai dengan aturan diskon
        // member
        double totalPrice = services.stream()
                .mapToDouble(Service::getPrice)
                .sum();

        // Terapkan diskon jika customer adalah member
        if (customer.getMember() != null) {
            switch (customer.getMember().getMembershipName()) {
                case "silver":
                    totalPrice *= 0.95; // Diskon 5%
                    break;
                case "gold":
                    totalPrice *= 0.90; // Diskon 10%
                    break;
                default:
                    break;
            }
        }

        return totalPrice;
    }
}
