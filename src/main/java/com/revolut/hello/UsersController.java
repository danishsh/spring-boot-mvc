package com.revolut.hello;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

	static Log log = LogFactory.getLog(UsersController.class);
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/hello/{name}", method=RequestMethod.GET)
	public ResponseEntity<String> getUser(@PathVariable String name) {
		RevolutUser user = userService.getUser(name);
		 String message = "";
		if(user!=null) {
			String  dob = user.getDateOfBirth();
			
			//1.get the year, month and days from birthday 
			String year = dob.split("-")[0];
			String month = dob.split("-")[1];
			String days = dob.split("-")[2];
			log.info(year + " "+ month+ " "+days);
			
			//set the current year in the birthday
			LocalDate thisYrBday = LocalDate.of(LocalDate.now().getYear(), Integer.parseInt(month), Integer.parseInt(days));
			
			//get the no of days between birthday and today
			long noOfDaysBetween = ChronoUnit.DAYS.between(LocalDate.now(),thisYrBday);
			
			//if it is negative, implies birthday is passed this year
			if(noOfDaysBetween < 0 ){
				// get the number of days of birthday to next year
				noOfDaysBetween = ChronoUnit.DAYS.between(LocalDate.now(),thisYrBday.plusYears(1));
			}
			message = "{\"message\": \"Hello, "+user.getName()+"! Your birthday is in "+noOfDaysBetween+" day(s) \"}";
			
		    if(noOfDaysBetween == 0 ){
		    	message= "{\"message\": \"Hello, "+user.getName()+"! Happy Birthday! \"}";
		    }
		    return ResponseEntity.status(HttpStatus.OK).body(message);
		}
		else {
			message= "{\"message\": \"Hello, No Record found ! \"}";
			log.info("Hello, No Record found ! ");			
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
		}
	}
	@RequestMapping(value="/hello/{name}", method=RequestMethod.PUT)
		public ResponseEntity<String> updateUser(@RequestBody RevolutUser newUser, @PathVariable String name) {
		
		//validation for name -- should be only letters
		if(name != null && !"".equals(name.trim()) && validateName(name)) {
			
			//validation for birthday -- should be only YYYY-MM-DD a date before today date
			if(isValidDateFormat(newUser.getDateOfBirth())) {
				log.info("Validation Pass :Format of date of birth for "+name);
				if(isValidDate(newUser.getDateOfBirth())) {
					log.info("Validation Pass: Date is valid for "+name);
					newUser.setName(name);
					userService.updateUser(newUser);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).body("{\\\"message\\\": \\\"No Content ! \\\"}");
				}
				else {
					log.error("Validation Fail: Date is invalid for"+name);
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\\\"message\\\": \\\"Enter Valid Date of Birth  ! \\\"}");
				}
			}
			else {
				log.error("Validation Fail: Date Format is invalid for"+name);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\\\"message\\\": \\\"Enter Date of Birth in YYYY-MM-DD format ! \\\"}");
			}
		}
		else {
			log.error("Validation Fail: Invalid Name enter, only letters allowed");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\\\"message\\\": \\\"Choose a valid name ! \\\"}");
		}
	}
	
	@RequestMapping(value="/hello", method=RequestMethod.GET)
	public List<RevolutUser> getAllUsers() {
		return userService.getAllUsers();
	}

	/**
	 * Validates name for only letter
	 * @param name
	 * @return boolean true if pass, false if fail
	 */
	private static boolean validateName( String name ){
	      return name.matches( "[A-Z][a-zA-Z]*" );
	}
	
	/**
	 * Validates Date format of date of birth
	 * @param dob
	 * @return boolean true if pass, false if fail
	 */
	private boolean isValidDateFormat(String dob) {
		try {
            LocalDate.parse(dob);
            return true;
        }
        catch(DateTimeException e){
            return false;
        }
	}
	/**
	 * Validates Date of birth, checks if date is not a future date
	 * @param dob
	 * @return boolean true if pass, false if fail
	 */
	private boolean isValidDate(String dob) {
		try {
	            if ( ChronoUnit.DAYS.between(LocalDate.now(),LocalDate.parse(dob)) > 1 ) {
	            	return false;
	            }
	            else {
	            	return true;
	            }
        	}
        catch(DateTimeException e){
            return false;
        }
	}
	
}
