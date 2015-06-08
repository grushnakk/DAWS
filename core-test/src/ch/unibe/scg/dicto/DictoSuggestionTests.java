package ch.unibe.scg.dicto;

import static ch.unibe.scg.dicto.DictoAcceptanceDefStatementTests.setUp;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import ch.unibe.scg.dicto.states.StateResult;

public class DictoSuggestionTests {

	@Test
	public void newID() {
		StateResult result = setUp("Vie");
		List<String> actual = result.getSuggestions();
		List<String> expected = list();
		assertEquals(expected, actual);
	}
	
	@Test
	public void type() {
		StateResult result = setUp("View = ");
		List<String> actual = result.getSuggestions();
		List<String> expected = list("Package", "Class", "Website", "File", "Component", "XMLTag", "Attribute");
		assertEquals(expected, actual);
	}
	@Test
	public void with() {
		StateResult result = setUp("View = Package ");
		List<String> actual = result.getSuggestions();
		List<String> expected = list("with");
		assertEquals(expected, actual);
	}
	
	@Test
	public void argName() {
		StateResult result = setUp("View = Package with ");
		List<String> actual = result.getSuggestions();
		List<String> expected = list("name");
		assertEquals(expected, actual);
	}
	
	public List<String> list(String... args) {
		List<String> list = new ArrayList<>();
		for(String arg : args) {
			list.add(arg);
		}
		return list;
	}
}
