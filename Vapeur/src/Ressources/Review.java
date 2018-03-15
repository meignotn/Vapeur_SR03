package Ressources;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.Session;

import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "review")
public class Review {

	@ManyToOne
	@JoinColumn(name = "author")
	private User author;

	@ManyToOne
	@JoinColumn(name = "game")
	private Game game;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@Column(name = "review")
	private String review;

	public Review(User author, Game game, String review, int mark) {
		this.author = author;
		this.game = game;
		this.review = review;
		this.mark = mark;
	}

	public Review() {
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	@Column(name = "mark")
	private int mark;

	public void changeReview(String new_review, Session session) {
		this.review = new_review;
		session.beginTransaction();
		session.update(this);
		session.getTransaction().commit();
	}
	
	public void changeMark(int mark, Session session) {
		this.mark = mark;
		session.beginTransaction();
		session.update(this);
		session.getTransaction().commit();
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

	public static void main(String[] args) {
		Session session = SFactory.getSession();

		Type type = new Type("loli", 988);
		Game g = new Game("azre", type, 988, "c un jeu bien");
		User u = new User("test", "test", "test", "test");
		Review r = new Review(u, g, "fa", 54);
		session.beginTransaction();
		session.saveOrUpdate(type);
		session.saveOrUpdate(g);
		session.saveOrUpdate(u);
		session.saveOrUpdate(r);
		session.getTransaction().commit();
		System.out.println(r);
		System.out.println(g.getReviews());

		session.close();
	}

}
