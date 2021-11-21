package com.pastley.model.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.pastley.util.PastleyValidate;
import com.sun.istack.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project Pastley-User.
 * @author Leyner Jose Ortega Arias.
 * @Github https://github.com/leynerjoseoa.
 * @contributors serbuitrago.
 * @version 1.0.0.
 */
@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nickname", nullable = false, unique = true, length = 100)
	private String nickname;

	@Column(name = "points")
	private Long points;

	@Column(name = "password", nullable = false, length = 50)
	private String password;

	@Column(name = "ip", nullable = true)
	private String ip;

	@Column(name = "last_password", nullable = true, length = 50)
	private String lastPassword;

	@Column(name = "statu", nullable = false, columnDefinition = "tinyint(1) default 1")
	private boolean statu;

	@Column(name = "session", nullable = false, columnDefinition = "tinyint(1) default 1")
	private boolean session;

	@Column(name = "date_register", nullable = false)
	private String dateRegister;

	@Column(name = "date_update", nullable = true)
	private String dateUpdate;

	@Column(name = "date_last_date", nullable = true)
	private String dateLastDate;

	@Column(name = "date_session", nullable = true)
	private String dateSession;

	@ManyToOne
	@JoinColumn(name = "id_person", nullable = false)
	private Person person;
	
	@ManyToMany
	@NotNull
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id_user"), inverseJoinColumns = @JoinColumn(name = "id_role"))
	private Set<Role> roles = new HashSet<>();

	/**
	 * Method that validates the attributes of the class.
	 * 
	 * @param isId, Represents if you want to validate the id.
	 * @return The error occurred.
	 */
	public String validate(boolean isId) {
		String chain = null;
		if (isId && id <= 0)
			chain = "El id del rol debe ser mayor a cero.";
		if (!PastleyValidate.isChain(password)) 
			chain = "La clave no es valida.";
		if(!PastleyValidate.isChain(nickname))
			chain = "El apodo no es valido.";
		if(person == null) 
			chain = "No se ha recibido la persona.";	
		return chain;
	}

	/**
	 * 
	 * @param dateRegister
	 * @param dateUpdate
	 */
	public void date(String dateRegister, String dateUpdate) {
		this.dateRegister= dateRegister;
		this.dateUpdate= dateUpdate;
	}
	
	/**
	 * 
	 * @param statu
	 * @param session
	 */
	public void is(boolean statu, boolean session) {
		this.statu = statu;
		this.session = session;
	}
}