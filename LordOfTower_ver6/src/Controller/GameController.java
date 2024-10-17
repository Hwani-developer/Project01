package Controller;

import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

import DAO.EnemyDAO;
import DAO.PlayerDAO;
import DTO.EnemyDTO;
import DTO.PlayerDTO;
import DTO.UsersDTO;

public class GameController {

	// Model 객체 가져오기
	private userController userController;
	private PlayerDAO playerDAO;
	private EnemyDAO enemyDAO;

	// 생성자에서 필요 객체 초기화
	public GameController() {
		this.playerDAO = new PlayerDAO();
		this.enemyDAO = new EnemyDAO();
		this.userController = new userController();
	}

	public void startGame(UsersDTO user, Scanner sc) {

		Random ran = new Random();

		// 플레이어 정보 불러오기

		// 초기값주기

		// 1. Player 초기값
		// player_id(자동증가, 시퀀스):1
		// player_health: 500
		// player_attack: 60
		// player_defense: 30
		// user_id: UsersDTO에 저장된 user 정보 가져오기
		// currentFloor: 1 (1층에서부터 시작)

		// 플레이어 정보 불러오기 (DB에서 해당 user_id를 가진 플레이어 정보를 불러옵니다) (수정7)
		PlayerDTO player;
		try {
			player = playerDAO.getPlayerById(user.getUserId());
			if (player == null) {
				// 플레이어 정보가 없을 경우 새로 생성 => 이 경우 player의 초기값을 위 정보대로 준다.
				player = new PlayerDTO(0, 500, 60, 30, user.getUserId(), 1);
				playerDAO.savePlayer(player);
				System.out.println("새 플레이어가 생성되었습니다");

				// 생성된 플레이어의 player_id가 제대로 설정되었는지 확인(변경2-3)
				if (player.getPlayer_id() == 0) {
					System.out.println("플레이어 ID 생성 오류!");
					return;
				} else {
					System.out.println("플레이어 ID: " + player.getPlayer_id() + "가 생성되었습니다.");
				}

			} else {
				if(player.getCurrent_floor()!=50) {
					
					System.out.println("기존 플레이어 정보를 불러옵니다.");
					System.out.println(player.getCurrent_floor() + "층에서 재시작합니다.");
				}else {
					System.out.println("");
				}

			}

			// 플레이어가 저장된 상태에서 적 정보 불러오기 시도(변경8) => integrity constraint violation 문제에 대한 해결책
			// 적 정보가 저장될 때, EnemyDTO에서 참조하는 player_id가 Player 테이블에 존재하지 않는 문제 해결
			EnemyDTO enemy = enemyDAO.getEnemyById(player.getPlayer_id());
			if (enemy == null) {
				System.out.println("적 정보가 없습니다. 새로운 적을 생성합니다");
				enemy = new EnemyDTO(0, 100, 20, 10, player.getPlayer_id()); // 적 생성 시 player_id 참조
				enemyDAO.saveEnemy(enemy);
				System.out.println("새로운 적이 생성되었습니다.");

			} else {
				System.out.println("기존 적 정보를 불러옵니다.");
			}
		} catch (SQLException e) {
			System.out.println("플레이어 불러오기 중 오류 발생.");
			return;
		}
		System.out.println(player.getCurrent_floor());

		// 2. Enemy 초기값
//      enemy_id(자동증가,시퀀스) : 1
//      enemy_health: 0
//      enemy_attack: 0
//      enemy_defense: 0
//      player_id:UsersDTO에 저장된 user 정보 가져오기

		// 적 초기화 (기존 적이 있는지 확인하고 없으면 새로 생성)
		// 여기서 enemy 정보 불러오기가 안되는 에러 발생!!

		String result = userController.registerUser(user.getUserName(), user.getPassWord());

		// 플레이어 정보 불러오기
		PlayerDTO loggedInPlayer = userController.getPlayerByUserId(user.getUserId());

		// 적 초기화 (기존 적이 있는지 확인하고 없으면 새로 생성)
		EnemyDTO enemy;
		try {
			int playerId = player.getPlayer_id();
			System.out.println("플레이어 ID: " + playerId); // player_id 확인

			// 적 정보를 player_id로 조회
			enemy = enemyDAO.getEnemyById(playerId);

			// 적 정보가 없는 경우 새로 생성
			if (enemy == null) {
				System.out.println("적 정보가 없습니다. 새로운 적을 생성합니다.");
				// 적의 초기 값 설정: 적의 health, attack, defense 값을 임의로 설정
				enemy = new EnemyDTO(0, 100, 20, 10, player.getPlayer_id()); // 적 초기 값
				enemyDAO.saveEnemy(enemy);
				System.out.println("새로운 적이 생성되었습니다.");
			} else {
				System.out.println("기존 적 정보를 불러옵니다.");
			}
		} catch (SQLException e) {
			System.out.println("적 정보 불러오기 중 오류 발생: " + e.getMessage());
			return;
		}
		// 현재 floor=player 객체의 currentFloor =>DB 연동 변수

//		int currentFloor = player.getCurrent_floor(); // 1층부터 시작

		// 유저(Player)=> DB 연동 변수
		int player_attack = player.getplayer_attack(); // 유저 공격력
		int player_defense = player.getplayer_defense(); // 유저 방어력
		int player_health = player.getplayer_health(); // 유저 체력 // (경험치와 레벨은 아래 로직에 넣기 어려워 뺐습니다!)

		// 파수꾼(Enemy)=> DB 연동 변수
		int enemy_attack = enemy.getEnemy_attack(); // 파수꾼 공격력 =>enemy
		int enemy_defense = enemy.getEnemy_defense(); // 파수꾼 방어력
		int enemy_health = enemy.getEnemy_health(); // 파수꾼 체력

		// 기타변수3

		String choice = null; // Scanner로 숫자 받을 때 쓸 변수(=저장공간)
		int tempAttack = ran.nextInt(5) + 30; // 임시변수
		int tempShield = ran.nextInt(5) + 15; // 임시변수
		int tempHealth = 150; // 임시변수
		int attack = 0; // 공격에서 방어력 뺀 값;
		int temp = 0; // 임시로 숫자를 저장해야할때 쓸 저장공간

		// 로직짜기(1층부터 50층까지)
		// 탑오르기 시작!

		climbLoop: // 희진이
		while (true) {

			int currentFloor = player.getCurrent_floor(); // 1층부터 시작(현재 층: db에서 가져오기)
//			System.out.println(currentFloor);

			// i층 파수꾼(Enemy) 초기화
			enemy_attack = tempAttack;
			enemy_defense = tempShield;
			enemy_health = tempHealth;
			enemy_attack = enemy_attack + ran.nextInt(5); // 파수꾼 공격력
			enemy_defense = enemy_defense + ran.nextInt(5); // 파수꾼 방어력
			enemy_health = enemy_health + ran.nextInt(30); // 파수꾼 체력
			tempAttack = enemy_attack;
			tempShield = enemy_defense;
			tempHealth = enemy_health;

			// 게임 계속 진행 or 로그아웃?
			if (currentFloor >= 2 ) {

				System.out.println("게임 계속 진행하시겠습니까? 아니면 로그아웃 하시겠습니까?");
				System.out.print("[1]계속 진행   [2]로그아웃>>");
				int sel = sc.nextInt();
				if (sel == 2) {
					// 로그아웃
					userController.logoutUser();
					System.out.println("로그아웃 되었습니다. 게임을 종료합니다.");
					break climbLoop; // 로그아웃 시 climbLoop 종료
				}
			}

			System.out.println(currentFloor + "층에 왔습니다. " + currentFloor + "층의 파수꾼이 당신을 기다리고 있습니다.");
			System.out.println("╔════════════ °• ♔ •° ════════════╗");

			System.out.println(user.getUserName() + "의 체력: " + player_health + " 공격력: " + player_attack + " 방어력: "
					+ player_defense);
			System.out.println("╚════════════ °• ♔ •° ════════════╝");
			System.out.println("파수꾼의 체력: " + enemy_health + " 공격력: " + enemy_attack + " 방어력: " + enemy_defense);
			battleLoop: while (true) {
				userTurnLoop: // 윤지님
				while (true) {
					System.out.println(
							"\n----------------------------------------------------------------------------\n");
					System.out.println("[1] 찌르기 [2] 베기 [3] 겁주기 [4] 숨고르기");
					System.out.print("행동을 선택하세요. >> ");
					choice = sc.next();
					if (choice.equals("1")) {
						// 찌르기: 상대에게 높은 데미지를 줄수도 있지만, 반대로 스쳐서 약한
						// 데미지를 줄 확률도 있다.
						attack = ran.nextInt(2 * player_attack) + player_attack / 2 - enemy_defense;
						if (attack > 0) {
							enemy_health = enemy_health - attack;
							System.out.println(user.getUserName() + "(이)가 깊숙이 찌릅니다!");
							if (enemy_health <= 0) {
								enemy_health = 0;
								System.out.println(currentFloor + "층 파수꾼의 체력: " + enemy_health);
								System.out.println(currentFloor + "층 파수꾼이 쓰러집니다!");
								break battleLoop;
							} else {
								System.out.println(currentFloor + "층 파수꾼의 체력: " + enemy_health);
								break userTurnLoop;
							}
						} else {
							attack = 0;
							System.out.println("파수꾼의 방어력을 뚫지 못했습니다!");
							break userTurnLoop;
						}
					} else if (choice.equals("2")) {
						// 베기: 정확한 데미지를 줄 수 있지만, 반대로 깊게 상처입히기는 힘들다.
						attack = ran.nextInt(10) + player_attack - enemy_defense;
						if (attack > 0) {
							enemy_health = enemy_health - attack;
							System.out.println(user.getUserName() + "(이)가 강하게 벱니다!");
							if (enemy_health <= 0) {
								enemy_health = 0;
								System.out.println(currentFloor + "층 파수꾼의 체력: " + enemy_health);
								System.out.println(currentFloor + "층 파수꾼이 쓰러집니다!");
								break battleLoop;
							} else {
								System.out.println(currentFloor + "층 파수꾼의 체력: " + enemy_health);
								break userTurnLoop;
							}
						} else {
							attack = 0;
							System.out.println("파수꾼의 방어력을 뚫지 못했습니다!");
							break userTurnLoop;
						}
					} else if (choice.equals("3")) {
						// 겁주기: 상대를 겁 줘서, 위축되게 만든다.(소량 상대의 공격력을 낮춘다.)
						enemy_attack = enemy_attack - ran.nextInt(10) - 3;
						System.out.println(user.getUserName() + "의 카리스마가 뿜뿜합니다! ");
						if (enemy_attack <= 0) {
							enemy_attack = 0;
							System.out.println(currentFloor + "층 파수꾼의 공격력: " + enemy_attack);
							break userTurnLoop;
						} else {
							System.out.println(currentFloor + "층 파수꾼의 공격력: " + enemy_attack);
							break userTurnLoop;
						}
					} else if (choice.equals("4")) {
						// 숨고르기: 차분하게 심호흡하며, 체력을 회복한다.
						player_health = player_health + ran.nextInt(10) + 50;
						System.out.println(user.getUserName() + "(이)가 차분히 숨을 고릅니다!");
						System.out.print(user.getUserName() + "의 체력이 " + player_health + "에서 ");
						System.out.println(player_health + "이 되었습니다! ");
						break userTurnLoop;
						/*
						 * (->절대값으로 체력회복시키는 이유는 초반에 유저체력이 낮을땐, 절대값이 상대적으로 큰 체력을 회복하지만, 후반에 유저체력과 적 공격력이
						 * 높을땐, 절대값 체력회복이 상대적으로 낮은 체력회복효과라고 느껴지게 하기 위해서 이다. -> 후반부로 갈수록 체력 회복은 상점의 포션으로
						 * 사용하게 해서, 골드 재화를 지속적으로 소비하게 하기 위해, + 공격력업과 방어력업을 할 수 있는 골드 재화를 너무 많이 남겨두지 못하고
						 * 포션 구매에 재화를 탕진할 수 있게 하기 위해 이다.)
						 */
					} else {
						System.out.println("1, 2, 3 중 선택해주세요");
					}
				} // userTurnLoop
				enemyTurnLoop: while (true) {
					System.out.println(currentFloor + "층 파수꾼이 [1] 간보기 [2] 이판사판 돌진 [3] 포션 마시기 중 선택합니다!");
					int sel = ran.nextInt(3) + 1;
					if (sel == 1) {
						// 간보기: 약한 데미지를 주며, 상대를 탐색합니다
						attack = ran.nextInt(10) + enemy_attack / 2 - player_defense;
						if (attack > 0) {
							player_health = player_health - attack;
							System.out.println(currentFloor + "층 파수꾼이 가볍게 공격하며, 상대의 상태를 파악합니다!");
							if (player_health <= 0) {
								player_health = 0;
								System.out.println(user.getUserName() + "의 체력: " + player_health + "이 되었습니다!");
								System.out.println("Game Over~!!!");
								System.out.println();
								System.out.println("[1] 종료하기 [2] 다시게임하기");
								System.out.print("선택지를 결정해주세요 >> ");
								choice = sc.next();
								if (choice.equals("1")) {
									break climbLoop; // programLoop인데 확인차 climbLoop로
								} else if (choice.equals("2")) {
									break climbLoop;
								}
							} else {
								System.out.println(user.getUserName() + "의 체력: " + player_health + "이 되었습니다!");
								break enemyTurnLoop;
							}
						} else {
							attack = 0;
							System.out.println("파수꾼이 가볍게 공격했지만 " + user.getUserName() + "의 방어력을 뚫지 못했습니다!");
							break enemyTurnLoop;
						}
					} else if (choice.equals("2")) {
						// 이판사판 돌진: 이판사판 돌진하여, user에게 큰 데미지를 주며,
						// 파수꾼 자신도 소량의 데미지를 입는다.
						System.out.println(currentFloor + "층 파수꾼이 이판사판 돌진하며, 공격합니다!");
						attack = ran.nextInt(10) + 3 * enemy_attack / 2 - player_defense;
						enemy_health = enemy_health - ran.nextInt(10) - enemy_attack / 5;
						if (enemy_health <= 0) {
							enemy_health = 0;
							System.out.println(currentFloor + "층 파수꾼의 체력: " + enemy_health);
							System.out.println(currentFloor + "층 파수꾼이 쓰러집니다!");
							break battleLoop;
						} else {
							if (attack > 0) {
								player_health = player_health - attack;
								if (player_health <= 0) {
									player_health = 0;
									System.out.println(user.getUserName() + "의 체력: " + player_health + "이 되었습니다!");
									System.out.println("Game Over~!!!");
									System.out.println();
									System.out.println("[1] 종료하기 [2] 다시게임하기");
									System.out.print("선택지를 결정해주세요 >> ");
									choice = sc.next();
									if (choice.equals("1")) {
										break climbLoop; // programLoop인데 확인차 climbLoop로
									} else if (choice.equals("2")) {
										break climbLoop;
									}
								} else {
									System.out.println(user.getUserName() + "의 체력: " + player_health + "이 되었습니다!");
									break enemyTurnLoop;
								}
							} else {
								attack = 0;
								System.out.println("파수꾼이 이판사판 돌진했지만 " + user.getUserName() + "의 방어력을 뚫지 못했습니다!");
								break enemyTurnLoop;
							}
						}
					} else {
						// 포션 마시기: 파수꾼이 포션을 마셔, 소량 체력을 회복합니다.
						System.out.println(currentFloor + "층 파수꾼이 주머니에서 포션을 꺼내 마십니다!");
						System.out.print(currentFloor + "층 파수꾼의 체력이 " + enemy_health + "에서 ");
						enemy_health = enemy_health + ran.nextInt(5) + enemy_defense / 5;
						System.out.print(enemy_health + "이 되었습니다! ");
						break enemyTurnLoop;
					}
				} // enemyTurnLoop
			} // battleLoop
			rewardLoop: while (true) {
				System.out.println(currentFloor + "층 파수꾼의 [1] 롱소드  [2] 갑옷  [3] 포션");
				System.out.print("셋 중 하나의 보상을 얻을 수 있습니다!  선택해주세요. >> ");
				choice = sc.next();
				if (choice.equals("1")) {
					System.out.println(currentFloor + "층 파수꾼의 롱소드를 얻으셨습니다!");
					temp = ran.nextInt(5) + 10;
					player_attack = player_attack + temp;
					System.out.println(user.getUserName() + "의 공격력이 " + temp + "만큼 증가했습니다!");
					System.out.println(user.getUserName() + "의 체력: " + player_health + " 공격력: " + player_attack
							+ " 방어력: " + player_defense);
					System.out.println(
							"\n----------------------------------------------------------------------------\n");
					break rewardLoop;
				} else if (choice.equals("2")) {
					System.out.println(currentFloor + "층 파수꾼의 갑옷을 얻으셨습니다!");
					temp = ran.nextInt(2) + 5;
					player_defense = player_defense + temp;
					System.out.println(user.getUserName() + "의 방어력이 " + temp + "만큼 증가했습니다!");
					System.out.println(user.getUserName() + "의 체력: " + player_health + " 공격력: " + player_attack
							+ " 방어력: " + player_defense);
					System.out.println(
							"\n----------------------------------------------------------------------------\n");
					break rewardLoop;
				} else if (choice.equals("3")) {
					System.out.println(currentFloor + "층 파수꾼의 포션을 얻으셨습니다!");
					temp = ran.nextInt(100) + 100; // 100~200 회복 시켜주기!
					player_health = player_health + temp;
					System.out.println(user.getUserName() + "의 체력이 " + temp + "만큼 증가했습니다!");
					System.out.println(user.getUserName() + "의 체력: " + player_health + " 공격력: " + player_attack
							+ " 방어력: " + player_defense);
					System.out.println(
							"\n----------------------------------------------------------------------------\n");
					break rewardLoop;
				} else {
					System.out.println("1,2,3 중 선택해주세요.");
				}
			} // rewardLoop
			if (currentFloor == 50) {
				System.out.println("Congratulation!!");
				System.out.println("파수꾼의 탑 50층을 모두 정벌하셨습니다!");
				System.out.println("");
				System.out.println("축하합니다. 게임을 종료하겠습니다.");
				System.out.println("해당 아이디로 다시 접속할 경우, 50층 파수꾼을 다시 격파해보실 수 있습니다.");


				break climbLoop;
				// climbLoop

			}

			// 플레이어 상태 업데이트
			player.setplayer_health(player_health);
			player.setplayer_attack(player_attack);
			player.setplayer_defense(player_defense);
			try {
				playerDAO.updatePlayer(player); // 플레이어 정보 업데이트
			} catch (SQLException e) {
				System.out.println("플레이어 정보 업데이트 중 SQL 오류 발생");
			}

			// 적 상태 업데이트
			enemy.setEnemy_health(enemy_health);
			enemy.setEnemy_attack(enemy_attack);
			enemy.setEnemy_defense(enemy_defense);

			try {
				if (enemyDAO.isEnemyExists(enemy.getEnemy_id())) {
					enemyDAO.updateEnemy(enemy); // 적 정보 업데이트
				} else {
					enemyDAO.insertEnemy(enemy); // 적 정보 삽입
				}
			} catch (SQLException e) {
				System.out.println("적 정보 저장/업데이트 중 SQL 오류 발생");
			}

			if (currentFloor < 50) {

				currentFloor++; // 1층 올리고
				player.setCurrent_floor(currentFloor); // current_floor db 업데이트
			}

		} // climbloop
	}

}
