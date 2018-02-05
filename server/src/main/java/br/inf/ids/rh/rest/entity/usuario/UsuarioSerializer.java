package br.inf.ids.rh.rest.entity.usuario;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class UsuarioSerializer {

	public static class Serializer extends JsonSerializer<Usuario> {

		@Override
		public void serialize(Usuario usuario, JsonGenerator gen, SerializerProvider pro) throws IOException, JsonProcessingException {
			
			Usuario usu = new Usuario();
			usu.setId(usuario.getId());
			usu.setNome(usuario.getNome());
			
			gen.writeObject(usu);
			
		}
		
	}
	
}
