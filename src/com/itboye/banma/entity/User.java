package com.itboye.banma.entity;
/**
 * 用户
 * @author Administrator
 *
 */
public class User {
	private int id;
	private String idcode;//优惠码
	private String head;
	private String idnumber;
	private String wexin_bind;//微信绑定
	
	public String getIdcode() {
		return idcode;
	}
	public void setIdcode(String idcode) {
		this.idcode = idcode;
	}
	public String getIdnumber() {
		return idnumber;
	}
	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}
	public String getWexin_bind() {
		return wexin_bind;
	}
	public void setWexin_bind(String wexin_bind) {
		this.wexin_bind = wexin_bind;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	private String username;
	private String password;
	private String email;
	private String mobile;
	private String realname;
	private String nickname;
	private int sex;
	private String birthday;
	private String last_login_time;
	private String update_time;
	private String status;
	private String score;
	private String login;
	private String reg_ip;
	private String reg_time;
	private String last_login_ip;
	private String qq;
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getLast_login_time() {
		return last_login_time;
	}
	public void setLast_login_time(String last_login_time) {
		this.last_login_time = last_login_time;
	}
	public String getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getReg_ip() {
		return reg_ip;
	}
	public void setReg_ip(String reg_ip) {
		this.reg_ip = reg_ip;
	}
	public String getReg_time() {
		return reg_time;
	}
	public void setReg_time(String reg_time) {
		this.reg_time = reg_time;
	}
	public String getLast_login_ip() {
		return last_login_ip;
	}
	public void setLast_login_ip(String last_login_ip) {
		this.last_login_ip = last_login_ip;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	
	
}
