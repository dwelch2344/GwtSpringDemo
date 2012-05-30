package co.davidwelch.test.GwtSpringDemo.security;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class HardCodedUserDetailsAuthentcationProvider extends AbstractUserDetailsAuthenticationProvider{

	private Logger logger = Logger.getLogger( getClass().getName() );
	private final String username, password;
	
	public HardCodedUserDetailsAuthentcationProvider(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		logger.info( String.format("additionalAuthenticationChecks requested on %s details with %s authentication", userDetails, authentication) );
	}

	@Override
	protected UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		
		
		Object creds = authentication.getCredentials();
		if( creds != null && String.class.isAssignableFrom(creds.getClass()) ){
			String pw = (String) creds;
			if( this.username.equalsIgnoreCase(username) && this.password.equals(pw) ){
				boolean enabled = true, 
						accountNonExpired = true, 
						credentialsNonExpired = true, 
						accountNonLocked = true;
				List<GrantedAuthority> authorities = Collections.emptyList();
				User user = new User(username, pw, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities );
				return user;
			}
			throw new BadCredentialsException("Invalid credentials!!");
		}
		
		// creds should never be null, so we shouldn't ever end up here
		throw new IllegalStateException("Unreachable code");
	}

	
	
}
