package DTO;

public class UsersDTO {

	// 필드명
	private int userId; // ID(이 부분 FK로 다른 테이블에 연결)
	private String userName; // 사용자이름
	private String passWord; // PW

	// 초기 생성자
	public UsersDTO(int userId, String userName, String passWord) {
		this.userId = userId;
		this.userName = userName;
		this.passWord = passWord;
	}
	// getter setter

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

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	// toString 재정의
	// 객체를 출력하거나 로그로 남길 때 객체의 메모리 주소 대신, 해당 객체의 필드 값을 직접 확인 가능

	@Override
	public String toString() {
		return "UsersDTO{" + "userId=" + userId + ", username='" + userName + '\'' + ", password='" + passWord + '\''
				+ '}';
	}

}
