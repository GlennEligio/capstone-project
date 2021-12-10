/*
 * package com.example.Assignment1;
 * 
 * import java.net.URI; import java.net.URISyntaxException;
 * 
 * import org.junit.Assert; import org.junit.jupiter.api.Test; import
 * org.junit.runner.RunWith; import
 * org.springframework.boot.test.context.SpringBootTest; import
 * org.springframework.boot.test.context.SpringBootTest.WebEnvironment; import
 * org.springframework.boot.web.server.LocalServerPort; import
 * org.springframework.http.HttpEntity; import
 * org.springframework.http.HttpHeaders; import
 * org.springframework.http.HttpMethod; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.test.context.junit4.SpringRunner; import
 * org.springframework.web.client.HttpClientErrorException; import
 * org.springframework.web.client.RestTemplate;
 * 
 * import com.model.Employee;
 * 
 * @RunWith(SpringRunner.class)
 * 
 * @SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) public class
 * MyAppTest {
 * 
 * @LocalServerPort int randomServerPort;
 * 
 * @Test public void testGetMethod() throws URISyntaxException {
 * 
 * // Arrange RestTemplate template = new RestTemplate(); String baseurl =
 * "http://localhost:" + randomServerPort + "/showEmp"; URI uri = new
 * URI(baseurl); HttpHeaders headers = new HttpHeaders();
 * headers.set("X-COM-LOCATION", "USA"); HttpEntity<Employee> entity = new
 * HttpEntity<>(null, headers);
 * 
 * // Act try { template.exchange(uri, HttpMethod.GET, entity, String.class);
 * 
 * // Assert Assert.fail(); }catch(HttpClientErrorException e) {
 * Assert.assertEquals(400, e.getRawStatusCode()); } }
 * 
 * @Test public void testAddMethod() throws URISyntaxException {
 * 
 * // Arrange RestTemplate template = new RestTemplate(); String baseurl =
 * "http://localhost:" + randomServerPort + "/addEmp"; URI uri = new
 * URI(baseurl); HttpHeaders headers = new HttpHeaders();
 * headers.set("X-COM-PERSIST", "true"); Employee emp = new Employee("admin",
 * "manager", "aa@gmail.com"); HttpEntity<Employee> entity = new
 * HttpEntity<>(emp, headers);
 * 
 * // Act ResponseEntity<String> result = template.postForEntity(uri, entity,
 * String.class);
 * 
 * // Assert Assert.assertEquals(200, result.getStatusCodeValue()); } }
 */