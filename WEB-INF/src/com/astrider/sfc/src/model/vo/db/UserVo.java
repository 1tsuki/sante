package com.astrider.sfc.src.model.vo.db;

import java.sql.Date;

import com.astrider.sfc.app.annotation.Column;
import com.astrider.sfc.app.annotation.Table;
import com.astrider.sfc.app.annotation.Valid;
import com.astrider.sfc.app.lib.BaseVo;
import com.astrider.sfc.src.model.vo.form.RegisterFormVo;

@Table("users")
public class UserVo extends BaseVo {
	private static final long serialVersionUID = -2456490578422461383L;

	@Column(physic = "user_id", logic = "ユーザーID", isPrimaryKey = true)
	@Valid(isNotNull = true, isNotBlank = true, isMaxLength = true, maxLength = 8)
	private int userId;
	@Column(physic = "user_name", logic = "ユーザー名")
	@Valid(isNotNull = true, isNotBlank = true, isMaxLength = true, maxLength = 32, isRegexp = true, regexp = "^[0-9a-zA-Z]+$")
	private String userName;
	@Column(physic = "email", logic = "メールアドレス")
	@Valid(isNotNull = true, isNotBlank = true, isMaxLength = true, maxLength = 128)
	private String email;
	@Column(physic = "auth_token", logic = "認証トークン")
	@Valid(isNotNull = true, isNotBlank = true, isMaxLength = true, maxLength = 150)
	private String authToken;
	@Column(physic = "email_token", logic = "メールアドレス認証トークン")
	@Valid(isNotNull = true, isNotBlank = true, isLength = true, length = 16)
	private String emailToken;
	@Column(physic = "male", logic = "性別")
	private boolean male;
	@Column(physic = "height", logic = "身長")
	@Valid(isMin = true, isMaxLength = true, min = 0, maxLength = 3)
	private int height;
	@Column(physic = "weight", logic = "体重")
	@Valid(isMin = true, isMaxLength = true, min = 0, maxLength = 3)
	private int weight;
	@Column(physic = "age", logic = "年齢")
	@Valid(isMin = true, isMaxLength = true, min = 0, maxLength = 3)
	private int age;
	@Column(physic = "created_at", logic = "生成時刻")
	private Date createdAt;
	@Column(physic = "deleted_at", logic = "削除時刻")
	private Date deletedAt;
	@Column(physic = "is_deleted", logic = "削除フラグ")
	private boolean isDeleted;
	@Column(physic = "is_confirmed", logic = "確認フラグ")
	private boolean isConfirmed;
	@Column(physic = "is_available", logic = "有効フラグ")
	private boolean isAvailable;

	public UserVo() {
	}

	public UserVo(RegisterFormVo vo) {
		setUserName(vo.getUserName());
		setEmail(vo.getEmail());
		setMale(vo.isMale());
		setHeight(vo.getHeight());
		setWeight(vo.getWeight());
		setAge(vo.getAge());
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public String getEmailToken() {
		return emailToken;
	}

	public void setEmailToken(String emailToken) {
		this.emailToken = emailToken;
	}

	public boolean isMale() {
		return male;
	}

	public void setMale(boolean male) {
		this.male = male;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public boolean isConfirmed() {
		return isConfirmed;
	}

	public void setConfirmed(boolean isConfirmed) {
		this.isConfirmed = isConfirmed;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
}
