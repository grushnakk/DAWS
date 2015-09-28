package ch.unibe.scg.dicto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.github.kevinsawicki.http.HttpRequest;

public class LoadTest {

	public static void main(String[] args) throws IOException {
		String data = new String(Files.readAllBytes(Paths.get("testdata/data10.txt")));
		
		System.out.println(HttpRequest.get("http://localhost:4567/autocomplete", true, "dicto", data).body());
	}
	

}
