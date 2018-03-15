package Ressources;

import java.io.IOException;

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
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;


@Entity
@Table(name="library")
public class Library {
	
	@ManyToOne
	@JoinColumn(name = "owner")
	public User owner;
	
	@ManyToOne
	@JoinColumn(name = "game")
	public Game game;
	
	@ManyToOne
	@JoinColumn(name = "buyer")
	public User buyer;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int id;
	
	@Column(name="active")
	public int active;
	
	public Library(User owner, Game game, User buyer, int active) {
		this.owner = owner;
		this.game = game;
		this.buyer = buyer;
		this.active=active;
	}
	
	public Library(){
		
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}
	
	public void activate(Session session){
		this.setActive(1);
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
}
	