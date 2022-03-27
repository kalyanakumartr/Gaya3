package org.hbs.gaya.util;

public class CustomException extends Exception
{
	private static final long	serialVersionUID	= -3033215444998388428L;
	private String				logMessage;
	private String				logMessageTrace;
	private String				logExcepType		= "Exception";
	private boolean				isTriggerEmail		= false;

	public CustomException()
	{
	}

	public CustomException(String logMessage)
	{
		this.logMessage = logMessage;
	}

	public CustomException(String logMessage, boolean isTriggerEmail)
	{
		this.logMessage = logMessage;
		this.isTriggerEmail = isTriggerEmail;
	}

	public CustomException(String logMessage, String logExcepType)
	{
		this.logMessage = logMessage;
		this.logExcepType = logExcepType;
	}

	public CustomException(String logMessage, String logExcepType, boolean isTriggerEmail)
	{
		this.logMessage = logMessage;
		this.logExcepType = logExcepType;
		this.isTriggerEmail = isTriggerEmail;
	}

	public String getLogExcepType()
	{
		return logExcepType;
	}

	public String getLogInfoMessage()
	{
		return logMessage;
	}

	public String getLogMessage()
	{
		this.logMessage = ConstUtil.asString(this);
		return logMessage;
	}

	public String getLogMessageTrace()
	{
		return logMessageTrace;
	}

	public boolean isTriggerEmail()
	{
		return isTriggerEmail;
	}

	public void setLogExcepType(String logExcepType)
	{
		this.logExcepType = logExcepType;
	}

	public void setLogMessage(String logMessage)
	{
		this.logMessage = logMessage;
	}

	public void setLogMessageTrace(String logMessageTrace)
	{
		this.logMessageTrace = logMessageTrace;
	}

	public void setTriggerEmail(boolean isTriggerEmail)
	{
		this.isTriggerEmail = isTriggerEmail;
	}

}
