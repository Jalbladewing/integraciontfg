package com.tfgllopis.integracion;

import org.mindrot.jbcrypt.BCrypt;

public class CheckPassword 
{
	private static int LOGROUNDS = 11;
	
	 public static String hash(String password) {
	        return BCrypt.hashpw(password, BCrypt.gensalt(LOGROUNDS));
	    }

	    public static boolean verifyHash(String password, String hash) 
	    {
	        return BCrypt.checkpw(password, hash);
	    }
}
