package FrontEnd;

import BackEnd.HomeBalance;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AddAccountWin extends JFrame {

    AddAccountWin(HomeBalance homeBalance, AccountsWin previousWin, AddAccountWin[] addAccountWin, MainWindow mainWindow){
        setSize(400,300);
        setLocationRelativeTo(previousWin);
        setTitle("Dodaj konto");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setLayout(null);

        JLabel name = new JLabel("Nazwa:");
        name.setBounds(10,10,90,30);
        name.setHorizontalAlignment(SwingConstants.CENTER);
        add(name);
        JTextField NameField = new JTextField(30);
        NameField.setText("Nazwa");
        NameField.setFont(Font.getFont("Courier New"));
        NameField.setBounds(110,10,280,30);
        add(NameField);

        JLabel currency = new JLabel("Waluta:");
        currency.setBounds(10,50,190,30);
        currency.setHorizontalAlignment(SwingConstants.CENTER);
        add(currency);
        JComboBox currList = new JComboBox(homeBalance.get_curr());
        currList.setBounds(210,50,170,30);
        add(currList);

        JLabel balance = new JLabel("Stan konta:");
        balance.setBounds(10,90,190,30);
        balance.setHorizontalAlignment(SwingConstants.CENTER);
        add(balance);
        JTextField balanceInput = new JTextField(30);
        balanceInput.setText("0");
        balanceInput.setFont(Font.getFont("Courier New"));
        balanceInput.setBounds(210,90,170,30);
        add(balanceInput);

        JLabel minimum_balance = new JLabel("Ostrzeż poniżej:");
        minimum_balance.setBounds(10,130,190,30);
        minimum_balance.setHorizontalAlignment(SwingConstants.CENTER);
        add(minimum_balance);
        JTextField minimumInput = new JTextField(30);
        minimumInput.setText("0");
        minimumInput.setFont(Font.getFont("Courier New"));
        minimumInput.setBounds(210,130,170,30);
        add(minimumInput);

        JLabel max_out = new JLabel("Limit miesięczny:");
        max_out.setBounds(10,170,190,30);
        max_out.setHorizontalAlignment(SwingConstants.CENTER);
        add(max_out);
        JTextField max_outInput = new JTextField(30);
        max_outInput.setText("4000");
        max_outInput.setFont(Font.getFont("Courier New"));
        max_outInput.setBounds(210,170,170,30);
        add(max_outInput);

        JButton AddButton = new JButton("Dodaj");
        AddButton.setBounds(150,220, 100, 30);
        add(AddButton);
        AddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (NameField.getText().length()==0) new CommunicationWindow("Prosze wpisać nazwę konta");
                else if (NameField.getText().indexOf(";") != -1) new CommunicationWindow("Nazwa nie może zawierać ;");
                else try{
                    homeBalance.addAccount(
                            NameField.getText(),
                            (String) currList.getSelectedItem(),
                            Double.parseDouble(balanceInput.getText()),
                            Double.parseDouble(minimumInput.getText()),
                            Double.parseDouble(max_outInput.getText()));
                    addAccountWin[0] = null;
                    previousWin.update();
                    dispose();
                }
                catch (NumberFormatException exception){
                    new CommunicationWindow("Proszę zamienić dane na liczby");
                }
                catch (Exception exception) {
                    new CommunicationWindow(exception.getMessage());
                }
            }
        });


    }
}
