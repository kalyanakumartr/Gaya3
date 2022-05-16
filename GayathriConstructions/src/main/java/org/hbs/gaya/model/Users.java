package org.hbs.gaya.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Users extends CreatedModifiedDateStatus implements Serializable
{

	private static final long	serialVersionUID	= -8743496249588408243L;

	@Id
	@Column(name = "employeeId")
	private String				employeeId;

	@Column(name = "emailId")
	private String				emailId;

	@Column(name = "password")
	private String				password;

	@Column(name = "userName")
	private String				userName;

	@Column(name = "lastName")
	@Builder.Default
	private String				lastName			= "";

	@Column(name = "mobileNo")
	private String				mobileNo;

}
