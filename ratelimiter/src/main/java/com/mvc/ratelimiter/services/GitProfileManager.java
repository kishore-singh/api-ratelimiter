package com.mvc.ratelimiter.services;

import java.util.List;

import com.mvc.ratelimiter.datamodels.GitProfileRequest;
import com.mvc.ratelimiter.datamodels.GitUserProfile;

public interface GitProfileManager {

	public List<GitUserProfile> processGitProfileList(final List<GitProfileRequest> profileRequestList) throws Exception;
}
