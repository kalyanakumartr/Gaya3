package org.hbs.gaya.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hbs.gaya.util.CommonValidator;
import org.hbs.gaya.util.ConstUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sequence")
@NoArgsConstructor
public class Sequence implements Serializable
{
	private static final long	serialVersionUID	= 1696926036927427862L;

	@Id
	@Column(name = "autoId")
	private long				autoId;
	
	@Column(name = "format")
	private String				format;
	
	@Column(name = "sequenceId")
	private long				sequenceId;
	
	@Column(name = "sequenceKey")
	private String				sequenceKey;

	@Transient
	public String generate(Map<String, String> dataMap)
	{
		StringBuilder sb = new StringBuilder();
		if (CommonValidator.isNotNullNotEmpty(format))
		{
			for (String fmt : format.split(ConstUtil.HASH)) // yyyyMMdd
			{
				if (fmt.contains("yy") || fmt.contains("MM") || fmt.contains("dd") || fmt.contains("HH") || fmt.contains("hh") || fmt.contains("mm"))
				{
					sb.append(new SimpleDateFormat(fmt).format(new Date()));
				}
				else if (fmt.equals("sequenceId"))
				{
					sb.append(this.sequenceId);
				}
				else if (dataMap.containsKey(fmt))
				{
					sb.append(dataMap.get(fmt));
				}
				else
				{
					sb.append(fmt);
				}
			}
			return sb.toString();
		}
		else
		{
			return this.sequenceId + "";
		}
	}


	public Sequence(long sequenceId, String sequenceKey)
	{
		super();
		this.sequenceId = sequenceId;
		this.sequenceKey = sequenceKey;
	}
}
