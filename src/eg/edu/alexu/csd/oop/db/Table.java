package eg.edu.alexu.csd.oop.db;

import java.util.ArrayList;
import java.util.HashMap;

public class Table {

private	HashMap<String, Integer> hh;
private	ArrayList<ArrayList<String>> ss ;
private	ArrayList<String> namesCol ;
private	ArrayList<String> nameInt ;
	public Table(ArrayList<String> namesCol, ArrayList<String> nameInt){
		ss= new ArrayList<ArrayList<String>>();
		hh = new HashMap<String, Integer>();
		this.setNamesCol(new ArrayList<String>()); this.setNamesCol(namesCol);
		this.setNameInt(new ArrayList<String>()); this.setNameInt(nameInt);
		
		for(int i = 0;i< namesCol.size(); i++) {
			  ss.add(new ArrayList<String>());
		      hh.put(namesCol.get(i), i) ; 
		}
  //    ss.get(hh.get("id")).add("3"); ss.get(hh.get("id")).add("4"); ss.get(hh.get("id")).add("5"); ss.get(hh.get("id")).add("6");	
  //    ss.get(hh.get("date")).add("30"); ss.get(hh.get("date")).add("40"); ss.get(hh.get("date")).add("50"); ss.get(hh.get("date")).add("60");
//	  ss.get(hh.get("position")).add("eng");  ss.get(hh.get("position")).add("doc");  ss.get(hh.get("position")).add("prof"); ss.get(hh.get("position")).add("profes");	
	}
	public ArrayList<String> getNamesCol() {
		return namesCol;
	}
	public void setNamesCol(ArrayList<String> namesCol) {
		this.namesCol = namesCol;
	}
	public ArrayList<String> getNameInt() {
		return nameInt;
	}
	public void setNameInt(ArrayList<String> nameInt) {
		this.nameInt = nameInt;
	}
	public ArrayList<ArrayList<String>> getTablelist() {
		return ss;
	}
	public HashMap<String, Integer> getTableHash() {
		return hh;
	}
	public void setTableList (	ArrayList<ArrayList<String>> ss) {
		this.ss=ss;
	}
	
	
}