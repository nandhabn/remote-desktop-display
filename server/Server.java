import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.net.Socket;
import java.net.UnknownHostException;

@SuppressWarnings("serial")
public class Server extends JFrame {
  JTextField ipbox;
  JButton ipbtn;

  private Server() {
    ipbox = new JTextField(10);
    ipbtn = new JButton("Connect");
    ipbox.setBounds(50, 50, 150, 20);
    ipbox.addActionListener(new act(ipbox));
    ImageIcon icon = new ImageIcon();
    

    JPanel panelc = new JPanel();
    JPanel panell = new JPanel();
    panell.add(ipbox);
    panell.add(ipbtn);
    panelc.setSize(500, 640);
    panelc.setBackground(Color.black);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JLabel label = new JLabel();
    ipbtn.addActionListener(new stream(icon, ipbox.getText().toString(),label));
    label.setIcon(icon);
    panelc.add(label);
    setLayout(new BorderLayout());
    add(panelc, BorderLayout.CENTER);
    add(panell, BorderLayout.WEST);
    setSize(300, 300);
    setBackground(Color.BLACK);
  }

  public static void main(String[] args) {
    new Server().setVisible(true);

  }

}

class act implements ActionListener {

  JTextField t;

  act(JTextField t) {
    this.t = t;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    t.setText("");
  }

}



class stream implements ActionListener {
  Thread th = null;
  String ip = null,oldip=null;
  ImageIcon icon;
  Socket sock = null;
  JLabel jpl=null;

  stream(ImageIcon icon, String ip,JLabel jpl) {
    this.icon=icon;
    this.ip = ip;
    this.jpl=jpl;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
  
    if(th==null)
    {
      th=new Thread(new Runnable(){
      
        @Override
        public void run() {
          while (true){
            try {
                sock = new Socket(ip, 8000);
            } catch (UnknownHostException e1) {
              e1.printStackTrace();
            } catch (IOException e1) {
              e1.printStackTrace();
            }
            if(sock!=null)
            {
              BufferedImage bimg=null;
              try {
                bimg = ImageIO.read(sock.getInputStream());
                System.out.println(bimg);
              } catch (IOException e1) {
                e1.printStackTrace();
              }
      
            icon=new ImageIcon(bimg.getSubimage(0,0,1000,650));
              jpl.setIcon(icon);
            }
      
        }}
      });
      
      th.start();
    }
  }

  

  

}