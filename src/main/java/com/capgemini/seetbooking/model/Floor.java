package com.capgemini.seetbooking.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity  //it is a pojo class we can create table name and column in the entity it goes to the database we can visible  
@Table(name = "floors")  //table name
public class Floor {
	@Id     //primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)  //it will generate the id
	@Column(name = "floor_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "office_id", nullable = false)  //join column is used the join the column
	private Office office;

	@OneToMany(mappedBy = "floor", cascade = CascadeType.ALL)
	private List<Room> rooms;

	@Column(nullable = false)
	private String floorNumber;

	// Add other floor details as needed

	// Constructors, getters, setters
	public Floor() {
	}

	public Floor(Long id, Office office, String floorNumber) {
		super();
		this.id = id;
		this.office = office;
		this.floorNumber = floorNumber;
	}

	public Floor(String floorNumber) {
		super();
		this.floorNumber = floorNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public String getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(String floorNumber) {
		this.floorNumber = floorNumber;
	}

	@Override   
	public String toString() { 
		return "Floor [id=" + id + ", office=" + office + ", floorNumber=" + floorNumber + "]";
	}

}
