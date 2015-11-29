package ch.unibe.dicto;

import static spark.Spark.*;

import java.util.ArrayList;
import java.util.List;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonArray;

import ch.unibe.scg.dicto.Dicto;
import ch.unibe.scg.dicto.model.Argument;
import ch.unibe.scg.dicto.model.Environment;
import ch.unibe.scg.dicto.model.Predicate;
import ch.unibe.scg.dicto.model.Rule;
import ch.unibe.scg.dicto.model.Variable;
import ch.unibe.scg.dicto.model.VariableType;
import ch.unibe.scg.dicto.parser.Context;
import ch.unibe.scg.dicto.states2.StateMachine;
import ch.unibe.scg.dicto.states2.StateMachineResult;
import spark.Request;
import spark.Response;
import spark.Route;
public class WebService {

	@SuppressWarnings("serial")
	public static void main(String[] args) {
		final Environment env;
		
		/*
		 * env
		 */
		/*
		 * PACKAGE
		 */
		List<Argument> packageArgs = new ArrayList<>();
		packageArgs.add(new Argument("name"));
		List<Rule> packageRules = new ArrayList<>();
		packageRules.add(new Rule("depend on", new ArrayList<Predicate>(){{
			add(Predicate.MUST);
			add(Predicate.CANNOT);
			add(Predicate.CAN_ONLY);
		}}));
		packageRules.add(new Rule("access", new ArrayList<Predicate>() {{
			add(Predicate.ONLY_CAN);
		}}));
		VariableType pack = new VariableType("Package", packageArgs, packageRules);
		/*
		 * WEBSITE
		 */
		List<Argument> websiteArgs = new ArrayList<>();
		websiteArgs.add(new Argument("url"));
		List<Rule> websiteRules = new ArrayList<>();
		websiteRules.add(new Rule("have latency <", new ArrayList<Predicate>(){{
			add(Predicate.MUST);
			add(Predicate.ONLY_CAN);
		}}));
		VariableType website = new VariableType("Website", websiteArgs, websiteRules);
		/*
		 * FILE
		 */
		List<Argument> fileArgs = new ArrayList<>();
		fileArgs.add(new Argument("path"));
		List<Rule> fileRules = new ArrayList<>();
		fileRules.add(new Rule("contain text", new ArrayList<Predicate>(){{
			add(Predicate.MUST);
			add(Predicate.CANNOT);
			add(Predicate.CAN_ONLY);
			add(Predicate.ONLY_CAN);
		}}));
		VariableType file = new VariableType("File", fileArgs, fileRules);
		
		List<VariableType> types = new ArrayList<>();

		
		types.add(pack);
		types.add(website);
		types.add(file);
		List<Variable> variables = new ArrayList<>();
		env = new Environment(variables, types);
		final StateMachine machine = new Dicto().build(env);
		/*
		 * the server part
		 */
		post(new Route("/autocomplete") {
			
			@Override
			public Object handle(Request request, Response arg1) {
				String in = request.body();
				StateMachineResult result = machine.run(new Context(in), env.copy());
				arg1.type("text/json");
				JsonArray ja = Json.array(result.getSuggestions().toArray(new String[0]));
				return ja.toString();
			}
		});
	}

}
