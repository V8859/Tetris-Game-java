import TetrisConfiguration.SoundPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;

public class ControlScheme {
    private boolean music;
    private boolean sound;
    private boolean pause;
    public ControlScheme(TetrisApp p1, TetrisApp p2, TetrisMultiPlayer App, Boolean ExtendMode){
        this.sound = p1.sound();
        this.music = p1.music();
        this.pause = p1.pause();
        p1.gameLoop().setSound(p1.sound());
        if (ExtendMode){
            p2.gameLoop().setSound(p1.sound());
        }


        String SoundSetting  = checkMorS(p1.sound());
        String MusicSetting  = checkMorS(p1.music());
        App.getOverlayPane().setSound_status(SoundSetting);
        App.getOverlayPane().setMusic_status(MusicSetting);

        App.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("LEFT"), "moveLeft");
        App.getActionMap().put("moveLeft", new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                p1.gameLoop().handleInput("left");
                if (sound){
                    p1.effectPlayer().playSound(-20.0f);
                }
            }
        });

        App.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("RIGHT"), "moveRight");
        App.getActionMap().put("moveRight", new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                p1.gameLoop().handleInput("right");
                if (sound){
                    p1.effectPlayer().playSound(-20.0f);
                }
            }
        });

        App.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("DOWN"), "moveDown");
        App.getActionMap().put("moveDown", new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                p1.gameLoop().handleInput("down");
                if (sound){
                    p1.effectPlayer().playSound(-20.0f);
                }
            }
        });

        App.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("UP"), "rotate");
        App.getActionMap().put("rotate", new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                p1.gameLoop().handleInput("rotate");
                if (sound){
                    p1.effectPlayer().playSound(-20.0f);
                }
            }
        });

        App.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("P"), "pause");
        App.getActionMap().put("pause", new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                p1.gameLoop().handleInput("pause");
                if (ExtendMode){
                    p2.gameLoop().handleInput("pause");

                }
                pause = !pause;
                p1.pauseLabel().setVisible(pause);
                p1.layeredPane().revalidate();
                p1.layeredPane().repaint();
                if (pause){
                    p1.gameLoop().setPause();
                }else {
                    p1.gameLoop().unPause();
                }

                if(ExtendMode){
                    p2.pauseLabel().setVisible(pause);
                    p2.layeredPane().revalidate();
                    p2.layeredPane().repaint();
                    if (pause){
                        p2.gameLoop().setPause();
                    }else {
                        p2.gameLoop().unPause();
                    }
                }
            }
        });
        App.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("S"),"sound-off");
        App.getActionMap().put("sound-off", new javax.swing.AbstractAction(){
            public void actionPerformed(java.awt.event.ActionEvent e){
                sound = !sound;
                String soond;
                p1.gameLoop().setSound(sound);
                if (ExtendMode){
                    p2.gameLoop().setSound(sound);
                }
                if (sound){
                    soond = "On";
                }else{
                    soond = "Off";
                }
                App.getOverlayPane().setSound_status(soond);
            }
        });


        App.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("M"),"music-off");
        App.getActionMap().put("music-off", new javax.swing.AbstractAction(){
            public void actionPerformed(java.awt.event.ActionEvent e){
                music = !music;
                String muzic;
                if (music){
                    p1.musicPlayer().loopSoundInThread(-30.0f);
                    if (ExtendMode){
                        p2.musicPlayer().loopSoundInThread(-30.0f);
                    }
                    muzic = "On";

                }else{
                    p1.musicPlayer().stopSound();
                    if (ExtendMode){
                        p2.musicPlayer().stopSound();
                    }
                    muzic = "Off";
                }
                App.getOverlayPane().setMusic_status(muzic);
            }
        });
//        App.addComponentListener(new ComponentAdapter() {
//            @Override
//            public void componentResized(ComponentEvent e) {
//                Dimension size = App.getSize();
//                layeredPane.setPreferredSize(size);
//                gamePanel.setBounds(0,0, size.width, size.height);
//                pauseLabel.setBounds(0,0, size.width, size.height);
//                layeredPane.revalidate();
//                layeredPane.repaint();
//                super.componentResized(e);
//            }
//        });
        if (ExtendMode) {
            App.getInputMap().put(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, 0), "mLeft");
            App.getActionMap().put("mLeft", new javax.swing.AbstractAction() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    p2.gameLoop().handleInput("left");
                    if (sound) {
                        p2.effectPlayer().playSound(-20.0f);
                    }
                }
            });

            App.getInputMap().put(javax.swing.KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, 0), "mRight");
            App.getActionMap().put("mRight", new javax.swing.AbstractAction() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    p2.gameLoop().handleInput("right");
                    if (sound) {
                        p2.effectPlayer().playSound(-20.0f);
                    }
                }
            });

            App.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("SPACE"), "mDown");
            App.getActionMap().put("mDown", new javax.swing.AbstractAction() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    p2.gameLoop().handleInput("down");
                    if (sound) {
                        p2.effectPlayer().playSound(-20.0f);
                    }
                }
            });

            App.getInputMap().put(javax.swing.KeyStroke.getKeyStroke("L"), "Srotate");
            App.getActionMap().put("Srotate", new javax.swing.AbstractAction() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    p2.gameLoop().handleInput("rotate");
                    if (sound) {
                        p2.effectPlayer().playSound(-20.0f);
                    }
                }
            });
        }
    }

    private String checkMorS(boolean s){
        if (s){
            return "On";
        }else{
            return "Off";
        }

    }
}

