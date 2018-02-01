package br.inf.ids.rh.rest.resources.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.inf.ids.rh.App;
import br.inf.ids.rh.core.database.DataManager;
import br.inf.ids.rh.core.exceptions.RestException;
import br.inf.ids.rh.rest.entity.Usuario;

@RestController
@RequestMapping(App.api_prefix + "v1/usuario")
public class UsuarioController {
	
	@Autowired
	private DataManager dm;
	
	@Autowired
	private UsuarioResource usuarioResource;
	
	
	@GetMapping
	public List<Usuario> todos(@RequestHeader(name="Authorization") String auth) throws RestException {
		try {
			return usuarioResource.all();
		} catch (Exception e) {
			throw new RestException(e);
		} finally {
			dm.close();
		}
	}
	
	@PostMapping
	public Usuario gravar(@RequestBody Usuario usuario) throws Exception {
		try {
			usuarioResource.gravar(usuario);
			dm.commit();
			return usuario;
		} catch (Exception e) {
			throw new RestException(e);
		} finally {
			dm.close();
		}
		
	}
	
	
}
