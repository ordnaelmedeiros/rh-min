package br.inf.ids.rh.rest.resources.certificado;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.inf.ids.rh.core.database.CopiaProp;
import br.inf.ids.rh.core.database.DataManager;
import br.inf.ids.rh.core.exceptions.RestException;
import br.inf.ids.rh.rest.entity.certificado.Certificado;
import br.inf.ids.rh.rest.entity.certificado.CertificadoDocumento;
import br.inf.ids.rh.rest.entity.certificado.CertificadoSituacao;
import br.inf.ids.rh.rest.entity.usuario.Usuario;

@Component
public class CertificadoResource {
	
	@Autowired
	private DataManager dm;
	
	public Certificado byId(Long id) {
		return dm.byId(Certificado.class, id);
	}
	
	public List<Certificado> doUsuario(Usuario usuario) {
		
		TypedQuery<Certificado> query = dm.createQuery(
				" select c from Certificado c "
				+ " where c.usuario = :usuario "
				, Certificado.class);
		query.setParameter("usuario", usuario);
		
		return query.getResultList();
		
	}
	
	public void gravar(Certificado certificado) throws Exception {
		certificado.setSituacao(CertificadoSituacao.PENDENTE);
		certificado.setMotivo(null);
		dm.gravar(certificado);
	}
	
	public void alterarSemRestricao(Certificado daView) throws Exception {
		
		Certificado doBanco = dm.byId(Certificado.class, daView.getId());
		CopiaProp.copia(daView, doBanco, "id", "situacao", "motivo");
		dm.alterar(doBanco);
		
	}
	
	public void alterar(Certificado daView) throws Exception {
		
		Certificado doBanco = dm.byId(Certificado.class, daView.getId());
		if (doBanco.getSituacao().equals(CertificadoSituacao.APROVADO)) {
			throw new RestException("Certificado aprovado não pode ser alterado");
		}
		CopiaProp.copia(daView, doBanco, "id", "situacao", "motivo");
		
		dm.alterar(doBanco);
		
	}
	
	private void situacao(Long id, CertificadoSituacao situacao, String motivo) throws Exception {
		Certificado certificado = dm.byId(Certificado.class, id);
		certificado.setSituacao(situacao);
		certificado.setMotivo(motivo);
		dm.alterar(certificado);
	}
	
	public void aprovar(Long id, String motivo) throws Exception {
		this.situacao(id, CertificadoSituacao.APROVADO, motivo);
	}
	public void reprovar(Long id, String motivo) throws Exception {
		this.situacao(id, CertificadoSituacao.REPROVADO, motivo);
	}
	
	public void gravarDocumento(Long certificadoId, String dados) {
		
		Certificado certificado = this.byId(certificadoId);
		
		TypedQuery<CertificadoDocumento> query = dm.createQuery(
				" select d CertificadoDocumento d "
				+ " where d.certificado = :certificado "
				, CertificadoDocumento.class);
		
		query.setParameter("certificado", certificado);
		
		CertificadoDocumento doc;
		
		try {
			doc = query.getSingleResult();
		} catch (NoResultException e) {
			doc = null;
		}
		
		boolean gravar = false;
		if (doc==null) {
			gravar = true;
			doc = new CertificadoDocumento();
		}
		
		doc.setCertificado(certificado);
		doc.setDados(dados);
		
		if (gravar) {
			dm.gravar(doc);
		} else {
			dm.alterar(doc);
		}
		
	}
	
	public CertificadoDocumento getDocumento(Long certificadoId) {
		
		Certificado certificado = this.byId(certificadoId);
		
		TypedQuery<CertificadoDocumento> query = dm.createQuery(
				" select d CertificadoDocumento d "
				+ " where d.certificado = :certificado "
				, CertificadoDocumento.class);
		
		query.setParameter("certificado", certificado);
		
		CertificadoDocumento documento = query.getSingleResult();
		return documento;
		
	}

	public List<Certificado> byUsuarioAndAno(Long usuarioId, Integer ano) {
		
		return dm.createQuery(
				" select c from Certificado c "
				+ " where c.usuario.id = :usuarioId "
				+ " and year(c.dataTermino) = :ano ",
				Certificado.class)
				.setParameter("usuarioId", usuarioId)
				.setParameter("ano", ano)
				.getResultList();
		
	}
	
}
