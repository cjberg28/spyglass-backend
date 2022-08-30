package com.skillstorm.spyglass.models;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="application_user")
@Validated
public class User {
	
	@Valid
	@Email
	@Id
	@Column(name="email")
	private String email;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Nullable
	@Column(name="date_of_birth")
	private LocalDate dateOfBirth;
	
	@Valid
	@NotBlank
	@NotEmpty
	@Column(name="password")
	private String password;
	
	//=================ENTITY MAPPINGS=========================
	
	@JsonIgnore
	@OneToMany(mappedBy="user") //Goal class' user variable
	private Set<Goal> goals;
	
	//=========================================================
	
	public User() {
		super();
	}
	
	public User(@Valid @Email String email, String firstName, String lastName, LocalDate dateOfBirth,
			@Valid @NotBlank @NotEmpty String password) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Goal> getGoals() {
		return goals;
	}

	public void setGoals(Set<Goal> goals) {
		this.goals = goals;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", firstName=" + firstName + ", lastName=" + lastName + ", dateOfBirth="
				+ dateOfBirth + ", password=" + password + "]";
	}
	
	
}
