package multiMedia.JavaSound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class UI extends JFrame implements ActionListener {
    private PlayerContext context = new PlayerContext().getInstance();

    public UI() {
        JPanel context = new JPanel();
        context.setLayout(new FlowLayout());
        JButton select = new JButton("select");
        JButton play = new JButton("play");
        JButton stop = new JButton("stop");
        JButton push = new JButton("push");
        JButton exit = new JButton("exit");
        select.setActionCommand("select");
        select.addActionListener(this);
        play.setActionCommand("play");
        play.addActionListener(this);
        stop.setActionCommand("stop");
        stop.addActionListener(this);
        push.setActionCommand("push");
        push.addActionListener(this);
        exit.setActionCommand("exit");
        exit.addActionListener(this);
        context.add(select);
        context.add(play);
        context.add(stop);
        context.add(push);
        context.add(exit);
        this.add(context);
        this.setVisible(true);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBackground(Color.blue);
    }

    public void actionPerformed(ActionEvent e) {
        try {
//			if(button.play.equals(e.getActionCommand())){
//				context.play();
//			}
//			if(button.stop.equals(e.getActionCommand())){
//				context.playStop("stop");
//			}
//			if(button.push.equals(e.getActionCommand())){
//				context.playStop("push");
//			}
//			if(button.exit.equals(e.getActionCommand())){
//				System.exit(0);
//			}
            if ("select".equals(e.getActionCommand())) {
                FileInputStream fis = openFile();
                if (fis != null) context.setFile(fis);
            }
            if ("play".equals(e.getActionCommand())) {
                context.play();
            }
            if ("stop".equals(e.getActionCommand())) {
                context.playStop("stop");
            }
            if ("push".equals(e.getActionCommand())) {
                context.playStop("push");
            }
            if ("exit".equals(e.getActionCommand())) {
                System.exit(0);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public FileInputStream openFile() throws FileNotFoundException {
        JFileChooser file = new JFileChooser();
//		file.setDialogTitle("选择音乐文件");
//		file.setAccessory()
//		file.setForeground(Color.black);
        file.showOpenDialog(null);
        File fl = file.getSelectedFile();
        return file.getSelectedFile() == null ? null : new FileInputStream(file.getSelectedFile());
    }
}
