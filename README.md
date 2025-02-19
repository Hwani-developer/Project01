**[프로젝트 개요]**

1. 프로젝트 제목: 탑의 정벌 이야기 - 탑의 제왕(Lord of Tower)
2. 프로젝트 개요
   "탑의 제왕"은 플레이어가 50층의 탑을 정복하는 턴제 텍스트 어드벤처 게임이다.
   각 층마다 파수꾼이 지키고 있으며, 플레이어는 이 파수꾼을 물리치고 최종적으로 탑의 제왕이 되는 것이 목표이다.
   이 게임은 Java 및 MVC 패턴으로 구현되었으며, Java 프로그래밍, 객체 지향 설계, 게임 내 랜덤성 구현, 사용자 입력 검증 등의 기본적 JAVA 프로그래밍 기술을 향상하기 위해 기획한 일종의 미니 프로젝트이다.

3. 게임 진행 로직
   1) 게임 메커니즘
   - 플레이어는 탑을 올라가며 각 층마다 적인 파수꾼과 전투를 벌인다.
   - 플레이어와 적은 각각의 턴을 번갈아가며 행동을 선택한다.
   - 플레이어가 체력이 모두 소진되거나 50층을 정복하면 게임이 종료된다.
          
   2) 플레이어 행동: 플레이어는 자신의 턴(Turn)에 아래 4가지 주요 행동을 선택할 수 있다.
   - 찌르기: 높은 데미지를 줄 수 있지만 편차가 심한 편 (High Risk, High Return)
   - 베기: 안정적인 데미지를 주지만 강력한 공격력을 기대하기는 어려움
   - 겁주기: 적의 공격력 감소시키는 효과 
   - 숨고르기: 플레이어 자신의 체력을 일정량 회복시키는 효과
     
   3) 적 행동: 적인 파수꾼은 랜덤으로 아래 행동 중 하나를 선택한다.
   - 간보기: 약한 데미지를 주며 상대 탐색
   - 이판사판 돌진: 강력한 데미지를 주지만 적 자신도 약간의 피해 입음 
   - 포션 마시기: 적의 체력을 소량 회복한다.
     
   4) 전투 시스템
   - 플레이어 턴: [1]찌르기, [2]베기, [3]겁주기, [4]숨고르기 4가지 중 하나 선택하여 적에게 공격하거나 자신을 회복할 수 있다.
   - 적 턴: [1] 간보기, [2]이판사판 돌진, [3] 포션 마시기 행동을 랜덤으로 하나 선택한다.
   - 한쪽 체력이 0이 될때까지 게임은 지속된다. 만약 플레이어 체력이 0이면 게임은 종료된다.
     반면, 적의 체력이 0이 되면 플레이어는 다음 층으로 올라갈 수 있게 된다.
     
   5) 게임 종료 조건
   - 플레이어가 체력이 모두 소진되면 게임 오버된다.
   - 플레이어가 50층까지 게임을 정복할 경우 게임 오버된다. 이 경우 50층을 다시 플레이할 수 있는 옵션이 주어진다.
     
   6) 보상 시스템
   - 롱소드: 플레이어 공격력 증가
   - 갑옷: 플레이어 방어력 증가
   - 포션: 플레이어 체력 회복

4. 프로젝트 목표
   - Java의 기본 문법과 로직에 대한 이해를 높이고, 게임 로직을 설계하는 경험을 쌓기
   - 객체 지향 프로그래밍(OOP) 개념 및 MVC 패턴을 활용하여 프로그램 구조화 및 관리 능력 향상하기
   - 사용자 입력 검증 및 예외 처리를 통해 보다 안정적인 프로그램을 구현하기
   - 랜덤성 구현을 통해 게임의 흥미 요소를 추가하고, 다양한 결과를 도출할 수 있도록 하기

5. 진행 순서
   - 1단계: 기본 전투 시스템 및 플레이어와 적의 턴 기반 행동 로직 설계
   - 2단계: 데이터베이스 설계  
   - 3단계: 회원가입, 로그인 등 회원관리 로직 구현(MVC패턴)
   - 4단계: 게임 로직 구현(MVC 패턴)
   - 5단계: 사용자 입력 검증 및 예외 처리 추가
   - 6단계: 최종 테스트 및 디버깅, 게임 완성 
