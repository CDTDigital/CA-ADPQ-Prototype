package com.intimetec.crns.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity model class for UserLogin table
 * @author shiva.dixit
 */

@Entity
@Table(name="user_login")
public class UserLogin {
	@Id
	@Column(name="user_id")
	private int id;

	@Column(name="password")
	private String password;
	
	@Column(name="status")
	private boolean status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
