package br.inf.ids.rh.rest.entity.certificado;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.inf.ids.rh.core.database.EntityId;

@Entity
@Audited
public class CertificadoDocumento extends EntityId {

	@JsonIgnore
	@NotNull
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="certificadoId", foreignKey=@ForeignKey(name="fk_Certificado_Documento"))
	private Certificado certificado;
	
	@Lob
	@NotNull
	private String dados;

	public Certificado getCertificado() {
		return certificado;
	}

	public void setCertificado(Certificado certificado) {
		this.certificado = certificado;
	}

	public String getDados() {
		return dados;
	}

	public void setDados(String dados) {
		this.dados = dados;
	}
	
}
