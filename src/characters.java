import java.util.*;

public class characters {
    OvalHitbox hitbox;
    String name, description, attack1name, attack2name, attack3name, attack4name, specialattackname;
    int HP, attack1power, attack2power, attack3power, attack4power, specialattackpower;
    Boolean moveLeft, moveRight, jumping, doubleJumping;

    public void setHitbox(OvalHitbox hitbox) {
        this.hitbox = hitbox;
    }

    public Boolean getMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(Boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public Boolean getMoveRight() {
        return moveRight;
    }

    public void setMoveRight(Boolean moveRight) {
        this.moveRight = moveRight;
    }

    public Boolean getJumping() {
        return jumping;
    }

    public void setJumping(Boolean jumping) {
        this.jumping = jumping;
    }

    public Boolean getDoubleJumping() {
        return doubleJumping;
    }

    public void setDoubleJumping(Boolean doubleJumping) {
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

    public int getAttack4power() {
        return attack4power;
    }

    public void setAttack4power(int attack4power) {
        this.attack4power = attack4power;
    }

    public void setHitbox(Hitbox hitbox) {
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

    public int getHP() {
        return HP;
    }

    public void setHP(int hP) {
        HP = hP;
    }

    public int getAttack1power() {
        return attack1power;
    }

    public void setAttack1power(int attack1power) {
        this.attack1power = attack1power;
    }

    public int getAttack2power() {
        return attack2power;
    }

    public void setAttack2power(int attack2power) {
        this.attack2power = attack2power;
    }

    public int getAttack3power() {
        return attack3power;
    }

    public void setAttack3power(int attack3power) {
        this.attack3power = attack3power;
    }
 
    public int getSpecialattackpower() {
        return specialattackpower;
    }

    public void setSpecialattackpower(int specialattackpower) {
        this.specialattackpower = specialattackpower;
    }
}
