package br.com.digitalhouse.security.permissoes;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.springframework.security.access.prepost.PreAuthorize;

public @interface CheckSecurity {
	
	public @interface Cliente {
		
		// DH01
		@PreAuthorize("isAuthenticated() and " + "hasAuthority('DH01')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeSalvarOuAtualizar {}
		
		// DH02
		@PreAuthorize("isAuthenticated() and " + "hasAuthority('DH02')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeConsultar {}
		
		// DH03
		@PreAuthorize("isAuthenticated() and " + "hasAuthority('DH03')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface PodeExcluir {}
	}
	
	
	public @interface Estado {
		
		// DH02
		@PreAuthorize("isAuthenticated() and " + "hasAuthority('DH02')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface ListarEstados {}	
		
		// DH03
		@PreAuthorize("isAuthenticated() and " + "hasAuthority('DH03')")
		@Retention(RUNTIME)
		@Target(METHOD)
		public @interface ListarCidadesPorEstado {}	
	}
	
}