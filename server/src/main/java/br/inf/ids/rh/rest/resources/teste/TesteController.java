package br.inf.ids.rh.rest.resources.teste;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.inf.ids.rh.App;

@RestController
@RequestMapping(App.api_prefix+"/v1/teste")
public class TesteController {
	
	@GetMapping("/ping")
	public String ping() {
		return "ping: " + LocalDateTime.now();
	}
	
}
