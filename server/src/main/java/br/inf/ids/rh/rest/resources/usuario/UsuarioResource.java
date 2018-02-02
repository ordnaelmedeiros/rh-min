package br.inf.ids.rh.rest.resources.usuario;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.inf.ids.rh.core.database.DataManager;
import br.inf.ids.rh.rest.entity.acesso.AcessoToken;
import br.inf.ids.rh.rest.entity.usuario.Perfil;
import br.inf.ids.rh.rest.entity.usuario.Usuario;
import br.inf.ids.rh.rest.entity.usuario.UsuarioPerfil;
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
		gravarPerfis(usuario);
		return usuario;

	}
	

	public void alterar(Usuario usuario) throws Exception {
		
		acessoResource.alterar(usuario.getAcesso());
		dm.alterar(usuario);
		gravarPerfis(usuario);
		
	}

	public List<Usuario> all() {
		return dm.all(Usuario.class);
	}

	public Usuario byUserName(String username) {
		
		try {
			
			return dm.createQuery("select u from Usuario u where u.nome = :nome", Usuario.class)
					.setParameter("nome", username)
					.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		}
		
	}
	
	public Usuario byId(long id) {
		Usuario usuario = dm.byId(Usuario.class, id);
		if (usuario!=null) {
			this.carregaPerfis(usuario);
		}
		return usuario;
	}
	
	public Usuario byAuth(String auth) {
		
		try {
			
			AcessoToken acessoToken = dm.createQuery(
					"select t from AcessoToken t where t.token = :token ", AcessoToken.class)
					.setParameter("token", auth).getSingleResult();
			
			Usuario usuario = dm.createQuery(
					"select u from Usuario u where u.acesso = :acesso", Usuario.class)
					.setParameter("acesso", acessoToken.getAcesso())
					.getSingleResult();
			
			this.carregaPerfis(usuario);
			
			return usuario;
			
		} catch (NoResultException e) {
			return null;
		}
		
	}
	
	private void carregaPerfis(Usuario usuario) {
		
		TypedQuery<UsuarioPerfil> query = dm.createQuery(
				" select p from UsuarioPerfil p "
				+ " where p.usuario = :usuario ",
				UsuarioPerfil.class);
		
		query.setParameter("usuario", usuario);
		
		List<UsuarioPerfil> perfis;
		try {
			perfis = query.getResultList();
		} catch (NoResultException e) {
			perfis = new ArrayList<>();
		}
		
		if (perfis==null) {
			perfis = new ArrayList<>();
		}
		
		boolean temPerfil = false;
		for (Perfil perfil : Perfil.values()) {
		
			temPerfil = false;
			for (UsuarioPerfil usuarioPerfil : perfis) {
				if (perfil.equals(usuarioPerfil.getPerfil())) {
					temPerfil = true;
					usuarioPerfil.setSelecionado(true);
					break;
				}
			}
			if (!temPerfil) {
				UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
				usuarioPerfil.setSelecionado(false);
				usuarioPerfil.setPerfil(perfil);
				perfis.add(usuarioPerfil);
			}
		}
		
		usuario.setPerfis(perfis);
		
	}
	
	private UsuarioPerfil getPerfilByUser(UsuarioPerfil perfil) {
		
		TypedQuery<UsuarioPerfil> query = dm.createQuery(
				" select p from UsuarioPerfil p "
				+ " where p.usuario = :usuario "
				+ " and p.perfil = :perfil ",
				UsuarioPerfil.class);
		query.setParameter("usuario", perfil.getUsuario());
		query.setParameter("perfil", perfil.getPerfil());
		
		UsuarioPerfil doBanco;
		try {
			doBanco = query.getSingleResult();
		} catch (NoResultException e) {
			doBanco = null;
		}
		
		return doBanco;
		
	}
	
	private void gravarPerfis(Usuario usuario) {
		
		for (UsuarioPerfil perfil : usuario.getPerfis()) {
			
			UsuarioPerfil doBanco = this.getPerfilByUser(perfil);
			
			if (perfil.getSelecionado()) {
				if (doBanco==null) {
					doBanco = new UsuarioPerfil();
					doBanco.setUsuario(usuario);
					doBanco.setPerfil(perfil.getPerfil());
					dm.gravar(doBanco);
				} else {
					doBanco.setUsuario(usuario);
					doBanco.setPerfil(perfil.getPerfil());
					dm.alterar(doBanco);
				}
			} else {
				if (doBanco!=null) {
					dm.excluir(doBanco);
				}
			}
			
		}
		
	}

}
