package game.weapons;

import com.googlecode.lanterna.graphics.TextGraphics;

public class Shotgun extends SpecialWeapon {
    public Shotgun(int damage, int range, int ammo) {
        super(damage, range, ammo);
    }

    //private int xAoE; //AoE stands for Area of Effect
   // private int yAoE;

    @Override
    public void draw(TextGraphics graphics) {

    }
}