package org.hbs.gaya.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;

import org.hbs.gaya.model.Users;
import org.hbs.gaya.security.CustomUserDetails;
import org.springframework.security.core.Authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class ConstUtil
{

	public static final String	SUCCESS			= "success";
	public static final String	FAILURE			= "failure";
	public static final String	ERROR			= "error";
	public static final String	HASH			= "#";
	public static final String	HYPHEN			= "-";
	public static final String	SLASH			= "/";
	public static final String	BACKSLASH		= "\\";
	public static final String	FILE			= "file:";
	public static final String	SLASH_STARS		= SLASH + "**";
	public static final String	COMMA_SPACE		= ", ";
	public static final String	FROM			= " From ";
	public static final String	SELECT_DISTINCT	= " Select Distinct ";
	public static final String	SPACE			= " ";
	public static final String	EQUALTO			= " = ";
	public static final String	DOT				= ".";
	public static final String	COLON			= ":";
	public static final String	WHERE_1_1		= " Where 1=1 ";
	public static final String	SELECT			= " Select ";
	public static final String	UPDATE			= " Update ";
	public static final String	DELETE			= " Delete ";
	public static final String	SET				= " Set ";
	public static final String	DOUBLE_EQUAL_TO	= "==";
	public static final String	EMPTY			= "";
	public static final String	UNDERSCORE		= "_";

	private ConstUtil()
	{

	}

	public static Users getUser(Authentication auth)
	{
		if(auth != null)
			return Users.builder().employeeId(((CustomUserDetails) auth.getPrincipal()).getEmployeeId()).build();
		else
			return Users.builder().employeeId("System").build();
	}

	public static String asString(Exception excep)
	{
		StringWriter logMessageWriter = new StringWriter();
		excep.printStackTrace(new PrintWriter(logMessageWriter));
		return logMessageWriter.toString();
	}

	public static String asJson(Object object)
	{
		try
		{
			return new ObjectMapper().writeValueAsString(object);
		}
		catch (JsonProcessingException e)
		{
			e.printStackTrace();
		}
		return "Parsing Error";
	}

	public static String getCurrency(Double value)
	{
		return "&#x20B9; " + new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}
}
