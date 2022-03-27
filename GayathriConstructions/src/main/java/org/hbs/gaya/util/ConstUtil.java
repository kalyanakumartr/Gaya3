package org.hbs.gaya.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class ConstUtil
{

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
}
