package br.inf.ids.rh;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.inf.ids.rh.core.database.DataManager;
import br.inf.ids.rh.rest.entity.Usuario;
import br.inf.ids.rh.rest.entity.acesso.Acesso;
import br.inf.ids.rh.rest.resources.usuario.UsuarioResource;

@Component
public class AppInit {

	@Autowired
	private DataManager dm;
	
    @Autowired
    private UsuarioResource usuarioResource;
    
    @PostConstruct
    public void init() throws Exception {
    	
    	try {
    		
    		Usuario usuario = usuarioResource.byId(1l);
    		
    		if (usuario==null) {
    			
    			Acesso acesso = new Acesso();
    			acesso.setNomeDeAcesso("ids.software");
    			acesso.setEmail("idssoftware@gmail.com");
    			acesso.setSenha("ids@0207");
    			
    			usuario = new Usuario();
    			usuario.setAcesso(acesso);
    			usuario.setNome("IDS Software");
    			
    			usuarioResource.gravar(usuario);
    			
    			dm.commit();
    			
    		}
    		
		} catch (Exception e) {
			throw e;
		} finally {
			dm.close();
		}
    	
    }
    
}
