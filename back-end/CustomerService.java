package com.project.vm.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.vm.entities.Booking;
import com.project.vm.entities.Customer;
import com.project.vm.entities.ERole;
import com.project.vm.entities.Role;
import com.project.vm.entities.User;
import com.project.vm.exceptions.AlreadyExistsException;
import com.project.vm.exceptions.DeletionException;
import com.project.vm.exceptions.NotFoundException;
import com.project.vm.exceptions.ValidationException;
import com.project.vm.repositories.IBookingRepository;
import com.project.vm.repositories.ICustomerRepository;
import com.project.vm.repositories.IRoleRepository;
import com.project.vm.repositories.IUserRepository;
import com.project.vm.serviceInterfaces.ICustomerService;



@Service
public class CustomerService implements ICustomerService{
	@Autowired
	ICustomerRepository customerRepository;
	
	@Autowired
	IBookingRepository bookingRepository;
	
	@Autowired 
	IUserRepository userRepository;
	
	@Autowired
	IRoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	public Customer addCustomer(Customer c) throws ValidationException{
		
		final String emailPattern = "^(.+)@(.+)$";
		final String mobilePattern = "[0-9]{10}";

		String email = c.getEmailId();
		if (email.matches(emailPattern)) {
			
			Customer existingemail = customerRepository.findByEmailId(c.getEmailId());
			if(existingemail != null) {
				throw new AlreadyExistsException("EmailId Already Exists!");
			}
		}
		
		else {
			throw new ValidationException("Invalid Email");
		}
		
		String mobileNo = c.getMobileNumber();
		if (mobileNo.matches(mobilePattern)) {
			
			Customer existingmobileno = customerRepository.findByMobileNumber(c.getMobileNumber());
			if(existingmobileno != null) {
				throw new AlreadyExistsException("Mobile Number Already Exists!");
			}
		}
		
		else {
			throw new ValidationException("Mobile number should be 10 digits");
		}
		
		Customer customer = customerRepository.findCustomerByFirstNameAndLastName(c.getFirstName(),c.getLastName());		
			if(customer == null){
				customerRepository.save(c);
				User newUser = new User();
				newUser.setUsername(c.getFirstName());
				newUser.setEmail(c.getEmailId());
				newUser.setPassword("cust");
				Set<Role> roles = new HashSet<>();
				Role userRole = roleRepository.findByName(ERole.ROLE_CUSTOMER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(userRole);
				newUser.setRoles(roles);
				userRepository.save(newUser);
				
			}else
				throw new AlreadyExistsException("Customer "+ c.getFirstName()  +" " +c.getLastName() + " already exists.");
			return customer;
	}
	
	@Override
	public Customer removeCustomer(int id){
		Optional<Customer> customer = customerRepository.findById(id);
		if(customer.isEmpty()){
			throw new NotFoundException("Customer with id " +id +" not found");
 		}
		Customer c = customer.get(); 
		List<Booking> booking = bookingRepository.findByCustomer(c);
		if(!(booking.isEmpty())) {
			throw new  DeletionException("Customer with boooking cannot be deleted");
		}
		customerRepository.delete(c);	
		return c;
	}
	
	@Override
	@Transactional
	public Customer updateCustomer(Customer customer) {
		if(customer != null) {
			Customer c = viewCustomer(customer.getCustomerId());
			c.setEmailId(customer.getEmailId());
		return c;	
		}
		else
			throw new NotFoundException("Customer Does Not Exist");
	}
	
	@Override
	public Customer viewCustomer(int id)
	{
		Optional<Customer> customer = customerRepository.findById(id);
		if(customer.isEmpty()){
			throw new NotFoundException("Customer with id " +id +" not found");
 		}
		return customer.get();
	}
	
	@Override
	public List<Customer> viewAllCustomer(String vtype) {
		List<Customer> customers = customerRepository.findByVehicleType(vtype);
		if(customers.isEmpty()){
			throw new NotFoundException("Customer not found");
 		}
		return customers;		
	}
	
	@Override
	public List<Customer> viewAllCustomersByLocation(String location) {
		List<Customer> customers = customerRepository.findByVehicleLocation(location);
		if(customers.isEmpty()){
			throw new NotFoundException("Customer not found");
 		}
		return customers;		
	}
	
	
	public List<Customer> viewCustomers()
	{	
		List<Customer> customers = customerRepository.findAll();
		if(customers.isEmpty()) {
			throw new NotFoundException("No customers found");
		}
		return customers;
	}
	
	public List<Customer> viewCustomerByEmail(String emailId){
		List<Customer> customers = customerRepository.findByCustomerEmail(emailId);
		if(customers.isEmpty()) {
			throw new NotFoundException("No customers found");
		}
		return customers;
	}
	
}