package co.davidwelch.test.GwtSpringDemo.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Named;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Named("org.springframework.security.authenticationManager")
@Component
public class FakeManager extends ProviderManager{

	public FakeManager() {
		super();
		List<AuthenticationProvider> list = new ArrayList<AuthenticationProvider>();
		list.add(new AbstractUserDetailsAuthenticationProvider() {
			
			@Override
			protected UserDetails retrieveUser(String username,
					UsernamePasswordAuthenticationToken authentication)
					throws AuthenticationException {
				Collection<? extends GrantedAuthority> authorities = Collections.emptyList();
				
				return new User("user", "password", true, true, true, true, authorities);
			}
			
			@Override
			protected void additionalAuthenticationChecks(UserDetails userDetails,
					UsernamePasswordAuthenticationToken authentication)
					throws AuthenticationException {
				System.out.println("What's up");
			}
		});
		setProviders(list);
	}
	
	@Override
	public Authentication doAuthentication(Authentication authentication)
			throws AuthenticationException {
		return new UsernamePasswordAuthenticationToken(null, null);
	}

	
}
