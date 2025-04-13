import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int frameWidth = 360;
    int frameHeight = 640;

    // image attributes
    Image backgroundImage;
    Image birdImage;
    Image lowerPipeImage;
    Image upperPipeImage;
    Image gameOverImage;
    Image restartImage;

    // player
    int playerStartPosX = frameWidth / 8;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;
    Player player;

    // pipe attributes
    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;
    ArrayList<Pipe> pipes;

    // game over
    int gameOverWidth = 180;
    int gameOverHeight = 45;
    int restartWidth = 120;
    int restartHeight = 40;

    // game logic
    Timer gameLoop;
    Timer pipesCooldown;
    int gravity = 1;
    int score = 0;
    JLabel scoreLabel;
    boolean gameOver = false;

    // constructor
    public FlappyBird() {
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setFocusable(true);
        addKeyListener(this);
        scoreLabel = new JLabel("0");
        scoreLabel.setForeground(Color.white);
        scoreLabel.setFont(new Font("", Font.BOLD, 42));
        this.setLayout(null);
        scoreLabel.setBounds(20, 20, 200, 48);
        this.add(scoreLabel);

        // load images
        backgroundImage = new ImageIcon(getClass().getResource("assets/background.png")).getImage();
        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();
        gameOverImage = new ImageIcon(getClass().getResource("assets/gameOver.png")).getImage();
        restartImage = new ImageIcon(getClass().getResource("assets/restart.png")).getImage();

        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);
        pipes = new ArrayList<Pipe>();

        pipesCooldown = new Timer(4500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("pipa");
                placePipes();
            }
        });
        pipesCooldown.start();

        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
    }

    public void draw(Graphics g){
        g.drawImage(backgroundImage, 0, 0, frameWidth, frameHeight, null);

        g.drawImage(player.getImage(), player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight(), null);

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight(), null);
        }

        if (gameOver){
            g.drawImage(gameOverImage, (frameWidth - gameOverWidth)/2, (frameHeight - gameOverHeight)/3, gameOverWidth, gameOverHeight, null);
            g.drawImage(restartImage, (frameWidth - restartWidth)/2, (frameHeight - restartHeight)/2, restartWidth, restartHeight, null);
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void move(){
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipe.getVelocityX());

            if (i % 2 == 0 && !pipe.getPassed() && player.getPosX() > pipe.getPosX()) {
                score++;
                scoreLabel.setText("" + score);
                pipe.setPassed(true);
            }
        }
    }

    public void boxCollision() {
        Rectangle playerBox = new Rectangle(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight());

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            Rectangle pipeBox = new Rectangle(pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeight());

            if (playerBox.intersects(pipeBox)) {
                gameOver = true;
            }
        }

        if (player.getPosY() + player.getHeight() > frameHeight) {
            gameOver = true;
        }
    }


    public void placePipes(){
        int randomPosY = (int) (pipeStartPosY - (pipeHeight/2) - Math.random() * (pipeHeight/2));
        int openingSpace = frameHeight/4;

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, (randomPosY + openingSpace + pipeHeight), pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (!gameOver){
            move();
            boxCollision();
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e){

    }

    @Override
    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            player.setVelocityY(-10);
        }
        else if (e.getKeyCode() == KeyEvent.VK_R){
            restart();
        }
    }

    @Override
    public void keyReleased(KeyEvent e){

    }

    public void restart() {
        // reset player
        player.setPosX(playerStartPosX);
        player.setPosY(playerStartPosY);
        player.setVelocityY(0);

        // reset pipa
        pipes.clear();

        // reset skor
        score = 0;
        scoreLabel.setText("" + score);

        // reset game over
        gameOver = false;
    }

}