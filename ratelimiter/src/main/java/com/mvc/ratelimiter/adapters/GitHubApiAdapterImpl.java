package com.mvc.ratelimiter.adapters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.mvc.ratelimiter.datamodels.GitUserProfile;

@Component
public class GitHubApiAdapterImpl implements GitHubApiAdapter{

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${git.api.search.user.url}")
	private String gitSearchUserUrl;
	
	@Value("${git.api.user.details.url}")
	private String findUserDetailsUrl;
	
	private final static String PLUS_OPERATOR_AS_STRING="+";
	
	private final static String LOCATION_FILTER="location:";
	
	private static final Logger logger = LoggerFactory.getLogger(GitHubApiAdapterImpl.class);
	
	@Override
	public String searchGitUsers(final String firstName, final String lastName, final String location) throws Exception {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(gitSearchUserUrl).queryParam("q", firstName+PLUS_OPERATOR_AS_STRING+lastName+PLUS_OPERATOR_AS_STRING+LOCATION_FILTER+location);
		logger.info("Executing Rest Call for {}", builder.toString());
		return restTemplate.getForObject(builder.build().toUri(), String.class);
	}

	@Override
	public GitUserProfile getUSersPublicDetails(String login) throws Exception {
		final String url=findUserDetailsUrl+login;
		logger.info("GitHubApiAdapterImpl.getUSersPublicDetails final Request Url is {}", url);
		GitUserProfile gitUserDetails=restTemplate.getForObject(url, GitUserProfile.class);
		return gitUserDetails;
	}
	
}
