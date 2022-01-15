package enemies

import game.Position
import game.enemies.Acolyte
import game.weapons.HandCannon
import game.weapons.Weapon
import spock.lang.Specification

class AcolyteTest extends Specification {
    def 'Acolyte Creation'() {
        given:
        Acolyte enemy = new Acolyte(new Position(3,3));

        when:
        def health = enemy.getHealth()
        Weapon weapon = enemy.getWeapon();
        then:
        weapon instanceof HandCannon;
        health == 60
    }

    def 'Moving Acolyte'() {
        given:
        Acolyte enemy1 = new Acolyte(new Position(10,10));
        Acolyte enemy2 = new Acolyte(new Position(10,10));
        when:
        enemy1.setPosition(enemy1.moveUp())
        enemy1.setPosition(enemy1.moveRight())
        enemy2.setPosition(enemy2.moveLeft())
        enemy2.setPosition(enemy2.moveDown())
        then:
        enemy1.getPosition() == new Position(10,9)
        enemy2.getPosition() == new Position(9,10)
    }

    def 'Get Damaged'()
    {
        given:
        Acolyte Acolyte = new Acolyte(new Position(10,10))
        Acolyte Acolyte1 = new Acolyte(new Position(10,10))
        when:
        Acolyte.takeDamage(50)
        Acolyte1.takeDamage(60)
        then:
        Acolyte.getHealth() == 10
        Acolyte1.getHealth() == 0

    }

    def 'Delay Test'()
    {
        given:
        Acolyte enemy = new Acolyte(new Position(10,10));
        Acolyte enemy1 = new Acolyte(new Position(10,10));
        when:
        enemy1.setPosition(enemy1.moveUp())
        enemy1.setPosition(enemy1.moveUp())
        enemy.setPosition(enemy1.moveUp())
        enemy.setPosition(enemy1.moveUp())
        enemy.setPosition(enemy1.moveUp())
        enemy.setPosition(enemy1.moveUp())
        then:
        enemy1.getPosition() == new Position(10,9)
        enemy1.getRemainingTime() == 5
        enemy.getPosition() == new Position(10,8)
    }
}