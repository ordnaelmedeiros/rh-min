package br.inf.ids.rh.core.acessoutil;

import static br.inf.ids.rh.rest.entity.acesso.AcessoSituacao.ATIVO;
import static br.inf.ids.rh.rest.entity.acesso.AcessoSituacao.REVALIDAR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.inf.ids.rh.core.exceptions.RestException;
import br.inf.ids.rh.rest.entity.usuario.Perfil;
import br.inf.ids.rh.rest.entity.usuario.Usuario;
import br.inf.ids.rh.rest.entity.usuario.UsuarioPerfil;
import br.inf.ids.rh.rest.resources.usuario.UsuarioResource;

@RequestScope
@Component
public class AcessoUtil {
	
	@Autowired
	private UsuarioResource uResource;
	
	public Usuario validaAcesso(String auth, Perfil ...perfis) throws RestException {
		
		Usuario usuario = uResource.byAuth(auth);
		if (usuario==null) {
			throw new RestException("Acesso Negado");
		}
		
		if (usuario.getAcesso().getSituacao().equals(ATIVO)) {
			for (Perfil perfil : perfis) {
				for (UsuarioPerfil usuarioPerfil : usuario.getPerfis()) {
					if (usuarioPerfil.getSelecionado() && perfil.equals(usuarioPerfil.getPerfil())) {
						return usuario;
					}
				}
			}
		} else if (usuario.getAcesso().getSituacao().equals(REVALIDAR)) {
			throw new RestException("Necessário revalidar senha");
		}
		throw new RestException("Acesso Negado");
		
	}
	
}
