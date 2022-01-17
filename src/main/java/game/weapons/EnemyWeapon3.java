package game.weapons;

public class EnemyWeapon3 extends Weapon {
    @Override
    protected int generateDamage() {return 20;}

    @Override
    protected int generateRange() {
        return 10;
    }

    @Override
    protected int getStartAmmo() {return -1;}
}
