package Ressources;
import java.security.MessageDigest;

public class Hasher {
	public String hashing_method;
	
	public Hasher(String hm){
		this.hashing_method=hm;
	}
	
	public static String hashing(Hasher hasher, String input){
		String hashed_password = null;
		try
		{
			MessageDigest md = MessageDigest.getInstance(hasher.hashing_method);
			md.update(input.getBytes());
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}
			hashed_password = sb.toString();
			return hashed_password;
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return hashed_password;
	}
	public static void main(String[] args) {
		Hasher hasher = new Hasher("MD5");
		System.out.println(Hasher.hashing(hasher, "password"));
	}
}
