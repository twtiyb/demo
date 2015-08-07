package game.Playplan;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-8-10
 */
public class TestDraw {
    public static void main(String[] args) {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MyPanel p = new MyPanel();
        p.addMouseMotionListener(p);
        f.add(p);
        f.pack();
        f.setVisible(true);
    }
}

class Utils {
    public static final boolean isArcArcCollides(float x1, float y1, float r1,
                                                 float x2, float y2, float r2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)) <= (r1 + r2);
    }
}

class MyBoom {
    public final float x, y;
    public final float degree;
    public int life = 20;
    private Image data;
    public final int width = 20, height = 20;
    private float scale;

    public MyBoom(float x, float y, float degree) {
        this.x = x;
        this.y = y;
        this.degree = degree;
        scale = 0.2f;
        try {
            data = ImageIO.read(new File(TestDraw.class.getResource("").getPath() + "\\"
                    + "boom.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawSelf(Graphics2D g2d) {
        g2d.rotate(degree + Math.PI, x, y);
        int tempWidth = (int) (width * scale);
        int tempHeight = (int) (height * scale);
        g2d.drawImage(data, (int) x - tempWidth / 2, (int) y - tempHeight / 2,
                tempWidth, tempHeight, null);
        life--;
        scale = scale + 0.1f;
        g2d.rotate(-degree - Math.PI, x, y);
    }
}

class MyBullet {
    public float x, y;
    public final float speed;
    public final float speedX, speedY;
    public final float degree;
    public int life = 100;
    private Image data;
    public final int width = 15, height = 20;

    public MyBullet(float x, float y, float speedX, float speedY, float degree) {
        speed = 8;
        this.x = x;
        this.y = y;
        this.speedX = speedX;
        this.speedY = speedY;
        this.degree = degree;
        try {
            data = ImageIO.read(new File(TestDraw.class.getResource("").getPath() + "\\"
                    + "bullet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawSelf(Graphics2D g2d) {
        g2d.rotate(degree + Math.PI, x, y);
        g2d.drawImage(data, (int) x - width / 2, (int) y - height / 2, width,
                height, null);
        g2d.rotate(-degree - Math.PI, x, y);
    }

    public void moveSelf() {
        this.x += speedX * speed;
        this.y += speedY * speed;
        life--;
    }
}

class MyEnemy {
    public float x, y;
    public float speedX, speedY;
    public float speed;
    public float degree;
    private Image data;
    public final int width = 40, height = 40;

    public MyEnemy(float x, float y) {
        this.x = x;
        this.y = y;
        speed = 1;
        try {
            data = ImageIO.read(new File(TestDraw.class.getResource("").getPath() + "\\"
                    + "rubbish.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void adjustDegree() {
        if (speedX == 0 && speedY >= 0) {
            degree = 0;
        } else if (speedX == 0 && speedY < 0) {
            degree = (float) Math.PI;
        } else if (speedY == 0 && speedX < 0) {
            degree = (float) Math.PI * 3 / 2;
        } else if (speedY == 0 && speedX > 0) {
            degree = (float) Math.PI / 2;
        } else if (speedX < 0 && speedY > 0) {
            degree = (float) (-Math.asin(speedX));
        } else if (speedX > 0 && speedY > 0) {
            degree = (float) (Math.asin(-speedX));
        } else if (speedX > 0 && speedY < 0) {
            degree = (float) (Math.PI - Math.asin(-speedX));
        } else if (speedX < 0 && speedY < 0) {
            degree = (float) (Math.PI - Math.asin(-speedX));
        }
    }

    public void setTowards(float x, float y) {
        float tempX = x - this.x;
        float tempY = y - this.y;
        float distance = (float) Math.sqrt(tempX * tempX + tempY * tempY);
        speedX = tempX / distance;
        speedY = tempY / distance;
        adjustDegree();
    }

    public void drawSelf(Graphics2D g2d) {
        g2d.rotate(degree + Math.PI, x, y);
        g2d.drawImage(data, (int) x - width / 2, (int) y - height / 2, width,
                height, null);
        g2d.rotate(-degree - Math.PI, x, y);
    }

    public boolean ofOfBound() {
        return x < 0 || y < 0 || x > MyPanel.width || y > MyPanel.height;
    }

    public void moveSelf() {
        this.x += speedX * speed;
        this.y += speedY * speed;
    }
}

class MyCharactor {
    public float x, y;
    public float speedX, speedY;
    public float speed;
    public float degree;
    private Image data;
    public final int width = 40, height = 40;

    public MyCharactor(float x, float y) {
        this.x = x;
        this.y = y;
        speed = 2;
        try {
            data = ImageIO.read(new File(TestDraw.class.getResource("").getPath() + "\\"
                    + "player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void adjustDegree() {
        if (speedX == 0 && speedY >= 0) {
            degree = 0;
        } else if (speedX == 0 && speedY < 0) {
            degree = (float) Math.PI;
        } else if (speedY == 0 && speedX < 0) {
            degree = (float) Math.PI * 3 / 2;
        } else if (speedY == 0 && speedX > 0) {
            degree = (float) Math.PI / 2;
        } else if (speedX < 0 && speedY > 0) {
            degree = (float) (-Math.asin(speedX));
        } else if (speedX > 0 && speedY > 0) {
            degree = (float) (Math.asin(-speedX));
        } else if (speedX > 0 && speedY < 0) {
            degree = (float) (Math.PI - Math.asin(-speedX));
        } else if (speedX < 0 && speedY < 0) {
            degree = (float) (Math.PI - Math.asin(-speedX));
        }
    }

    public void setTowards(float x, float y) {
        float tempX = x - this.x;
        float tempY = y - this.y;
        float distance = (float) Math.sqrt(tempX * tempX + tempY * tempY);
        speedX = tempX / distance;
        speedY = tempY / distance;
        adjustDegree();
    }

    public void drawSelf(Graphics2D g2d) {
        g2d.rotate(degree + Math.PI, x, y);
        g2d.drawImage(data, (int) x - width / 2, (int) y - height / 2, width,
                height, null);
        g2d.rotate(-degree - Math.PI, x, y);
    }

    public void moveSelf() {
        this.x += speedX * speed;
        this.y += speedY * speed;
    }

    public MyBullet autoShot() {
        return new MyBullet(x, y, speedX, speedY, degree);
    }
}

class MyPanel extends JPanel implements MouseMotionListener {
    private static final long serialVersionUID = 1L;
    public static final int width = 800, height = 480;
    private MyCharactor c;
    private List<MyBullet> bullets;
    private List<MyEnemy> enemies;
    private List<MyBoom> booms;
    private int step;
    private int fps = 1000 / 60;
    private int de_time;
    private int de_sleep;
    private boolean flag = true;
    private Image back;

    public MyPanel() {
        super();
        this.setPreferredSize(new Dimension(width, height));
        c = new MyCharactor(width / 2, height / 2);
        bullets = new ArrayList<MyBullet>();
        enemies = new ArrayList<MyEnemy>();
        booms = new ArrayList<MyBoom>();
        try {
            back = ImageIO.read(new File(TestDraw.class.getResource("").getPath() + "\\"
                    + "background.jpg"));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        new Thread(new Runnable() {

            public void run() {
                while (flag) {
                    long start = System.currentTimeMillis();
                    repaint();
                    long end = System.currentTimeMillis();
                    if (end - start < fps) {
                        try {
                            Thread.sleep(fps - (end - start));
                            de_time = (int) (end - start);
                            de_sleep = (int) (fps - (end - start));
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }
        }).start();
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        if (back == null) {
            g2d.fillRect(0, 0, width, height);
        } else {
            g2d.drawImage(back, 0, 0, width, height, null);
        }
        for (int i = 0; i < bullets.size(); ++i) {
            MyBullet b = bullets.get(i);
            b.drawSelf(g2d);
            b.moveSelf();
            if (b.life < 0) {
                bullets.remove(b);
            }
            for (int j = 0; j < enemies.size(); ++j) {
                MyEnemy e = enemies.get(j);
                if (Utils.isArcArcCollides(b.x, b.y, (b.width + b.height) / 4,
                        e.x, e.y, (e.width + e.height) / 4)) {
                    booms.add(new MyBoom(e.x, e.y, e.degree));
                    enemies.remove(e);
                    bullets.remove(b);
                    continue;
                }
            }
        }
        for (int i = 0; i < enemies.size(); ++i) {
            MyEnemy e = enemies.get(i);
            e.drawSelf(g2d);
            e.moveSelf();
            if (e.ofOfBound()) {
                // e.setTowards(c.x, c.y);
                enemies.remove(e);
            }
        }
        if (c != null) {
            c.drawSelf(g2d);
            c.moveSelf();
            for (int i = 0; i < enemies.size(); ++i) {
                MyEnemy e = enemies.get(i);
                if (Utils.isArcArcCollides(c.x, c.y, (c.width + c.height) / 4,
                        e.x, e.y, (e.width + e.height) / 4)) {
                    System.out.println("碰上啦....");
                    flag = false;
                    return;
                }
            }
            step++;
            if (step == 15) {
                step = 0;
                bullets.add(c.autoShot());
                int key = (int) (Math.random() * 4);
                MyEnemy e;
                switch (key) {
                    case 0:
                        e = new MyEnemy(0, (float) (Math.random() * height));
                        e.setTowards(c.x, c.y);
                        enemies.add(e);
                        break;
                    case 1:
                        e = new MyEnemy(width, (float) (Math.random() * height));
                        e.setTowards(c.x, c.y);
                        enemies.add(e);
                        break;
                    case 2:
                        e = new MyEnemy((float) (Math.random() * width), 0);
                        e.setTowards(c.x, c.y);
                        enemies.add(e);
                        break;
                    case 3:
                        e = new MyEnemy((float) (Math.random() * width), height);
                        e.setTowards(c.x, c.y);
                        enemies.add(e);
                        break;
                }
            }
        }
        for (int i = 0; i < booms.size(); ++i) {
            MyBoom b = booms.get(i);
            b.drawSelf(g2d);
            if (b.life == 0) {
                booms.remove(b);
            }
        }
        g2d.setColor(Color.GRAY);
        g2d.drawString("渲染逻辑耗时:" + de_time + "ms", 10, 20);
        g2d.drawString("帧暂停的时间:" + de_sleep + "ms", 10, 40);
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        c.setTowards(x, y);
    }

    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        c.setTowards(x, y);
    }

}