package br.inf.ids.rh.rest.resources.certificado;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.inf.ids.rh.App;
import br.inf.ids.rh.core.acessoutil.AcessoUtil;
import br.inf.ids.rh.core.database.DataManager;
import br.inf.ids.rh.core.exceptions.RestException;
import br.inf.ids.rh.rest.entity.certificado.Certificado;
import br.inf.ids.rh.rest.entity.certificado.Motivo;
import br.inf.ids.rh.rest.entity.usuario.Perfil;
import br.inf.ids.rh.rest.entity.usuario.Usuario;

@RestController
@RequestMapping(App.api_prefix + "v1/certificado")
public class CertificadoController {

	@Autowired
	private DataManager dm;
	
	@Autowired
	private AcessoUtil acessoUtil;
	
	@Autowired
	private CertificadoResource certificadoResource;
	
	@GetMapping("/usuario/{id}/ano/{ano}")
	public List<Certificado> byUserAndAno(
			@RequestHeader(name="Authorization") String auth,
			@PathVariable("id") Long usuarioId,
			@PathVariable("ano") Integer ano) throws RestException {
		
		try {
			
			this.acessoUtil.validaAcesso(auth, Perfil.ADMIN);
			return this.certificadoResource.byUsuarioAndAno(usuarioId, ano);
			
		} catch (Exception e) {
			throw new RestException(e);
		} finally {
			dm.close();
		}
		
	}
	
	@GetMapping("/logado/ano/{ano}")
	public List<Certificado> byLogadoAndAno(
			@RequestHeader(name="Authorization") String auth,
			@PathVariable("ano") Integer ano) throws RestException {
		
		try {
			
			Usuario usuario = this.acessoUtil.validaAcesso(auth, Perfil.ADMIN, Perfil.CERTIFICADO);
			return this.certificadoResource.byUsuarioAndAno(usuario.getId(), ano);
			
		} catch (Exception e) {
			throw new RestException(e);
		} finally {
			dm.close();
		}
		
	}
	
	@GetMapping("/{id}")
	public Certificado byId(
			@RequestHeader(name="Authorization") String auth,
			@PathVariable("id") Long id) throws RestException {
		
		try {
			
			this.acessoUtil.validaAcesso(auth, Perfil.ADMIN, Perfil.CERTIFICADO);
			Certificado certificado = this.certificadoResource.byId(id);
			return certificado;
			
		} catch (Exception e) {
			throw new RestException(e);
		} finally {
			dm.close();
		}
		
	}
	
	@PostMapping("/logado")
	public Certificado gravar(
			@RequestHeader(name="Authorization") String auth,
			@RequestBody Certificado certificado) throws RestException {
		
		try {
			
			Usuario usuario = this.acessoUtil.validaAcesso(auth, Perfil.ADMIN, Perfil.CERTIFICADO);
			certificado.setUsuario(usuario);
			certificadoResource.gravar(certificado);
			dm.commit();
			return certificado;
			
		} catch (Exception e) {
			throw new RestException(e);
		} finally {
			dm.close();
		}

	}
	
	@PutMapping("/logado")
	public Certificado alterar(
			@RequestHeader(name="Authorization") String auth,
			@RequestBody Certificado certificado) throws RestException {
		
		try {
			
			Usuario usuario = this.acessoUtil.validaAcesso(auth, Perfil.ADMIN, Perfil.CERTIFICADO);
			certificado.setUsuario(usuario);
			certificadoResource.alterar(certificado);
			dm.commit();
			return certificado;
			
		} catch (Exception e) {
			throw new RestException(e);
		} finally {
			dm.close();
		}

	}
	
	@DeleteMapping("/logado/{id}")
	public void remover(
			@RequestHeader(name="Authorization") String auth,
			@PathVariable("id") Long id) throws RestException {
		try {
			
			Usuario usuario = this.acessoUtil.validaAcesso(auth, Perfil.ADMIN, Perfil.CERTIFICADO);
			certificadoResource.excluir(usuario, id);
			dm.commit();
			
		} catch (Exception e) {
			throw new RestException(e);
		} finally {
			dm.close();
		}

	}
	
	@GetMapping("/aprovar/{ano}")
	public List<Certificado> paraAprovarNoAno(
			@RequestHeader(name="Authorization") String auth,
			@PathVariable("ano") Integer ano) throws RestException {
		
		try {
			
			this.acessoUtil.validaAcesso(auth, Perfil.ADMIN);
			return this.certificadoResource.paraAprovar(ano);
			
		} catch (Exception e) {
			throw new RestException(e);
		} finally {
			dm.close();
		}
		
	}
	
	@PutMapping("/aprovar/{id}")
	public void aprovar(
			@RequestHeader(name="Authorization") String auth,
			@PathVariable("id") Long id,
			@RequestBody Motivo motivo) throws RestException {
		
		try {
			
			this.acessoUtil.validaAcesso(auth, Perfil.ADMIN);
			certificadoResource.aprovar(id, motivo.getDescricao());
			dm.commit();
			
		} catch (Exception e) {
			throw new RestException(e);
		} finally {
			dm.close();
		}

	}
	
	@PutMapping("/reprovar/{id}")
	public void reprovar(
			@RequestHeader(name="Authorization") String auth,
			@PathVariable("id") Long id,
			@RequestBody Motivo motivo) throws RestException {
		
		try {
			
			this.acessoUtil.validaAcesso(auth, Perfil.ADMIN);
			certificadoResource.reprovar(id, motivo.getDescricao());
			dm.commit();
			
		} catch (Exception e) {
			throw new RestException(e);
		} finally {
			dm.close();
		}

	}
	
}
