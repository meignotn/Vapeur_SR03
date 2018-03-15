package Ressources;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

@NamedQueries({
		@NamedQuery(name = "findUser", query = "SELECT c FROM User c WHERE c.name Like :custName or c.nickname like :custName"),
		@NamedQuery(name = "findUserByMail", query = "SELECT c FROM User c WHERE c.mail Like :custMail"),
		@NamedQuery(name = "login", query = "SELECT c FROM User c WHERE c.mail = :userMail and c.password = :hashedPassword") })
@Entity
@Table(name = "user")
public class User {

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "wallet")
	private int wallet;

	@JsonIgnore
	public int getWallet() {
		return wallet;
	}

	public void setWallet(int wallet) {
		this.wallet = wallet;
	}

	@Column(name = "name")
	private String name;

	@Column(name = "mail")
	private String mail;

	@Column(name = "password")
	private String password;
	
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@OneToMany(mappedBy = "owner")
	@JsonIgnore
	private Set<Library> library = new HashSet<Library>();

	@OneToMany(mappedBy = "author")
	private Set<Review> review = new HashSet<Review>();
	
	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "friend", joinColumns = { @JoinColumn(name = "user1") }, inverseJoinColumns = {
			@JoinColumn(name = "user2") })
	private Set<User> friends = new HashSet<User>();

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "friend_request", joinColumns = { @JoinColumn(name = "receiver") }, inverseJoinColumns = {
			@JoinColumn(name = "claimant") })
	private Set<User> friend_request = new HashSet<User>();

	@JsonIgnore
	public Set<Review> getReviews() {
		return review;
	}
	@JsonIgnore
	public void setReviews(Set<Review> reviews) {
		this.review = reviews;
	}

	public User() {
	}

	public User(String nickname, String name, String mail, String hashed_password) {
		this.nickname = nickname;
		this.name = name;
		this.mail = mail;
		this.password = hashed_password;
	}

	@JsonIgnore
	public Set<Library> getLibrary() {
		return library;
	}

	public void setLibrary(Set<Library> library) {
		this.library = library;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@JsonIgnore
	public Set<User> getFriends() {
		return friends;
	}

	public void setFriends(Set<User> friends) {
		this.friends = friends;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public static List<User> findUser(String pattern) {
		pattern = "%" + pattern + "%";
		SessionFactory factory = null;
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		Session session = factory.openSession();
		List<User> res = session.createNamedQuery("findUser").setParameter("custName", pattern).getResultList();
		session.close();
		return res;
	}

	public static List<User> findUserByMail(String mail, Session session) {
		List<User> res = new ArrayList<User>();
		for (Object u : session.createNamedQuery("findUserByMail").setParameter("custMail", mail).getResultList())
			res.add(session.get(User.class, ((User) u).getId()));
		return res;
	}

	public static User login(String userMail, String hashedPassword) {
		Session session = SFactory.getSession();
		List<User> res = session.createNamedQuery("login").setParameter("userMail", userMail)
				.setParameter("hashedPassword", hashedPassword).getResultList();
		session.close();
		if (res.size() == 1)
			return res.get(0);
		return null;
	}

	public void addFriend(User user, Session session) {
		user.getFriend_request().add(this);
		session.beginTransaction();
		session.update(user);
		session.getTransaction().commit();
	};

	public void removeFriend(User user, Session session) {
		this.friends.remove(user);
		session.getTransaction().begin();
		session.update(this);
		session.getTransaction().commit();
	};

	public void acceptFriendRequest(User fr, Session session) {
		this.friends.add(fr);
		this.friend_request.remove(fr);
		session.beginTransaction();
		session.update(this);
		session.getTransaction().commit();
	};

	public void rejectFriendRequest(User fr, Session session) {
		this.friend_request.remove(fr);
		session.beginTransaction();
		session.update(this);
		session.getTransaction().commit();
	};

	public void giveGame(User user, Library library, Session session) {
		library.owner = user;
		session.beginTransaction();
		session.update(library);
		session.getTransaction().commit();
	};

	public void buyGame(Game game, Session session) {
		Library lib = new Library(this, game, this, 0);
		this.getLibrary().add(lib);
		this.wallet -= game.getPrice();
		session.beginTransaction();
		session.save(lib);
		session.update(this);
		session.getTransaction().commit();
	};

	public void addReview(Game game, String review, int mark, Session session) {
		Review rev = new Review(this, game, review, mark);
		session.save(rev);
	};
	
	public void addWallet(int quantity, Session session){
		this.wallet += quantity;
		session.beginTransaction();
		session.update(this);
		session.getTransaction().commit();
	};
	
	public void changeName(String new_name, Session session){
		this.name = new_name;
		session.beginTransaction();
		session.update(this);
		session.getTransaction().commit();
	};
	
	public void changeNickname(String new_nickname, Session session){
		this.nickname = new_nickname;
		session.beginTransaction();
		session.update(this);
		session.getTransaction().commit();
	};
	
	public void changeMail(String new_mail, Session session){
		this.mail = new_mail;
		session.beginTransaction();
		session.update(this);
		session.getTransaction().commit();
	};
	
	public void changePassword(String new_password, Session session){
		Hasher hasher = new Hasher("MD5");
		String hashed_new_password = Hasher.hashing(hasher, new_password);
		this.password =  hashed_new_password;
		session.beginTransaction();
		session.update(this);
		session.getTransaction().commit();
	};
	
	public static void main(String[] args) {
		System.out.println(User.findUser("chu"));
		Session session = SFactory.getSession();
		session.close();

	}

	@JsonIgnore
	public Set<User> getFriend_request() {
		return friend_request;
	}

	public void setFriend_request(Set<User> friend_request) {
		this.friend_request = friend_request;
	}

	public boolean equals(Object O) {
		if (O instanceof User)
			return this.id == ((User) O).id;
		return false;
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
}
