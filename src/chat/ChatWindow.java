package chat;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ChatWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 600;
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_POSX = 700;
    private static final int WINDOW_POSY = 200;

    JButton btnConnect = new JButton("Connect");
    JButton btnExit = new JButton("Exit");

    ChatWindow() throws IOException {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("CHat-bibachat");
        setResizable(false);


        btnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (BufferedReader reader = new BufferedReader(new FileReader("log.txt"))){
                    String line = reader.readLine();
                    while (line != null)
                        System.out.println(line);

                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JPanel panSet = new JPanel(new GridLayout(5, 1));
        JTextField loginField = new JTextField("login");
        JTextField passField = new JTextField("pass");
        JTextField ipField = new JTextField("ip");
        JTextField portField = new JTextField("port");
        panSet.add(loginField);
        panSet.add(passField);
        panSet.add(ipField);
        panSet.add(portField);
        panSet.add(btnConnect);

        add(panSet, BorderLayout.NORTH);

        JPanel panMid = new JPanel(new GridLayout(1, 2));
        JTextArea fieldChat = new JTextArea();
        fieldChat.setEditable(false);
        JList<String> listUsers = new JList<>();
        String users[] = {"Dima", "Katya", "Sasha", "Vasya", "Ignat"};
        listUsers.setListData(users);
        panMid.add(fieldChat);
        panMid.add(listUsers);

        add(panMid);





        JPanel panChat = new JPanel(new GridLayout(2, 1));
        JTextField fieldMessage = new JTextField("Message");
        JButton btnSend = new JButton("Send");
        FileWriter fw = new FileWriter("log.txt");
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fieldChat.append(fieldMessage.getText() + String.format("%n"));
                try {
                    fw.write(fieldMessage.getText()+"\n");
                    fw.flush();
                } catch (IOException exception){
                    System.out.println(exception.getMessage());
                }
            }
        });
        panChat.add(fieldMessage);
        panChat.add(btnSend);

        add(panChat, BorderLayout.SOUTH);

        setVisible(true);
    }
}