import java.util.*;

public class Character {
    OvalHitbox hitbox;
    String name, description, attack1name, attack2name, attack3name, attack4name, specialattackname;
    double HP, Weight, attack1power, attack1knockback, attack2power, attack2knockback, attack3power, attack3knockback, attack4power, attack4knockback, specialattackpower, specialknockback;
    boolean jumping, doubleJumping, facingRight;
    
    //For Charlie
    //Make ImageIcon variables for each attack image (i.e. attack1Image, attack2Image, etc.), these don't need getters and setters
    //Make constructor take in parameters in this order
    //hitbox, attack1power, attack1knockback, attack1Image, (do this for each attack with special attack being at the end), HP, weight, name
    //override the .equals method so that it compares the string names using string.equals() and if they're the same it returns true and if else return false
    //make a String variable called currentAttack with setters and getters
    
    //Also leave this here for reference but
    /*
    attack 1 = left tilt
    attack 2 = right tilt
    attack 3 = up tilt
    attack 4 = down tilt
    special = special
    */
    
    
    public Character(OvalHitbox hitbox)
    {
    	this.hitbox = hitbox;
    	hitbox.setKB(600);
    	hitbox.setDamage(10);
    }
    
public void setH(double wantedH){
        
        int IntH = (int) wantedH;
        
        hitbox.setH(IntH);
    }

    public void setK(double wantedK){
        int IntK = (int) wantedK;

        hitbox.setK(IntK);
    }
    
    public void setMoveRight(boolean moveRight){
        hitbox.setMoveRight(moveRight);
        if(moveRight)
        	facingRight = true;
    }

    public boolean getMoveRight(){
    	
    	return hitbox.getMoveRight();
    }
    
    public void setMoveLeft(boolean moveLeft){
        hitbox.setMoveLeft(moveLeft);
        if(moveLeft)
        	facingRight = false;
    }

    public boolean getMoveLeft(){
    	
    	return hitbox.getMoveLeft();
    }
    
    public void setMoveUp(boolean moveUp){
        hitbox.setMoveUp(moveUp);
    }

    public boolean getMoveUp(){
    	
    	return hitbox.getMoveUp();
    }
    
    public void setMoveDown(boolean moveDown){
        hitbox.setMoveDown(moveDown);
    }

    public boolean getMoveDown(){
    	
    	return hitbox.getMoveDown();
    }

    
    public double getWeight() {
        return Weight;
    }


    public void setWeight(double weight) {
        Weight = weight;
    }


    public double getAttack1knockback() {
        return attack1knockback;
    }


    public void setAttack1knockback(double attack1knockback) {
        this.attack1knockback = attack1knockback;
    }


    public double getAttack2knockback() {
        return attack2knockback;
    }


    public void setAttack2knockback(double attack2knockback) {
        this.attack2knockback = attack2knockback;
    }


    public double getAttack3knockback() {
        return attack3knockback;
    }


    public void setAttack3knockback(double attack3knockback) {
        this.attack3knockback = attack3knockback;
    }


    public double getAttack4knockback() {
        return attack4knockback;
    }


    public void setAttack4knockback(double attack4knockback) {
        this.attack4knockback = attack4knockback;
    }


    public double getSpecialKnockback() {
        return specialknockback;
    }


    public void setSpecialKnockback(double specialknockback) {
        this.specialknockback = specialknockback;
    }

    public Boolean getJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
        hitbox.setMoveUp(jumping);
    }

    public Boolean getdoubleJumping() {
        return doubleJumping;
    }

    public void setdoubleJumping(boolean doubleJumping) {
        this.doubleJumping = doubleJumping;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public String getAttack4name() {
        return attack4name;
    }

    public void setAttack4name(String attack4name) {
        this.attack4name = attack4name;
    }

    public String getSpecialattackname() {
        return specialattackname;
    }

    public void setSpecialattackname(String specialattackname) {
        this.specialattackname = specialattackname;
    }

    public double getAttack4power() {
        return attack4power;
    }

    public void setAttack4power(double attack4power) {
        this.attack4power = attack4power;
    }

    public void setHitbox(OvalHitbox hitbox) {
        this.hitbox = hitbox;
    }
 
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttack1name() {
        return attack1name;
    }

    public void setAttack1name(String attack1name) {
        this.attack1name = attack1name;
    }

    public String getAttack2name() {
        return attack2name;
    }

    public void setAttack2name(String attack2name) {
        this.attack2name = attack2name;
    }

    public String getAttack3name() {
        return attack3name;
    }

    public void setAttack3name(String attack3name) {
        this.attack3name = attack3name;
    }

    public double getHP() {
        return HP;
    }

    public void setHP(double hP) {
        HP = hP;
    }

    public double getAttack1power() {
        return attack1power;
    }

    public void setAttack1power(double attack1power) {
        this.attack1power = attack1power;
    }

    public double getAttack2power() {
        return attack2power;
    }

    public void setAttack2power(double attack2power) {
        this.attack2power = attack2power;
    }

    public double getAttack3power() {
        return attack3power;
    }

    public void setAttack3power(double attack3power) {
        this.attack3power = attack3power;
    }
 
    public double getSpecialattackpower() {
        return specialattackpower;
    }

    public void setSpecialattackpower(double specialattackpower) {
        this.specialattackpower = specialattackpower;
    }

	public boolean getDoubleJumping()
	{
		return doubleJumping;
	}

	public void setDoubleJumping(boolean doubleJumping)
	{
		this.doubleJumping = doubleJumping;
	}

	public boolean getFacingRight()
	{
		return facingRight;
	}

	public void setFacingRight(boolean facingRight)
	{
		this.facingRight = facingRight;
	}
    
}
