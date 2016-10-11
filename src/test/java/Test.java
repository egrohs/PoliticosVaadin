import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;

public class Test {
	public static void main(String[] a) throws Exception {
		//Class.forName("org.h2.Driver");
		JDBCConnectionPool pool = new SimpleJDBCConnectionPool("org.h2.Driver", "jdbc:h2:politicosh2db", "sa", "sa");
		// add application code here

//		TableQuery tq = new TableQuery("Politico", pool);
//		tq.setVersionColumn("OPTLOCK");
		//SQLContainer containerPoliticos = new SQLContainer(tq);

		FreeformQuery query = new FreeformQuery(
		        "SELECT * FROM POLITICO", pool, "NOME");
		SQLContainer containerPoliticos = new SQLContainer(query);
		
		
		for (Object o : containerPoliticos.getItemIds()) {
			Property p = containerPoliticos.getContainerProperty(o, "NOME");
			Object data = p.getValue();
			System.out.println(data);
		}
		// conn.close();
	}
}