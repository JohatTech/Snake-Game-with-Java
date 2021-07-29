import javax.swing.JFrame;

public class GameFrame extends JFrame {
    GameFrame(){
        new PanelFondo();
        this.add(new PanelFondo());
        this.setTitle("Snake game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
