package com.revolut.hello;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class UsersControllerTest extends AbstractTest {
	   @Override
	   @Before
	   public void setUp() {
	      super.setUp();
	   }
	   @Test
	   public void getUsersList() throws Exception {
	      String uri = "/hello";
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(200, status);
	      String content = mvcResult.getResponse().getContentAsString();
	      RevolutUser[] userlist = super.mapFromJson(content, RevolutUser[].class);
	      assertTrue(userlist.length > 0);
	   }
	   @Test
	   public void getUser() throws Exception {
	      String uri = "/hello/Danish";
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
	         .accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(200, status);
	   }
	   @Test
	   public void updateUser() throws Exception {
	      String uri = "/hello/Danish";
	      RevolutUser user = new RevolutUser();
	      user.setDateOfBirth("1982-02-23");
	      String inputJson = super.mapToJson(user);
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(204, status);
	   }
	   @Test
	   public void checkInvalidName() throws Exception {
	      String uri = "/hello/Danish123";
	      RevolutUser user = new RevolutUser();
	      user.setDateOfBirth("1982-02-23");
	      String inputJson = super.mapToJson(user);
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(400, status);
	   }
	   @Test
	   public void checkInvalid_day_in_date() throws Exception {
	      String uri = "/hello/John";
	      RevolutUser user = new RevolutUser();
	      user.setDateOfBirth("1982-02-30");
	      String inputJson = super.mapToJson(user);
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(400, status);
	   }
	   @Test
	   public void checkInvalid_month_in_date() throws Exception {
	      String uri = "/hello/Tom";
	      RevolutUser user = new RevolutUser();
	      user.setDateOfBirth("1982-13-10");
	      String inputJson = super.mapToJson(user);
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(400, status);
	   }
	   @Test
	   public void checkInvalid_date_format_1() throws Exception {
	      String uri = "/hello/Tom";
	      RevolutUser user = new RevolutUser();
	      user.setDateOfBirth("11-1982-10");
	      String inputJson = super.mapToJson(user);
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(400, status);
	   }
	   @Test
	   public void checkInvalid_date_format_DDMMYYYY() throws Exception {
	      String uri = "/hello/Tom";
	      RevolutUser user = new RevolutUser();
	      user.setDateOfBirth("02-10-1982");
	      String inputJson = super.mapToJson(user);
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(400, status);
	   }
	   @Test
	   public void checkValid_date_format_YYYYMMDD() throws Exception {
	      String uri = "/hello/Tim";
	      RevolutUser user = new RevolutUser();
	      user.setDateOfBirth("1982-12-10");
	      String inputJson = super.mapToJson(user);
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(204, status);
	   }
	   @Test
	   public void checkValidDate_plusTwoDay() throws Exception {
	      String uri = "/hello/Jimmy";
	      RevolutUser user = new RevolutUser();
	      LocalDate now_plusTwoDays = LocalDate.now().plusDays(2);
	      
	      user.setDateOfBirth(now_plusTwoDays.toString());
	      String inputJson = super.mapToJson(user);
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(400, status);
	   }
	   @Test
	   public void checkValidDate_plusOneMonth() throws Exception {
	      String uri = "/hello/Jimmy";
	      RevolutUser user = new RevolutUser();
	      LocalDate now_plusOneMonth = LocalDate.now().plusMonths(1);
	      user.setDateOfBirth(now_plusOneMonth.toString());
	      String inputJson = super.mapToJson(user);
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(400, status);
	   }
	   @Test
	   public void checkValidDate_plusOneWeek() throws Exception {
	      String uri = "/hello/Jimmy";
	      RevolutUser user = new RevolutUser();
	      LocalDate now_plusOneWeek = LocalDate.now().plusWeeks(1);
	      user.setDateOfBirth(now_plusOneWeek.toString());
	      String inputJson = super.mapToJson(user);
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(400, status);
	   }
	   @Test
	   public void checkValidDate_plusOneYear() throws Exception {
	      String uri = "/hello/Jimmy";
	      RevolutUser user = new RevolutUser();
	      LocalDate now_plusOneYear = LocalDate.now().plusYears(1);
	      user.setDateOfBirth(now_plusOneYear.toString());
	      String inputJson = super.mapToJson(user);
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(400, status);
	   }
	   
}