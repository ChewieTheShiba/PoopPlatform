import java.util.*;

public class characters {
    Hitbox hitbox;
    String name, description, attack1name, attack2name, attack3name;
    int HP, attack1power, attack2power, attack3power, specialattackpower;

    public Hitbox getHitbox() {
        return hitbox;
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
