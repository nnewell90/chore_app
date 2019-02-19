package edu.missouriwestern.csmp.gis.helloworld;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	@RequestMapping("/helloworld")
	public String sayHelloWorld() {
		return "Hello World";
	}
}
