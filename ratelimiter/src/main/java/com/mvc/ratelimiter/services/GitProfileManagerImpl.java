package com.mvc.ratelimiter.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mvc.ratelimiter.adapters.GitHubApiAdapterImpl;
import com.mvc.ratelimiter.datamodels.GitProfileRequest;
import com.mvc.ratelimiter.datamodels.GitUserProfile;
import com.mvc.ratelimiter.utils.JsonUtil;

@Component
public class GitProfileManagerImpl implements GitProfileManager {

	private static final String KEY_FOR_ITEMS = "items";

	private static final Logger logger = LoggerFactory.getLogger(GitProfileManagerImpl.class);

	@Autowired
	GitHubApiAdapterImpl gitHubApiAdapter;

	@Override
	public List<GitUserProfile> processGitProfileList(List<GitProfileRequest> profileRequestList) throws Exception {

		List<GitUserProfile> gitUserList = new ArrayList<GitUserProfile>();

		Map<GitProfileRequest,Future<List<String>>> profileSearchResultMap=findUserLoginListByFirstLastNameAndLocation(profileRequestList);
		logger.info("Profile Search Result map Size {}", profileSearchResultMap.size());
		
		Set<GitProfileRequest> keySet=profileSearchResultMap.keySet();
		
		for (GitProfileRequest profile : keySet) {
			gitUserList.add(findExactUserProfileFromMatchedProfilesInParallel(profileSearchResultMap.get(profile).get(), profile));
		}
		
		return gitUserList;
	}

	private List<String> findUserLoginListByFirstLastNameAndLocation(final GitProfileRequest profile) throws Exception {

		logger.info("Searching for User {}", profile.toString());

		List<String> loginNameList = new ArrayList<String>();
		String searchResults = gitHubApiAdapter.searchGitUsers(profile.getFirstName(), profile.getLastName(),
				profile.getLocation());
		JsonObject searchJson = JsonUtil.getJsonObjectFromstring(searchResults);
		if (searchJson.has(KEY_FOR_ITEMS)) {
			JsonArray itemArray = searchJson.getAsJsonArray(KEY_FOR_ITEMS);
			for (JsonElement obj : itemArray) {
				JsonObject o = (JsonObject) obj;
				loginNameList.add(o.get("login").getAsString());
			}
		}
		return loginNameList;
	}

	private Map<GitProfileRequest,Future<List<String>>> findUserLoginListByFirstLastNameAndLocation(final List<GitProfileRequest> profileRequestList)
			throws Exception {

		Map<GitProfileRequest,Future<List<String>>> profileRequestMap= new HashMap<GitProfileRequest,Future<List<String>>>();
		ExecutorService executorServiceForSearch = Executors.newFixedThreadPool(profileRequestList.size());
		for (int i = 0; i < profileRequestList.size(); i++) {
			final int index=i;
			profileRequestMap.put(profileRequestList.get(index), executorServiceForSearch.submit(new Callable<List<String>>() {
				@Override
				public List<String> call() throws Exception {
					logger.info(Thread.currentThread().getName()+" processing for profile {}", profileRequestList.get(index).toString());
					return findUserLoginListByFirstLastNameAndLocation(profileRequestList.get(index));
				}
			}));
		}

		executorServiceForSearch.shutdown();
		return profileRequestMap;

	}
	

	private GitUserProfile findExactUserProfileFromMatchedProfilesInParallel(final List<String> loginNameList,
			final GitProfileRequest profileTobeMatched) throws Exception {

		logger.info("Matching for User {} on List of Users {}", profileTobeMatched.toString(),
				loginNameList.toString());
		GitUserProfile gitProfile = new GitUserProfile();
		ExecutorService executorServiceForMatchingProfile= Executors.newFixedThreadPool(loginNameList.size());
		
		List<Future<GitUserProfile>> list=new LinkedList<Future<GitUserProfile>>();
		
		for (String loginName : loginNameList) {
			final String localLoginName=loginName;
			list.add(executorServiceForMatchingProfile.submit(new Callable<GitUserProfile>() {
				@Override
				public GitUserProfile call() throws Exception {
					logger.info(Thread.currentThread().getName() +" Executing for {}",localLoginName );
					return gitHubApiAdapter.getUSersPublicDetails(localLoginName);
				}
			}));
			
			logger.info("gitProfile: {} ", gitProfile.toString());
			for(Future<GitUserProfile> profile:list) {
				gitProfile=profile.get();
			if (gitProfile.getLocation().equals(profileTobeMatched.getLocation()) && gitProfile.getName()
					.contains(profileTobeMatched.getFirstName() ) && gitProfile.getName().contains(profileTobeMatched.getLastName())) {
				return gitProfile;

			}

		}
		}
		executorServiceForMatchingProfile.shutdown();
		return gitProfile;
	}
	
}
