package br.inf.ids.rh.rest.resources.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.inf.ids.rh.App;
import br.inf.ids.rh.core.acessoutil.AcessoUtil;
import br.inf.ids.rh.core.database.DataManager;
import br.inf.ids.rh.core.exceptions.RestException;
import br.inf.ids.rh.rest.entity.usuario.Perfil;
import br.inf.ids.rh.rest.entity.usuario.Usuario;

@RestController
@RequestMapping(App.api_prefix + "v1/usuario")
public class UsuarioController {
	
	@Autowired
	private DataManager dm;
	
	@Autowired
	private UsuarioResource usuarioResource;
	
	@Autowired
	private AcessoUtil acessoUtil;
	
	@GetMapping("/todos")
	public List<Usuario> todos(@RequestHeader(name="Authorization") String auth) throws RestException {
		try {
			
			acessoUtil.validaAcesso(auth, Perfil.ADMIN);
			
			return usuarioResource.all();
		} catch (Exception e) {
			throw new RestException(e);
		} finally {
			dm.close();
		}
	}
	
	@GetMapping("/{id}")
	public Usuario byId(
			@RequestHeader(name="Authorization") String auth,
			@PathVariable("id") Long id) throws RestException {
		try {
			
			acessoUtil.validaAcesso(auth, Perfil.ADMIN);
			return usuarioResource.byId(id);
			
		} catch (Exception e) {
			throw new RestException(e);
		} finally {
			dm.close();
		}
	}
	
	@GetMapping(value="/logado")
	public Usuario byToken(@RequestHeader(name="Authorization") String auth) throws RestException {
		try {
			
			Usuario usuario = usuarioResource.byAuth(auth);
			return usuario;
			
		} catch (Exception e) {
			throw new RestException(e);
		} finally {
			dm.close();
		}
	}
	
	@PostMapping
	public Usuario gravar(
			@RequestHeader(name="Authorization") String auth,
			@RequestBody Usuario usuario) throws Exception {
		try {
			
			acessoUtil.validaAcesso(auth, Perfil.ADMIN);
			
			usuarioResource.gravar(usuario);
			dm.commit();
			return usuario;
		} catch (Exception e) {
			throw new RestException(e);
		} finally {
			dm.close();
		}
		
	}
	
	@PutMapping
	public Usuario alterar(
			@RequestHeader(name="Authorization") String auth,
			@RequestBody Usuario usuario) throws Exception {
		try {
			
			acessoUtil.validaAcesso(auth, Perfil.ADMIN);
			usuarioResource.alterar(usuario);
			dm.commit();
			return usuario;
		} catch (Exception e) {
			throw new RestException(e);
		} finally {
			dm.close();
		}
		
	}
	
	@PutMapping("/logado")
	public Usuario alterarLogado(@RequestHeader(name="Authorization") String auth) throws Exception {
		try {
			
			acessoUtil.validaAcesso(auth, Perfil.ADMIN, Perfil.CERTIFICADO);
			Usuario usuario = usuarioResource.byAuth(auth);
			usuarioResource.alterar(usuario);
			
			dm.commit();
			return usuario;
			
		} catch (Exception e) {
			throw new RestException(e);
		} finally {
			dm.close();
		}
		
	}
	
	
}
