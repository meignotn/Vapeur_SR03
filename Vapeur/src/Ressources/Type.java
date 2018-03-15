package Ressources;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.cfg.Configuration;


@Entity
@Table(name = "type")
public class Type {
	
	@Id
	@Column(name="id")
	private int id;
	
	@Column(name="type")
	private String type;
	
	public Type(String name,int id) {
		this.type = name;
		this.id = id;
	}
	public Type(){
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return type;
	}
	public void setName(String name) {
		this.type = name;
	}
	
	public static void main(String[] args) {
		SessionFactory factory = null;
		try{
	         factory = new Configuration().configure().addAnnotatedClass(Ressources.Type.class).buildSessionFactory();
	      }catch (Throwable ex) { 
	         System.err.println("Failed to create sessionFactory object." + ex);
	         throw new ExceptionInInitializerError(ex); 
	      }Session session = factory.openSession();
	      Transaction tx = null;
	      Integer type = null;
	      try{
	         tx = session.beginTransaction();
	         Type azr = new Type("test",10);
	         session.saveOrUpdate(azr); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	}
}
