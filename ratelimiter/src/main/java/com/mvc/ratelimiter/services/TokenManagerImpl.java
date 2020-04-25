package com.mvc.ratelimiter.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.mvc.ratelimiter.datamodels.Token;

@Component
public class TokenManagerImpl implements TokenManager{

	@Autowired
	@Qualifier("userTokenMap")
	java.util.concurrent.ConcurrentHashMap<String, Token> userTokenMap;

	private static final Logger logger = LoggerFactory.getLogger(TokenManagerImpl.class);
	@Override
	public Token generateUserToken(String userId) throws Exception {
		logger.info("Generating user token for {}", userId);
		
		if(userId==null || userId.equals("")) {
			throw new Exception("Valid user id or Ip addres need to be passed in argument, Ca'nt Generate Token for null");
		}
		long code=Code();//method calling 
		String userToken=""; 
		for (long i=code;i!=0;i/=100)//a loop extracting 2 digits from the code  
		    { 
		        long digit=i%100;//extracting two digits 
		        if (digit<=90) 
		        digit=digit+32;  
		        //converting those two digits(ascii value) to its character value 
		        char ch=(char) digit; 
		        // adding 32 so that our least value be a valid character  
		        userToken=ch+userToken;//adding the character to the string 
		    } 
		long tokenExpiryTime=System.currentTimeMillis()+ 24*60*60*1000; // Setting Token Expiry Time for 24 hours
		Token token= new Token(userId, userToken, tokenExpiryTime);
		return token;
	}

	@Override
	public boolean validateUserToken(String userId, String userToken) throws Exception {
		if(userTokenMap.containsKey(userId) && (userTokenMap.get(userId).getTokenExpiryTime() > System.currentTimeMillis())) {
			return true;
		}
		
		return false;
	}

	public static long Code() //this code returns the  unique 16 digit code  
	{  //creating a 16 digit code using Math.random function 
	    long code   =(long)((Math.random()*9*Math.pow(10,15))+Math.pow(10,15)); 
	    return code; //returning the code 
	} 
	
	
	
}
