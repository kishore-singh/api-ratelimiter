package com.mvc.ratelimiter.main;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.mvc.ratelimiter.main.RateLimiterImpl;

@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class RateLimiterImplTest {

	@InjectMocks
	RateLimiterImpl rateLimiter;
	
	@Mock
	java.util.concurrent.ConcurrentHashMap<String, CopyOnWriteArrayList<Long>> userRequestMap;
	
	@Mock
	java.util.concurrent.ConcurrentHashMap<String, Long> usagePolicyVoilatingUsers;
	
	String userId;

	@Before
	public void setUp() {
		userId="ksingh10";
		//RateLimiter.rateLimiterTimeUnit="1";
		rateLimiter.setRateLimiterTimeUnit("1");
		CopyOnWriteArrayList<Long> list= new CopyOnWriteArrayList<Long>();
		list.add(System.currentTimeMillis());
		list.add(System.currentTimeMillis()-1000);
		Mockito.when(userRequestMap.containsKey(userId)).thenReturn(true);
		Mockito.when(userRequestMap.get(userId)).thenReturn(list);
	}
	
	@Test
	public void verifyIfUserLimitExistShouldReturnTrueIfRequestListSizeIsLessThenLimit() throws Exception {
		assertTrue(rateLimiter.verifyIfUserLimitExist(userId, 3, System.currentTimeMillis()));
	}
	
	@Test
	public void verifyIfUserLimitExistShouldReturnFalseIfRequestListSizeIsLessThenLimit() throws Exception {
		assertTrue(!rateLimiter.verifyIfUserLimitExist(userId, 2, System.currentTimeMillis()));
	}
}
