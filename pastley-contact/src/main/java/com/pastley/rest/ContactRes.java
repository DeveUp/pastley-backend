package com.pastley.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pastley.entity.Company;
import com.pastley.entity.Contact;
import com.pastley.entity.TypePQR;
import com.pastley.model.Email;
import com.pastley.service.ContactService;
import com.pastley.util.PastleyResponse;

@RestController
@RequestMapping("contact")
public class ContactRes {

	@Autowired
	private ContactService contactService;

	///////////////////////////////////////////////////////
	// Method - Get
	///////////////////////////////////////////////////////
	/**
	 * Method that allows consulting a payment method by its id.
	 * 
	 * @param id, Represents the identifier of the payment method.
	 * @return The generated response.
	 */

	@RequestMapping(value = "id")
	public ResponseEntity<?> findById(@PathVariable("id") Long id) {
		PastleyResponse response = new PastleyResponse();

		Contact contact = contactService.findById(id);
		if (contact != null) {
			response.add("contact", contact, HttpStatus.OK);
		} else {
			response.add("message", "No hay ningun Contacto Registrado con ese ID " + id + ".", HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response.getMap());
	}

	/**
	 * Method that allows you to obtain all payment methods.
	 * 
	 * @return The generated response.
	 */

	@GetMapping
	public ResponseEntity<?> findAll() {
		PastleyResponse response = new PastleyResponse();
		List<Contact> list = contactService.findAll();
		if (list.isEmpty()) {
			response.add("message", "No hay ningun contacto registrado", HttpStatus.NOT_FOUND);
		} else {
			response.add("contact", list, HttpStatus.OK);
		}
		return ResponseEntity.ok(response.getMap());

	}

	///////////////////////////////////////////////////////
	// Method - Post
	///////////////////////////////////////////////////////
	/**
	 * Method that allows you to register a sale.
	 * 
	 * @param sale, Represents the sale to register.
	 * @return The generated response.
	 */
	@PostMapping(value = "/create")
	public ResponseEntity<?> create(@RequestBody Contact method) {
		PastleyResponse response = new PastleyResponse();
		if (method != null) {
			Contact aux = contactService.findById(method.getId());// validar si el pqr existe; que los campos no esten vacios , que el usuario exista 
			if (aux == null) {	
				aux = contactService.save(method);
				//Email email = new Email("",);//de, usuario,clave, para 
				try {
					//email.sendMail();

					if (aux != null) {
						response.add("method", aux, HttpStatus.OK);
						response.add("message", "Se ha registrado el Contacto con id " + aux.getId() + ".");
					} else {
						response.add("message", "No se ha registrado el Contacto.", HttpStatus.NO_CONTENT);
					}
				}

				catch (Exception e) {
					response.add("menssage", "No se ha podido Enviar el correo", HttpStatus.NO_CONTENT);
				}

			} else {
				response.add("message", "Ya existe un Contacto con ese id '" + method.getId() + "'.",
						HttpStatus.NO_CONTENT);
			}
		} else {
			response.add("message", "No se ha recibido el Contacto.", HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(response.getMap());
	}

	///////////////////////////////////////////////////////
	// Method - Put
	///////////////////////////////////////////////////////
	/**
	 * Method that allows updating a sale.
	 * 
	 * @param sale, Represents the sale to update.
	 * @return The generated response.
	 */
	@PutMapping(value = "/update")
	public ResponseEntity<?> update(@RequestBody Contact contact) {
		PastleyResponse response = new PastleyResponse();
		return ResponseEntity.ok(response.getMap());
	}

	///////////////////////////////////////////////////////
	// Method - Delete
	///////////////////////////////////////////////////////
	/**
	 * Method that allows you to delete a sale by means of its id.
	 * 
	 * @param id, Represents the identifier of the sale to be deleted.
	 * @return The generated response.
	 */
	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		PastleyResponse response = new PastleyResponse();
		return ResponseEntity.ok(response.getMap());
	}

}
