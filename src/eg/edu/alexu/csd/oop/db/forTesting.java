package eg.edu.alexu.csd.oop.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class forTesting {

	public static void main(String[] args) throws SQLException {
		HashMap<String, Integer> hh = new HashMap<String, Integer>();
	//	System.out.println( "CREATE   TABLE   table_name1(column_name1 varchar , column_name2    int,  column_name3 varchar)    "
	//			.replaceAll("\\s{2,}", " ").trim().split(" ")[4]  );
         
		String query1 = "sample" + System.getProperty("file.separator") + "testDB" ;
		String query2 = "CREATE TABLE table_name1(column_name1 varchar, column_name2 int, column_name3 varchar)";
		
	GeneralDBMS gg = new GeneralDBMS();
  // System.out.println(	gg.executeStructureQuery("CREATE DATABASE TestDB") );
  // System.out.println(  gg.executeStructureQuery(query2) );
//	System.out.println( gg.executeQuery("select * from table_name1" ).length  );
	/*	String que = "CREATE TABLE table_name6(column_name1 varchar, column_name2 int, column_name3 varchar)";
				que = que.replaceAll("\\s{2,}", " ").trim();
				String name= que.split(" ")[2];
				String col []= que.split("\\(")[1].split(",");
				String []colname = new String[3]; String coltype[] = new String[3]; 
				for(int i=0; i<col.length;i++) {
					colname[i] =col[i].replaceAll("\\s{2,}", " ").trim().split(" ")[0];
					coltype[i] = col[i].replaceAll("\\s{2,}", " ").trim().split(" ")[1];
					coltype[i]= coltype[i].replace(")", "");
				}
				for(int i = 0; i<colname.length;i++) {
					System.out.println("name= "+ colname[i] + " type= "+coltype[i]);
				}
				*/
						
				createDataBase cdb = new createDataBase();
	//	System.out.println(cdb.executeStructureQuery("create database testDb"));
	//	System.out.println(cdb.executeStructureQuery("drop database testDb"));
	//	System.out.println(cdb.executeStructureQuery("CREATE TABLE table_name1(column_name1 varchar , column_name2  int,  column_name3 varchar) "));
		
 /*		//create table
		int numcol =3;
	   String[] names = {"name1", "name2", "name3" };
   	 ArrayList<ArrayList<String>> ss= new ArrayList<ArrayList<String>>(numcol);
   	for(int i = 0; i < numcol; i++)  {
        ss.add(new ArrayList<String>());
        hh.put(names[i], i);
    } 
   	
   	//update table
    String changes[] ={"name1", "name3", "name2"}; String[] values = {"makrm", "db", "1"};
  
   	for(int i=0; i<changes.length;i++) {
   		int colind = hh.get(changes[i]);
   		ss.get(colind).add(values[i]);
   	}
 
   	//select all table 
   	for(int i= 0; i<ss.get(0).size(); i++) {
   		for(int j =0; j<numcol;j++) {
   			System.out.print(ss.get(j).get(i) + "   ");
   		} System.out.println();
   	}
   	//select 1 coulm 
   	for(int i= 0; i<ss.get(0).size(); i++) {
   			System.out.print(ss.get(hh.get("name2")).get(i) + "   ");	
   	} */

	

				
				GeneralDBMS ob=new GeneralDBMS();
			/*	ob.executeStructureQuery("CREATE DATABASE TestDB");
				ob.executeStructureQuery("create table table_name1(id int, date int, position varchar) ");
		   //  	System.out.println(	ob.executeQuery("SELECT * FROM table_name1 where date < 55" )[0].length ) ;
				int result;
				try {
					
					result = ob.executeUpdateQuery("update table_name1 set date=9 where date<55");
					System.out.println(result);
					System.out.println(ob.executeUpdateQuery("update table_name1 set date=12 where date<11"));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  */
 //>>>>>>> makrm
				int result;
			
				try {
					ob.executeStructureQuery("CREATE DATABASE TestDB");
				//	result = ob.executeUpdateQuery("update table_name1 set date=9 where date<55");
				//	System.out.println(result);
					System.out.println(ob.executeStructureQuery("CREATE TABLE table_name4(column_name1 varchar, column_name2 int, column_name3 varchar)"));
					System.out.println(ob.executeUpdateQuery("INSERT INTO table_name4(column_name1, column_name3, column_name2) VALUES ('value1', 'value3', 4)"));
					System.out.println(ob.executeUpdateQuery("UPDATE table_name4 SET column_name1='1111111111', column_name2=2222222, column_name3='333333333'"));
					System.out.println(ob.executeStructureQuery("CREATE TABLE table_name5(column_name1 varchar, column_name2 int, column_name3 varchar)"));
					System.out.println(ob.executeUpdateQuery("INSERT INTO table_name5(column_name1, column_name3, column_name2) VALUES ('value1', 'value3', 4)"));
					System.out.println(ob.executeUpdateQuery("UPDATE table_name5 SET column_name1='1111111111', column_name2=2222222, column_name3='333333333'"));
					System.out.println(ob.executeStructureQuery("CREATE TABLE table_name6(column_name1 varchar, column_name2 int, column_name3 varchar)"));
					System.out.println(ob.executeUpdateQuery("INSERT INTO table_name6(column_name1, column_name3, column_name2) VALUES ('value1', 'value3', 4)"));
					System.out.println(ob.executeUpdateQuery("UPDATE table_name6 SET column_name1='1111111111', column_name2=2222222, column_name3='333333333'"));
					System.out.println(ob.executeStructureQuery("Create TABLE table_name7(column_name1 varchar, column_name2 int, column_name3 varchar)"));
					System.out.println(ob.executeUpdateQuery("INSERT INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)"));
					System.out.println(ob.executeUpdateQuery("INSERt INTO table_name1(column_NAME1, COLUMN_name3, column_name2) VALUES ('value1', 'value3', 4)"));
					System.out.println(ob.executeUpdateQuery("UPDATE table_namE1 SET column_name1='11111111', COLUMN_NAME2=22222222, column_name3='333333333' WHERE coLUmn_NAME3='VALUE3'"));
					System.out.println(ob.executeStructureQuery("CREATE TABLE table_name2(column_name1 varchar, column_name2 int, column_name3 varchar)"));
					System.out.println(ob.executeUpdateQuery("INSERt INTO table_name2(column_name1, column_name3, column_name2) VALUES ('value1', 'value3', 4)"));
					System.out.println(ob.executeUpdateQuery("DELETE From table_name2  WHERE column_name1='value1'"));
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
   	
	}

}
