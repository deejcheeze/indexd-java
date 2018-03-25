package cdis.indexd.auth;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import cdis.indexd.Env;
import cdis.indexd.enums.Role;
import cdis.indexd.model.User;


@Stateless
public class JwtTokenizer implements AuthTokenizer {
	
	@Inject
	private Env env;
	
	@Inject
	private Logger logger;
	
	private Algorithm algoHmac;

	private String tokenSecret;
	
	@Inject
	public JwtTokenizer() {
		
	}
	
	public JwtTokenizer(String secret) {
		this.tokenSecret = secret;
		logger = LoggerFactory.getLogger(getClass());
	}
	
	@PostConstruct
	public void init() {
		
		try {
			if(this.tokenSecret == null) {
				this.tokenSecret = env.getEnv("index.token");
			}
			algoHmac = Algorithm.HMAC256(this.tokenSecret);
		} catch (IllegalArgumentException | UnsupportedEncodingException e) {
			logger.error("Exception initializing JWT", e);
		}
	}

	@Override
	public String create(User user) {
		
		LocalDate createTime = LocalDate.now().plusDays(30);
		Date from = Date.from(createTime.atStartOfDay(ZoneId.systemDefault()).toInstant());
		
		String token = JWT.create()
				.withExpiresAt(from)
				.withSubject(user.getUsername())
				.withIssuer("nurl.ng")
				.withClaim("email", user.getUsername())
				.withClaim("role", user.getRole().name())
				.sign(algoHmac);
		return token;
	}

	@Override
	public User verify(String token) {
		JWTVerifier verifier = JWT.require(algoHmac)
				.withIssuer("nurl.ng")
				.build();
		
		try {
			DecodedJWT jwt = verifier.verify(token);
			Claim role = jwt.getClaim("role");
			String userEmail = jwt.getSubject();
			
			User user = new User();
			user.setUsername(userEmail);
			user.setRole(Role.valueOf(role.asString()));
			return user;
		} catch (JWTVerificationException e) {
			logger.error("Invalid JWT token", e);
		}
		return null;
	}
	
	public static void main(String[] args) {
		JwtTokenizer jwt = new JwtTokenizer("AwtTokin");
		jwt.init();
		
		User user = new User();
		user.setUsername("aaaaaa@sss");
		
		System.out.println(jwt.create(user));
	}
	
}
