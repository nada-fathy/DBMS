package eg.edu.alexu.csd.oop.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;


public class GeneralDBMS implements Database {
  
 Query ob = new Query();
 xml xfile = new xml();
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
 
	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {
		File temp=new File(databaseName);
        if(temp.exists()) {
        	if(dropIfExists) {
        		File files[]=temp.listFiles();
        		for(int i=0;i<files.length;i++) {
        			files[i].delete();
        		}
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

	@Override
	public boolean executeStructureQuery(String query) throws SQLException {
		
		if( ob.call(query)== null ||  ! ob.call(query).equalsIgnoreCase("structure"))
			
				throw  new SQLException();
		
		
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
			createDatabase("sample" + System.getProperty("file.separator") + databaseName.toLowerCase(), true);
		}
		else if(regex[0].equalsIgnoreCase("create") && regex[1].equalsIgnoreCase("table")){
			String name=  regex[2].split("\\(")[0]; 
			if(tablesNames.containsKey(name)) return false;
			ArrayList<String> colum=new ArrayList<String>();
			ArrayList<String> colType=new ArrayList<String>();
			if(dir!=null) {
			
			tablesNames.put(name, countTables);  countTables++;
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
			
			Table nt= new Table(colum,colType);
			dbtables.add(nt);
			//add xml and schema files
			
			//create xml file
			xfile.createXml(getDir(), name, colum, colType);	
					//create schema file
			
			}
			else {   throw new SQLException();
	  	  }
		}
	   else if(regex[0].equalsIgnoreCase("drop") && regex[1].equalsIgnoreCase("database")){
		   String databaseName = regex[2].toLowerCase(); 
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

	@Override
	public Object[][] executeQuery(String query) throws SQLException {
		if(! ob.call(query).equalsIgnoreCase("execute"))
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}

		  query = query.replaceAll("\\s{2,}", " ").trim();
			String regex [] = query.split(" ");
			int num = tablesNames.get(regex[3]);
			Table table = dbtables.get(num);
			Object[][] selItem = null;
			if(regex.length<4 )
				try {
					throw new Exception();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			for(int m=0; m<table.getNameInt().size(); m++) {
				if(table.getNameInt().get(m) == regex[3]) {
					selItem = new Integer[table.getTablelist().get(0).size()][table.getNameInt().size()];
				}
				else {
					selItem = new String[table.getTablelist().get(0).size()][table.getNamesCol().size()];
				}
			}
			
		// select * table
		if ( regex[0].equalsIgnoreCase("select") && regex[1].equals("*") && regex.length==4 && tablesNames.containsValue(regex[3])) {
	    		for(int i=0; i<table.getTablelist().get(0).size(); i++) {
	    			for(int j=0 ; j<table.getNamesCol().size(); j++) {
	    				//// h,k
	    				selItem[i][j] = table.getTablelist().get(i).get(j);
	    			}
	    		}
	    }
		
		//select * where
		if ( regex[0].equalsIgnoreCase("select") && regex[1].equals("*") && regex.length==8 && tablesNames.containsValue(regex[3])) {
			int flag =0;
					for(int l=0; l<table.getNamesCol().size() ; l++) {
						if(table.getNamesCol().get(l) == regex[5]) {
							flag=1; break;
						}
					}
			if(flag == 1) {
				int a=0;
				if(regex[6] == String.valueOf('=')) {
				     for(int i=0 ; i<table.getNamesCol().size(); i++){
					      selItem[0][i] = table.getTablelist().get(i).get(Integer.parseInt(regex[7]));
				     }
				}
				if(regex[6] == String.valueOf('>')) {
					for(int i=0; i<table.getTablelist().get(Integer.parseInt(regex[7])).size(); i++) {  
						for(int j=0; j<table.getNamesCol().size(); j++) {
							selItem[i][j] = table.getTablelist().get(j).get(i);
						}
					}
				}
				if(regex[6] == String.valueOf('<')) {
					for(int i=Integer.parseInt(regex[7]); i>0 ; i--) {
						for(int j=0; j<table.getNamesCol().size(); j++) {
							selItem[a][j] = table.getTablelist().get(j).get(i);
						}a++;
					}
				}	
			}
		}
		
		    //select column_name
			if ( regex[0].equalsIgnoreCase("select") && regex[1].equals("*") && regex.length==8 && tablesNames.containsValue(regex[3])) {
				int flag =0,n=0;
						for(int l=0; l<table.getNamesCol().size() && flag!=1 ; l++) {
							if(table.getNamesCol().get(l) == regex[5]) {
								for(n=0; n<table.getNamesCol().size(); n++) {
									if(table.getNamesCol().get(n) == regex[1]) {
										flag =1 ; break;
									}
								}
							}
						}
					
				
				if(flag == 1) {
					int a = 0;
					if(regex[6] == String.valueOf('=')) {
						selItem[0][0] = table.getTablelist().get(n).get(Integer.parseInt(regex[7]));
					}
					if(regex[6] == String.valueOf('>')) {
						for(int i=Integer.parseInt(regex[7]); i<table.getTablelist().get(n).size() ; i++) {
							selItem[0][a++] = table.getTablelist().get(n).get(i);
						}
					}
					if(regex[6] == String.valueOf('<')) {
						for(int i=Integer.parseInt(regex[7]); i>0; i--) {
							selItem[0][a++] = table.getTablelist().get(n).get(i);
						}
					}
				}
				
			}return selItem;
		
		
	    } 
	
		
	

	public int executeUpdateQuery(String query) throws SQLException {
		if(! ob.call(query).equalsIgnoreCase("update"))
			try {
				throw new Exception();
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		int result=0;
		int index=-1;
		boolean isint=false;
		boolean where=false;
		ArrayList<Integer>rows=new ArrayList<Integer>();
		ArrayList<ArrayList<String>> updated =new ArrayList<ArrayList<String>>();
		query = query.replaceAll("s{2,}", "" ).trim();
		String regex [] = query.split(" ");
		
		if(regex[0].equalsIgnoreCase("update")) {
			String name=regex[1].toLowerCase();
			if(!tablesNames.containsKey(name)) throw new SQLException();
			else {
				String temb=query;
				Table t=dbtables.get(tablesNames.get(name)); 
				updated.addAll(t.getTablelist());
				
				temb=temb.replace(regex[0],"");
				
				temb=temb.replace(regex[1],"");
			
				temb=temb.replace(regex[2],"");
			
				
				for(int i=3;i<regex.length;i++) {
					if(regex[i].equalsIgnoreCase("where")) {
						where=true;
						int indexOfWhere=i;
						for(int j=i;j<regex.length;j++) {  //query is deleted except columns and their new values
							temb=temb.replace(regex[j],"");
						}
						String condition=query;
						String cond[];
						for(int l=0;l<=indexOfWhere;l++) {
							condition=condition.replace(regex[l],"");
						}
						condition=condition.replaceAll("\\s","");
						
						if(condition.contains("<>")) {
							cond=condition.split("<>");
							
							for(int j=0;j<t.getNamesCol().size();j++) {  
								if(cond[0].equalsIgnoreCase(t.getNamesCol().get(j))) {
									index=j;
								}
							}
							if(index==-1) {
								return 0;
							}
							for(int j=0;j<t.getNameInt().size();j++) {
								if(cond[0].equalsIgnoreCase(t.getNameInt().get(j))) {
									isint=true;
								}
							}
							if(isint &&cond[1].contains("'")) {
								return 0;
							}
							if(isint&&!cond[1].contains("'")) {
								for(int j=0;j<t.getTablelist().get(index).size();j++) {
									if(Integer.parseInt(t.getTablelist().get(index).get(j))!=Integer.parseInt(cond[1])) {
										rows.add(j);
									}
								}
							}
							else if(!isint &&!cond[1].contains("'")) {
								return 0;
							}
							else {
								cond[1]=cond[1].replaceAll("'","");
								for(int j=0;j<t.getTablelist().get(index).size();j++) {
									if(!(t.getTablelist().get(index).get(j).equalsIgnoreCase(cond[1]))) {
										rows.add(j);
									}
								}
							}
						}
						
						else if(condition.contains("<=")) {
							
							cond=condition.split("<=");
							
							if(cond[1].contains("'")) {
								return 0;
							}
							
							for(int j=0;j<t.getNamesCol().size();j++) {  
								if(cond[0].equalsIgnoreCase(t.getNamesCol().get(j))) {
									index=j;
								}
							}
							if(index==-1) {
								return 0;
							}
							
							for(int j=0;j<t.getNameInt().size();j++) {
								if(cond[0].equalsIgnoreCase(t.getNameInt().get(j))) {
									isint=true;
								}
							}
							if(isint==false) {
								return 0;
							}
							for(int j=0;j<t.getTablelist().get(index).size();j++) {
								if(Integer.parseInt(t.getTablelist().get(index).get(j))<=Integer.parseInt(cond[1])) {
									rows.add(j);
								}
							}
						}
						else if(condition.contains(">=")) {
							
							cond=condition.split(">=");
							
							if(cond[1].contains("'")) {
								return 0;
							}
							
							for(int j=0;j<t.getNamesCol().size();j++) {  
								if(cond[0].equalsIgnoreCase(t.getNamesCol().get(j))) {
									index=j;
								}
							}
							if(index==-1) {
								return 0;
							}
							
							for(int j=0;j<t.getNameInt().size();j++) {
								if(cond[0].equalsIgnoreCase(t.getNameInt().get(j))) {
									isint=true;
								}
							}
							if(isint==false) {
								return 0;
							}
							for(int j=0;j<t.getTablelist().get(index).size();j++) {
								if(Integer.parseInt(t.getTablelist().get(index).get(j))>=Integer.parseInt(cond[1])) {
									rows.add(j);
								}
							}
						}
						
						else if(condition.contains("=")) {
							
							cond=condition.split("=");
							
							for(int j=0;j<t.getNamesCol().size();j++) {  
								if(cond[0].equalsIgnoreCase(t.getNamesCol().get(j))) {
									index=j;
								}
							}
							if(index==-1) {
								return 0;
							}
							for(int j=0;j<t.getNameInt().size();j++) {
								if(cond[0].equalsIgnoreCase(t.getNameInt().get(j))) {
									isint=true;
								}
							}
							if(isint &&cond[1].contains("'")) {
								return 0;
							}
							if(isint&&!cond[1].contains("'")) {
								for(int j=0;j<t.getTablelist().get(index).size();j++) {
									if(Integer.parseInt(t.getTablelist().get(index).get(j))==Integer.parseInt(cond[1])) {
										rows.add(j);
									}
								}
							}
							else if(!isint &&!cond[1].contains("'")) {
								return 0;
							}
							else {
								cond[1]=cond[1].replaceAll("'","");
								for(int j=0;j<t.getTablelist().get(index).size();j++) {
									if(t.getTablelist().get(index).get(j).equalsIgnoreCase(cond[1])) {
										rows.add(j);
									}
								}
							}
						}
						else if(condition.contains("<")) {
							
							cond=condition.split("<");
							if(cond[1].contains("'")) {   //check if target value is string
								return 0;
							}
							
							for(int j=0;j<t.getNamesCol().size();j++) {  //check the column name
								if(cond[0].equalsIgnoreCase(t.getNamesCol().get(j))) {
									index=j;
								}
							}
							if(index==-1) {
								return 0;
							}
							
							for(int j=0;j<t.getNameInt().size();j++) {
								if(cond[0].equalsIgnoreCase(t.getNameInt().get(j))) {
									isint=true;
								}
							}
							if(isint==false) {
								return 0;
							}
							for(int j=0;j<t.getTablelist().get(index).size();j++) {
								if(Integer.parseInt(t.getTablelist().get(index).get(j))<Integer.parseInt(cond[1])) {
									rows.add(j);
								}
							}
							
	
						}
						else if(condition.contains(">")) {
							
							cond=condition.split(">");
							if(cond[1].contains("'")) {
								return 0;
							}
							
							for(int j=0;j<t.getNamesCol().size();j++) {  
								if(cond[0].equalsIgnoreCase(t.getNamesCol().get(j))) {
									index=j;
								}
							}
							if(index==-1) {
								return 0;
							}
							
							for(int j=0;j<t.getNameInt().size();j++) {
								if(cond[0].equalsIgnoreCase(t.getNameInt().get(j))) {
									isint=true;
								}
							}
							if(isint==false) {
								return 0;
							}
							for(int j=0;j<t.getTablelist().get(index).size();j++) {
								if(Integer.parseInt(t.getTablelist().get(index).get(j))>Integer.parseInt(cond[1])) {
									rows.add(j);
								}
							}
						}
						
						
						
						
						
						temb=temb.replaceAll("\\s","");   //removing all spaces
						if(temb.contains(",")) {     //we have more than 1 column to be updated
							String splitOrder[]=temb.split(",");  //[column=value,column=value,.....]
							for(int j=0;j<splitOrder.length;j++) {
								isint=false;
								ArrayList<String>arr=new ArrayList<String>();
								String split[]=splitOrder[j].split("=");
								index=-1;
								for(int l=0;l<t.getNamesCol().size();l++) {  
									if(split[0].equalsIgnoreCase(t.getNamesCol().get(l))) {
										index=l;
									}
								}
								if(index==-1) {
									return 0;
								}
								for(int k=0;k<updated.get(index).size();k++) {
									if(rows.contains(k)) {
										arr.add(split[1]);
									}
									else {
										arr.add(updated.get(index).get(k));
									}
								}
								for(int l=0;l<t.getNameInt().size();l++) {
									if(split[0].equalsIgnoreCase(t.getNameInt().get(l))) {
										isint=true;
									}
								}
								if(isint&&split[1].contains("'")) {
									return 0;
								}
								if(!isint&&!split[1].contains("'")) {
									return 0;
								}
								if(isint&&!split[1].contains("'")) {
									result=rows.size();
									updated.remove(index);
									updated.add(index, arr);
								}
								if(!t.getNameInt().contains(split[0])&&split[1].contains("'")) {
									result=rows.size();
									updated.remove(index);
									updated.add(index, arr);
								
							}
								arr.clear();
						}
						}
						else {
							String split[]=temb.split("=");   // [column name,new value]
							ArrayList<String>arr=new ArrayList<String>();
							index=-1;
							isint=false;
							for(int l=0;l<t.getNamesCol().size();l++) {  
								if(split[0].equalsIgnoreCase(t.getNamesCol().get(l))) {
									index=l;
								}
							}
							if(index==-1) {
								return 0;
							}
							for(int k=0;k<updated.get(index).size();k++) {
								if(rows.contains(k)) {
									arr.add(split[1]);
								}
								else {
									arr.add(updated.get(index).get(k));
								}
							}
							for(int l=0;l<t.getNameInt().size();l++) {
								if(split[0].equalsIgnoreCase(t.getNameInt().get(l))) {
									isint=true;
								}
							}
							
							if(isint&&split[1].contains("'")) {
								return 0;
							}
							if(!isint&&!split[1].contains("'")) {
								return 0;
							}
							if(isint&&!split[1].contains("'")) {
								result=rows.size();
								updated.remove(index);
								updated.add(index, arr);
							}
							if(!isint&&split[1].contains("'")) {
								result=rows.size();
								updated.remove(index);
								updated.add(index, arr);
							}
							
						}
						i=regex.length+1;
					}
				}
				if(where==false) {
					temb=temb.replaceAll("\\s",""); 
					
					if(temb.contains(",")) {     
						String splitOrder[]=temb.split(","); 
						for(int j=0;j<splitOrder.length;j++) {
							ArrayList<String>arr=new ArrayList<String>();
							String split[]=splitOrder[j].split("=");
							index=-1;
							isint=false;
							for(int l=0;l<t.getNamesCol().size();l++) {  
								if(split[0].equalsIgnoreCase(t.getNamesCol().get(l))) {
									index=l;
								}
							}
							if(index==-1) {
								return 0;
							}
							
							for(int k=0;k<updated.get(index).size();k++) {
								arr.add(split[1]);
							}
							
							for(int l=0;l<t.getNameInt().size();l++) {
								if(split[0].equalsIgnoreCase(t.getNameInt().get(l))) {
									isint=true;
								}
							}
							if(isint&&split[1].contains("'")) {
								return 0;
							}
							
							if(!isint&&!split[1].contains("'")) {
								return 0;
							}
							
							if(isint&&!split[1].contains("'")) {
								updated.remove(index);
								updated.add(index, arr);
								result=updated.get(index).size();
							}
							
							if(!isint&&split[1].contains("'")) {
								updated.remove(index);
								updated.add(index, arr);
								result=updated.get(index).size();
							}
						}
					}
					else {
						String split[]=temb.split("=");   // [column name,new value]
						ArrayList<String>arr=new ArrayList<String>();
						
						index=-1;
						isint=false;
						for(int l=0;l<t.getNamesCol().size();l++) {  
							if(split[0].equalsIgnoreCase(t.getNamesCol().get(l))) {
								index=l;
							}
						}
						if(index==-1) {
							return 0;
						}
						for(int k=0;k<updated.get(index).size();k++) {
							arr.add(split[1]);
							
						}
						for(int l=0;l<t.getNameInt().size();l++) {
							if(split[0].equalsIgnoreCase(t.getNameInt().get(l))) {
								isint=true;
							}
						}
						
						if(isint&&split[1].contains("'")) {
							return 0;
						}
						
						if(!isint&&!split[1].contains("'")) {
							return 0;
						}
						
						if(isint&&!split[1].contains("'")) {
							updated.remove(index);
							updated.add(index, arr);
							result=updated.get(index).size();
						}
						
						if(!isint&&split[1].contains("'")) {
							updated.remove(index);
							updated.add(index, arr);
							result=updated.get(index).size();
						}
						
					}
				}
				t.setTableList(updated);
			} 
		}
		

 

		
		//insert query
		

		if(regex[0].equalsIgnoreCase("Insert")) {
			String name=regex[2];
			if(name.contains("(")) {
				name=name.split("\\(")[0];
			}
			
			if(tablesNames.containsKey(name)) {
				Table t=dbtables.get(tablesNames.get(name)); 
				updated.addAll(t.getTablelist());
				String temb=query;
				int i=0;
				temb=temb.replaceAll("\\s", "");
				int counter=t.getNamesCol().size();   //counting number of columns.
				String newValues=query;
				newValues=newValues.replaceAll("\\s", "");
				if(temb.contains(")V") || temb.contains(")v")) {   //columns are mentioned before values.
					
					
					String parse[]=temb.split("(V|v)(A|a)(L|l)(U|u)(E|e)(S|s)");
					temb=temb.replace(regex[0],"");
					temb=temb.replace(regex[1],"");
					temb=temb.replace(name,"");
					temb=temb.replace(parse[1],"");
					temb=temb.replaceAll("(V|v)(A|a)(L|l)(U|u)(E|e)(S|s)","");
					temb=temb.replace("(","");temb=temb.replace(")","");
					String col[]=temb.split(",");    //names of columns
					
					if(col.length!=counter) {
						return 0;
					}
					
					parse=newValues.split("(V|v)(A|a)(L|l)(U|u)(E|e)(S|s)");
					
					newValues=newValues.replace(parse[0],"");
					newValues=newValues.replaceAll("(V|v)(A|a)(L|l)(U|u)(E|e)(S|s)","");
					newValues=newValues.replace("(","");newValues=newValues.replace(")","");
					String values[]=newValues.split(",");
					if(values.length!=counter) {
						return 0;
					}
					for(i=0;i<col.length;i++) {   //check for all columns
						index=-1;
						isint=false;
						for(int l=0;l<t.getNamesCol().size();l++) {  
							if(col[i].equalsIgnoreCase(t.getNamesCol().get(l))) {
								index=l;
							}
						}
						if(index==-1) {
							return 0;
						}
						for(int l=0;l<t.getNameInt().size();l++) {
							if(col[i].equalsIgnoreCase(t.getNameInt().get(l))) {
								isint=true;
							}
						}
						
						if(isint&&values[i].contains("'")) {
							return 0;
						}
						if(!isint&&!values[i].contains("'")) {
							return 0;
						}
					}
					for(i=0;i<col.length;i++) {
						for(int l=0;l<t.getNamesCol().size();l++) {  
							if(col[i].equalsIgnoreCase(t.getNamesCol().get(l))) {
								index=l;
							}
						}
						ArrayList<String>arr=new ArrayList<String>();
						arr=updated.get(index);
						if(values[i].contains("'")) {
							values[i]=values[i].replaceAll("'","");
							arr.add(values[i]);
						}
						else {
							arr.add(values[i]);
						}
						
						updated.remove(index);
						updated.add(index,arr);
					}
					
					}else {
						
						String parse[]=newValues.split("(V|v)(A|a)(L|l)(U|u)(E|e)(S|s)");
						newValues=newValues.replace(parse[0],"");
						newValues=newValues.replaceAll("(V|v)(A|a)(L|l)(U|u)(E|e)(S|s)","");
						newValues=newValues.replace("(","");newValues=newValues.replace(")","");
						String values[]=newValues.split(",");
						if(values.length!=counter) {
							return 0;
						}
						for(i=0;i<t.getNamesCol().size();i++) {   //check for types only
							isint=false;
							for(int l=0;l<t.getNameInt().size();l++) {
								if(t.getNamesCol().get(i).equalsIgnoreCase(t.getNameInt().get(l))) {
									isint=true;
								}
							}
							
							if(isint&&values[i].contains("'")) {
								return 0;
							}
							if(!isint&&!values[i].contains("'")) {
								return 0;
							}
						}
						for(i=0;i<t.getNamesCol().size();i++) {
							ArrayList<String>arr=new ArrayList<String>();
							arr=updated.get(i);
							if(values[i].contains("'")) {
								values[i]=values[i].replaceAll("'","");
								arr.add(values[i]);
							}
							else {
								arr.add(values[i]);
							}
							
							updated.remove(i);
							updated.add(i,arr);
						}
						
				}
				t.setTableList(updated);
				result=1;
			}
		}
		

		//delete query
		if(regex[0].equalsIgnoreCase("delete")) {
			int flag=0;
			String name=regex[2];
			if(name.contains("(")) {
				name=name.split("\\(")[0];
			}
			if(tablesNames.containsKey(name)) {
				index=tablesNames.get(name);
				Table t=dbtables.get(tablesNames.get(name)); 
				updated.addAll(t.getTablelist());
				for(int i=0;i<regex.length;i++) {
					if(regex[i].equalsIgnoreCase("where")) {
						flag=1;
					}
				}
				if(flag==0) {      //there is no condition then remove all rows
					result=updated.get(0).size();
					tablesNames.remove(name);
					dbtables.remove(index);
					countTables--;
				}
				else {
					String temb=query;
					temb=temb.replace(regex[0],"");
					temb=temb.replace(regex[1],"");
					temb=temb.replace(name,"");
					temb=temb.replaceAll("(W|w)(H|h)(E|e)(R|r)(E|e)","");
					temb=temb.replaceAll("\\s","");
					String cond[];
					if(temb.contains("<>")) {
						index=-1;
						cond=temb.split("<>");
						
						for(int j=0;j<t.getNamesCol().size();j++) {  
							if(cond[0].equalsIgnoreCase(t.getNamesCol().get(j))) {
								index=j;
							}
						}
						if(index==-1) {
							return 0;
						}
						for(int j=0;j<t.getNameInt().size();j++) {
							if(cond[0].equalsIgnoreCase(t.getNameInt().get(j))) {
								isint=true;
							}
						}
						if(isint &&cond[1].contains("'")) {
							return 0;
						}
						if(isint&&!cond[1].contains("'")) {
							for(int j=0;j<t.getTablelist().get(index).size();j++) {
								if(Integer.parseInt(t.getTablelist().get(index).get(j))!=Integer.parseInt(cond[1])) {
									rows.add(j);
								}
							}
						}
						else if(!isint &&!cond[1].contains("'")) {
							return 0;
						}
						else {
							cond[1]=cond[1].replaceAll("'","");
							for(int j=0;j<t.getTablelist().get(index).size();j++) {
								if(!(t.getTablelist().get(index).get(j).equalsIgnoreCase(cond[1]))) {
									rows.add(j);
								}
							}
						}
					}
					
					else if(temb.contains("<=")) {
						index=-1;
						cond=temb.split("<=");
						
						if(cond[1].contains("'")) {
							return 0;
						}
						
						for(int j=0;j<t.getNamesCol().size();j++) {  
							if(cond[0].equalsIgnoreCase(t.getNamesCol().get(j))) {
								index=j;
							}
						}
						if(index==-1) {
							return 0;
						}
						
						for(int j=0;j<t.getNameInt().size();j++) {
							if(cond[0].equalsIgnoreCase(t.getNameInt().get(j))) {
								isint=true;
							}
						}
						if(isint==false) {
							return 0;
						}
						for(int j=0;j<t.getTablelist().get(index).size();j++) {
							if(Integer.parseInt(t.getTablelist().get(index).get(j))<=Integer.parseInt(cond[1])) {
								rows.add(j);
							}
						}
					}
					else if(temb.contains(">=")) {
						index=-1;
						cond=temb.split(">=");
						
						if(cond[1].contains("'")) {
							return 0;
						}
						
						for(int j=0;j<t.getNamesCol().size();j++) {  
							if(cond[0].equalsIgnoreCase(t.getNamesCol().get(j))) {
								index=j;
							}
						}
						if(index==-1) {
							return 0;
						}
						
						for(int j=0;j<t.getNameInt().size();j++) {
							if(cond[0].equalsIgnoreCase(t.getNameInt().get(j))) {
								isint=true;
							}
						}
						if(isint==false) {
							return 0;
						}
						for(int j=0;j<t.getTablelist().get(index).size();j++) {
							if(Integer.parseInt(t.getTablelist().get(index).get(j))>=Integer.parseInt(cond[1])) {
								rows.add(j);
							}
						}
					}
					
					else if(temb.contains("=")) {
						index=-1;
						cond=temb.split("=");
						for(int j=0;j<t.getNamesCol().size();j++) {  
							if(cond[0].equalsIgnoreCase(t.getNamesCol().get(j))) {
								index=j;
								
							}
						}
						if(index==-1) {
							return 0;
						}
						for(int j=0;j<t.getNameInt().size();j++) {
							if(cond[0].equalsIgnoreCase(t.getNameInt().get(j))) {
								isint=true;
							}
						}
						if(isint &&cond[1].contains("'")) {
							return 0;
						}
						if(isint&&!cond[1].contains("'")) {
							for(int j=0;j<t.getTablelist().get(index).size();j++) {
								if(Integer.parseInt(t.getTablelist().get(index).get(j))==Integer.parseInt(cond[1])) {
									rows.add(j);
								}
							}
						}
						else if(!isint &&!cond[1].contains("'")) {
							return 0;
						}
						else {
							cond[1]=cond[1].replaceAll("'","");
							for(int j=0;j<t.getTablelist().get(index).size();j++) {
								if(t.getTablelist().get(index).get(j).equalsIgnoreCase(cond[1])) {
									rows.add(j);
								}
							}
						}
					}
					else if(temb.contains("<")) {
						index=-1;
						cond=temb.split("<");
						if(cond[1].contains("'")) {   //check if target value is string
							return 0;
						}
						
						for(int j=0;j<t.getNamesCol().size();j++) {  //check the column name
							if(cond[0].equalsIgnoreCase(t.getNamesCol().get(j))) {
								index=j;
							}
						}
						if(index==-1) {
							return 0;
						}
						
						for(int j=0;j<t.getNameInt().size();j++) {
							if(cond[0].equalsIgnoreCase(t.getNameInt().get(j))) {
								isint=true;
							}
						}
						if(isint==false) {
							return 0;
						}
						for(int j=0;j<t.getTablelist().get(index).size();j++) {
							if(Integer.parseInt(t.getTablelist().get(index).get(j))<Integer.parseInt(cond[1])) {
								rows.add(j);
							}
						}
						

					}
					else if(temb.contains(">")) {
						index=-1;
						cond=temb.split(">");
						if(cond[1].contains("'")) {
							return 0;
						}
						
						for(int j=0;j<t.getNamesCol().size();j++) {  
							if(cond[0].equalsIgnoreCase(t.getNamesCol().get(j))) {
								index=j;
							}
						}
						if(index==-1) {
							return 0;
						}
						
						for(int j=0;j<t.getNameInt().size();j++) {
							if(cond[0].equalsIgnoreCase(t.getNameInt().get(j))) {
								isint=true;
							}
						}
						if(isint==false) {
							return 0;
						}
						for(int j=0;j<t.getTablelist().get(index).size();j++) {
							if(Integer.parseInt(t.getTablelist().get(index).get(j))>Integer.parseInt(cond[1])) {
								rows.add(j);
							}
						}
					}
					
					if(rows.size()==0) {
						return 0;
					}
					else {
						result=rows.size();
						for(int l=0;l<result;l++) {
							for(int j=0;j<t.getNamesCol().size();j++) {
								ArrayList<String> newValue=new ArrayList<String>();
								newValue=updated.get(j);
								index=rows.get(l);
								newValue.remove(index);
								updated.remove(j);
								updated.add(j,newValue);
							}
							for(int j=0;j<result;j++) {
								int k=rows.get(j)-1;
								rows.remove(j);
								rows.add(j, k);
							}
						}
						t.setTableList(updated);
						
					}
				}
			}
		}
		

		return result;
	}
		
		
	
	

}
