package com.mvc.ratelimiter.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mvc.ratelimiter.datamodels.ResponseWrapper;
import com.mvc.ratelimiter.datamodels.StatusBean;
import com.mvc.ratelimiter.datamodels.Token;
import com.mvc.ratelimiter.datamodels.TokenRequest;
import com.mvc.ratelimiter.datamodels.TokenResponse;
import com.mvc.ratelimiter.datastore.UserDataStoreImpl;
import com.mvc.ratelimiter.services.TokenManagerImpl;

@RestController
public class TokenController {

	@Autowired
	@Qualifier("userTokenMap")
	java.util.concurrent.ConcurrentHashMap<String, Token> userTokenMap;

	@Autowired
	TokenManagerImpl tokenManager;
	
	@Autowired
	UserDataStoreImpl userDataStore;

	private static final Logger logger = LoggerFactory.getLogger(TokenController.class);

	@RequestMapping(path = "/getSecurityToken", method = RequestMethod.POST)
	public ResponseWrapper<TokenResponse> getSecurityToken(@RequestBody TokenRequest request) throws Exception {

		logger.info("securityToken request received for user {}", request.getUsername());
		
		
		//TODO
		/*
		 *@To Do
		 * Here we need to validate the suer from the database, need to verify User name and password 
		 * I am not using any database as of now, so skipping this part and genrating token for user straight away 
		 */
		
		//if(userDataStore.getUserDetails(request)) {} // Commented as no db is being used for now...
		
		
		ResponseWrapper<TokenResponse> response = new ResponseWrapper<TokenResponse>();
		response.setStatus(StatusBean.STATUS_SUCCESS);
		System.out.println(userTokenMap.containsKey(request.getUsername()));
		if (userTokenMap.containsKey(request.getUsername()) && userTokenMap.get(request.getUsername()).getTokenExpiryTime() > System.currentTimeMillis())
		{
			response.setResult(new TokenResponse(request.getUsername(), userTokenMap.get(request.getUsername()).getUserToken()));
		}
		userTokenMap.put(request.getUsername(), tokenManager.generateUserToken(request.getUsername()));
		response.setResult(new TokenResponse(request.getUsername(), userTokenMap.get(request.getUsername()).getUserToken()));
		return response;

	}

}
