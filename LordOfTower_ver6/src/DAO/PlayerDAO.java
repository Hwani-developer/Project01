package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DTO.PlayerDTO;

public class PlayerDAO {

	// 1. 새 플레이어 추가 (INSERT)
	public void insertPlayer(PlayerDTO player) throws SQLException {
		String sql = "INSERT INTO PLAYER (player_id, health, attack, defense, user_id, current_floor) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, player.getPlayer_id());
			stmt.setInt(2, player.getplayer_health());
			stmt.setInt(3, player.getplayer_attack());
			stmt.setInt(4, player.getplayer_defense());
			stmt.setInt(5, player.getUser_id());
			stmt.setInt(6, player.getCurrent_floor());

			stmt.executeUpdate();
			System.out.println("플레이어가 저장되었습니다.");
		}
	}

	// 2. 플레이어 조회 (SELECT)
	public PlayerDTO getPlayerById(int playerId) throws SQLException {
		String sql = "SELECT * FROM PLAYER WHERE player_id = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, playerId);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return new PlayerDTO(rs.getInt("player_id"), rs.getInt("health"), rs.getInt("attack"),
						rs.getInt("defense"), rs.getInt("user_id"), rs.getInt("current_floor"));
			} else {
				return null;
			}
		}
	}

	// 3. 게임 중 새 플레이어 삽입
	public void savePlayer(PlayerDTO player) throws SQLException {
		String sql = "INSERT INTO PLAYER (player_id, health, attack, defense, user_id, current_floor) VALUES (player_seq.NEXTVAL, ?, ?, ?, ?, ?)";
		String sqlGetId = "SELECT player_seq.CURRVAL FROM dual"; // CURRVAL로 마지막으로 생성된 시퀀스 값을 가져옴

		try (Connection conn = DatabaseConnection.getConnection();
				// 자동 증가된 player_id를 가져오기 위해 PreparedStatement를 생성할 때
				// Statement.RETURN_GENERATED_KEYS를 사용
				PreparedStatement stmt = conn.prepareStatement(sql);
				PreparedStatement stmtGetId = conn.prepareStatement(sqlGetId)) {

			stmt.setInt(1, player.getplayer_health());
			stmt.setInt(2, player.getplayer_attack());
			stmt.setInt(3, player.getplayer_defense());
			stmt.setInt(4, player.getUser_id());
			stmt.setInt(5, player.getCurrent_floor());

			// savePlayer 메소드가 호출된 후 player_id 값이 자동 증가값으로 DB에서 반환되는지 확인(변경2-1)

			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected > 0) {
				// 자동 생성된 player_id 가져오기
				try (ResultSet rs = stmtGetId.executeQuery()) {
	                if (rs.next()) {
	                    player.setPlayer_id(rs.getInt(1));  // 생성된 player_id를 CURRVAL에서 가져옴
	                } else {
	                    throw new SQLException("플레이어 ID를 가져오지 못했습니다.");
	                }
	            }
			} else {

				System.out.println("플레이어 생성 실패");
			}

			System.out.println("플레이어가 저장되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

	}

	// 4. 플레이어 정보 업데이트 (UPDATE)
	public void updatePlayer(PlayerDTO player) throws SQLException {
		String sql = "UPDATE PLAYER SET health = ?, attack = ?, defense = ?, current_floor = ? WHERE player_id = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, player.getplayer_health());
			stmt.setInt(2, player.getplayer_attack());
			stmt.setInt(3, player.getplayer_defense());
			stmt.setInt(4, player.getCurrent_floor());
			stmt.setInt(5, player.getPlayer_id());

			// player_id가 DB에 이미 존재하는 플레이어에 대해 업데이트를 시도(변경 2-2)
			int rowsAffected = stmt.executeUpdate();
			if (rowsAffected == 0) {
				// 업데이트 실패 (해당 player_id가 없을 경우)
				System.out.println("업데이트 실패: 해당 플레이어를 찾을 수 없습니다.");
			}
			System.out.println("플레이어 상태가 업데이트되었습니다.");
		}
	}

	// 특정 user_id에 해당하는 플레이어 기록 삭제(변경1)
	public void deletePlayerByUserId(int userId) throws SQLException {
		String sql = "DELETE FROM PLAYER WHERE user_id = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, userId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("플레이어 삭제 중 오류 발생: " + e.getMessage());
			throw e;
		}
	}

}
