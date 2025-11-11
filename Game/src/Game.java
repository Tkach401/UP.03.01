import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/* @author Tkach1337 */

public class Game extends JFrame {

    private static Game game_window;
    private static Image background;
    private static Image security_logo;
    private static Image intruder;
    private static Image alarm_screen;
    private static long last_frame_time;
    private static float intruder_left = 200;
    private static float intruder_top = -100;
    private static float intruder_velocity = 150;
    private static int security_level = 100;
    private static boolean game_over = false;

    public static void main(String[] args) throws IOException {
        background = ImageIO.read(Game.class.getResourceAsStream("control_room.png"));
        security_logo = ImageIO.read(Game.class.getResourceAsStream("security_logo.png"));
        intruder = ImageIO.read(Game.class.getResourceAsStream("intruder.png"));
        alarm_screen = ImageIO.read(Game.class.getResourceAsStream("alarm_screen.png"));
        
        game_window = new Game();
        game_window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game_window.setLocation(200, 50);
        game_window.setSize(1200, 800);
        game_window.setResizable(false);
        game_window.setTitle("Система Мониторинга Безопасности - Уровень: 100%");
        
        last_frame_time = System.nanoTime();
        
        GameField game_field = new GameField();
        
        game_field.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (game_over) {
                    security_level = 100;
                    game_over = false;
                    intruder_velocity = 150;
                    intruder_top = -100;
                    intruder_left = 200;
                    game_window.setTitle("Система Мониторинга Безопасности - Уровень: 100%");
                    return;
                }
                
                int x = e.getX();
                int y = e.getY();
                float intruder_right = intruder_left + intruder.getWidth(null);
                float intruder_bottom = intruder_top + intruder.getHeight(null);
                boolean is_intruder = x >= intruder_left && x <= intruder_right && y >= intruder_top && y <= intruder_bottom;
                
                if (is_intruder) {
                    intruder_top = -100;
                    intruder_left = (int) (Math.random() * (game_field.getWidth() - intruder.getWidth(null)));
                    intruder_velocity += 5;
                    security_level = Math.min(security_level + 5, 100);
                    game_window.setTitle("Система Мониторинга Безопасности - Уровень: " + security_level + "%");
                }
            }
        });

        game_window.add(game_field);
        game_window.setVisible(true);
    }
    
    private static void onRepaint(Graphics g) {
        long current_time = System.nanoTime();
        float delta_time = (current_time - last_frame_time) * 0.000000001f;
        last_frame_time = current_time;
        
        if (!game_over) {
            intruder_top = intruder_top + intruder_velocity * delta_time;
            
            g.drawImage(background, 0, 0, null);
            g.drawImage(security_logo, 10, 10, null);
            g.drawImage(intruder, (int) intruder_left, (int) intruder_top, null);
            
            if (intruder_top > game_window.getHeight()) {
                security_level -= 20;
                if (security_level < 0) security_level = 0;
                game_window.setTitle("Система Мониторинга Безопасности - Уровень: " + security_level + "%");
                
                if (security_level <= 0) {
                    game_over = true; // Тренировка провалена
                    game_window.setTitle("ТРЕНИРОВКА ПРОВАЛЕНА! Нажмите для повторной попытки");
                }
                
                intruder_top = -100;
                intruder_left = (int) (Math.random() * (game_window.getWidth() - intruder.getWidth(null)));
            }
        } else {
            g.drawImage(alarm_screen, 0, 0, null);
            g.setColor(java.awt.Color.WHITE);
            g.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 40));
            g.drawString("ТРЕНИРОВКА ПРОВАЛЕНА!", 250, 250);
            g.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 24));
            g.drawString("*Нажмите для повторной попытки*", 300, 300);
        }
    }
    
    private static class GameField extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }
    }
}