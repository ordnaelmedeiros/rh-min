package br.inf.ids.rh.rest.resources.usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.inf.ids.rh.core.database.DataManager;
import br.inf.ids.rh.rest.entity.Usuario;
import br.inf.ids.rh.rest.resources.acesso.AcessoResource;

@Component
public class UsuarioResource {

	@Autowired
	private DataManager dm;
	
	@Autowired
	private AcessoResource acessoResource;
	
	public Usuario gravar(Usuario usuario) throws Exception {

		acessoResource.gravar(usuario.getAcesso());
		dm.gravar(usuario);
		return usuario;

	}

	public List<Usuario> all() {
		return dm.all(Usuario.class);
	}

	public Usuario byId(long id) {
		return dm.byId(Usuario.class, id);
	}
	
}
