package Meths;

import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JuegoRunner extends JPanel implements KeyListener, ActionListener {
   private boolean saltando = false;
private final int alturaSalto = 150;

    private int playerY = 360;
     private int playerX = 375;
    private int playerWidth = 28;
    private int playerHeight = 28;
    private int score = 0;
    private String Nombre;
    private boolean gameOver = false;
    private List<Obstaculo> obstaculos = new ArrayList<>();
    private int obstaculoWidth = 30;
    private int obstaculoHeight = 30;
    private Random random = new Random();
    private double velocidad = 3.5;
    private Timer timer;
     ReproductorAudioGame Sound = new ReproductorAudioGame(); 
    

    public JuegoRunner() {
           
        Sound.MP3();
       
        JFrame frame = new JFrame("CUBIX JUMP");
        frame.setSize(736, 460);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        
        frame.addKeyListener(this);
        frame.setResizable(false);
        timer = new Timer(10, this);
        timer.start();
        
    
        
        frame.setVisible(true);
        frame.setLocationRelativeTo(this);
       


       frame.add(this);
      
        
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    
    // Dibujar imagen de fondo
    ImageIcon imageIcon = new ImageIcon(getClass().getResource("/fondo/Escenario.gif"));
    Image image = imageIcon.getImage();
    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        
        // Dibujar jugador
        g.setColor(Color.GRAY);
        g.fillRect(100, playerY, playerWidth, playerHeight);

        // Dibujar obstáculos
        for (Obstaculo obs : obstaculos) {
            g.setColor(Color.red);
            g.fillRect(obs.x, obs.y, obstaculoWidth, obstaculoHeight);
        }

        // Dibujar score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 30);
         // Dibujar Guia
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString("Space Bar (Jump) " , 600, 30);

        // Mostrar "Game Over" si el juego ha terminado
        if (gameOver) {
            JOptionPane.showConfirmDialog(this, "");
        }
        
         
    }

    public void actionPerformed(ActionEvent e) {
           if (!gameOver) {
            moverJugador();
            generarObstaculos();
            moverObstaculos();
            detectarColisiones();
            actualizarScore();
            
            
        }
        repaint();
         
    }

   public void moverJugador() {
    if (playerY >= 380) {
        playerY = 1;
    }

    // Simular el salto
    if (saltando) {
        if (playerY > 360 - alturaSalto) {
            playerY -= 3; // Subir durante el salto
        } else {
            saltando = false;
        }
    } else {
        if (playerY < 360) {
            playerY += 3; // Bajar después del salto
        }
    }
}

    public void generarObstaculos() {
        if (random.nextInt(100) < 1) {
            obstaculos.add(new Obstaculo(800, 360));
        }
    }

    public void moverObstaculos() {
        for (Obstaculo obs : obstaculos) {
            obs.x -= velocidad;
        }
        obstaculos.removeIf(obs -> obs.x < 0);
    }
    
    public void detectarColisiones() {
    

        Rectangle playerBounds = new Rectangle(100, playerY, playerWidth, playerHeight);
        for (Obstaculo obs : obstaculos) {
            Rectangle obstaculoBounds = new Rectangle(obs.x, obs.y, obstaculoWidth, obstaculoHeight);
            if (playerBounds.intersects(obstaculoBounds)) {
            
                Sound.CloseMP3();
                String[] opciones = {"Reiniciar", "Cerrar"};
            
                guardarPuntajeBD(Nombre,score);
                int opcion = JOptionPane.showOptionDialog(null,
                "Tu Puntaje fue: "+score+"\n ¿Qué desea hacer?",
                "Confirmación",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]);

        if (opcion == 0) {
              
             
        reiniciarJuego();
         
          
         
     
          
  
         
            
        } else if (opcion == 1) {
           System.exit(0);
         
        
            
        }
                
               
            }
        }
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
public void reiniciarJuego() {
    playerY = 360;
    playerX = 375;
    score = 0;
    gameOver = false;
    obstaculos.clear();
    timer.restart();
    repaint();
    Sound.MP3();
}
        public void actualizarScore() {
        score++;
        
         
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
   if (key == KeyEvent.VK_SPACE && playerY == 360) {
        saltando = true;
    }
}
@Override
public void keyReleased(KeyEvent e) {
  
}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JuegoRunner());
    }

    private class Obstaculo {
        int x, y;

        Obstaculo(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public void guardarPuntajeBD(String Nombre,int puntaje) {
    String url = "jdbc:mysql://localhost:3306/CubixJump";
    String usuario = "root";
    String contraseña = "root";

     try {
        Connection con = DriverManager.getConnection(url, usuario, contraseña);
        String query = "INSERT INTO Puntaje (Nombre,Score) VALUES (?,?)";
        PreparedStatement stmt = con.prepareStatement(query);
        stmt.setString(1, Nombre);
        stmt.setInt(2, puntaje);
        stmt.executeUpdate();
        
        con.close();
        
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
    
}