package br.inf.ids.rh.rest.resources.certificado;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.inf.ids.rh.core.database.CopiaProp;
import br.inf.ids.rh.core.database.DataManager;
import br.inf.ids.rh.core.exceptions.RestException;
import br.inf.ids.rh.rest.entity.certificado.Certificado;
import br.inf.ids.rh.rest.entity.certificado.CertificadoDocumento;
import br.inf.ids.rh.rest.entity.certificado.CertificadoSituacao;
import br.inf.ids.rh.rest.entity.usuario.Usuario;

@RequestScope
@Component
public class CertificadoResource {
	
	@Autowired
	private DataManager dm;
	
	public Certificado byId(Long id) {
		Certificado certificado = dm.byId(Certificado.class, id);
		CertificadoDocumento documento = this.docByCertificado(id);
		certificado.setDados(documento.getDados());
		return certificado;
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
		
		if (certificado.getDados()==null || certificado.getDados().length()<20) {
			throw new RestException("Campo Dados é obrigatório");
		}
		
		certificado.setSituacao(CertificadoSituacao.PENDENTE);
		certificado.setMotivo(null);
		dm.gravar(certificado);
		
		CertificadoDocumento doc = new CertificadoDocumento();
		doc.setCertificado(certificado);
		doc.setDados(certificado.getDados());
		dm.gravar(doc);
		
	}
	
	public void alterarSemRestricao(Certificado daView) throws Exception {
		
		Certificado doBanco = dm.byId(Certificado.class, daView.getId());
		CopiaProp.copia(daView, doBanco, "id", "situacao", "motivo");
		dm.alterar(doBanco);
		
	}
	
	private CertificadoDocumento docByCertificado(Long certificadoId) {
		
		CertificadoDocumento documento = dm.createQuery(
				" select d from CertificadoDocumento d "
				+ " where d.certificado.id = :certificadoId ", 
				CertificadoDocumento.class)
				.setParameter("certificadoId", certificadoId)
				.getSingleResult();
		
		return documento;
		
	}
	
	public void alterar(Certificado daView) throws Exception {
		
		Certificado doBanco = dm.byId(Certificado.class, daView.getId());
		
		if (!doBanco.getUsuario().getId().equals(daView.getUsuario().getId())) {
			throw new RestException("Certificado só pode ser alterado por quem o cadastrou");
		}
		
		if (doBanco.getSituacao().equals(CertificadoSituacao.APROVADO)) {
			throw new RestException("Certificado aprovado não pode ser alterado");
		}
		
		CopiaProp.copia(daView, doBanco, "id", "situacao", "motivo");
		
		doBanco.setSituacao(CertificadoSituacao.PENDENTE);
		
		dm.alterar(doBanco);
		
		if (daView.getDados()==null || daView.getDados().length()<20) {
			CertificadoDocumento doc = docByCertificado(daView.getId());
			if (!doc.getDados().equals(daView.getDados())) {
				doc.setDados(daView.getDados());
				dm.alterar(doc);
			}
		}
		
		CopiaProp.copia(doBanco, daView);
		
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
	
	public void excluir(Usuario usuario, Long id) throws RestException {
		Certificado certificado = this.byId(id);
		if (!certificado.getUsuario().getId().equals(usuario.getId())) {
			throw new RestException("Certificado só pode ser excluído por que criou");
		}
		dm.excluir(certificado);
	}

	public List<Certificado> paraAprovar(Integer ano) {
		
		List<Certificado> lista = dm.createQuery(
				" select c from Certificado c "
				+ " where c.situacao = 'PENDENTE' "
				+ " and year(c.dataTermino) = :ano ",
				Certificado.class)
				.setParameter("ano", ano)
				.getResultList();
		
		return lista;
	}
	
}
