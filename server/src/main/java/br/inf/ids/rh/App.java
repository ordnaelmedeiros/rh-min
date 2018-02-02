package br.inf.ids.rh;

import static br.inf.ids.rh.PropriedadesConexao.serverPort;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Controller
@EnableAutoConfiguration
@SpringBootApplication
public class App {

	public static final String api_prefix = "api/";
	
    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "RH<br/><a href='swagger-ui.html'>Doc<a/>";
    }

    public static void main(String[] args) throws Exception {
    	
    	PropriedadesConexao.verifica();
    	//args[args.length] = "-server.port="+serverPort();
    	String[] args2 = new String[1];
    	args2[0] = "--server.port="+serverPort();
    	SpringApplication.run(App.class, args2);
    	
    }
    
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
          .addResourceLocations("classpath:/META-INF/resources/");
     
        registry.addResourceHandler("/webjars/**")
          .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    
}
