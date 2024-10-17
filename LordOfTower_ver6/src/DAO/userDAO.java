package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DTO.UsersDTO;

public class userDAO {

	// 사용자 추가 (회원가입)
	public void addUser(UsersDTO user) throws SQLException {

		// 이미 존재하는 사용자 => 확인 메시지 띄우기
		if (isUserNameTaken(user.getUserName())) { // 중복 사용자명 확인
			System.out.println("이미 존재하는 사용자명입니다.");

		}

		String sql = "INSERT INTO USERS (user_id, username, password) VALUES (user_seq.NEXTVAL, ? , ?)";

		// DatabaseConnection : JDBC 연결 설정하는 클래스
		// => 데이터베이스에 연결할 수 있는 기능을 제공
		try (Connection conn = DatabaseConnection.getConnection();

				PreparedStatement stmt = conn.prepareStatement(sql)) {
			if (conn != null) {
				System.out.println("DB 연결 성공!!");
			} else {

				System.out.println("DB 연결 실패~");
			}

			stmt.setString(1, user.getUserName());
			stmt.setString(2, user.getPassWord());
			stmt.executeUpdate();

		} catch (SQLException e) {

			System.out.println("오류발생");
		}
	}

	// 로그인
	public UsersDTO loginUser(String username, String password) throws SQLException {
		String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int userId = rs.getInt("user_id");
				UsersDTO user = new UsersDTO(userId, username, password);
//				System.out.println("로그인성공!! " + username);
				return user; // 로그인 성공 시 사용자 정보를 반환
			} else {
				System.out.println("로그인 실패!!");
				return null; // 로그인 실패 시 null 반환
			}
		}

	}

	// 사용자 로그아웃
	public void logoutUser(String username) {
		userDAO userDAO = new userDAO();
		userDAO.logoutUser(username);
	}

	// 회원 탈퇴
	public void deleteUser(int userId) throws SQLException {
		String sql = "DELETE FROM USERS WHERE user_id = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, userId); // userId에 해당하는 사용자 삭제
			int result = stmt.executeUpdate();

			if (result > 0) {
				System.out.println("회원 탈퇴 완료!");
			} else {
				System.out.println("해당 사용자를 찾을 수 없습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	// username으로 사용자 조회 (변경4)
	public UsersDTO getUserByUsername(String username) throws SQLException {
		String sql = "SELECT * FROM USERS WHERE username=?";
		UsersDTO user = null;

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, username);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int userId = rs.getInt("user_id");
				String password = rs.getString("password");

				// UsersDTO 객체에 조회한 사용자 정보 저장
				user = new UsersDTO(userId, username, password);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return user;

	}

	// 사용자 중복 확인 메서드
	public boolean isUserNameTaken(String username) throws SQLException {

		String sql = "SELECT COUNT(*) FROM Users WHERE username=?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			// ? 부분에 mainView입력=>userController=> userDAO로 온 username 삽입
			stmt.setString(1, username);
			// SQL 실행
			ResultSet rs = stmt.executeQuery();
			// 중복된 사용자명 있으면 true 반환
			if (rs.next()) {
				return rs.getInt(1) > 0; // 중복된 사용자명이 있으면 true 반환
			}
			// 위에서 만약 ResultSet에서 값을 제대로 확인하지 않고 무조건 true를 반환할 경우
			// 회원가입마다 중복 간주됨!
		}
		return false;

	}

}
