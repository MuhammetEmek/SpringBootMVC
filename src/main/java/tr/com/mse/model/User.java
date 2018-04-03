package tr.com.mse.model;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User implements Cloneable {

	@Id
	private String objid;

	private String userCode;

	private String userName;

	private String userSurname;

	private String email;

	private String password;

	private boolean enabled;

	private Integer failedLoginCount;

	private Date registerDate;

	private Date lastPasswordChange;

	@ManyToMany
	@JoinTable(name = "USER_AUTHS", joinColumns = { @JoinColumn(name = "USER_OID", table = "USERS", referencedColumnName = "OBJID") }, inverseJoinColumns = {
	        @JoinColumn(name = "AUTH_OID", table = "AUTHORITIES", referencedColumnName = "OBJID") })
	private Set<Authority> authorities;

	public String getObjid() {
		return objid;
	}

	public void setObjid(String objid) {
		this.objid = objid;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserSurname() {
		return userSurname;
	}

	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
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

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Integer getFailedLoginCount() {
		return failedLoginCount;
	}

	public void setFailedLoginCount(Integer failedLoginCount) {
		this.failedLoginCount = failedLoginCount;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Date getLastPasswordChange() {
		return lastPasswordChange;
	}

	public void setLastPasswordChange(Date lastPasswordChange) {
		this.lastPasswordChange = lastPasswordChange;
	}

	@PrePersist
	public void preInsert() {
		this.enabled = true;
		this.failedLoginCount = 0;
		this.objid = UUID.randomUUID().toString();
		this.registerDate = new Date();
		this.lastPasswordChange = new Date();
	}

	public User clone() throws CloneNotSupportedException {
		return (User) super.clone();
	}
}