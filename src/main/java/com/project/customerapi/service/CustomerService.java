package com.project.customerapi.service;

import com.project.customerapi.exception.CustomerNotFoundException;
import com.project.customerapi.model.Customer;
import com.project.customerapi.model.Address;
import com.project.customerapi.repository.CustomerRepository;
import com.project.customerapi.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, AddressRepository addressRepository) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
    }

    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }
    public Optional<Customer> getCustomerById(Long id){
        return Optional.ofNullable(customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id)));
    }

    public Customer addCustomer(Customer customer) {
        if (customer.getAddresses() != null) {
            for (Address address : customer.getAddresses()) {
                address.setCustomer(customer);
            }
        }
        return customerRepository.save(customer);
    }



    public Customer updateCustomer(Long id,Customer newCustomerData){
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setFirstName(newCustomerData.getFirstName());
                    customer.setLastName(newCustomerData.getLastName());
                    customer.setEmail(newCustomerData.getEmail());
                    customer.setPhoneNumber(newCustomerData.getPhoneNumber());
                    return customerRepository.save(customer);
                })
                .orElseThrow(() -> new RuntimeException("Müşteri bulunamadı"));
    }

    public void deleteCustomer(Long id){
        customerRepository.deleteById(id);
    }

    public void addAddressToCustomer(Long customerId, Address address) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Müşteri Bulunamadı"));
        address.setCustomer(customer);   // Adresi ilişkilendir
        addressRepository.save(address); // Şimdi customer_id dolu gidecek
    }



    public List<Address> getAddressesByCustomerId(Long customerId) {
        return addressRepository.findByCustomerId(customerId);

    }
}


