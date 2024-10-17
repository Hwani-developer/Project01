package View;

import java.util.Scanner;

import Controller.GameController;
import Controller.userController;
import DAO.EnemyDAO;
import DAO.PlayerDAO;

import DTO.EnemyDTO;
import DTO.PlayerDTO;
import DTO.UsersDTO;

public class mainView {

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		// 컨트롤러 객체 가져오기
		userController userController = new userController();
		GameController gameController = new GameController();

		programLoop: // [1] 프로그램 전체 루프
		while (true) {
			System.out.println("=========로그인 창========");
			System.out.print("[1]회원가입 [2]로그인 [3]회원탈퇴 [4]게임종료>>");
			// choice: 문자열로 받기!!
			String choice = sc.next();

			// [1] 회원가입이ㅡ 경우
			if (choice.equals("1")) {

				// 사용자 회원가입
				System.out.print("아이디를 입력하세요 >>");
				String username = sc.next();
				System.out.println();
				System.out.print("비밀번호를 입력하세요 >>");
				String password = sc.next();
				// userController를 통해 회원가입 처리
				String result = userController.registerUser(username, password);
				System.out.println(result);

			} else if (choice.equals("2")) {
				// 사용자 로그인
				if (userController.isLoggedIn()) {
					System.out.println("이미 로그인 완료되었습니다");
				} else {
					System.out.print("아이디를 입력하세요 >>");
					String username = sc.next();
					System.out.println();
					System.out.print("비밀번호를 입력하세요 >>");
					String password = sc.next();

					UsersDTO loggedInUser = userController.loginUser(username, password); // 컨트롤러를 통해 로그인 처리

					// 로그인된 경우(UsersDTO의 객체인 loggedInUser에 로그인된 정보(username, password)가 담긴 경우)
					if (loggedInUser != null) {
						// 로그인 성공
						System.out.println("로그인 되었습니다!");
						System.out.println(loggedInUser.getUserName() + "님 환영합니다!!");
						// 로그인 성공 후 게임 시작
						gameController.startGame(loggedInUser, sc);
						break programLoop;
					} else {
						System.out.println("존재하지 않는 회원입니다.");
					}

				}

			} else if (choice.equals("3")) {

				System.out.print("아이디를 입력하세요 >>");
				String username = sc.next();
				System.out.println();
				System.out.print("비밀번호를 입력하세요 >>");
				String password = sc.next();

				UsersDTO loggedInUser = userController.loginUser(username, password); // 컨트롤러를 통해 로그인 처리

				// 로그인된 경우(UsersDTO의 객체인 loggedInUser에 로그인된 정보(username, password)가 담긴 경우)
				if (loggedInUser != null) {

					System.out.println("정말 회원 탈퇴를 진행하시겠습니까? [1]예 [2]아니오");
					int confirm = sc.nextInt();
					if (confirm == 1) {
						String result = userController.deleteUser(loggedInUser.getUserName()); // 회원 탈퇴 처리
						System.out.println(result);
						break programLoop;
					} else {
						System.out.println("회원 탈퇴가 취소되었습니다.");
						break programLoop;
					}

				} else {
					System.out.println("존재하지 않는 회원입니다.");
				}

			} else if (choice.equals("4")) {
				System.out.println("게임이 종료되었습니다");
				break programLoop;
			} else {
				System.out.println("1,2,3,4 중 하나 선택해주세요");
			}

		}

	}

}
