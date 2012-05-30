package co.davidwelch.test.GwtSpringDemo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import co.davidwelch.test.GwtSpringDemo.model.Address;
import co.davidwelch.test.GwtSpringDemo.model.Person;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home() {
	    	logger.info("requesting home");
		return "home";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login() {
	    	logger.info("requesting login");
		return "login";
	}

	
	@RequestMapping(value="/test", method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<String> blah(HttpServletResponse response){
		response.setContentType("application/json");
		
		ResponseEntity<String> result = new ResponseEntity<String>("Hello World", HttpStatus.ALREADY_REPORTED);
		return result;
	}
	
	@RequestMapping(value="/test2.gwt-json", method=RequestMethod.GET)
	public @ResponseBody Person personTest(){
		Address a = new Address();
		a.setStreet("123 Spooner Street");
		a.setCity("Quohog");
		a.setProvidence("Rhode Island");
		a.setCountry("USA");
		a.setPostalCode("84121-0234");
		
		Person result = new Person();
		result.setName("Peter Griffin");
		result.setAddress(a);
		return result;
	}
	
	@RequestMapping(value="/test-post.gwt-json", method=RequestMethod.POST)
	public @ResponseBody Person postTest(@RequestBody Address fromClient){
		Person result = new Person();
		result.setName("Peter Griffin");
		result.setAddress(fromClient);
		return result;
	}
	
	private List<Person> people = new ArrayList<Person>();
	
	@RequestMapping(value="test-get-people.gwt-json", method=RequestMethod.GET)
	public @ResponseBody List<Person> getPersonList(){
		return people;
	}
	
	@RequestMapping(value="test-post-people.gwt-json", method=RequestMethod.POST)
	public @ResponseBody List<Person> postPersonList(@RequestBody List<Person> people){
		logger.info("Received new list for people. " + people.size() + " entries.");
		this.people = people;
		return people;
	}
	
	
}

