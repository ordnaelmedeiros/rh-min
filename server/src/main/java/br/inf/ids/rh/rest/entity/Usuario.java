package br.inf.ids.rh.rest.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;

import br.inf.ids.rh.core.database.EntityId;
import br.inf.ids.rh.rest.entity.acesso.Acesso;

@Entity
@Audited
public class Usuario extends EntityId {
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="acessoId", foreignKey=@ForeignKey(name="fk_Acesso_Usuario"))
	private Acesso acesso;
	
	@Size(max=300)
	private String nome;

	public Acesso getAcesso() {
		return acesso;
	}

	public void setAcesso(Acesso acesso) {
		this.acesso = acesso;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
