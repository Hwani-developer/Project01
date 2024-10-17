package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	// 데이터베이스 연결 정보
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe"; // Oracle 데이터베이스 예시 URL (Oracle XE)
	private static final String USER = "LORDOFTOWER"; // 데이터베이스 사용자 이름
	private static final String PASSWORD = "12345"; // 데이터베이스 비밀번호

	// 데이터베이스 연결을 반환하는 메서드
	public static Connection getConnection() throws SQLException {
		try {
			// Oracle JDBC 드라이버 명시적으로 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}

}
