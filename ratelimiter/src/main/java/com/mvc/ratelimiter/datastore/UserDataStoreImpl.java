package com.mvc.ratelimiter.datastore;

import org.springframework.stereotype.Component;

import com.mvc.ratelimiter.datamodels.TokenRequest;

@Component
public class UserDataStoreImpl implements UserDataStore{

	@Override
	public boolean getUserDetails(TokenRequest user) {
		// TODO Auto-generated method stub
		return true;
	}

}
