package Ressources;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

@NamedQueries({
		@NamedQuery(name = "findGameWithType", query = "SELECT c FROM Game c WHERE c.title Like :custTitle and c.type.id = :custType"),
		@NamedQuery(name = "findGame", query = "SELECT c FROM Game c WHERE c.title Like :custTitle"),
		@NamedQuery(name = "allGame", query = "SELECT c FROM Game c") })
@javax.persistence.Entity
@Table(name = "game")
public class Game {
	@Column(name = "title")
	private String title;

	@OneToOne
	@JoinColumn(name = "type")
	private Type type;

	@Column(name = "price")
	private int price;

	@Column(name = "description")
	private String description;
	
	@Column(name = "image")
	private String image;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;
	
	@JsonIgnore
	@OneToMany(mappedBy = "game")
	private Set<Review> reviews = new HashSet<Review>();

	public Game(String title, Type type, int price, String desc) {
		this.title = title;
		this.description = desc;
		this.type = type;
		this.price = price;
	}

	public Game() {

	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String desc) {
		this.description = desc;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static List<Game> search(String game_title, Session session) {
		game_title = "%"+game_title+"%";
		List<Game> res = session.createNamedQuery("findGame").setParameter("custTitle", game_title)
				.getResultList();
		return res;
	}

	public static List<Game> search(String game_title, int type, Session session) {
		game_title = "%"+game_title+"%";
		List<Game> res = session.createNamedQuery("findGameWithType").setParameter("custTitle", game_title)
				.setParameter("custType", type).getResultList();
		return res;
	}

	public static List<Game> findAllGame(Session session) {
		List<Game> res = session.createNamedQuery("allGame").getResultList();
		return res;
	}

	public static void main(String[] args) {
		SessionFactory factory = null;
		try {
			factory = new Configuration().configure().addAnnotatedClass(Ressources.Type.class)
					.addAnnotatedClass(Ressources.User.class).addAnnotatedClass(Ressources.Library.class)
					.addAnnotatedClass(Ressources.Game.class).addAnnotatedClass(Ressources.Review.class)
					.buildSessionFactory();

		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		Session session = factory.openSession();
		Transaction tx = null;
		Integer employeeID = null;
		try {
			tx = session.beginTransaction();
			Type type = new Type("SEGA", 54);
			Game game = new Game("test", type, 10, "test");
			User user = new User("test", "test", "test", "test");
			User user2 = new User("test2", "test2", "test2", "test");
			Library lib = new Library(user, game, user2, 1);
			Review rev = new Review(user, game, "C TRO BIEN", 10);
			session.save(type);
			session.save(game);
			session.save(user);
			session.save(user2);
			session.save(lib);
			session.save(rev);
			session.refresh(user);
			System.out.println(user.getLibrary());
			System.out.println(user.getReviews());
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
	}

	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		String s = "";
		try {
			s = mapper.writeValueAsString(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return s;
	}
	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}
}
