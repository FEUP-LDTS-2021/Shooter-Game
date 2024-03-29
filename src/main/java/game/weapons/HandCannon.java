package game.weapons;

public class HandCannon extends Weapon{

    @Override
    protected char generateBulletChar() {
        return 'b';
    }

    @Override
    protected char generateType() {
        return 'P';
    }

    @Override
    protected String generateName() {
        return "HAND CANNON";
    }

    @Override
    protected int generateDamage() {
        return 40;
    }

    @Override
    protected int generateRange() {
        return 7;
    }

    @Override
    protected int getStartAmmo() {
        return -1;
    }

}
