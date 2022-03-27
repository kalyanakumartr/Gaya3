package org.hbs.gaya.util;

import java.io.Serializable;
import java.util.UUID;

public interface EBusinessKey extends Serializable
{

	public enum EKey implements EnumInterface
	{
		Primary;

		public static String auto()
		{
			try
			{
				Thread.sleep(1);
			}
			catch (Exception excep)
			{
				Thread.currentThread().interrupt();
			}
			return UUID.randomUUID().toString();
		}

		public static String timeline(String... codes)
		{
			try
			{
				Thread.sleep(1);
			}
			catch (Exception excep)
			{
				Thread.currentThread().interrupt();
			}
			return (String.join("", codes) + System.currentTimeMillis()).toUpperCase();
		}
	}

	public String getBusinessKey(String... combination);
}
