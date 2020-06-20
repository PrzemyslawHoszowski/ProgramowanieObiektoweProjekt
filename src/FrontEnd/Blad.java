package FrontEnd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Blad extends JFrame {
    public Blad(String text){
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(600,140);
        setLocation((d.width-400)/2,(d.height-260)/2);
        setTitle("Blad");
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel msg = new JLabel(text);
        msg.setBounds(10,10,580,30);
        msg.setHorizontalAlignment(SwingConstants.CENTER);
        add(msg);

        JButton okButton = new JButton("Ok");
        okButton.setBounds(260,50,80,40);
        add(okButton);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }
}
