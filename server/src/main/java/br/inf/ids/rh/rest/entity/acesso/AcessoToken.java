package br.inf.ids.rh.rest.entity.acesso;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;

import br.inf.ids.rh.core.database.EntityId;

@Entity
@Audited
public class AcessoToken extends EntityId {
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="acessoId", foreignKey=@ForeignKey(name="fk_Acesso_Token"))
	private Acesso acesso;
	
	@Size(max=128)
	private String token;

	public Acesso getAcesso() {
		return acesso;
	}

	public void setAcesso(Acesso acesso) {
		this.acesso = acesso;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
