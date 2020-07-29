package eg.edu.alexu.csd.oop.db;

import java.io.File;
import java.util.ArrayList;

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
import org.w3c.dom.Node;

public class xml {
	public void createXml(String dir,String name,ArrayList<String > colum,ArrayList<String > colType) {
		try {
			File f=new File(dir+System.getProperty("file.separator")+name+".xml");
			 DocumentBuilderFactory documentFactory =DocumentBuilderFactory.newInstance();
		      DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
		      Document document =documentBuilder.newDocument();
		      Element root=document.createElement(name);
		      document.appendChild(root);
		      for(int i=0;i<colum.size();i++) {
		      Element column=document.createElement(colum.get(i));
		      root.appendChild(column);
		      Attr attr=document.createAttribute("type");
		      if(colType.contains(colum.get(i))) {
		      attr.setValue("int");}
		      else{attr.setValue("String");}
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
	}
	public void deleteXml(String dir,String name) {
		File f=new File(dir+System.getProperty("file.separator")+name+".xml");
		f.delete();
	}
	public void updateXml(String dir,String name,int n,ArrayList<String> col) {
	try {
		File f=new File(dir+System.getProperty("file.separator")+name+".xml");
		 DocumentBuilderFactory documentFactory =DocumentBuilderFactory.newInstance();
	     DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
	     Document document =documentBuilder.parse(f);
	     Node table=document.getFirstChild();
	     Node oldcol=table.getChildNodes().item(n);
	     String temp=oldcol.getNodeName();
	     String type=oldcol.getAttributes().getNamedItem("type").getTextContent();
	     Element newcol=document.createElement(temp);
	     Attr attr=document.createAttribute("type");
	     attr.setValue(type);
	     newcol.setAttributeNode(attr);
	     for(int i=0;i<col.size();i++) {
	     Element r=document.createElement("id"+Integer.toString(i));
	     r.setTextContent(col.get(i));
	     newcol.appendChild(r);
	     }
	     table.replaceChild(newcol, oldcol);
	     try {
	   	  Transformer transformer=TransformerFactory.newInstance().newTransformer();
				Source source=new DOMSource(document);
				Result result=new StreamResult(f);
				transformer.transform(source, result);
	    
	     } catch (Exception e) {
				// TODO: handle exception
			} 
	} catch (Exception e) {
		// TODO: handle exception
	}
	}
}
