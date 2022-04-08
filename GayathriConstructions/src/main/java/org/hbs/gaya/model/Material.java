package org.hbs.gaya.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "material")
@Getter
@Setter
@NoArgsConstructor
public class Material extends CreatedModifiedDateStatus implements Serializable
{

	private static final long serialVersionUID = -7368928522851898839L;

	@Id
	@Column(name = "materialId")
	private String				materialId;

	@Column(name = "materialName")
	private String				materialName;

	@Column(name = "numberCode")
	private String				numberCode;

	@Column(name = "description")
	private String				description;
	
	@Column(name = "displayOrder")
	private Integer				displayOrder;


}
