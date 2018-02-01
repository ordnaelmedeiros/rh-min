package br.inf.ids.rh.rest.entity.acesso;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;

import br.inf.ids.rh.core.database.EntityId;

@Entity
@Audited
public class Acesso extends EntityId {
	
	@NotNull
	@Size(max=50)
	@Column(unique=true)
	private String nomeDeAcesso;
	
	@NotNull
	@Size(max=100)
	@Column(unique=true)
	private String email;
	
	@NotNull
	@Size(max=64)
	private String senha;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length=10)
	private AcessoSituacao situacao;
	
	public String getNomeDeAcesso() {
		return nomeDeAcesso;
	}

	public void setNomeDeAcesso(String nomeDeAcesso) {
		this.nomeDeAcesso = nomeDeAcesso;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public AcessoSituacao getSituacao() {
		return situacao;
	}

	public void setSituacao(AcessoSituacao situacao) {
		this.situacao = situacao;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
