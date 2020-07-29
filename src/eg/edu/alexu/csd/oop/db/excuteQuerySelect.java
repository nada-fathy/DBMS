package eg.edu.alexu.csd.oop.db;

import java.sql.SQLException;
import java.util.ArrayList;

public class excuteQuerySelect {

	public Object[][] executeQuery(String query) throws SQLException {
		query = query.replaceAll("\\s{2,}", " ").trim();
		String regex [] = query.split(" ");
		createDataBase database = new createDataBase();
		Table table = new Table(new ArrayList<String>(),new ArrayList<String>());
		Object[][] selItem = null;
		if(regex.length<4 )
			try {
				throw new Exception();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
        //select * where
	    if ( regex[0].equalsIgnoreCase("select") && regex[1].equals("*") ) {
		if(database.tablesNames.containsValue(regex[3])) {
			int k=0,h=0;
			for(int i=0; i<table.getNamesCol().size() ; i++) {
				k=0;
				if(table.getNamesCol().get(i).equals(regex[5])) {
					for(int n=0; n<table.getNameInt().size(); n++) {
    					if(regex[5] == table.getNameInt().get(n)) {selItem = new Integer[table.getTablelist().size()][table.getNamesCol().size()];}
    					else {selItem = new String[table.getTablelist().size()][table.getNamesCol().size()];}
    				}
					if(regex[6] == String.valueOf('=')) {
						for(int j=0; j<table.getTablelist().size() ; j++) {
							if(table.getTablelist().get(j).get(i) == regex[7]) {
								selItem[k++][h] = table.getTablelist().get(j).get(i);
							}
					    }h++;}
					if(regex[6] == String.valueOf('>')) {
						for(int j=0; j<table.getTablelist().size() ; j++) {
							if(Integer.parseInt(table.getTablelist().get(j).get(i)) > Integer.parseInt(regex[7])) {
								 selItem[k++][h] = table.getTablelist().get(j).get(i);
							} 
					    }h++;}
					if(regex[6] == String.valueOf('<')) {
						for(int j=0; j<table.getTablelist().size() ; j++) {
							if(Integer.parseInt(table.getTablelist().get(j).get(i)) < Integer.parseInt(regex[7])) {
								 selItem[k++][h] = table.getTablelist().get(j).get(i);
							}
					    }h++;
					}
				}
			}
		return selItem;	
		}
		}
		//select column_name
	    if ( regex[0].equalsIgnoreCase("select") && regex[1] != "*" ) {
	    	int k=0,h=0;
	    	for(int m=0 ; m<table.getNamesCol().size() ; m++) {
	    	if(database.tablesNames.containsValue(regex[3]) && table.getNamesCol().get(m).equals(regex[1])) {
	    		for(int i=0 ; i<table.getNamesCol().size(); i++) {
	    			for(int j=0; j<table.getTablelist().size() ; j++) {
	    			k=0;
	    			if(table.getNamesCol().get(i).equals(regex[5])) {
	    				for(int n=0; n<table.getNameInt().size(); n++) {
	    					if(regex[5] == table.getNameInt().get(n)) {selItem = new Integer[table.getTablelist().size()][table.getNamesCol().size()];}
	    					else {selItem = new String[table.getTablelist().size()][table.getNamesCol().size()];}
	    				}
	    				if(regex[6] == String.valueOf('>')) {
	    		    	if(Integer.parseInt(table.getTablelist().get(j).get(m)) > Integer.parseInt(regex[7])) {
	    		    		selItem[k++][h] = table.getTablelist().get(j).get(m);
	    		    	}}
	    				if(regex[6] == String.valueOf('<')) {
		    		    	if(Integer.parseInt(table.getTablelist().get(j).get(m)) < Integer.parseInt(regex[7])) {
		    		    		selItem[k++][h] = table.getTablelist().get(j).get(m);
		    		    	}}
	    				if(regex[6] == String.valueOf('=')) {
		    		    	if(table.getTablelist().get(j).get(m) == regex[7]) {
		    		    		selItem[k++][h] = table.getTablelist().get(j).get(m);
		    		    	}
		    		  }
	    			}
	    		}h++;
	    		}
	    	}
		}return selItem;
	    } 
	// select * table	
	if ( regex[0].equalsIgnoreCase("select") && regex[1].equals("*") && regex.length==4) {
    	int k=0,h=0;
    	if(database.tablesNames.containsValue(regex[3])){
    		for(int i=0; i<table.getTablelist().size(); i++) {
    			for(int j=0 ; j<table.getNamesCol().size(); j++) {
    				selItem[k++][h] = table.getTablelist().get(i).get(j);
    			}h++;
    		}
    	}
    	return selItem;
    }
	return selItem;
	}

}