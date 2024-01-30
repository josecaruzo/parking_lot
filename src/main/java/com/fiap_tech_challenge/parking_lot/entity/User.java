package com.fiap_tech_challenge.parking_lot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap_tech_challenge.parking_lot.dto.User.UserDTO;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 50, nullable = false, unique = true)
	private String name;

	@Column(length = 50, nullable = false, unique = true)
	private String email;

	@Column(length = 50, nullable = false)
	@JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
	@JsonIgnore
	private String password;

	@OneToMany(mappedBy = "user")
	private Set<Car> cars = new HashSet<>();

	@OneToMany(mappedBy = "user")
	private Set<Purchase> receipts = new HashSet<>();
	public User(){}

	public User(Long id, String name, String email, String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public User(UserDTO userDTO){
		this.id = userDTO.id();
		this.name = userDTO.name();
		this.email = userDTO.email();
		this.password = userDTO.password();
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

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	//@JsonManagedReference
	public Set<Car> getCars() {
		return cars;
	}
	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", password='" + password + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(id, user.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
