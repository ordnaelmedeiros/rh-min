package br.inf.ids.rh.rest.entity.certificado;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.inf.ids.rh.core.database.EntityId;
import br.inf.ids.rh.rest.entity.usuario.Usuario;
import br.inf.ids.rh.rest.entity.usuario.UsuarioSerializer;

@Entity
@Audited
public class Certificado extends EntityId {
	
	@NotNull
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="usuarioId", foreignKey=@ForeignKey(name="fk_Certificado_Usuario"))
	@JsonSerialize(using=UsuarioSerializer.Serializer.class)
	private Usuario usuario;
	
	@NotNull
	@Column
	private String nome;
	
	@NotNull
	@Column
	private Integer cargaHoraria;
	
	@Column
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dataInicio;
	
	@NotNull
	@Column
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate dataTermino;
	
	@Column(columnDefinition="text")
	private String descricao;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length=30)
	private CertificadoSituacao situacao;
	
	@Column(columnDefinition="text")
	private String motivo;
	
	@Transient
	private String dados;
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCargaHoraria() {
		return cargaHoraria;
	}

	public void setCargaHoraria(Integer cargaHoraria) {
		this.cargaHoraria = cargaHoraria;
	}

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataTermino() {
		return dataTermino;
	}

	public void setDataTermino(LocalDate dataTermino) {
		this.dataTermino = dataTermino;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public CertificadoSituacao getSituacao() {
		return situacao;
	}

	public void setSituacao(CertificadoSituacao situacao) {
		this.situacao = situacao;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getDados() {
		return dados;
	}

	public void setDados(String dados) {
		this.dados = dados;
	}
	
}
