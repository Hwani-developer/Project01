//package View;
//
//import java.util.Random;
//import java.util.Scanner;
//
//import Controller.userController;
//import DAO.EnemyDAO;
//import DAO.PlayerDAO;
//import DAO.userDAO;
//import DTO.PlayerDTO;
//import DTO.UsersDTO;
//
//public class Game {
//
//	 // 로그인 후 게임 메뉴
//    public static void game(userController userController, UsersDTO loggedInUser) {
//      	
//    	Scanner sc = new Scanner(System.in);
//		Random ran = new Random();
//		
//		
//		int current_floor = 1; // 층수 (1층으로 선언 및 할당=초기화)
//		int choice = 0; // Scanner로 숫자를 받을 때 쓸 변수(=저장공간)
//		String inputID = "input"; // Scanner로 글자 받을때, 쓸 변수
//		// 앞으로f 나올 스텟값들은 게임 실행이 된다면, 적극적으로 의견내서, 바꿔도 좋다!
//	
//		// 유저(Player)
////		PlayerDTO(int player_id, int health, int attack, int defense, int user_id, int current_floor)
//		
//		int player_attack = 60; // 유저 공격력
//		int player_defense = 30; // 유저 방어력
//		int player_health = 500; // 유저 체력 // (경험치와 레벨은 아래 로직에 넣기 어려워 뺐습니다!)
//		// 파수꾼(Enemy)=> 랜덤수로 구현
//		int enemy_attack; // 파수꾼 공격력 =>enemy
//		int enemy_defense ; // 파수꾼 방어력
//		int enemy_health ; // 파수꾼 체력
//		// 임시변수
//		int tempAttack  = ran.nextInt(5) + 30;
//		int tempShield = ran.nextInt(5) + 15;
//		int tempHealth = 150;
//		
//		int attack = 0; // 공격에서 방어력 뺀 값;
//		int temp = 0; // 임시로 숫자를 저장해야할때 쓸 저장공간
//		// 가을이, test, 0은 초기화를 위해 임시로 할당한 값입니다!
//		
//		// 아래 값들도 mainView에서 로그인 완료된 경우 그 정보들을 그대로 가져올 예정임.
//		String userName = "가을이"; // 게임에서 사용할 유저의 이름
//		String USER_ID = "test"; // 유저의 아이디
//		int PASSWORD = 12345; // 유저의 비밀번호
//		
//		// 탑오르기 시작!
//		climbLoop: // 희진이
//		for (int i = 1; i <= 50; i++) {
//			current_floor = i;
//			// i층 파수꾼(Enemy) 초기화
//			enemy_attack=tempAttack;
//			enemy_defense=tempShield;
//			enemy_health=tempHealth;
//			enemy_attack = enemy_attack + ran.nextInt(5); // 파수꾼 공격력
//			enemy_defense = enemy_defense + ran.nextInt(5); // 파수꾼 방어력
//			enemy_health = enemy_health + ran.nextInt(30); // 파수꾼 체력
//			tempAttack=enemy_attack;
//			tempShield=enemy_defense;
//			tempHealth=enemy_health;
//			System.out.println(current_floor + "층에 왔습니다. " + current_floor + "층의 파수꾼이 당신을 기다리고 있습니다.");
//			System.out.println("╔════════════ °• ♔ •° ════════════╗");
//			
//			System.out.println(userName+"의 체력: "+player_health+" 공격력: "+player_attack+" 방어력: "+player_defense);
//			System.out.println("╚════════════ °• ♔ •° ════════════╝");
//			System.out.println("파수꾼의 체력: " + enemy_health + " 공격력: " + enemy_attack + " 방어력: " + enemy_defense);
//			battleLoop: while (true) {
//				userTurnLoop: // 윤지님
//				while (true) {
//					System.out.println("\n----------------------------------------------------------------------------\n");
//					System.out.println("[1] 찌르기 [2] 베기 [3] 겁주기 [4] 숨고르기");
//					System.out.print("행동을 선택하세요. >> ");
//					choice = sc.nextInt();
//					if (choice == 1) {
//						// 찌르기: 상대에게 높은 데미지를 줄수도 있지만, 반대로 스쳐서 약한
//						// 데미지를 줄 확률도 있다.
//						attack = ran.nextInt(2 * player_attack) + player_attack / 2 - enemy_defense;
//						if (attack > 0) {
//							enemy_health = enemy_health - attack;
//							System.out.println(userName + "(이)가 깊숙이 찌릅니다!");
//							if (enemy_health <= 0) {
//								enemy_health = 0;
//								System.out.println(i + "층 파수꾼의 체력: " + enemy_health);
//								System.out.println(i + "층 파수꾼이 쓰러집니다!");
//								break battleLoop;
//							} else {
//								System.out.println(i + "층 파수꾼의 체력: " + enemy_health);
//								break userTurnLoop;
//							}
//						} else {
//							attack = 0;
//							System.out.println("파수꾼의 방어력을 뚫지 못했습니다!");
//							break userTurnLoop;
//						}
//					} else if (choice == 2) {
//						// 베기: 정확한 데미지를 줄 수 있지만, 반대로 깊게 상처입히기는 힘들다.
//						attack = ran.nextInt(10) + player_attack - enemy_defense;
//						if (attack > 0) {
//							enemy_health = enemy_health - attack;
//							System.out.println(userName + "(이)가 강하게 벱니다!");
//							if (enemy_health <= 0) {
//								enemy_health = 0;
//								System.out.println(i + "층 파수꾼의 체력: " + enemy_health);
//								System.out.println(i + "층 파수꾼이 쓰러집니다!");
//								break battleLoop;
//							} else {
//								System.out.println(i + "층 파수꾼의 체력: " + enemy_health);
//								break userTurnLoop;
//							}
//						} else {
//							attack = 0;
//							System.out.println("파수꾼의 방어력을 뚫지 못했습니다!");
//							break userTurnLoop;
//						}
//					} else if (choice == 3) {
//						// 겁주기: 상대를 겁 줘서, 위축되게 만든다.(소량 상대의 공격력을 낮춘다.)
//						enemy_attack = enemy_attack - ran.nextInt(10) - 3;
//						System.out.println(userName + "의 카리스마가 뿜뿜합니다! ");
//						if (enemy_attack <= 0) {
//							enemy_attack = 0;
//							System.out.println(i + "층 파수꾼의 공격력: " + enemy_attack);
//							break userTurnLoop;
//						} else {
//							System.out.println(i + "층 파수꾼의 공격력: " + enemy_attack);
//							break userTurnLoop;
//						}
//					} else if (choice == 4) {
//						// 숨고르기: 차분하게 심호흡하며, 체력을 회복한다.
//						player_health = player_health + ran.nextInt(10) + 50;
//						System.out.println(userName + "(이)가 차분히 숨을 고릅니다!");
//						System.out.print(userName + "의 체력이 " + player_health + "에서 ");
//						System.out.println(player_health + "이 되었습니다! ");
//						break userTurnLoop;
//						/*
//						 * (->절대값으로 체력회복시키는 이유는 초반에 유저체력이 낮을땐, 절대값이 상대적으로 큰 체력을 회복하지만, 후반에 유저체력과 적 공격력이
//						 * 높을땐, 절대값 체력회복이 상대적으로 낮은 체력회복효과라고 느껴지게 하기 위해서 이다. -> 후반부로 갈수록 체력 회복은 상점의 포션으로
//						 * 사용하게 해서, 골드 재화를 지속적으로 소비하게 하기 위해, + 공격력업과 방어력업을 할 수 있는 골드 재화를 너무 많이 남겨두지 못하고
//						 * 포션 구매에 재화를 탕진할 수 있게 하기 위해 이다.)
//						 */
//					} else {
//						System.out.println("1, 2, 3 중 선택해주세요");
//					}
//				} // userTurnLoop
//				enemyTurnLoop: while (true) {
//					System.out.println(i + "층 파수꾼이 [1] 간보기 [2] 이판사판 돌진 [3] 포션 마시기 중 선택합니다!");
//					choice = ran.nextInt(3) + 1;
//					if (choice == 1) {
//						// 간보기: 약한 데미지를 주며, 상대를 탐색합니다
//						attack = ran.nextInt(10) + enemy_attack / 2 - player_defense;
//						if (attack > 0) {
//							player_health = player_health - attack;
//							System.out.println(i + "층 파수꾼이 가볍게 공격하며, 상대의 상태를 파악합니다!");
//							if (player_health <= 0) {
//								player_health = 0;
//								System.out.println(userName + "의 체력: " + player_health + "이 되었습니다!");
//								System.out.println("Game Over~!!!");
//								System.out.println();
//								System.out.println("[1] 종료하기 [2] 다시게임하기");
//								System.out.print("선택지를 결정해주세요 >> ");
//								choice = sc.nextInt();
//								if (choice == 1) {
//									break climbLoop; // programLoop인데 확인차 climbLoop로
//								} else if (choice == 2) {
//									break climbLoop;
//								}
//							} else {
//								System.out.println(userName + "의 체력: " + player_health + "이 되었습니다!");
//								break enemyTurnLoop;
//							}
//						} else {
//							attack = 0;
//							System.out.println("파수꾼이 가볍게 공격했지만 " + userName + "의 방어력을 뚫지 못했습니다!");
//							break enemyTurnLoop;
//						}
//					} else if (choice == 2) {
//						// 이판사판 돌진: 이판사판 돌진하여, user에게 큰 데미지를 주며,
//						// 파수꾼 자신도 소량의 데미지를 입는다.
//						System.out.println(i + "층 파수꾼이 이판사판 돌진하며, 공격합니다!");
//						attack = ran.nextInt(10) + 3 * enemy_attack / 2 - player_defense;
//						enemy_health = enemy_health - ran.nextInt(10) - enemy_attack / 5;
//						if (enemy_health <= 0) {
//							enemy_health = 0;
//							System.out.println(i + "층 파수꾼의 체력: " + enemy_health);
//							System.out.println(i + "층 파수꾼이 쓰러집니다!");
//							break battleLoop;
//						} else {
//							if (attack > 0) {
//								player_health = player_health - attack;
//								if (player_health <= 0) {
//									player_health = 0;
//									System.out.println(userName + "의 체력: " + player_health + "이 되었습니다!");
//									System.out.println("Game Over~!!!");
//									System.out.println();
//									System.out.println("[1] 종료하기 [2] 다시게임하기");
//									System.out.print("선택지를 결정해주세요 >> ");
//									choice = sc.nextInt();
//									if (choice == 1) {
//										break climbLoop; // programLoop인데 확인차 climbLoop로
//									} else if (choice == 2) {
//										break climbLoop;
//									}
//								} else {
//									System.out.println(userName + "의 체력: " + player_health + "이 되었습니다!");
//									break enemyTurnLoop;
//								}
//							} else {
//								attack = 0;
//								System.out.println("파수꾼이 이판사판 돌진했지만 " + userName + "의 방어력을 뚫지 못했습니다!");
//								break enemyTurnLoop;
//							}
//						}
//					} else {
//						// 포션 마시기: 파수꾼이 포션을 마셔, 소량 체력을 회복합니다.
//						System.out.println(i + "층 파수꾼이 주머니에서 포션을 꺼내 마십니다!");
//						System.out.print(i + "층 파수꾼의 체력이 " + enemy_health + "에서 ");
//						enemy_health = enemy_health + ran.nextInt(5) + enemy_defense / 5;
//						System.out.print(enemy_health + "이 되었습니다! ");
//						break enemyTurnLoop;
//					}
//				} // enemyTurnLoop
//			} // battleLoop
//			rewardLoop: while (true) {
//				System.out.println(i + "층 파수꾼의 [1] 롱소드  [2] 갑옷  [3] 포션");
//				System.out.print("셋 중 하나의 보상을 얻을 수 있습니다!  선택해주세요. >> ");
//				choice = sc.nextInt();
//				if (choice == 1) {
//					System.out.println(i + "층 파수꾼의 롱소드를 얻으셨습니다!");
//					temp = ran.nextInt(5) + 10;
//					player_attack = player_attack + temp;
//					System.out.println(userName + "의 공격력이 " + temp + "만큼 증가했습니다!");
//					System.out.println(userName + "의 체력: " + player_health + " 공격력: " + player_attack + " 방어력: " + player_defense);
//					System.out.println("\n----------------------------------------------------------------------------\n");
//					break rewardLoop;
//				} else if (choice == 2) {
//					System.out.println(i + "층 파수꾼의 갑옷을 얻으셨습니다!");
//					temp = ran.nextInt(2) + 5;
//					player_defense = player_defense + temp;
//					System.out.println(userName + "의 방어력이 " + temp + "만큼 증가했습니다!");
//					System.out.println(userName + "의 체력: " + player_health + " 공격력: " + player_attack + " 방어력: " + player_defense);
//					System.out.println("\n----------------------------------------------------------------------------\n");
//					break rewardLoop;
//				} else if (choice == 3) {
//					System.out.println(i + "층 파수꾼의 포션을 얻으셨습니다!");
//					temp = ran.nextInt(100) + 100; // 100~200 회복 시켜주기!
//					player_health = player_health + temp;
//					System.out.println(userName + "의 체력이 " + temp + "만큼 증가했습니다!");
//					System.out.println(userName + "의 체력: " + player_health + " 공격력: " + player_attack + " 방어력: " + player_defense);
//					System.out.println("\n----------------------------------------------------------------------------\n");
//					break rewardLoop;
//				} else {
//					System.out.println("1,2,3 중 선택해주세요.");
//				}
//			} // rewardLoop
//			if (i == 50) {
//				System.out.println("Congratulation!!");
//				System.out.println("파수꾼의 탑 50층을 모두 정벌하셨습니다!");
//				System.out.println();
//				System.out.println("[1] 종료하기 [2] 다시게임하기");
//				System.out.print("선택지를 결정해주세요 >> ");
//				choice = sc.nextInt();
//				if (choice == 1) {
//					break climbLoop; // programLoop인데 확인차 climbLoop로
//				} else if (choice == 2) {
//					break climbLoop;
//				}
//			}
//		} // climbLoop
//
//          
//    }
//          
//    
//}
