package br.inf.ids.rh;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class PropriedadesConexao {

	private static Properties prop;
	
	public static String postgresHost() {
		return prop.getProperty("postgres.host");
	}
	
	public static String postgresPort() {
		return prop.getProperty("postgres.port");
	}
	
	public static String postgresDatabase() {
		return prop.getProperty("postgres.database");
	}
	
	public static String postgresUser() {
		return prop.getProperty("postgres.user");
	}
	
	public static String postgresPassword() {
		return prop.getProperty("postgres.password");
	}
	
	public static String serverPort() {
		return prop.getProperty("server.port");
	}
	
	public static void verifica() throws Exception {
		
		List<String> listaProp = Arrays.asList(
				"postgres.host",
				"postgres.port",
				"postgres.database",
				"postgres.user",
				"postgres.password",
				"server.port");
		
		try (Scanner keyboard = new Scanner(System.in)) {
			
			File file = new File("rh.properties");
			if (!file.exists()) {
				file.createNewFile();
			}
			System.out.println("Lendo arquivo de configuração: " + file.getAbsolutePath());
			
			prop = new Properties();
			prop.load(new FileInputStream(file));
			
			String valor = null;
			for (String propriedade : listaProp) {
				
				valor = prop.getProperty(propriedade);
				
				while (valor==null || valor.length()<2) {
					System.out.println("Informe "+propriedade+":");
					valor = keyboard.nextLine().trim();
					prop.setProperty(propriedade, valor);
				}
				
			}
			
			prop.store(new FileOutputStream(file), null);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
	}
	
}
