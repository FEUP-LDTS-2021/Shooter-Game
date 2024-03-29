package game.gui;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFrame;
import game.*;
import game.entities.Bullet;
import game.entities.ImmobileEntity;
import game.entities.MovingEntity;
import game.entities.Position;
import game.state.command.Command;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class LanternaGUI implements GUI {

    private final TerminalScreen screen;
    private final int width;
    private final int height;

    public LanternaGUI(int height, int width) throws URISyntaxException, IOException, FontFormatException {
        AWTTerminalFontConfiguration fontConfig = AWTTerminalFontConfiguration.newInstance(loadFont());
        TerminalSize terminalSize = new TerminalSize(width,height);
        Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(terminalSize).setForceAWTOverSwing(true)
                .setTerminalEmulatorFontConfiguration(fontConfig).createTerminal();
        ((AWTTerminalFrame)terminal).addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().dispose();
                System.exit(0);
            }
        });
        this.height=height;
        this.width=width;
        screen = new TerminalScreen(terminal);
        screen.setCursorPosition(null);
        screen.startScreen();
        screen.doResizeIfNecessary();
    }

    private Font loadFont() throws URISyntaxException, IOException, FontFormatException {
        URL resource = getClass().getClassLoader().getResource("fate.ttf");
        File fontFile = new File(resource.toURI());
        Font font =  Font.createFont(Font.TRUETYPE_FONT, fontFile);
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(font);
        Font loadedFont = font.deriveFont(Font.PLAIN,45);
        return loadedFont;
    }

    public LanternaGUI(TerminalScreen screen) {
        this.screen = screen;
        this.width = 10;
        this.height = 10;
    } //used for testing purposes

    @Override
    public void drawRectangle(TextGraphics textGraphics, String color, int width, int height, Position pos) {
        textGraphics.setBackgroundColor(TextColor.Factory.fromString(color));
        textGraphics.fillRectangle(new TerminalPosition(pos.getX(), pos.getY()), new TerminalSize(width, height), ' ');
    }

    @Override
    public void fillBackground(TextGraphics textGraphics, String color) {
        textGraphics.setBackgroundColor(TextColor.Factory.fromString(color));
        textGraphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(this.width, this.height), ' ');
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public KeyStroke pollInput() throws IOException {
        return screen.pollInput();
    }

    @Override
    public void clear() {
        screen.clear();
    }

    @Override
    public void refresh() throws IOException {
        screen.refresh();
    }

    @Override
    public void close() throws IOException {
        screen.close();
    }

    @Override
    public void drawBullet(Bullet bullet)
    {
        TextGraphics tg = screen.newTextGraphics();
        tg.setBackgroundColor(TextColor.Factory.fromString("#567D46"));
        tg.setForegroundColor(TextColor.Factory.fromString(bullet.getColor()));
        tg.enableModifiers(SGR.BOLD);
        tg.putString(new TerminalPosition(bullet.getPosition().getX(), bullet.getPosition().getY()), String.valueOf(bullet.getCharacter()));
        tg.disableModifiers(SGR.BOLD);
    }

    @Override
    public void drawImmobileEntity(ImmobileEntity immobileEntity) {
        TextGraphics tg = screen.newTextGraphics();
        tg.setBackgroundColor(TextColor.Factory.fromString("#567D46"));
        tg.setForegroundColor(TextColor.Factory.fromString(immobileEntity.getColor()));
        tg.putString(new TerminalPosition(immobileEntity.getPosition().getX(), immobileEntity.getPosition().getY()), String.valueOf(immobileEntity.getCharacter()));
    }

    @Override
    public void drawMovingEntity(MovingEntity movingEntity) {
        TextGraphics tg = screen.newTextGraphics();
        tg.setBackgroundColor(TextColor.Factory.fromString("#567D46"));
        tg.setForegroundColor(TextColor.Factory.fromString(movingEntity.getColor()));
        if (movingEntity.getDamaged() > 0) tg.setForegroundColor(TextColor.Factory.fromString("#ff0000")); // Red for damage color
        tg.putString(new TerminalPosition(movingEntity.getPosition().getX(), movingEntity.getPosition().getY()), String.valueOf(movingEntity.getCharacter()));
        movingEntity.decreaseDamaged();
    }

    @Override
    public void drawMenu(Game game, int selected, List<Command> commandsList) {
        TextGraphics tg = screen.newTextGraphics();
        tg.putString(width/5,2,"pppppp  pppppp  pppppp  pppppp ");
        tg.putString(width/5,3,"pp      p    p    pp    pp    ");
        tg.putString(width/5,4,"ppppp   pppppp    pp    ppppp ");
        tg.putString(width/5,5,"pp      pp  pp    pp    pp    ");
        tg.putString(width/5,6,"pp      pp  pp    pp    pppppp");
        for(int i = 0; i < commandsList.size(); i++)
            tg.putString(width/3,
                    height/3+i,commandsList.get(i).getText());
        tg.putString(width/3 - 2,
                height/3+selected,"->");
        tg.putString(width-9,height-4,"UP : W");
        tg.putString(width-9,height-3,"DW : S");
        tg.putString(width-9,height-2,"GO : E");
    }

    @Override
    public void drawInventory(Game game, int selected) {
        TextGraphics tg = screen.newTextGraphics();
        tg.putString(1,1,"EQUIPPED WEAPONS");
        tg.putString(1,2,game.getLevel().getPlayer().getPrimaryWeapon().getName());
        tg.putString(1,3,game.getLevel().getPlayer().getSpecialWeapon().getName());
        tg.putString(1,4,game.getLevel().getPlayer().getHeavyWeapon().getName());

        for(int i = 0; i < game.getLevel().getPlayer().getInventory().size(); i++)
            tg.putString(width/3,
                    height/4+i,game.getLevel().getPlayer().getInventory().get(i).getName());
        tg.putString(width/3 - 2,
                height/4+selected,"->");
        tg.putString(width-9,height-4,"UP : W");
        tg.putString(width-9,height-3,"DW : S");
        tg.putString(width-9,height-2,"GO : E");
        tg.putString(width-9,height-1,"BK : Q");
    }

    @Override
    public void drawInstructions() {
        TextGraphics tg = screen.newTextGraphics();
        tg.putString(1, height/4,"-CONTROLS-");
        tg.putString(1, height/4 + 1,"USE W, A, S, AND D TO MOVE");
        tg.putString(1, height/4 + 2,"USE E TO SHOOT");
        tg.putString(1, height/4 + 3,"ACCESS THE MENU WITH Q AND THE INVENTORY WITH I");
        tg.putString(1, height/4 + 5,"-GAMEPLAY-");
        tg.putString(1, height/4 + 6,"KILL ALL ENEMIES TO PROGRESS TO NEXT LEVEL");
        tg.putString(1, height/4 + 7,"STAY AWAY FROM COMBAT TO GET HEALING");
        tg.putString(1, height/4 + 8,"p HAVE FUN! :) p");
        tg.putString(width-9,height-1,"BK : Q");
    }

    @Override
    public void drawGame(Level level) {
        TextGraphics tg = screen.newTextGraphics();
        tg.setBackgroundColor(TextColor.Factory.fromString("#567D46"));
        tg.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(level.getNumColumns(), level.getNumRows()), ' ');
        level.draw(this);
        tg.setBackgroundColor(TextColor.Factory.fromString("#000000"));
        tg.fillRectangle(new TerminalPosition(0, height-3),new TerminalSize(width,3),' ');
        tg.putString(new TerminalPosition(0, height-3),"HEALTH :" + level.getPlayer().getHealth());
        tg.putString(new TerminalPosition(0, height-2),"WEAPON :" + level.getPlayer().getUsingWeapon().getName());
        if(level.getPlayer().getUsingWeapon().getAmmo() >= 0){tg.putString(new TerminalPosition(0, height-1),"AMMO :" + level.getPlayer().getUsingWeapon().getAmmo());}
        else {tg.putString(new TerminalPosition(0, height-1),"AMMO :" + "e");}
    }

    @Override
    public void drawGameOver()
    {
        TextGraphics tg = screen.newTextGraphics();
        tg.putString(width/3, height/3, "YOU LOSE");
        tg.putString(width/3 - 2, height/2, "->EXIT");
    }

}