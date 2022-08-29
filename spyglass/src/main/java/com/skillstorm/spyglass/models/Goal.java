package com.skillstorm.spyglass.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

@Entity
@Table(name="goals")
@Validated
public class Goal {
	
	@Valid
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Valid
	@NotBlank
	@NotEmpty
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Column(name="image_src")
	private String imageSrc;
	
	@Valid
	@NotNull
	@Future
	@Column(name="target_date")
	private LocalDate targetDate;
	
	@Valid
	@NotNull
	@DecimalMin("0")
	@Column(name="target_amount")
	private float targetAmount;
	
	@Valid
	@NotNull
	@DecimalMin("0")
	@Column(name="current_amount")
	private float currentAmount;
	
	@Valid
	@NotNull
	@Email
	@Column(name="user_id", insertable=false, updatable=false)
	private String userId;
	
	//===================MAPPING FIELDS===================================
	
	@ManyToOne
	@JoinColumn(name="user_id") //Goal table's userId FK that references User
	private User user;
	
	//====================================================================
	
	public Goal(@Valid int id, @Valid @NotBlank @NotEmpty String name, String description, String imageSrc,
			@Valid @NotNull @Future LocalDate targetDate, @Valid @NotNull @DecimalMin("0") float targetAmount,
			@Valid @NotNull @DecimalMin("0") float currentAmount) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.imageSrc = imageSrc;
		this.targetDate = targetDate;
		this.targetAmount = targetAmount;
		this.currentAmount = currentAmount;
	}

	public Goal(@Valid @NotBlank @NotEmpty String name, String description, String imageSrc,
			@Valid @NotNull @Future LocalDate targetDate, @Valid @NotNull @DecimalMin("0") float targetAmount,
			@Valid @NotNull @DecimalMin("0") float currentAmount) {
		super();
		this.name = name;
		this.description = description;
		this.imageSrc = imageSrc;
		this.targetDate = targetDate;
		this.targetAmount = targetAmount;
		this.currentAmount = currentAmount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageSrc() {
		return imageSrc;
	}

	public void setImageSrc(String imageSrc) {
		this.imageSrc = imageSrc;
	}

	public LocalDate getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(LocalDate targetDate) {
		this.targetDate = targetDate;
	}

	public float getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(float targetAmount) {
		this.targetAmount = targetAmount;
	}

	public float getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(float currentAmount) {
		this.currentAmount = currentAmount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Goal [id=" + id + ", name=" + name + ", description=" + description + ", imageSrc=" + imageSrc
				+ ", targetDate=" + targetDate + ", targetAmount=" + targetAmount + ", currentAmount=" + currentAmount
				+ "]";
	}
	
	
	
}
