package tictactoe;

import tictactoe.GameWindow;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingWindow extends JFrame {
    private static final int WINDOW_HEIGHT = 230;
    private static final int WINDOW_WIDTH = 350;

     static int MODE=0;

     static int sizeX=1;
     static int sizeY=1;
     static int WIN_len=0;

    JButton btnStart = new JButton("Start new game");

    SettingWindow(GameWindow gameWindow) {
        setLocationRelativeTo(gameWindow);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    setLayout(new GridLayout(10,1));
        add(new JLabel("Select play mode:"));
        ButtonGroup bg = new ButtonGroup();
        JRadioButton pvp = new JRadioButton("Person vs person");
        JRadioButton pve = new JRadioButton("Person vs comp");
        bg.add(pve);
        bg.add(pvp);
        add(pve);
        add(pvp);
        add(new JLabel("Select field size: "));
        JLabel labelCurrentField = new JLabel("Current field size: ");
        add(labelCurrentField);
        JSlider slider = new JSlider(3,10,3);
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                labelCurrentField.setText("Current field size: " + slider.getValue());
            }
        });
        add(slider);
        add(new JLabel("Select WIN size: "));
        JLabel labelCurrentWinSize = new JLabel("Current WIN size: ");
        add(labelCurrentWinSize);
        JSlider sliderWinSize = new JSlider(3,10,3);
        sliderWinSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                labelCurrentWinSize.setText("Current WIN size: " + sliderWinSize.getValue());
            }
        });
        add(sliderWinSize);
        add(btnStart);
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pve.isSelected()){
                    MODE = 1;
                }
                sizeX = slider.getValue();
                sizeY = slider.getValue();
                WIN_len = sliderWinSize.getValue();
                gameWindow.startNewGame(MODE,sizeX,sizeY,WIN_len);
                setVisible(false);
            }
        });
    }

}
