import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.Random;

public class PanelFondo extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static int DELAY = 110;
    final int x[] = new int[GAME_UNITS];
    final int Y[] = new int[GAME_UNITS];
    int bodyPart = 6;
    int appleEat;
    int appleX, appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    PanelFondo() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLUE);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
        newApple();
    }
    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();

    }
    public void paint(Graphics g) {
        super.paint(g);
        draw(g);
    }
    public void draw(Graphics g) {
        if(running){
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
            for (int i = 0; i<bodyPart; i++){
                if(i == 0){
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], Y[i], UNIT_SIZE, UNIT_SIZE);   
                }
                else{
                    g.setColor(new Color(45,188,0));
                    g.fillRect(x[i], Y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD,40));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score :" + appleEat , (SCREEN_WIDTH - metrics.stringWidth("Score :" + appleEat))/2, UNIT_SIZE);
        }
        else{
            gameOver(g);
        }
            
    }

    public void newApple() {
        appleX = random.nextInt((int )SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        appleY = random.nextInt((int )SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;
    }

    public void move() {
        for( int i = bodyPart; i>0 ;i--){
            x[i] = x[i-1];
            Y[i] = Y[i-1];
        }
        switch(direction){
            case 'U':
            Y[0] = Y[0]-UNIT_SIZE;
            break;
            case 'D':
            Y[0] = Y[0]+UNIT_SIZE;
            break;
            case 'L':
            x[0] = x[0]-UNIT_SIZE;
            break;
            case 'R':
            x[0] = x[0]+UNIT_SIZE;
            break;
        }
    }
    public void checkApple(){
        if ((x[0] == appleX) && (Y[0]==appleY)){
            bodyPart++;
            appleEat++;
            DELAY --;
            newApple();
        }
    }
    public void checkColisions() {
        //for colision head-to-body
        for (int i = bodyPart; i>0;i--){
            if((x[0]==x[i]) && (Y[0]==Y[i])){
                running =false;
            }
        }
        //for colision head-to-wall
        if (x[0] <0){
            running = false;
        }
        if (x[0] >SCREEN_WIDTH){
            running = false;
        }
        if (Y[0]<0){
            running = false;
        }
        if (Y[0]>SCREEN_HEIGHT){
            running = false;
        }
        if (!running){
            timer.stop();
        }
    }
    public void gameOver(Graphics g) {
       //game over text 
       g.setColor(Color.red);
       g.setFont(new Font("Ink Free", Font.BOLD,75));
       FontMetrics metrics = getFontMetrics(g.getFont());
       g.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (running){
            move();
            checkApple();
            checkColisions();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                if(direction!='R'){
                    direction = 'L';
                }    
                break;
                case KeyEvent.VK_RIGHT:
                if(direction!='L'){
                    direction = 'R';
                }
                break;
                case KeyEvent.VK_UP:
                if(direction!='D'){
                    direction = 'U';
                }
                break;
                case KeyEvent.VK_DOWN:
                if(direction!='U'){
                    direction = 'D';
                }
                break;
            }
        }
    }
}
