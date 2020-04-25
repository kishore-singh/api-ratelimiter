package com.mvc.ratelimiter.services;

import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.mvc.ratelimiter.datamodels.Token;
import com.mvc.ratelimiter.services.TokenManagerImpl;

public class TokenManagerImplTest {
	
	   @Before
	    public void initMocks(){
	        MockitoAnnotations.initMocks(this);
	    }
	   
	   @Test
	   public void generateUserTokenShouldGenerateATokeValue() throws Exception {
		   TokenManagerImpl tokenManager= new TokenManagerImpl();
		   Token token=tokenManager.generateUserToken("ksingh10");
		   assertNotNull(token);
	   }
	   
	   @Test(expected= Exception.class)
	   public void generateUserTokenShouldThrowExceptionIfUserIdIsNull() throws Exception{
		   TokenManagerImpl tokenManager= new TokenManagerImpl();
		   Token token=tokenManager.generateUserToken(null);
		   assertNotNull(token);
	   }
	   
	   @Test(expected= Exception.class)
	   public void generateUserTokenShouldThrowExceptionIfUserIdIsEmptyString() throws Exception{
		   TokenManagerImpl tokenManager= new TokenManagerImpl();
		   Token token=tokenManager.generateUserToken("");
		   assertNotNull(token);
	   }
}
