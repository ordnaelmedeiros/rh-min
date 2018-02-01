package br.inf.ids.rh.core.database;

import javax.persistence.EntityManager;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.inf.ids.rh.rest.entity.Usuario;
import br.inf.ids.rh.rest.entity.acesso.Acesso;
import br.inf.ids.rh.rest.entity.acesso.AcessoToken;

public class EntityManagerUtil {

	private static SessionFactory sessions = null;
	
	static {
		
		Configuration cfg = new Configuration()
			    
				.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect")
				
				.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver")
				.setProperty("hibernate.connection.username", "postgres")
				.setProperty("hibernate.connection.password", "123456")
				.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/rh_min")
				
				.setProperty("hibernate.c3p0.min_size", "5")
				.setProperty("hibernate.c3p0.max_size", "20")
				.setProperty("hibernate.c3p0.timeout", "1800")
				.setProperty("hibernate.c3p0.max_statements", "50")
				
				//.setProperty("org.hibernate.envers.default_schema", "public")
				.setProperty("org.hibernate.envers.audit_table_suffix", "_aud")
				
				.setProperty("hibernate.hbm2ddl.auto", "update")
				.setProperty("hibernate.format_sql", "false")
				.setProperty("hibernate.show_sql", "false");
		
		cfg.addAnnotatedClass(Acesso.class);
		cfg.addAnnotatedClass(AcessoToken.class);
		cfg.addAnnotatedClass(Usuario.class);
		
		
		//cfg.addAnnotatedClass(Endereco.class);
		//cfg.addAnnotatedClass(Telefone.class);
		
		sessions = cfg.buildSessionFactory();
		
	}

	public EntityManager getEntityManager() {
		return sessions.createEntityManager();
	}
	
}