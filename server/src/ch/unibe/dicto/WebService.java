package ch.unibe.dicto;

import static spark.Spark.*;
import spark.Request;
import spark.Response;
import spark.Route;

public class WebService {
	

	public static void main(String[] args) {

		get(new Route("/hello") {
			
			@Override
			public Object handle(Request arg0, Response arg1) {
				return "Hello World";
			}
		});

	}

}
