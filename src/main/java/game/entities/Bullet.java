package game.entities;


import game.weapons.Weapon;

public class Bullet extends MovingEntity{
    private int range;
    private final int damage;
    private final boolean shotByPlayer;
    private final char character;
    public Bullet(Position position, Weapon weapon, char direction, boolean shotByPlayer, char character) {
        super(position);
        this.character = character;
        this.setDirection(direction);
        damage = weapon.getDamage();
        range = weapon.getRange();
        this.shotByPlayer = shotByPlayer;
    }

    @Override
    protected int generateActionDelay() {
        return 0;
    }

    @Override
    protected char generateCharacter() {
        return 'b';
    }

    @Override
    protected String generateColor() {
        return "#000000";
    }

    public boolean decreaseRange()
    {
        if(range > 0){ range --; return true;}
        return false;
    }

    public int getRange() {
        return range;
    }

    public int getDamage() {
        return damage;
    }

    public void move()
    {
        switch(getDirection())
        {
            case 'N':
                this.setPosition(this.moveUp()); break;
            case 'E':
                this.setPosition(this.moveRight()); break;
            case 'W':
                this.setPosition(this.moveLeft()); break;
            case 'S':
                this.setPosition(this.moveDown()); break;
        }
    }

    public boolean isShotByPlayer() {
        return shotByPlayer;
    }

    @Override
    public char getCharacter() {
        return character;
    }
}
