package com.mvc.ratelimiter.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mvc.ratelimiter.controllers.TokenController;
import com.mvc.ratelimiter.datamodels.ResponseWrapper;
import com.mvc.ratelimiter.datamodels.Token;
import com.mvc.ratelimiter.datamodels.TokenRequest;
import com.mvc.ratelimiter.datamodels.TokenResponse;
import com.mvc.ratelimiter.services.TokenManagerImpl;


@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class TokenControllerTest {

	@InjectMocks
	TokenController tokenController;

	@Mock
	TokenManagerImpl tokenManager;
	
	@Mock
	java.util.concurrent.ConcurrentHashMap<String, Token> userTokenMap;

	@Test
	public void testTokenControllerForTokenGeneration() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		TokenRequest tokenRequest=new TokenRequest("ksingh10", "pwd");
		Token token= new Token("ksingh10","token1",1000);
		Mockito.when(userTokenMap.get("ksingh10")).thenReturn(token);
		Mockito.when(tokenManager.generateUserToken("ksingh10")).thenReturn(token);
		ResponseWrapper<TokenResponse> responseEntity = tokenController.getSecurityToken(tokenRequest);
		assertEquals(responseEntity.getResult().getUsername(),"ksingh10");
		assertEquals(responseEntity.getResult().getToken(),"token1");
	}

}
