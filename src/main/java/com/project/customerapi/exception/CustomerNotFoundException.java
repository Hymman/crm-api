package com.project.customerapi.exception;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(Long id){
        super("Müşteri bulunamadı : "+id);
    }
}
