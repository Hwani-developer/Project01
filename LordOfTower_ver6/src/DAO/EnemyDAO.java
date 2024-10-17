package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DTO.EnemyDTO;

public class EnemyDAO {

    // 1. 새 적 추가 (INSERT)
    public void insertEnemy(EnemyDTO enemy) throws SQLException {
        if (isEnemyExists(enemy.getEnemy_id())) {
            System.out.println("적 정보가 이미 존재합니다. 적 정보를 업데이트합니다.");
            updateEnemy(enemy);  // 적이 이미 존재하면 업데이트 처리
        } else {
            String sql = "INSERT INTO ENEMY (enemy_id, enemy_health, enemy_attack, enemy_defense, player_id) VALUES (?, ?, ?, ?, ?)";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, enemy.getEnemy_id());
                stmt.setInt(2, enemy.getEnemy_health());
                stmt.setInt(3, enemy.getEnemy_attack());
                stmt.setInt(4, enemy.getEnemy_defense());
                stmt.setInt(5, enemy.getPlayer_id());

                stmt.executeUpdate();
                System.out.println("적이 저장되었습니다.");
            }
        }
    }
    
    // 2. 게임 중 새로운 적 삽입
    public void saveEnemy(EnemyDTO enemy) throws SQLException {
        String sql = "INSERT INTO ENEMY (enemy_id, enemy_health, enemy_attack, enemy_defense, player_id) VALUES (enemy_seq.NEXTVAL, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, enemy.getEnemy_health());
            stmt.setInt(2, enemy.getEnemy_attack());
            stmt.setInt(3, enemy.getEnemy_defense());
            stmt.setInt(4, enemy.getPlayer_id());

            stmt.executeUpdate();
            System.out.println("적이 저장되었습니다.");
        }
    }

    

    // 3. 적 정보 업데이트 (UPDATE)
    public void updateEnemy(EnemyDTO enemy) throws SQLException {
        String sql = "UPDATE ENEMY SET enemy_health = ?, enemy_attack = ?, enemy_defense = ? WHERE enemy_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, enemy.getEnemy_health());
            pstmt.setInt(2, enemy.getEnemy_attack());
            pstmt.setInt(3, enemy.getEnemy_defense());
            pstmt.setInt(4, enemy.getEnemy_id());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("적 상태가 업데이트되었습니다.");
            } else {
                System.out.println("적 상태 업데이트 실패.");
            }
        }
    }

    // 3. 적 존재 여부 확인 (선택)
    public boolean isEnemyExists(int playerId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM ENEMY WHERE enemy_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // 적이 존재하면 true 반환
            }
        }
        return false; // 적이 존재하지 않으면 false 반환
    }


    // 4. 적 정보 조회 (SELECT)
    // 여기서 적은 enemy_id가 아닌 player_id에 의해 조회해야 함!
    public EnemyDTO getEnemyById(int playerId) throws SQLException {
        String sql = "SELECT * FROM ENEMY WHERE enemy_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new EnemyDTO(
                    rs.getInt("enemy_id"),
                    rs.getInt("enemy_health"),
                    rs.getInt("enemy_attack"),
                    rs.getInt("enemy_defense"),
                    rs.getInt("player_id")
                );
            } else {
                return null;
            }
        }
    }
    
    // 5. 특정 player_id에 해당하는 적 기록 삭제(바꾼 부분2)
    public void deleteEnemiesByPlayerId(int playerId) throws SQLException {
        String sql = "DELETE FROM ENEMY WHERE player_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, playerId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("적 기록 삭제 중 오류 발생: " + e.getMessage());
            throw e;
        }
    }
    
}
