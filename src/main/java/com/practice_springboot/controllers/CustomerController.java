package com.practice_springboot.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.practice_springboot.models.Customer;
import com.practice_springboot.services.CustomerService;
import com.practice_springboot.services.IUploadFileService;


//@CrossOrigin(origins=)
@RestController
@RequestMapping("/api")
public class CustomerController {
	
	@Autowired
	private CustomerService service;
	
	@Autowired
	private IUploadFileService uploadService;
	
	
	@GetMapping("/customer")
	public List<Customer> listAll(){
		return service.findAll();
	}
	
	
	@GetMapping("/customer/page/{page}")
	public Page<Customer> listAll(@PathVariable Integer page){
		PageRequest pageable = PageRequest.of(page, 3);
		return service.findAll(pageable);
	}
	
	
	@GetMapping("/customer/{id}")
	public ResponseEntity<?> getById(@PathVariable Long id){
		Customer customer = null;
		Map<String, Object> response = new HashMap<>();
		
		try {
			customer = service.findById(id);
		} catch (DataAccessException e) {
			response.put(
				"message", 
				"Error al realizar la consulta en la base de datos");
			response.put(
				"error", 
				e.getMessage().concat(": ")
				.concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(
					response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(customer == null) {
			response.put(
				"message", 
				"El cliente con ID: ".concat(id.toString()
					.concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(
				response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
	
	
	@PostMapping("/customer")
	public ResponseEntity<?> create(@RequestBody Customer customer, BindingResult result) {
		Customer customerNew = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
				.stream()
				.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
				.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(
					response, HttpStatus.BAD_REQUEST);
		}
		
		try {
			customerNew = service.save(customer);
		} catch(DataAccessException e) {
			response.put(
				"message", 
				"Error al realizar el insert en la base de datos");
			response.put(
				"error", 
				e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(
				response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El cliente ha sido creado con éxito!");
		response.put("cliente", customerNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	
	@PutMapping("/customer/{id}")
	public ResponseEntity<?> update(@RequestBody Customer customer, BindingResult result, @PathVariable Long id){
		
		Customer currentCustomer = service.findById(id);
		Customer updateCustomer = null;
		Map<String, Object> response = new HashMap<>();

		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		if (currentCustomer == null) {
			response.put("message", "Error: no se pudo editar, el cliente ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {
			currentCustomer.setName(customer.getName());
			currentCustomer.setLast_name(customer.getLast_name());
			currentCustomer.setEmail(customer.getEmail());
			currentCustomer.setCreatedAt(customer.getCreatedAt());
			updateCustomer = service.save(currentCustomer);

		} catch (DataAccessException e) {
			response.put(
				"message", "Error al actualizar el cliente en la base de datos");
			response.put(
				"error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("message", "El cliente ha sido actualizado con éxito!");
		response.put("cliente", updateCustomer);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/customer/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		
		try {
			Customer customer = service.findById(id);
			String nameLastPhoto = customer.getPhoto();
			uploadService.delete(nameLastPhoto);
			service.delete(id);
		} catch (DataAccessException e) {
			response.put(
				"message", 
				"Error al eliminar el cliente de la base de datos");
			response.put(
				"error", 
				e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(
					response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put(
			"message", 
			"El cliente eliminado con éxito!");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
		
	}
	
	
	@PostMapping("/customer/upload")
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id){
		Map<String, Object> response = new HashMap<>();
		Customer customer = service.findById(id);
		
		if(!file.isEmpty()) {
			String nameFile = null;
			try {
				nameFile = uploadService.copy(file);
			} catch (IOException e) {
				response.put(
					"message", 
					"Error al subir la imagen del cliente");
				response.put(
					"error", 
					e.getMessage().concat(": ").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			String nameLastPhoto = customer.getPhoto();
			System.out.println(nameLastPhoto);
			uploadService.delete(nameLastPhoto);		
			customer.setPhoto(nameFile);
			System.out.println(customer);
			service.save(customer);
			
			response.put("cliente", customer);
			response.put("message", "Has subido correctamente la imagen: " + nameFile);
		}	
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	
	@GetMapping("/uploads/img/{namePhoto:.+}")
	public ResponseEntity<Resource> showPhoto(@PathVariable String namePhoto){
		Resource resource = null;
		try {
			resource = uploadService.load(namePhoto);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		HttpHeaders header = new HttpHeaders();
		header.add(
			HttpHeaders.CONTENT_DISPOSITION, 
			"attachment; filename=\"" + resource.getFilename() + "\""
		);
		return new ResponseEntity<Resource>(resource, header, HttpStatus.OK);
		
	}
}
