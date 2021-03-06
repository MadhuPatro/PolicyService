package com.cts.application.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cts.application.document.Policy;
import com.cts.application.service.PolicyService;


@RunWith(SpringRunner.class)
@WebMvcTest(value = PolicyServiceController.class, secure = false)
public class PolicyControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PolicyService policyService;


	@Test
	public void getAllPolicies() throws Exception {
		Map<String, Object> mockAdminResp = new HashMap<String, Object>();
		List<Policy> policies = new ArrayList<Policy>();
		Policy policy1 = new Policy();
		policy1.setPolicyNumber("1");
		policy1.setPolicyName("Policy1");
		policy1.setPolicyDetails("Details1");
		Policy policy2 = new Policy();
		policy2.setPolicyNumber("2");
		policy2.setPolicyName("Policy2");
		policy2.setPolicyDetails("Details2");
		policies.add(policy1);
		policies.add(policy2);
		
		mockAdminResp.put("policies", policies);
		Mockito.when(policyService.getAllPolicies()).thenReturn(policies);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/policies/getAllPolicies")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println("Result is > " + result.getResponse().getContentAsString());
		String expected = "{\r\n" + 
				"    \"policies\": [\r\n" + 
				"        {\r\n" + 
				"            \"policyNumber\": \"1\",\r\n" + 
				"            \"policyName\": \"Policy1\",\r\n" + 
				"            \"policyDetails\": \"Details1\"\r\n" + 
				"        },\r\n" + 
				"        {\r\n" + 
				"            \"policyNumber\": \"2\",\r\n" + 
				"            \"policyName\": \"Policy2\",\r\n" + 
				"            \"policyDetails\": \"Details2\"\r\n" + 
				"        }\r\n" + 
				"    ],\r\n" + 
				"    \"message\": \"Policies found successfully\",\r\n" + 
				"    \"status\": \"1\"\r\n" + 
				"}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}
	
}
