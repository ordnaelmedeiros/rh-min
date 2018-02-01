package br.inf.ids.rh.rest.resources.acesso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.inf.ids.rh.App;
import br.inf.ids.rh.core.database.DataManager;
import br.inf.ids.rh.core.exceptions.RestException;
import br.inf.ids.rh.rest.entity.acesso.AcessoToken;

@RestController
@RequestMapping(App.api_prefix+"/v1/login")
public class LoginController {
	
	@Autowired
	private DataManager dm;
	
	@Autowired
	private AcessoResource acessoResource;
	
	@PostMapping("/login")
	public AcessoToken login(@RequestBody Login login) throws RestException {
		try {
			AcessoToken token = acessoResource.login(login);
			dm.commit();
			token.setAcesso(null);
			return token;
		} catch (Exception e) {
			throw new RestException(e.getMessage());
		} finally {
			dm.close();
		}
	}
	
	@PostMapping("/revalidar")
	public void login(@RequestBody Revalida revalida) throws RestException {
		try {
			acessoResource.revalidar(revalida);
			dm.commit();
		} catch (Exception e) {
			throw new RestException(e.getMessage());
		} finally {
			dm.close();
		}
	}
	
}
