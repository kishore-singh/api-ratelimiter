package com.mvc.ratelimiter.adapters;

import com.mvc.ratelimiter.datamodels.GitUserProfile;

public interface GitHubApiAdapter {

	public GitUserProfile getUSersPublicDetails(final String login) throws Exception;

	public String searchGitUsers(final String firstName, final String lastName, final String location) throws Exception;
}
