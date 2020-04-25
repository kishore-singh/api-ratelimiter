package com.mvc.ratelimiter.datastore;

import com.mvc.ratelimiter.datamodels.TokenRequest;

/**
 * 
 * @author aapnam
 *  Uerdatastore is for handling the user related operations from
 *         datastore like, getting details, updating details.
 *
 */

public interface UserDataStore {

	public boolean getUserDetails(final TokenRequest user);

}
