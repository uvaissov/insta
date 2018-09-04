package kz.astana.uvaissov.insta.entity;

import java.sql.Timestamp;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "clients")
public class Client {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="client_id")
	private Long id;
	@NotNull
	@Column(name="name")
	private String name;
	@Column(name="regDate")
	@NotNull
	private Timestamp regDate;

	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "clients_user_ref", joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users;
	
	public Timestamp getRegDate() {
		return regDate;
	}
	public void setRegDate(Timestamp regDate) {
		this.regDate = regDate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String role) {
		this.name = role;
	}
	
	
}