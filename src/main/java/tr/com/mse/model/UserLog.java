package tr.com.mse.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import tr.com.mse.enums.UserOperation;

@Entity
@Table(name = "USER_LOGS")
public class UserLog {
	@Id
	private String objid;

	@OneToOne
	private User userInfo;

	@NotNull
	@Enumerated(EnumType.STRING)
	private UserOperation operation;

	private String fieldName;

	private String oldValue;

	private String newValue;

	@OneToOne
	private User operationUser;

	private Date operationDate;

	public String getObjid() {
		return objid;
	}

	public void setObjid(String objid) {
		this.objid = objid;
	}

	public User getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(User userInfo) {
		this.userInfo = userInfo;
	}

	public UserOperation getOperation() {
		return operation;
	}

	public void setOperation(UserOperation operation) {
		this.operation = operation;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public User getOperationUser() {
		return operationUser;
	}

	public void setOperationUser(User operationUser) {
		this.operationUser = operationUser;
	}

	public Date getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(Date operationDate) {
		this.operationDate = operationDate;
	}

	@PrePersist
	public void preInsert() {
		this.objid = UUID.randomUUID().toString();
		this.operationDate = new Timestamp(System.currentTimeMillis());
	}
}