import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.h2.engine.User;

import com.modelo.Politico;
import com.ui.MainUI;
import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;

public class Test {
	public static void main2(String[] a) throws Exception {
		// Class.forName("org.h2.Driver");
		JDBCConnectionPool pool = new SimpleJDBCConnectionPool("org.h2.Driver", "jdbc:h2:politicosh2db", "sa", "sa");
		// add application code here

		// TableQuery tq = new TableQuery("Politico", pool);
		// tq.setVersionColumn("OPTLOCK");
		// SQLContainer containerPoliticos = new SQLContainer(tq);

		FreeformQuery query = new FreeformQuery("SELECT * FROM partido WHERE sigla = 'PSB'", pool, "ID");
		SQLContainer containerPoliticos = new SQLContainer(query);

		for (Object o : containerPoliticos.getItemIds()) {
			Property p = containerPoliticos.getContainerProperty(o, "NOME");
			Object data = p.getValue();
			System.out.println(data);
		}
		// conn.close();
	}

	private static EntityManagerFactory factory;

	public static void main(String[] args) {
		factory = Persistence.createEntityManagerFactory(MainUI.PERSISTENCE_UNIT);
		EntityManager em = factory.createEntityManager();
		// Read the existing entries and write to console
		Query q = em.createQuery("SELECT p FROM Politico p where p.nome is not null");
		List<Politico> userList = q.getResultList();
		for (Politico user : userList) {
			System.out.println(user.getNome());
		}
		System.out.println("Size: " + userList.size());

		// Create new user
//		em.getTransaction().begin();
//		User user = new User();
//		user.setName("Tom Johnson");
//		user.setLogin("tomj");
//		user.setPassword("pass");
//		em.persist(user);
//		em.getTransaction().commit();

		em.close();
	}
}