package br.inf.ids.rh;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.inf.ids.rh.core.database.DataManager;
import br.inf.ids.rh.rest.entity.acesso.Acesso;
import br.inf.ids.rh.rest.entity.usuario.Perfil;
import br.inf.ids.rh.rest.entity.usuario.Usuario;
import br.inf.ids.rh.rest.entity.usuario.UsuarioPerfil;
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
    		
    		Usuario usuario = usuarioResource.byUserName("IDS Software");
    		
    		if (usuario==null) {
    			
    			UsuarioPerfil perfil = new UsuarioPerfil();
    			perfil.setPerfil(Perfil.ADMIN);
    			perfil.setSelecionado(true);
    			
    			Acesso acesso = new Acesso();
    			acesso.setNomeDeAcesso("ids.software");
    			acesso.setEmail("idssoftware@gmail.com");
    			acesso.setSenha("ids@0207");
    			
    			usuario = new Usuario();
    			usuario.setAcesso(acesso);
    			usuario.setNome("IDS Software");
    			usuario.setPerfis(Arrays.asList(perfil));
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
