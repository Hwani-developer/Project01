package DTO;

public class EnemyDTO {
	
//	필드
	private int enemy_id;
	private int enemy_health;
	private int enemy_attack;
	private int enemy_defense;
	private int player_id;
	
	// 초기생성자
	public EnemyDTO(int enemy_id, int enemy_health, int enemy_attack, int enemy_defense, int player_id) {
		
		this.enemy_id = enemy_id;
		this.enemy_health = enemy_health;
		this.enemy_attack = enemy_attack;
		this.enemy_defense = enemy_defense;
		this.player_id = player_id;
	}
	
	// getter setter
	public int getEnemy_id() {
		return enemy_id;
	}

	public void setEnemy_id(int enemy_id) {
		this.enemy_id = enemy_id;
	}

	public int getEnemy_health() {
		return enemy_health;
	}

	public void setEnemy_health(int enemy_health) {
		this.enemy_health = enemy_health;
	}

	public int getEnemy_attack() {
		return enemy_attack;
	}

	public void setEnemy_attack(int enemy_attack) {
		this.enemy_attack = enemy_attack;
	}

	public int getEnemy_defense() {
		return enemy_defense;
	}

	public void setEnemy_defense(int enemy_defense) {
		this.enemy_defense = enemy_defense;
	}

	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}
	
	
	
}



