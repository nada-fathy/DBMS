package eg.edu.alexu.csd.oop.db;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Query {

public String call(String s) {
	Database ob=new GeneralDBMS();
	int flag=0;
	String regex="(U|u)(P|p)(D|d)(A|a)(T|t)(E|e)";
	Pattern p=Pattern.compile(regex);
	Matcher match=p.matcher(s);
	
	if(match.find()) {
		flag=1;
		regex="^\\s*(u|U)(P|p)(D|d)(A|a)(T|t)(e|E)\\s+\\w+\\s+(S|s)(E|e)(T|t)\\s+\\w+\\s*=\\s*(\\d+|('\\s*\\w+\\s*'\\s*))(\\s*,\\s*\\w+\\s*=\\s*(\\d+|('\\s*\\w+\\s*'\\s*)))*\\s*((W|w)(H|h)(E|e)(R|r)(E|e)\\s+\\w+\\s*(((<|>|<=|>=|=|<>)\\s*\\d+)|((=|<>)\\s*'\\s*\\w+\\s*')))?\\s*;?$";
		p=Pattern.compile(regex);
		match=p.matcher(s);
		if(match.find()) {
			return "update";
		}
	}
	regex="(S|s)(E|e)(L|l)(E|e)(C|c)(T|t)";
	p=Pattern.compile(regex);
	match=p.matcher(s);
	if(match.find()) {
		flag=1;
		regex="^\\s*(s|S)(E|e)(L|l)(E|e)(C|c)(t|T)((\\s*\\*\\s*)|(\\s+(D|d)(I|i)(S|s)(T|t)(i|I)(N|n)(C|c)(T|t)\\s+)?(\\s+(\\w+)((\\s*,\\s*\\w+)+)*\\s+))(F|f)(R|r)(o|O)(M|m)\\s+\\w+(\\s+(W|w)(H|h)(E|e)(R|r)(E|e)\\s+\\w+\\s*(((<|>|<=|>=|=|<>)\\s*\\d+)|((=|<>)\\s*'\\s*\\w+\\s*')))?\\s*;?$";
		p=Pattern.compile(regex);
		match=p.matcher(s);
		if(match.find()) {
			return "execute";
		}
	}
	
	regex="(I|i)(N|n)(S|s)(E|e)(R|r)(T|t)";
	p=Pattern.compile(regex);
	match=p.matcher(s);
	if(match.find()) {
		flag=1;
		regex="^\\s*(I|i)(N|n)(S|s)(E|e)(R|r)(T|t)\\s+(I|i)(N|n)(t|T)(O|o)\\s+\\w+\\s*(\\(\\s*\\w+(\\s*,\\s*\\w+)*\\s*\\))?\\s*(v|V)(A|a)(L|l)(U|u)(E|e)(S|s)\\s*\\(\\s*('\\s*\\w+\\s*'|\\w+)(\\s*,\\s*('\\s*\\w+\\s*'|\\w+))*\\s*\\)\\s*;?$";
		p=Pattern.compile(regex);
		match=p.matcher(s);
		if(match.find()) {
			return "update";
		}
	}
	
	regex="(C|c)(R|r)(E|e)(A|a)(T|t)(E|e)\\s+(T|t)(A|a)(B|b)(L|l)(E|e)";
	p=Pattern.compile(regex);
	match=p.matcher(s);
	if(match.find()) {
		flag=1;
		regex="^\\s*(C|c)(R|r)(E|e)(A|a)(T|t)(E|e)\\s+(T|t)(A|a)(B|b)(L|l)(E|e)\\s+\\w+\\s*\\(\\s*\\w+\\s+\\w+(\\s*,\\s*\\w+\\s+\\w+)*\\s*\\)\\s*;?$";
		p=Pattern.compile(regex);
		match=p.matcher(s);
		if(match.find()) {
			return "structure";
		}
	}
	
	regex="(C|c)(R|r)(E|e)(A|a)(T|t)(E|e)\\s+(D|d)(A|a)(T|t)(A|a)(B|b)(A|a)(S|s)(E|e)";
	p=Pattern.compile(regex);
	match=p.matcher(s);
	if(match.find()) {
		flag=1;
		regex="^\\s*(C|c)(R|r)(E|e)(A|a)(T|t)(E|e)\\s+(D|d)(A|a)(T|t)(A|a)(B|b)(A|a)(S|s)(E|e)\\s+\\w+\\s*;?$";
		p=Pattern.compile(regex);
		match=p.matcher(s);
		if(match.find()) {
			return "structure";
		}
	}
	
	regex="(D|d)(R|r)(O|o)(P|p)\\s+(D|d)(A|a)(T|t)(A|a)(B|b)(A|a)(S|s)(E|e)";
	p=Pattern.compile(regex);
	match=p.matcher(s);
	if(match.find()) {
		flag=1;
		regex="^\\s*(D|d)(R|r)(O|o)(P|p)\\s+(D|d)(A|a)(T|t)(A|a)(B|b)(A|a)(S|s)(E|e)\\s+\\w+\\s*;?$";
		p=Pattern.compile(regex);
		match=p.matcher(s);
		if(match.find()) {
			return "structure";
		}
	}
	
	
	regex="(D|d)(R|r)(O|o)(P|p)\\s+(T|t)(A|a)(B|b)(L|l)(E|e)";
	p=Pattern.compile(regex);
	match=p.matcher(s);
	if(match.find()) {
		flag=1;
		regex="^\\s*(D|d)(R|r)(O|o)(P|p)\\s+(T|t)(A|a)(B|b)(L|l)(E|e)\\s+\\w+\\s*;?$";
		p=Pattern.compile(regex);
		match=p.matcher(s);
		if(match.find()) {
			return "structure";
		}
	}
	
	regex="(D|d)(E|e)(L|l)(E|e)(T|t)(E|e)";
	p=Pattern.compile(regex);
	match=p.matcher(s);
	if(match.find()) {
		flag=1;
		regex="^\\s*(D|d)(E|e)(L|l)(E|e)(T|t)(E|e)\\s+(F|f)(R|r)(O|o)(M|m)\\s+\\w+(\\s+(W|w)(H|h)(E|e)(R|r)(E|e)\\s+\\w+\\s*(((<|>|<=|>=|=|<>)\\s*\\d+)|((=|<>)\\s*'\\s*\\w+\\s*')))?\\s*$";
		p=Pattern.compile(regex);
		match=p.matcher(s);
		if(match.find()) {
			return "update";
		}
	}
	if(flag==0) {
		System.out.println("wrong query!!");
		return null;
	}
	else {
		flag=0;
	}
	return null;
	
}
public static void main(String []args) {
    Query ob = new Query();
	String s="INSERt INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)";
	System.out.println( ob.call(s) );
 }
}
