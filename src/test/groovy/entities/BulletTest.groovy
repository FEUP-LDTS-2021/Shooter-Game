package entities

import game.entities.Bullet
import game.entities.Player
import game.entities.Position
import game.weapons.HandCannon
import game.weapons.Shotgun
import game.weapons.Weapon
import spock.lang.Specification

class BulletTest extends Specification
{
    def 'Bullet Creation'()
    {
        given:
        Player player = Stub()
        player.getPosition() >> new Position(10,10)
        Weapon weapon = new HandCannon()
        Weapon weapon2 = new Shotgun()
        Bullet bullet = new Bullet(player.getPosition(),weapon, 'N' as char,false)
        Bullet bullet1 = new Bullet(player.getPosition(),weapon2, 'N' as char,false)
        when:
        int range = bullet.getRange()
        bullet1.decreaseRange()
        then:
        bullet1.getRange() == 1
        range == 7
    }
}