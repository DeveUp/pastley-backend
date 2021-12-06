package com.pastley.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @project Pastley-User.
 * @author Leyner Jose Ortega Arias.
 * @Github https://github.com/leynerjoseoa.
 * @contributors serbuitrago.
 * @version 1.0.0.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nickname", nullable = false, unique = true, length = 100)
	private String nickname;

	@Column(name = "points")
	private Long points;

	@Column(name = "password", nullable = false, length = 500)
	private String password;

	@Column(name = "ip", nullable = true)
	private String ip;

	@Column(name = "last_password", nullable = true, length = 500)
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
	
	@ManyToOne
	@JoinColumn(name = "id_role", nullable = false)
	private Role role;
}