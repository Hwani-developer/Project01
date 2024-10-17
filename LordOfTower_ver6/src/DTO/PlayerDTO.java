package DTO;

public class PlayerDTO {
	//필드
	private int player_id;
	private int player_health;
	private int player_attack;
	private int player_defense;
	private int user_id;
	private int current_floor;
	
	//초기생성자
	public PlayerDTO(int player_id, int player_health, int player_attack, int player_defense, int user_id, int current_floor) {
		
		this.player_id = player_id;
		this.player_health = player_health;
		this.player_attack = player_attack;
		this.player_defense = player_defense;
		this.user_id = user_id;
		this.current_floor = current_floor;
	}

	// getter setter
	public int getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(int player_id) {
		this.player_id = player_id;
	}

	public int getplayer_health() {
		return player_health;
	}

	public void setplayer_health(int player_health) {
		this.player_health = player_health;
	}

	public int getplayer_attack() {
		return player_attack;
	}

	public void setplayer_attack(int player_attack) {
		this.player_attack = player_attack;
	}

	public int getplayer_defense() {
		return player_defense;
	}

	public void setplayer_defense(int player_defense) {
		this.player_defense = player_defense;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public int getCurrent_floor() {
		return current_floor;
	}

	public void setCurrent_floor(int current_floor) {
		this.current_floor = current_floor;
	}
	
	
	
	
	
}

