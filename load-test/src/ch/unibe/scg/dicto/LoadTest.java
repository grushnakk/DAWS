package ch.unibe.scg.dicto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.github.kevinsawicki.http.HttpRequest;

public class LoadTest {

	public static void main(String[] args) throws IOException {
		//config
		String[] testdata = {
				new String(Files.readAllBytes(Paths.get("testdata/data10.txt"))), 
				new String(Files.readAllBytes(Paths.get("testdata/data100.txt"))), 
				new String(Files.readAllBytes(Paths.get("testdata/data1000.txt")))
		};
		int iterations = 1000;
		String url = "http://localhost:4567/autocomplete";
		
		// run tests
		System.out.println("load testing: " + url);
		for(String data : testdata) {
			System.out.print("data size: " + data.length() + " characters -> ");
			long timesum = 0;
			for(int i = 0; i < iterations; i++) {
				long stampA = System.currentTimeMillis();
				HttpRequest.get(url, true, "dicto", data).code(); //executes request
				timesum += (System.currentTimeMillis() - stampA);
			} 
			System.out.println(timesum + "ms");
		}		
	}
	

}
