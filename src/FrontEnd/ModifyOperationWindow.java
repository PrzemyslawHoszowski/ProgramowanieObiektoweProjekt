package FrontEnd;

import BackEnd.Account;
import BackEnd.OperationDir.Operation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModifyOperationWindow extends JFrame {

    AccOperationHistory previousWin;
    Account account;
    JTextField dateField;
    JTextField priorField;
    JTextField tagField;
    JTextField valueField;
    JTextField descField;
    Operation operation;

    void mydispose(){
        previousWin.update();
        dispose();
    }
    ModifyOperationWindow (Account account, AccOperationHistory previousWin, int index, int selectedRow){
        this.previousWin =  previousWin;
        this.account = account;
        String[] data;
        if (index != -1){
            try {
                operation = account.getOperation(index);
            } catch(Exception ex){
                new CommunicationWindow(ex.getMessage());
                return;
            }
            String[] operationData = operation.toString().split(";");
            data = new String[]{
                    "",
                    operationData[1], //Prior
                    operationData[2], //Date
                    operationData[3], //Tag
                    operationData[4], //Value
                    operationData[6]  //Description
            };
        }
        else{
            index = account.getNewOperationID();
            data = new String[]{
                    "",
                    "-1",
                    new SimpleDateFormat("dd.MM.yyyy").format(new Date()),
                    "Zakupy spożywcze",
                    "100",
                    "Opis"
            };
        }

        setLocationRelativeTo(previousWin);
        setResizable(false);
        setLayout(null);
        if (operation == null)
            setTitle("Dodanie operacji");
        else setTitle("Edycja operacji");

        setSize(500,305);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                previousWin.update();
                super.windowClosing(e);
            }
        });

        JLabel priorLabel = new JLabel("Priorytet (-1, gdy dochód)");
        priorLabel.setBounds(10,10,200,30);
        priorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(priorLabel);
        priorField = new JTextField(data[1]);
        priorField.setBounds(220,10,270,30);
        add(priorField);

        JLabel dataLabel = new JLabel("Data (DD.MM.RRRR)");
        dataLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dataLabel.setBounds(10,50,200,30);
        add(dataLabel);
        dateField = new JTextField(data[2]);
        dateField.setBounds(220,50,270,30);
        add(dateField);

        JLabel tagLabel = new JLabel("Tag");
        tagLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tagLabel.setBounds(10,90,200,30);
        add(tagLabel);
        tagField =  new JTextField(data[3]);
        tagField.setBounds(220,90,270,30);
        add(tagField);

        JLabel valueLabel = new JLabel("Wartość transakcji");
        valueLabel.setHorizontalAlignment(0);
        valueLabel.setBounds(10,130,200,30);
        add(valueLabel);
        valueField = new JTextField(data[4]);
        valueField.setBounds(220,130,270,30);
        add(valueField);

        JLabel descLabel = new JLabel("Opis");
        descLabel.setBounds(10,165,480,20);
        descLabel.setHorizontalAlignment(0);
        add(descLabel);

        descField = new JTextField(data[5]);
        descField.setBounds(10,190,480,30);
        add(descField);
        setVisible(true);

        JButton returnButton = new JButton("Cofnij");
        returnButton. setBounds(10 ,230,235,30);
        add(returnButton);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mydispose();
            }
        });
        JButton createButton;
        if (operation == null)
            createButton = new JButton("Stwórz");
        else
            createButton = new JButton("Zatwierdź zmiany");
        createButton.setBounds(255,230,235,30);
        add(createButton);

        int finalIndex = index;
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int priority;
                int ID = account.getNewOperationID();
                Date day;
                double value;
                try{
                    priority = Integer.parseInt(priorField.getText());
                    if (priority>20 || priority < -1) throw new Exception();
                } catch (Exception ex){
                    new CommunicationWindow("Priorytet musi być liczbą całkowitą od -1 do 20");
                    return;
                }
                try{
                    day = new SimpleDateFormat("dd.MM.yyyy").parse(dateField.getText());
                } catch (ParseException parseException) {
                    new CommunicationWindow("Błedny format daty");
                    return;
                }
                if (tagField.getText().length() > 40){
                    new CommunicationWindow("Limit znaków dla tagu wynosi 40");
                    return;
                }
                else if (tagField.getText().indexOf(";")!= -1){
                    new CommunicationWindow("Tag nie może zawierać ;");
                    return;
                }
                try{
                    value = Double.parseDouble(valueField.getText());
                } catch (NumberFormatException numberFormatException) {
                    new CommunicationWindow("Zły format wartości transakcji");
                    return;
                }
                if (descField.getText().length() > 100){
                    new CommunicationWindow("Limit znaków dla opisu wynosi 100");
                    return;
                }
                else if (descField.getText().indexOf(";")!=-1){
                    new CommunicationWindow("Opis nie może zawierać ;");
                    return;
                }
                if (operation==null){
                    Operation op = account.createOperation(priority,day,tagField.getText(),value,descField.getText());
                    account.addNewOperation(op);
                    previousWin.addOperation(op.toString().split(";"));
                }
                else{ /// Changing existing operation
                    try {
                        account.changeAt(finalIndex,priority,day,tagField.getText(),value,descField.getText());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    previousWin.reloadOperation(selectedRow);
                }
                previousWin.reloadBalance();
                mydispose();
            }
        });
    }

}
