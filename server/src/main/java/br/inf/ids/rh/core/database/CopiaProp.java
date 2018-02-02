package br.inf.ids.rh.core.database;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class CopiaProp {

	public static void copia(Object de, Object para, String ...exceptions) {
		
		List<String> exception = Arrays.asList(exceptions);
		
		for (Field paraField : para.getClass().getDeclaredFields()) {
			
			if (!exception.contains(paraField.getName())) {
				try {
					
					Field deField = de.getClass().getDeclaredField(paraField.getName());
					Object valor = deField.get(de);
					
					paraField.setAccessible(true);
					paraField.set(para, valor);
					
				} catch (Exception e) {
				}
			}
			
		}
		
	}
	
	public static void copia(Object de, Object para) {
		copia(de, para, "");
	}
	
}
