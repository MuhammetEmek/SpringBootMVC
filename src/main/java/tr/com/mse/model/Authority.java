package tr.com.mse.model;

import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import tr.com.mse.enums.AuthorityType;

@Entity
@XmlRootElement
@Table(name = "AUTHORITIES")
public class Authority {

    @Id
	private String objid;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthorityType name;

	@ManyToMany(mappedBy = "authorities")
	private Set<User> users;

	public String getObjid() {
		return objid;
	}

	public void setObjid(String objid) {
		this.objid = objid;
	}

	public AuthorityType getName() {
		return name;
	}

	public void setName(AuthorityType name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@PrePersist
	public void preInsert() {
		this.objid = UUID.randomUUID().toString();
	}
}