package view;

import controller.Client;
import java.awt.BorderLayout;
import java.util.Random;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.List;
import javax.swing.*;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

import model.User;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

public class GameClientFrm extends javax.swing.JFrame {

    private final User competitor;

    private final int size = 15;
//    private final Timer timer;
    private Integer second;
    private Integer minute;
    private int numberOfMatch;

    private int userWin;
    private int competitorWin;
    private final String competitorIP;
    private JDialog currentDialog = null;

    private boolean isSubmitAllowed = false;
    
        // Phương thức hiển thị thông báo và đóng hộp thoại cũ nếu có
    public void showMessage(String message, String title, int messageType) {
        if (currentDialog != null) {
            currentDialog.dispose(); // Đóng hộp thoại cũ
        }

        // Tạo hộp thoại mới từ JOptionPane
        JOptionPane optionPane = new JOptionPane(message, messageType);
        currentDialog = optionPane.createDialog(this, title); // Lưu hộp thoại mới
        currentDialog.setVisible(true);
    }

     public void showWinMessage() {
        JOptionPane.showMessageDialog(this, "Bạn thắng , +2 điểm", "Thắng", JOptionPane.INFORMATION_MESSAGE);
         // Hiển thị hộp thoại xác nhận
    int choice = JOptionPane.showConfirmDialog(this, 
            "Bạn có muốn chơi tiếp không?", 
            "Tiếp tục?", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);

    // Xử lý dựa trên lựa chọn của người dùng
    if (choice == JOptionPane.NO_OPTION) {
        // Đóng giao diện
        Client.closeView(Client.View.GAME_CLIENT);
        Client.openView(Client.View.HOMEPAGE);
    }
    }
          public void showPermission() {
        JOptionPane.showMessageDialog(this, "Ban co quyen tra loi", "co quyen tra loi", JOptionPane.INFORMATION_MESSAGE);
         
    }
     
          public void showNotPermission() {
        JOptionPane.showMessageDialog(this, "Ban khong co quyen tra loi", "Khong co quyen tra loi", JOptionPane.INFORMATION_MESSAGE);
         
    }
         public void showLoserMessage() {
        JOptionPane.showMessageDialog(this, "Hix ! Thua rồi , + 0 điểm !", "Thua", JOptionPane.INFORMATION_MESSAGE);
         // Hiển thị hộp thoại xác nhận
    int choice = JOptionPane.showConfirmDialog(this, 
            "Bạn có muốn chơi tiếp không?", 
            "Tiếp tục?", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);

    // Xử lý dựa trên lựa chọn của người dùng
    if (choice == JOptionPane.NO_OPTION) {
        // Đóng giao diện
        Client.closeView(Client.View.GAME_CLIENT);
        Client.openView(Client.View.HOMEPAGE);
    }
    }
     
             public void showTieMessage() {
        JOptionPane.showMessageDialog(this, "Hòa, + 1 điểm !!!", "Hòa", JOptionPane.INFORMATION_MESSAGE);
         // Hiển thị hộp thoại xác nhận
    int choice = JOptionPane.showConfirmDialog(this, 
            "Bạn có muốn chơi tiếp không?", 
            "Tiếp tục?", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);

    // Xử lý dựa trên lựa chọn của người dùng
    if (choice == JOptionPane.NO_OPTION) {
        // Đóng giao diện
         Client.closeView(Client.View.GAME_CLIENT);
         Client.openView(Client.View.HOMEPAGE);
    }
    }
     
             


    public GameClientFrm(User competitor, int room_ID, int isStart, String competitorIP) {
        initComponents();
        numberOfMatch = isStart;
        this.competitor = competitor;
        this.competitorIP = competitorIP;

        userWin = 0;
        competitorWin = 0;

        this.setTitle("Duoi Hinh Bat Chu Nhom 1");
        jLabel1.setIcon(new ImageIcon("assets/game/anhDHBC.png"));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("assets/image/icon.png").getImage());
        this.setResizable(false);
        this.getContentPane().setLayout(null);

        // Tạo ImageIcon từ đường dẫn ảnh
        ImageIcon imageIcon = new ImageIcon("assets/dhbc/new_init/anh1.gif");

// Tạo JLabel chứa hình ảnh
        JLabel imageLabel = new JLabel(imageIcon);

// Xóa tất cả các thành phần cũ của imagePanel (nếu có)
        imagePanel.removeAll();

// Đặt layout cho imagePanel
        imagePanel.setLayout(new BorderLayout());

// Thêm JLabel vào imagePanel
        imagePanel.add(imageLabel, BorderLayout.CENTER);
        correctAnswer = "dua_hau";
// Cập nhật và vẽ lại imagePanel
        imagePanel.revalidate();
        imagePanel.repaint();
        

        //Setup UI
        playerLabel.setFont(new Font("Arial", Font.BOLD, 15));
        competitorLabel.setFont(new Font("Arial", Font.BOLD, 15));
        roomNameLabel.setFont(new Font("Arial", Font.BOLD, 15));
        roomNameLabel.setAlignmentX(JLabel.CENTER);
        playerNicknameValue.setText(Client.user.getNickname());
        playerNumberOfGameValue.setText(Integer.toString(Client.user.getNumberOfGame()));
        playerNumberOfWinValue.setText(Integer.toString(Client.user.getNumberOfWin()));
        playerButtonImage.setIcon(new ImageIcon("assets/game/" + Client.user.getAvatar() + ".jpg"));
        roomNameLabel.setText("Phòng: " + room_ID);
        vsIcon.setIcon(new ImageIcon("assets/game/swords-1.png"));
        competitorNicknameValue.setText(competitor.getNickname());
        competotorNumberOfGameValue.setText(Integer.toString(competitor.getNumberOfGame()));
        competitorNumberOfWinValue.setText(Integer.toString(competitor.getNumberOfWin()));
        competotorButtonImage.setIcon(new ImageIcon("assets/game/" + competitor.getAvatar() + ".jpg"));
        competotorButtonImage.setToolTipText("Xem thông tin đối thủ");

       
       

    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jFrame3 = new javax.swing.JFrame();
        jFrame4 = new javax.swing.JFrame();
        playerNumberOfWinLabel = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        playerNicknameLabel = new javax.swing.JLabel();
        playerNumberOfGameLabel = new javax.swing.JLabel();
        competitorNumberOfWinLabel = new javax.swing.JLabel();
        competitorNicknameLabel = new javax.swing.JLabel();
        competotorNumberOfGameLabel = new javax.swing.JLabel();
        playerNicknameValue = new javax.swing.JLabel();
        playerNumberOfGameValue = new javax.swing.JLabel();
        playerNumberOfWinValue = new javax.swing.JLabel();
        competitorNicknameValue = new javax.swing.JLabel();
        competotorNumberOfGameValue = new javax.swing.JLabel();
        competitorNumberOfWinValue = new javax.swing.JLabel();
        gamePanel = new javax.swing.JPanel();
        imagePanel = new javax.swing.JPanel();
        answerField = new javax.swing.JTextField();
        submitBut = new javax.swing.JButton();
        jButtonClose = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButtonWantToAnswer = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        playerLabel = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        competitorLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        roomNameLabel = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        playerButtonImage = new javax.swing.JLabel();
        vsIcon = new javax.swing.JLabel();
        competotorButtonImage = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame3Layout = new javax.swing.GroupLayout(jFrame3.getContentPane());
        jFrame3.getContentPane().setLayout(jFrame3Layout);
        jFrame3Layout.setHorizontalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame3Layout.setVerticalGroup(
            jFrame3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jFrame4Layout = new javax.swing.GroupLayout(jFrame4.getContentPane());
        jFrame4.getContentPane().setLayout(jFrame4Layout);
        jFrame4Layout.setHorizontalGroup(
            jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame4Layout.setVerticalGroup(
            jFrame4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setAutoRequestFocus(false);
        setBackground(new java.awt.Color(204, 255, 204));

        playerNumberOfWinLabel.setText("Số ván thắng");

        playerNicknameLabel.setText("Nickname");

        playerNumberOfGameLabel.setText("Số ván chơi");

        competitorNumberOfWinLabel.setText("Số ván thắng");

        competitorNicknameLabel.setText("Nickname");

        competotorNumberOfGameLabel.setText("Số ván chơi");

        playerNicknameValue.setText("{nickname}");

        playerNumberOfGameValue.setText("{sovanchoi}");

        playerNumberOfWinValue.setText("{sovanthang}");

        competitorNicknameValue.setText("{nickname}");

        competotorNumberOfGameValue.setText("{sovanchoi}");

        competitorNumberOfWinValue.setText("{sovanthang}");

        gamePanel.setBackground(new java.awt.Color(227, 253, 227));

        imagePanel.setPreferredSize(new java.awt.Dimension(400, 225));

        javax.swing.GroupLayout imagePanelLayout = new javax.swing.GroupLayout(imagePanel);
        imagePanel.setLayout(imagePanelLayout);
        imagePanelLayout.setHorizontalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        imagePanelLayout.setVerticalGroup(
            imagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 225, Short.MAX_VALUE)
        );

        answerField.setBackground(new java.awt.Color(255, 250, 225));
        answerField.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        answerField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                answerFieldActionPerformed(evt);
            }
        });

        submitBut.setBackground(new java.awt.Color(153, 255, 255));
        submitBut.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        submitBut.setText("Submit");
        submitBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitButActionPerformed(evt);
            }
        });

        jButtonClose.setBackground(new java.awt.Color(255, 102, 102));
        jButtonClose.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButtonClose.setText("Rời trận");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 51, 51));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButtonWantToAnswer.setText("Tôi muốn trả lời");
        jButtonWantToAnswer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonWantToAnswerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout gamePanelLayout = new javax.swing.GroupLayout(gamePanel);
        gamePanel.setLayout(gamePanelLayout);
        gamePanelLayout.setHorizontalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gamePanelLayout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(jButtonClose)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(57, 57, 57))
            .addGroup(gamePanelLayout.createSequentialGroup()
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(gamePanelLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(imagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(gamePanelLayout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addComponent(answerField, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gamePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gamePanelLayout.createSequentialGroup()
                        .addComponent(jButtonWantToAnswer)
                        .addGap(128, 128, 128))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, gamePanelLayout.createSequentialGroup()
                        .addComponent(submitBut)
                        .addGap(151, 151, 151))))
        );
        gamePanelLayout.setVerticalGroup(
            gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(gamePanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(imagePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(answerField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jButtonWantToAnswer)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(submitBut)
                .addGap(18, 18, 18)
                .addGroup(gamePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonClose)
                    .addComponent(jButton1))
                .addGap(36, 36, 36))
        );

        jPanel2.setBackground(new java.awt.Color(102, 102, 102));

        playerLabel.setForeground(new java.awt.Color(255, 255, 255));
        playerLabel.setText("Bạn");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(playerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(playerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.setBackground(new java.awt.Color(102, 102, 102));
        jPanel3.setForeground(new java.awt.Color(102, 102, 102));

        competitorLabel.setForeground(new java.awt.Color(255, 255, 255));
        competitorLabel.setText("Đối thủ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(competitorLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(173, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(competitorLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel4.setBackground(new java.awt.Color(102, 102, 102));

        roomNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        roomNameLabel.setText("{Tên Phòng}");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roomNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(roomNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(102, 102, 102));

        playerButtonImage.setBackground(new java.awt.Color(102, 102, 102));

        competotorButtonImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                competotorButtonImageActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(vsIcon, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                        .addComponent(playerButtonImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(competotorButtonImage, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(playerButtonImage, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(vsIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(competotorButtonImage, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(playerNumberOfWinLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26)
                                .addComponent(playerNumberOfWinValue, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(competitorNicknameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(competitorNicknameValue, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(competotorNumberOfGameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(competitorNumberOfWinLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(competotorNumberOfGameValue, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(competitorNumberOfWinValue, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(playerNumberOfGameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(playerNicknameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(playerNicknameValue, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(playerNumberOfGameValue))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(gamePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(jLabel7))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(playerNicknameLabel)
                            .addComponent(playerNicknameValue))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(playerNumberOfGameLabel)
                            .addComponent(playerNumberOfGameValue))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(playerNumberOfWinLabel)
                            .addComponent(playerNumberOfWinValue))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(competitorNicknameLabel)
                            .addComponent(competitorNicknameValue))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(competotorNumberOfGameLabel)
                            .addComponent(competotorNumberOfGameValue))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(competitorNumberOfWinLabel)
                            .addComponent(competitorNumberOfWinValue))))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
            .addComponent(gamePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        //for(int i=0; i<5; i++){
            //    for(int j=0;j<5;j++){
                //        gamePanel.add(button[i][j]);
                //    }
            //}

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void competotorButtonImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_competotorButtonImageActionPerformed

        Client.openView(Client.View.COMPETITOR_INFO, competitor);

    }//GEN-LAST:event_competotorButtonImageActionPerformed


    private void updateImage() {
        // Mảng chứa đường dẫn đến 11 ảnh
        // Mảng chứa đường dẫn đến 11 ảnh và câu trả lời tương ứng
        String[][] imagesWithAnswers = {
                {"assets/dhbc/anh2.gif", "dong_tien"},
                {"assets/dhbc/anh3.gif", "con_voi"},
                {"assets/dhbc/anh4.gif", "dau_cong"},
                {"assets/dhbc/anh5.gif", "con_ngua"},
                {"assets/dhbc/anh6.gif", "con_co"},
                {"assets/dhbc/anh7.gif", "ba_ba"},
                {"assets/dhbc/anh8.gif", "nha_tu"},
                {"assets/dhbc/anh9.gif", "con_nguoi"}
        };

        // Tạo đối tượng Random để chọn ngẫu nhiên
        Random random = new Random();

        // Chọn ngẫu nhiên một phần tử từ mảng
        int randomIndex = random.nextInt(imagesWithAnswers.length);
        String randomImagePath = imagesWithAnswers[randomIndex][0];
        correctAnswer = imagesWithAnswers[randomIndex][1];  // Lưu câu trả lời đúng

        // Tạo ImageIcon mới với đường dẫn của ảnh ngẫu nhiên
        ImageIcon imageIcon = new ImageIcon(randomImagePath);

        // Tạo JLabel chứa hình ảnh
        JLabel imageLabel = new JLabel(imageIcon);

        // Xóa tất cả các thành phần cũ của imagePanel (nếu có)
        imagePanel.removeAll();

        // Đặt layout cho imagePanel
        imagePanel.setLayout(new BorderLayout());

        // Thêm JLabel vào imagePanel
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        // Cập nhật và vẽ lại imagePanel
        imagePanel.revalidate();
        imagePanel.repaint();
    }

    private void answerFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_answerFieldActionPerformed
        // TODO add your handling code here:
        submitButActionPerformed (evt);
    }//GEN-LAST:event_answerFieldActionPerformed

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        // TODO add your handling code here:
        this.dispose();
        try {
            Client.socketHandle.write("client2-leave," + Client.user.getID());
        } catch (IOException ex) {
            Logger.getLogger(GameClientFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Client.homePageFrm.dispose();
        Client.openView(Client.View.HOMEPAGE);
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        Client.homePageFrm.dispose();
        Client.openView(Client.View.HOMEPAGE);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButtonWantToAnswerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonWantToAnswerActionPerformed
        try {
            // TODO add your handling code here:

            Client.socketHandle.write("want-to-answer," + Client.user.getID());
        } catch (IOException ex) {
            Logger.getLogger(GameClientFrm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonWantToAnswerActionPerformed
    
    private String correctAnswer;
    private int correctAnswerCount = 0;
    private int questionCount = 0;

    public void resetCountAnswerCorrect(){
        correctAnswerCount  = 0;
        questionCount = 0;
    }

    public void setSubmitAllowed(boolean allowed) {
        isSubmitAllowed = allowed;
        submitBut.setEnabled(allowed); // Optionally enable/disable the button
    }
    
    private void submitButActionPerformed(java.awt.event.ActionEvent evt) {

        if (!isSubmitAllowed) {
            System.out.println("Ban khong co quyen tra loi ");
            return;
        }
      

        // Lấy đáp án từ JTextField
        String userAnswer = answerField.getText();

        // So sánh đáp án người dùng nhập với đáp án đúng đã được cập nhật khi hiển thị ảnh
        if (userAnswer.equals(correctAnswer)) {
            // Nếu đúng, hiển thị thông báo "Correct!"
            JOptionPane.showMessageDialog(this, "Correct!", "Result", JOptionPane.INFORMATION_MESSAGE);
            correctAnswerCount++;
        } else {
            // Nếu sai, hiển thị thông báo "Wrong! Try again."
            JOptionPane.showMessageDialog(this, "Wrong! Try again.", "Result", JOptionPane.WARNING_MESSAGE);
        }

        questionCount++;
        if (questionCount == 3) {
            System.out.println("so cau dung la : " + correctAnswerCount );
            sendCorrectAnswers(correctAnswerCount);
            questionCount = 0;
            correctAnswerCount = 0;
             JOptionPane.showMessageDialog(this, "Vui lòng đợi kết quả", "wait", JOptionPane.INFORMATION_MESSAGE);
             

        } else {
            // Cập nhật ảnh mới sau khi trả lời
            updateImage();
        }
        answerField.setText(""); 
    }

    public void sendCorrectAnswers(int correctAnswers) {
        try {
            Client.socketHandle.write("correct-answers," + correctAnswers);
            System.out.println("gui dap an dung : " + correctAnswers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public void increaseWinMatchToUser() {
        Client.user.setNumberOfWin(Client.user.getNumberOfWin() + 1);
        playerNumberOfWinValue.setText("" + Client.user.getNumberOfWin());


    }

    public void increaseWinMatchToCompetitor() {
        competitor.setNumberOfWin(competitor.getNumberOfWin() + 1);
        competitorNumberOfWinValue.setText("" + competitor.getNumberOfWin());
    }

    public void displayDrawGame() {
      
    }

    public void newgame() {

        if (numberOfMatch % 2 == 0) {
//            startTimer();
//            countDownLabel.setVisible(true);
        }
        numberOfMatch++;
    }

    public void updateNumberOfGame() {
        competitor.setNumberOfGame(competitor.getNumberOfGame() + 1);
        competotorNumberOfGameValue.setText(Integer.toString(competitor.getNumberOfGame()));
        Client.user.setNumberOfGame(Client.user.getNumberOfGame() + 1);
        playerNumberOfGameValue.setText(Integer.toString(Client.user.getNumberOfGame()));
    }


    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField answerField;
    private javax.swing.JLabel competitorLabel;
    private javax.swing.JLabel competitorNicknameLabel;
    private javax.swing.JLabel competitorNicknameValue;
    private javax.swing.JLabel competitorNumberOfWinLabel;
    private javax.swing.JLabel competitorNumberOfWinValue;
    private javax.swing.JButton competotorButtonImage;
    private javax.swing.JLabel competotorNumberOfGameLabel;
    private javax.swing.JLabel competotorNumberOfGameValue;
    private javax.swing.JPanel gamePanel;
    private javax.swing.JPanel imagePanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonWantToAnswer;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JFrame jFrame3;
    private javax.swing.JFrame jFrame4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel playerButtonImage;
    private javax.swing.JLabel playerLabel;
    private javax.swing.JLabel playerNicknameLabel;
    private javax.swing.JLabel playerNicknameValue;
    private javax.swing.JLabel playerNumberOfGameLabel;
    private javax.swing.JLabel playerNumberOfGameValue;
    private javax.swing.JLabel playerNumberOfWinLabel;
    private javax.swing.JLabel playerNumberOfWinValue;
    private javax.swing.JLabel roomNameLabel;
    private javax.swing.JButton submitBut;
    private javax.swing.JLabel vsIcon;
    // End of variables declaration//GEN-END:variables


}