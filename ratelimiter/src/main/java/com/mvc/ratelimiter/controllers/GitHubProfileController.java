package com.mvc.ratelimiter.controllers;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mvc.ratelimiter.datamodels.GitProfileRequest;
import com.mvc.ratelimiter.datamodels.GitUserProfile;
import com.mvc.ratelimiter.datamodels.ResponseWrapper;
import com.mvc.ratelimiter.datamodels.StatusBean;
import com.mvc.ratelimiter.services.GitProfileManagerImpl;

/**
 * 
 * @author Kishore
 * This Controller Handles request for GitHub Profiles (For both guest and registered users)
 */

@RestController
public class GitHubProfileController {

	@Autowired
	GitProfileManagerImpl GitProfileManager;

	private static final Logger logger = LoggerFactory.getLogger(GitHubProfileController.class);

	@RequestMapping(path = "/getGitHubProfileDetails", method = RequestMethod.POST)
	public ResponseWrapper<List<GitUserProfile>> getGitHubProfileDetails(
			@RequestBody List<GitProfileRequest> gitProfilRrequestList) throws Exception {
		logger.info("getGitHubProfileDetails request received for {} users", gitProfilRrequestList.size());
		ResponseWrapper<List<GitUserProfile>> response = new ResponseWrapper<List<GitUserProfile>>();
		List<GitUserProfile> gitUserList = GitProfileManager.processGitProfileList(gitProfilRrequestList);
		response.setStatus(StatusBean.STATUS_SUCCESS);
		response.setResult(gitUserList);
		return response;
	}

}
