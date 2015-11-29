package ch.unibe.scg.dicto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;

public class LoadTest {
	
	static final String TEMPLATE_FILE = "testdata/templateData.txt";

	/*
	 * CONFIG
	 */
	
	static final int[] SIZES = {10, 100, 1000, 2000, 3000, 4000, 5000}; //how often the template will be concatenated (10 statements in the template
	static final int ITERATIONS = 1000; //how often each file will be send to the server
	static final String URL = "http://192.168.192.46:4567/autocomplete"; //the service url

	public static void main(String[] args) throws IOException {
		String[] data = generateData();
		long[] time = new long[data.length];
		long start;
		System.out.println("starting...");
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < ITERATIONS; j++) {
				try {
					start = System.currentTimeMillis();
					HttpRequest.post(URL).send(data[i]).ok(); //wait until response availabe
					time[i] += System.currentTimeMillis() - start;
				} catch(HttpRequestException ex) {
					j--;
					ex.printStackTrace();
				}
			}
			System.out.println("done!");
		}
		//create result
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < data.length; i++) {
			builder.append("SIZE: " + (SIZES[i] * 10)).append(" -> ").append(time[i]);
			builder.append("\n");
		}
		System.out.println(builder.toString());
	}
	
	static String[] generateData() throws IOException {
		String template = new String(Files.readAllBytes(Paths.get(TEMPLATE_FILE)));
		String[] data = new String[SIZES.length];
		StringBuilder builder = null;
		for(int i = 0; i < data.length; i++) {
			builder = new StringBuilder();
			for(int j = 0; j < SIZES[i]; j++) {
				builder.append(template.replaceAll("X", "" + j));
			}
			data[i] = builder.toString();
		}
		return data;
	}
	
}
