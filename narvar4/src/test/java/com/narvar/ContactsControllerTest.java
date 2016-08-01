package com.narvar;

import com.google.gson.JsonObject;
import com.narvar.Narvar4Application;
import com.narvar.domain.Contacts;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("postgres")
@WebAppConfiguration
@IntegrationTest
@SpringApplicationConfiguration(classes = Narvar4Application.class)
public class ControllerTest {
	
	
	@Autowired
	DataSource dataSource;

	RestTemplate restTemplate;


	@Before
	public void setUp() {
		restTemplate = new TestRestTemplate("user", "secret");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.execute("TRUNCATE TABLE contacts");
		jdbcTemplate.execute("INSERT INTO contacts VALUES (1, 'test1','test1@gmail.com','prof1')");
		jdbcTemplate.execute("INSERT INTO contacts VALUES (2, 'test2','test2@gmail.com','prof2')");
		
	}
	
	@Test
	public void shouldGetContacts() throws Exception {
		ResponseEntity<Map> responseEntity = restTemplate
				.getForEntity("http://localhost:8080/contactlist", Map.class);

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
		assertThat(responseEntity.getHeaders().getContentType(), is(MediaType.APPLICATION_JSON_UTF8));

		Map<String, Map<String,List<Object>>> account = responseEntity.getBody();
		assertEquals(account.get("_embedded").get("contacts").size(),2);
	}

	
	@Test
	public void shouldCreateContacts() throws Exception {
		
		// create payload
		RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/contacts/createUser";
        Map<String, String> map = new HashMap<String, String>();
        restTemplate.setMessageConverters(Arrays.asList(new StringHttpMessageConverter(), new FormHttpMessageConverter()));
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("name", "test3");
        params.set("email", "test3@gmail.com");
        params.set("profession", "prof3");
        ResponseEntity<String> entity= restTemplate.postForEntity(url, params,String.class);
        JSONObject resp=new JSONObject(entity.getBody());
        assertEquals(resp.get("name"), "test3");
        
	}

	@Test
	public void shouldUpdateContacts() throws Exception {
		
		// create payload
		RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/contacts/updateUser";
        Map<String, String> map = new HashMap<String, String>();
        restTemplate.setMessageConverters(Arrays.asList(new StringHttpMessageConverter(), new FormHttpMessageConverter()));
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.set("name", "test2");
        params.set("email", "test3@gmail.com");
        params.set("profession", "prof4");
        ResponseEntity<String> entity= restTemplate.postForEntity(url, params,String.class);
        JSONObject resp=new JSONObject(entity.getBody());
        assertEquals(resp.get("profession"), "prof4");
        
	}
	
	
	@Test
	public void shouldDeleteContacts() throws Exception {
		
		ResponseEntity<Map> responseEntity = restTemplate
				.exchange("http://localhost:8080/contacts/{name}/deleteUser",
						HttpMethod.DELETE, null, Map.class, "test2");

		assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
		assertThat(responseEntity.getBody(), nullValue());
        
	}
	
	
	
}
