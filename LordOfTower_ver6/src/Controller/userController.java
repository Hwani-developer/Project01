package Controller;

import java.sql.SQLException;

import DAO.EnemyDAO;
import DAO.PlayerDAO;
import DAO.userDAO;
import DTO.EnemyDTO;
import DTO.PlayerDTO;
import DTO.UsersDTO;

public class userController {

	// Model 객체 가져오기 (변경3)
	private userDAO userDAO;
	private UsersDTO loggedInUser; // 현재 로그인된 사용자 정보를 저장하는 변수
	private PlayerDAO playerDAO;
	private EnemyDAO enemyDAO;

	// 생성자에서 DAO 객체 초기화(변경3)
	public userController() {

		this.userDAO = new userDAO();
		this.loggedInUser = null;
		this.playerDAO = new PlayerDAO();
		this.enemyDAO = new EnemyDAO();

	}

	// 사용자 회원가입
	public String registerUser(String username, String password) {
		try {
			// userDAO의 isUserNameTaken(이미 존재하는 사용자명의 경우 => true)
			// username: mainView에서 입력받은 변수
			// 이미 존재하는 사용자명의 경우 아래 회원가입 로직으로 넘어가지 않고 여기서 끝남
			if (userDAO.isUserNameTaken(username)) {
				return "이미 존재하는 사용자명입니다";
			}

			// UsersDTO에 user_id=0(시퀀스값=> 자동증가), 입력받은 username, password 보내기
			UsersDTO newUser = new UsersDTO(0, username, password);
			// userDAO의 addUser에 UsersDTO에 저장된 username, password 다시 가져와 보내기
			userDAO.addUser(newUser);

			return "회원가입 완료!";
		} catch (SQLException e) {

			return "회원가입 실패!";
		}

	}

	// 사용자 로그인
	public UsersDTO loginUser(String username, String password) {
		try {
			UsersDTO user = userDAO.loginUser(username, password); // 입력받은 username, password로 userDAO에서 로그인처리된 객체를
																	// UsersDTO에 넣는다
			if (user != null) { // 로그인처리된 객체가 비어있지 않은 경우
				this.loggedInUser = user; // 로그인된 사용자 정보 저장
//				System.out.println(username + "님 로그인 완료되었습니다!");
				return user;
				// 로그인 실패 또는 SQL 오류 발생시 반환되는 UsersDTO는 null이 된다.
			} else {
//				System.out.println("유효하지 않은 아이디 또는 비밀번호입니다!");
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// 사용자 로그아웃
	public void logoutUser() {
		if (this.loggedInUser != null) {
			System.out.println(this.loggedInUser.getUserName() + "님의 로그아웃 완료!");
			this.loggedInUser = null; // 로그인된 사용자 정보 초기화 (로그아웃 처리)
		}
	}

	// 회원 탈퇴(변경5)
	public String deleteUser(String username) {
		try {
			// 해당 사용자 존재하는지 확인
			UsersDTO user=userDAO.getUserByUsername(username);
			if(user==null) {
				return "존재하지 않는 사용자입니다";
			}
			// 해당 사용자 플레이어 기록이 있는지 확인
			PlayerDTO player=playerDAO.getPlayerById(user.getUserId());
			
			 // 플레이어 기록이 있는 경우 해당 플레이어 및 관련 데이터를 삭제(제약조건 위반 방지용)
			if(player!=null) {
				enemyDAO.deleteEnemiesByPlayerId(player.getPlayer_id()); // 적 기록 삭제
				playerDAO.deletePlayerByUserId(user.getUserId()); // 플레이어 기록 삭제
			}
			// 사용자 삭제
			userDAO.deleteUser(user.getUserId());  // 여기에서 기존 deleteUser 메소드를 호출
			return "회원탈퇴가 완료되었습니다";
			
		} catch (SQLException e) {
            System.out.println("회원탈퇴 중 오류 발생: " + e.getMessage());
            return "회원탈퇴에 실패했습니다.";
        }
		
		
	}
//		if (this.loggedInUser == null) {
//			return "로그인된 사용자가 없습니다.";
//		}
//		try {
//			// 현재 로그인된 사용자 ID를 사용하여 회원 탈퇴
//			userDAO.deleteUser(loggedInUser.getUserId());
//			// 탈퇴 후 로그인 상태 초기화
//			this.loggedInUser = null;
//			return "회원 탈퇴가 완료되었습니다.";
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return "회원 탈퇴에 실패하였습니다.";
//		}
//
//	}

	// userController에서 플레이어 정보 가져오는 메소드 추가(변경7)
	public PlayerDTO getPlayerByUserId(int userId) {
		try {
	        return playerDAO.getPlayerById(userId);  // userId로 플레이어 정보 조회
	    } catch (SQLException e) {
	        System.out.println("플레이어 정보 불러오기 중 오류 발생: " + e.getMessage());
	        return null;
	    }
	}
	
	
	
	
	// 로그인 상태 확인
	public boolean isLoggedIn() {
		return this.loggedInUser != null;
	}
	
	

}
