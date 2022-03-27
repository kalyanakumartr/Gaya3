package org.hbs.gaya.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

public final class Masker
{
	private static final String	SHA_256	= "SHA-256";
	private static final String	SHA_1	= "SHA-1";
	private static final String	MD5		= "MD5";

	public static synchronized String decrypt(String encryptText, String decoding)
	{
		try
		{
			byte[] decodedBytes = Base64.decodeBase64(encryptText.getBytes(decoding));
			return new String(decodedBytes);
		}
		catch (UnsupportedEncodingException excep)
		{

		}
		return null;
	}

	public static synchronized String decryptBase64(String decryptText)
	{
		byte[] decodedBytes = Base64.decodeBase64(decryptText.getBytes());
		return new String(decodedBytes);
	}

	public static synchronized String encrypt(String plaintext, String algorithm)
	{
		try
		{
			MessageDigest msgDigest = MessageDigest.getInstance(algorithm);
			msgDigest.update(plaintext.getBytes(StandardCharsets.UTF_8));
			byte[] encodedBytes = Base64.encodeBase64(msgDigest.digest());
			return new String(encodedBytes);
		}
		catch (NoSuchAlgorithmException excep)
		{
			excep.printStackTrace();

		}
		return null;
	}

	public static synchronized String encryptBase64(String encryptText)
	{
		byte[] encodedBytes = Base64.encodeBase64(encryptText.getBytes());
		return new String(encodedBytes);
	}

	public static String generateMD5(String message) throws CustomException
	{
		return hashString(message, MD5);
	}

	public static String generateSHA1(String message) throws CustomException
	{
		return hashString(message, SHA_1);
	}

	public static String generateSHA256(String message) throws CustomException
	{
		return hashString(message, SHA_256);
	}

	private static String hashString(String message, String algorithm) throws CustomException
	{
		try
		{
			MessageDigest digest = MessageDigest.getInstance(algorithm);
			byte[] hashedBytes = digest.digest(message.getBytes(StandardCharsets.UTF_8));

			return convertByteArrayToHexString(hashedBytes);
		}
		catch (NoSuchAlgorithmException ex)
		{
			throw new CustomException("Could not generate hash from String" + ex);
		}
	}

	private static String convertByteArrayToHexString(byte[] arrayBytes)
	{
		StringBuilder stringBuffer = new StringBuilder();
		for (int i = 0; i < arrayBytes.length; i++)
		{
			stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return stringBuffer.toString();
	}

	private Masker()
	{

	}

}
