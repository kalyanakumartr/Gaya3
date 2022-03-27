package org.hbs.gaya.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class Users extends CreatedModifiedDateStatus implements Serializable
{

	private static final long	serialVersionUID	= -8743496249588408243L;

	@Id
	private String				employeeId;

	@Column(name = "email")
	private String				email;

	@Column(name = "password")
	private String				password;

	@Column(name = "userName")
	private String				userName;

	@Column(name = "lastName")
	private String				lastName = "";

	@Column(name = "mobileNo")
	private String				mobileNo;
	
	

}
