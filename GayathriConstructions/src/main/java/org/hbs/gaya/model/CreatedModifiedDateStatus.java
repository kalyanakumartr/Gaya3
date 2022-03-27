package org.hbs.gaya.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class CreatedModifiedDateStatus implements Serializable
{

	private static final long	serialVersionUID	= 513207930832531090L;

	@Column(name = "createdDate")
	private LocalDateTime		createdDate;

	@ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "createdBy", nullable = false)
	private Users				createdUser;

	@Column(name = "modifiedDate")
	private LocalDateTime		modifiedDate;

	@ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "modifiedBy", nullable = false)
	private Users				modifiedUser;

	@Column(name = "status")
	private Boolean				status;

	@Transient
	public String getCreatedBy()
	{
		if(getCreatedUser() != null)
			return this.getCreatedUser().getUserName() + " " + this.getCreatedUser().getLastName();
		return "";
	}

	@Transient
	public String getModifiedBy()
	{
		if (getModifiedUser() != null)
			return this.getModifiedUser().getUserName() + " " + this.getModifiedUser().getLastName();
		return "";
	}

}
