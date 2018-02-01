package br.inf.ids.rh.core.criptografar;

import java.security.MessageDigest;

public class Criptografar {
	
	public static String sha256(String senha) throws Exception {
		
		try {
			
			if (senha==null || senha.length()==0) {
				return null;
			}
			
			MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
			byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));
			
			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
				hexString.append(String.format("%02X", 0xFF & b));
			}
			String senhahex = hexString.toString();
			
			return senhahex;
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
}
