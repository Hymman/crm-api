package com.project.customerapi.controller;

import com.project.customerapi.model.Address;
import com.project.customerapi.model.Customer;
import com.project.customerapi.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;


@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;


    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @Operation(summary = "Tüm müşterileri getir", description = "bütün müşteri listesini döndürür")
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }
    @Operation(summary = """
            ID'ye göre müşteri getir""", description = "Belirtilen ID'ye sahip müşteri bilgilerini getirir")
    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id){
        Optional<Customer> customer = customerService.getCustomerById(id);
        return customer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Yeni müşteri ekle",description = "Verilen müşterileri listeye ekler")
    @PostMapping
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
        Customer savedCustomer = customerService.addCustomer(customer);
        return ResponseEntity.ok(savedCustomer);
    }

    @Operation(summary = "Müşteri Güncelle",description = "Id ye göre müşteri bilgilerini günceller")
    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer newCustomerData){
        Customer updatedCustomer = customerService.updateCustomer(id,newCustomerData);
        return  ResponseEntity.ok(updatedCustomer);
    }

    @Operation(summary = "Müşteri Sil",description = "Belirtilen Id ye göre müşteriyi databaseden siler")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    // Add new Adress to Customer :
    @PostMapping("/{customerId}/addresses")
    public ResponseEntity<Void> addAddressToCustomer(@PathVariable Long customerId, @RequestBody Address address){
        customerService.addAddressToCustomer(customerId,address);
        return ResponseEntity.ok().build();
    }

    // Get the Cusotmer Adress info metod
    @GetMapping("/{customerId}/addresses")
    public ResponseEntity<List<Address>> getAddressesByCustomerId(@PathVariable Long customerId){
        return ResponseEntity.ok(customerService.getAddressesByCustomerId(customerId));
    }
}
