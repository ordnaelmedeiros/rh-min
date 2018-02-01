package br.inf.ids.rh.rest.resources.acesso;

import static br.inf.ids.rh.rest.entity.acesso.AcessoSituacao.ATIVO;
import static br.inf.ids.rh.rest.entity.acesso.AcessoSituacao.INATIVO;
import static br.inf.ids.rh.rest.entity.acesso.AcessoSituacao.REVALIDAR;

import java.time.LocalDateTime;

import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.inf.ids.rh.core.criptografar.Criptografar;
import br.inf.ids.rh.core.database.DataManager;
import br.inf.ids.rh.core.exceptions.RestException;
import br.inf.ids.rh.rest.entity.acesso.Acesso;
import br.inf.ids.rh.rest.entity.acesso.AcessoToken;

@Component
public class AcessoResource {
	
	@Autowired
	private DataManager dm;
	
	private Acesso byLogin(String nome, String senha) throws Exception {
		
		TypedQuery<Acesso> query = dm.createQuery(
			"select a from Acesso a "
			+ " where a.senha = :senha and "
			+ " (a.nomeDeAcesso = :nome or a.email = :nome) ", Acesso.class);
		
		query.setParameter("nome", nome);
		query.setParameter("senha", Criptografar.sha256(senha));
		
		Acesso acesso;
		try {
			acesso = query.getSingleResult();
		} catch (Exception e) {
			acesso = null;
		}
		
		return acesso;
		
	}
	
	public AcessoToken login(Login login) throws Exception {
		
		Acesso acesso = this.byLogin(login.getNome(), login.getSenha());
		
		if (acesso!=null) {

			if (ATIVO.equals(acesso.getSituacao())) {
				
				String now = LocalDateTime.now().toString();
				
				AcessoToken token = new AcessoToken();
				token.setAcesso(acesso);
				token.setToken(Criptografar.sha256(acesso.getNomeDeAcesso()+now)+Criptografar.sha256(now));
				
				dm.gravar(token);

				return token;
				
			} else if (REVALIDAR.equals(acesso.getSituacao())) {
				throw new RestException("Necessário revalidar senha");
			}
			
		}
		
		throw new RestException("Usuário ou senha inválido");
			
	}
	
	public void revalidar(Revalida revalida) throws Exception {
		
		Acesso acesso = this.byLogin(revalida.getNome(), revalida.getSenhaAntiga());
		
		if (acesso!=null && !INATIVO.equals(acesso.getSituacao())) {
			
			acesso.setSenha(revalida.getSenhaNova());
			acesso.setSituacao(ATIVO);
			acesso.setSenha(Criptografar.sha256(acesso.getSenha()));
			
			dm.alterar(acesso);
			
		} else {
		
			throw new RestException("Usuário ou senha inválido");
			
		}
		
	}

	public Acesso gravar(Acesso acesso) throws Exception {
		
		acesso.setSituacao(REVALIDAR);
		acesso.setSenha(Criptografar.sha256(acesso.getSenha()));
		
		dm.gravar(acesso);
		
		return acesso;
		
	}

	public Acesso byId(long id) {
		return dm.byId(Acesso.class, id);
	}
	
}
