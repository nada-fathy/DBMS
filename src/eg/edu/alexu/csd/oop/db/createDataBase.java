package eg.edu.alexu.csd.oop.db;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class createDataBase {
	String dir;
	ArrayList<Table> dbtables=new ArrayList<Table>();
	HashMap<String, Integer> tablesNames = new HashMap<String, Integer>();
	int countTables=0;
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public String createDatabase(String databaseName, boolean dropIfExists) {
		 File temp=new File(databaseName);
	        if(temp.exists()) {
	        	if(dropIfExists) {
	        		temp.delete();
	        		temp.mkdirs();
	        	}
	        }
	        else {
	        	temp.mkdirs();
	        }
	        setDir(temp.getAbsolutePath());
	        return temp.getAbsolutePath();
	       
	}
public boolean executeStructureQuery(String query) {
	query = query.replaceAll("\\s{2,}", " ").trim();
	String regex [] = query.split(" ");
	if(regex.length<3 )
		try {
			throw new Exception();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	if( regex[0].equalsIgnoreCase("create") && regex[1].equalsIgnoreCase("database")) {
		String databaseName = regex[2];
		createDatabase("sample" + System.getProperty("file.separator") + databaseName, true);
	}
	else if(regex[0].equalsIgnoreCase("create") && regex[1].equalsIgnoreCase("table")){
		String name=  regex[2]; 
		ArrayList<String> colum=new ArrayList<String>();
		ArrayList<String> colType=new ArrayList<String>();
		if(dir!=null) {
		countTables++;
		tablesNames.put(name, countTables);
		String col []= query.split("\\(")[1].split(",");
		String []colname = new String[3]; String coltype[] = new String[3]; 
		for(int i=0; i<col.length;i++) {
			colname[i] =col[i].replaceAll("\\s{2,}", " ").trim().split(" ")[0];
			coltype[i] = col[i].replaceAll("\\s{2,}", " ").trim().split(" ")[1];
			coltype[i]= coltype[i].replace(")", "");
		}
		for(int i=0;i<colname.length;i++) {
			colum.add(colname[i]);
		 if(coltype[i].equalsIgnoreCase("int"))  colType.add(colname[i]);
		}
		
		Table nt=new Table(colum,colType);
		dbtables.add(nt);
		//add xml and schema files
		
		//create xml file
				try {
				File f=new File(getDir()+System.getProperty("file.separator")+name+".xml");
				 DocumentBuilderFactory documentFactory =DocumentBuilderFactory.newInstance();
			      DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
			      Document document =documentBuilder.newDocument();
			      Element root=document.createElement(name);
			      document.appendChild(root);
			      for(int i=0;i<colum.size();i++) {
			      Element column=document.createElement(colum.get(i));
			      root.appendChild(column);
			      Attr attr=document.createAttribute("type");
			      attr.setValue(colType.get(i));
			      column.setAttributeNode(attr);}
			      try {
			    	  Transformer transformer=TransformerFactory.newInstance().newTransformer();
						Source source=new DOMSource(document);
						Result result=new StreamResult(f);
						transformer.transform(source, result);
			     
			      } catch (Exception e) {
						// TODO: handle exception
					}
			     } catch (ParserConfigurationException e) {
				  // TODO Auto-generated catch block
				  e.printStackTrace();
		        	}
				//create schema file
		
		}
		else { try {
			throw new Exception();
		} catch (Exception e) {
			
			e.printStackTrace();
		}
 return false; 	  }
	}
   else if(regex[0].equalsIgnoreCase("drop") && regex[1].equalsIgnoreCase("database")){
	   String databaseName = regex[2]; 
	   File temp=new File(databaseName);
	   if(temp.exists()) {
		   temp.delete();
		   setDir(null);
		   dbtables.clear();
		   tablesNames.clear();
		   return true; }
		   else {
			   try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}
		   }
	}
   else if( regex[0].equalsIgnoreCase("drop") && regex[1].equalsIgnoreCase("table")){
	   String name = regex[2];
		if((tablesNames.containsKey(name))) {
			int ntable=tablesNames.get(name);
			dbtables.remove(ntable);
			tablesNames.remove(name);
			countTables--;
			//delete xml file
			File f=new File(getDir()+System.getProperty("file.separator")+name+".xml");
			f.delete();
			//delete schema file		//delete xml and schema files
	}
 	else {try {
		throw new Exception();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
 return false;	}
   }
	return true;
}

}