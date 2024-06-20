package com.booking.service;

import java.util.List;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Service;

public class ValidationService {
    // Buatlah function sesuai dengan kebutuhan
    public static boolean validateCustomerId(List<Customer> customerList, String customerId) {
        return customerList.stream()
                .anyMatch(customer -> customer.getId().equals(customerId));
    }

    public static boolean validateEmployeeId(List<Person> personList, String employeeId) {
        return personList.stream()
                .filter(person -> person.getId().equals(employeeId) && person instanceof Employee)
                .findFirst()
                .isPresent();
    }

    public static boolean validateServiceId(List<Service> serviceList, String serviceId) {
        return serviceList.stream()
                .anyMatch(service -> service.getServiceId().equals(serviceId));
    }
}
