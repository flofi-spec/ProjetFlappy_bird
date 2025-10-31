/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author HP-Pro
 */
import javax.swing.*;//Permet de créer des interfaces graphiques modernes et contient les objets graphiques,like les boutons,panneaux,fenetres,cases a cocher
import java.awt.*;//contient les elements graphiques like les couleurs,polices,formes geometriques,coordonnées
import java.awt.event.*;//permet de controler les evenements déclenchés par l’utilisateur ou le système dont les keyEvents ma chewrie
import java.util.ArrayList;//Une structure de données qui permet de stocker plusieurs objets de façon dynamique contrairement aux tableaux sa taille augmente en fonction des elements ajoutés 
import java.util.Random;//dois je encore expliquer falonne du futur


public class Game extends JPanel implements ActionListener, KeyListener {

    int birdY = 250;
    int birdX = 100;
    int velocity = 0;
    int gravity = 1;
    boolean started = false;
    


    Timer timer;
    ArrayList<Rectangle> pipes;

    int score = 0;
    boolean gameOver = false;

    public Game() {

        JFrame frame = new JFrame("Flappy Bird -");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.addKeyListener(this);
        frame.setVisible(true);


pipes = new ArrayList<>();

addPipe();
addPipe();

timer = new Timer(20, this);
timer.start();
        addComponentListener(new java.awt.event.ComponentAdapter() {
    @Override
    public void componentResized(java.awt.event.ComponentEvent e) {
        if (pipes.isEmpty() && getHeight() > 0) {
            addPipe();
            addPipe();
        }
        

    }

    });
  }
    

    public void addPipe(){
        
    
 int minHeight = 50;   
    int gap =200;
    int heights = getHeight();
if (heights < 300) heights = 500;   // jamais plus petit que 500
if (heights > 700) heights = 500;   // jamais plus grand que 700

    int maxHeight = heights -gap;
    
    if ( maxHeight < 1) {
        maxHeight = 300;
    }
   
   Random random = new Random();

int height = random.nextInt(minHeight,maxHeight);

pipes.add(new Rectangle(500, 0, 60, height)); 
pipes.add(new Rectangle(500, height + gap, 60,600/* autre pb:ici je garde 600 pour l'instant mm si je sais que c'est faux car la formule getHeight()-height-gap ne marche pas*/));



           }



            

/*maxHeight = Math.max(1, getHeight() - gap - minHeight);
int height = minHeight + random.nextInt(maxHeight);*/

@Override



  public void paintComponent(Graphics g) {
        super.paintComponent(g);

        
        g.setColor(Color.cyan);
        g.fillRect(0, 0, 2000, 2000);

        
        g.setColor(Color.red);
        g.fillOval(birdX, birdY, 30, 30);

        
        g.setColor(Color.green);
        for (Rectangle r : pipes) {
            g.fillRect(r.x, r.y, r.width, r.height);
        }
      

        
        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Score: " + score, 20, 30);

        
        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("GAME OVER", 120, 300);
        }
    }



public void actionPerformed(ActionEvent e) {
    if (!started) return;

    velocity += gravity;
    birdY += velocity;
    if (birdY < 0) {
    birdY = 0;
    velocity = 0; 
}


    for (int i = 0; i < pipes.size(); i += 2) {
        Rectangle topPipe = pipes.get(i);
        Rectangle bottomPipe = pipes.get(i + 1);

        topPipe.x -= 5;
        bottomPipe.x -= 5;

        if (topPipe.x + topPipe.width < 0) {
            pipes.remove(i);
            pipes.remove(i); 
            score++;
            addPipe();
            continue;
        }

        Rectangle bird = new Rectangle(birdX, birdY, 30, 30);
        if (topPipe.intersects(bird) || bottomPipe.intersects(bird)) {
            gameOver = true;
            timer.stop();
        }
    }

    if (birdY > getHeight()) {
        gameOver = true;
        timer.stop();
    }

    repaint();
}



@Override
public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        if (!started) {
            started = true;
        } else if (!gameOver) {
            velocity = -10;
        } else {
            reset(); 
        }
    }

    if (e.getKeyCode() == KeyEvent.VK_R) {
        reset(); 
    }
}

public void reset() {
    
    birdX = 100;
    birdY = 250 /* getHeight()/2 represente l'ancienne valeur mais avec les 250 du debut ça marche  */;
    velocity = 0;

    
    score = 0;
    gameOver = false;
    started = false;

    
    pipes.clear();

    
    revalidate();
    repaint();

    
    SwingUtilities.invokeLater(() -> {
        if (getHeight() > 0) {
            addPipe();
            addPipe();  // 2 premières paires pour recommencer le jeu
        }
    });

    
    timer.start();
}


@Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        new Game();
    }

    
       
    }

    
    
   
           



    
    






  






        /*public void reset() {
    birdX = 100;
    birdY = getHeight() / 2;
    velocity = 0;
    score = 0;
    gameOver = false;
    started = false;

    pipes.clear();
    revalidate();
// 4 paires pour reprendre comme au début

    timer.start();
    repaint();
    System.out.println("RESET ! Taille écran = " + getHeight());//ajout

}*/
 

    


