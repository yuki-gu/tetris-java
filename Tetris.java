import java.awt.Event;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

class Tetris{
    public static void main(String[] args) {
        JFrame frame = new JFrame("テトリス");
        frame.setBounds(100, 100, 1000, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.white);
        GameBoard pnl = new GameBoard(20, 20, 10, 20, 30);
        pnl.interval = 1000;
        panel.add(pnl);
        frame.add(panel);
        frame.addKeyListener(new KeyListener(){
            @Override
            public void keyPressed(KeyEvent e) {
                int keycode = e.getKeyCode();
                switch (keycode){
                    case KeyEvent.VK_LEFT:
                        pnl.left();
                        break;
                    case KeyEvent.VK_RIGHT:
                        pnl.right();
                        break;
                    case KeyEvent.VK_UP:
                        pnl.leftRotation();
                        break;
                    case KeyEvent.VK_DOWN:
                        pnl.rightRotation();
                        break;
                    case KeyEvent.VK_SPACE:
                        pnl.momentaryFall();
                        break;
                    case KeyEvent.VK_SHIFT:
                        pnl.fall();
                        break;
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {

            }
            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        frame.setVisible(true);
    }
}


class Tetrimino1 extends Tetrimino{
    public Tetrimino1(int num_horizontal){
        int center = getCenter(num_horizontal);
        Color color = Color.cyan;
        blocks.add(new Block(center - 1, 0, color));
        blocks.add(new Block(center, 0, color));
        blocks.add(new Block(center + 1, 0, color));
        blocks.add(new Block(center + 2, 0, color));
        axis = new Block(center, 0, color);
    }
}

class Tetrimino2 extends Tetrimino{
    public Tetrimino2(int num_horizontal){
        int center = getCenter(num_horizontal);
        Color color = Color.orange;
        blocks.add(new Block(center + 1, 0, color));
        blocks.add(new Block(center - 1, 1, color));
        blocks.add(new Block(center, 1, color));
        blocks.add(new Block(center + 1, 1, color));
        axis = new Block(center, 1, color);
    }
}

class Tetrimino3 extends Tetrimino{
    public Tetrimino3(int num_horizontal){
        int center = getCenter(num_horizontal);
        Color color = Color.blue;
        blocks.add(new Block(center - 1, 0, color));
        blocks.add(new Block(center - 1, 1, color));
        blocks.add(new Block(center, 1, color));
        blocks.add(new Block(center + 1, 1, color));
        axis = new Block(center, 1, color);
    }
}

class Tetrimino4 extends Tetrimino{
    public Tetrimino4(int num_horizontal){
        int center = getCenter(num_horizontal);
        Color color = Color.red;
        blocks.add(new Block(center - 1, 0, color));
        blocks.add(new Block(center, 0, color));
        blocks.add(new Block(center, 1, color));
        blocks.add(new Block(center + 1, 1, color));
        axis = new Block(center, 1, color);
    }
}

class Tetrimino5 extends Tetrimino{
    public Tetrimino5(int num_horizontal){
        int center = getCenter(num_horizontal);
        Color color = Color.green;
        blocks.add(new Block(center, 0, color));
        blocks.add(new Block(center + 1, 0, color));
        blocks.add(new Block(center - 1, 1, color));
        blocks.add(new Block(center, 1, color));
        axis = new Block(center, 1, color);
    }
}

class Tetrimino6 extends Tetrimino{
    public Tetrimino6(int num_horizontal){
        int center = getCenter(num_horizontal);
        Color color = Color.magenta;
        blocks.add(new Block(center, 0, color));
        blocks.add(new Block(center - 1, 1, color));
        blocks.add(new Block(center, 1, color));
        blocks.add(new Block(center + 1, 1, color));
        axis = new Block(center, 1, color);
    }
}

class Tetrimino7 extends Tetrimino{
    public Tetrimino7(int num_horizontal){
        int center = getCenter(num_horizontal);
        Color color = Color.yellow;
        blocks.add(new Block(center, 0, color));
        blocks.add(new Block(center + 1, 0, color));
        blocks.add(new Block(center, 1, color));
        blocks.add(new Block(center + 1, 1, color));
    }
}

class GameBoard extends JPanel{
    int blockSize = 30; //1タイルの大きさ
    int borderWidth = 1; //枠線の幅
    int num_vertical = 20; //縦の個数
    int num_horizontal = 10; //横の個数
    public int interval = 2000;
    Color blockColor = Color.gray;
    Tetrimino[] Tetrimino6 = {
        new Tetrimino1(num_horizontal),
        new Tetrimino2(num_horizontal),
        new Tetrimino3(num_horizontal),
        new Tetrimino4(num_horizontal),
        new Tetrimino5(num_horizontal),
        new Tetrimino6(num_horizontal),
        new Tetrimino7(num_horizontal)
    };


    public void gameOver(){
        //ゲームオーバー処理
        resetBlock();
    }

    private Tetrimino getTetrimino(){
        if (Tetrimino6.length == 0) return null;
        return Tetrimino6[rand.nextInt(Tetrimino6.length)].getClone();
    }


    private List<JPanel> panels = new ArrayList<JPanel>();
    private List<Block> blocks = new ArrayList<Block>();
    private Tetrimino tetrimino = null;
    private int fallType = 0; //0 -> 通常, 1 -> ２倍, 2 -> 一瞬
    private Random rand = new Random();

    public GameBoard(int x, int y, int num_horizontal, int num_vertical, int blockSize){
        this.blockSize = blockSize;
        tetrimino = getTetrimino();
        setLayout(null);
        setBackground(Color.white);
        setBounds(x, y, (blockSize + borderWidth) * num_horizontal - borderWidth, (blockSize + borderWidth) * num_vertical - borderWidth);
        this.num_horizontal = num_horizontal;
        this.num_vertical = num_vertical;
        for (int cy = 0; cy < num_vertical; cy++){
            for (int cx = 0; cx < num_horizontal; cx++){
                JPanel panel = new JPanel();
                panel.setBounds((blockSize + borderWidth) * cx, (blockSize + borderWidth) * cy, blockSize, blockSize);
                panel.setBackground(blockColor);
                panels.add(panel);
                add(panel);
            }
        }
        draw();
        new Thread(new Runnable(){
            @Override
            public void run() {
                sleep(interval);
                while (true){
                    boolean result = down();
                    if (fallType == 1){
                        sleep(interval / 2);
                        fallType = 0;
                        continue;
                    }
                    if (fallType == 2){
                        if (result == false) fallType = 0;
                        else continue;
                    }
                    sleep(interval);
                }
            }
        }).start();
    }

    private void sleep(int time){
        try {
            Thread.sleep(time);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public void resetBlock(){
        blocks.clear();
        for (JPanel item : panels){
            item.setBackground(blockColor);
        }
    }

    public void fall(){
        fallType = 1;
    }

    public void momentaryFall(){
        fallType = 2;
    }

    public boolean down(){
        if (tetrimino == null) return false;
        if (isStopDown()){
            blocks.addAll(tetrimino.blocks);
            deleteLine();
            tetrimino = getTetrimino();
            if (isGameOver()){
                tetrimino = null;
                gameOver();
                return false;
            }
            draw();
            return false;
        }
        drawBefore();
        tetrimino.down();
        draw();
        return true;
    }

    public void left(){
        if (tetrimino == null) return;
        if (isMoveLeft()){
            drawBefore();
            tetrimino.move(-1);
            draw();
        }
    }

    public void right(){
        if (tetrimino == null) return;
        if (isMoveRight()){
            drawBefore();
            tetrimino.move(1);
            draw();
        }
    }

    public void leftRotation(){
        if (tetrimino == null || tetrimino.axis == null) return;
        if (isRotateLeft()){
            drawBefore();
            tetrimino.rotation(-1);
            draw();
        }
    }

    public void rightRotation(){
        if (tetrimino == null || tetrimino.axis == null) return;
        if (isRotateRight()){
            drawBefore();
            tetrimino.rotation(1);
            draw();
        }
    }

    private boolean isStopDown(){
        for (Block item : tetrimino.blocks){
            if (item.y >= num_vertical - 1) return true;
            for (Block item2 : blocks){
                if (item.x == item2.x && item.y + 1 == item2.y) return true;
            }
        }
        return false;
    }

    private boolean isMoveLeft(){
        for (Block item : tetrimino.blocks){
            if (item.x <= 0) return false;
            for (Block item2 : blocks){
                if (item.x - 1 == item2.x && item.y == item2.y) return false;
            }
        }
        return true;
    }

    private boolean isMoveRight(){
        for (Block item : tetrimino.blocks){
            if (item.x >= num_horizontal - 1) return false;
            for (Block item2 : blocks){
                if (item.x + 1 == item2.x && item.y == item2.y) return false;
            }
        }
        return true;
    }

    private boolean isRotateLeft(){
        if (tetrimino.axis == null) return false;
        for (Block item : tetrimino.blocks){
            int x = tetrimino.axis.x - (tetrimino.axis.y - item.y);
            int y = tetrimino.axis.y + (tetrimino.axis.x - item.x);
            if (x < 0 || y < 0 || x >= num_horizontal || y >= num_vertical) return false;
            for (Block item2 : blocks){
                if (x == item2.x && y == item2.y) return false;
            }
        }
        return true;
    }

    private boolean isRotateRight(){
        if (tetrimino.axis == null) return false;
        for (Block item : tetrimino.blocks){
            int x = tetrimino.axis.x + (tetrimino.axis.y - item.y);
            int y = tetrimino.axis.y - (tetrimino.axis.x - item.x);
            if (x < 0 || y < 0 || x >= num_horizontal || y >= num_vertical) return false;
            for (Block item2 : blocks){
                if (x == item2.x && y == item2.y) return false;
            }
        }
        return true;
    }

    private boolean isGameOver(){
        for (Block item : tetrimino.blocks){
            for (Block item2 : blocks){
                if (item.x == item2.x && item.y == item2.y) return true;
            }
        }
        return false;
    }

    private void deleteLine(){
        List<Block> deleteList = new ArrayList<Block>();
        List<Integer> deleteIndex = new ArrayList<Integer>();
        for (int i = 0; i < num_vertical; i++){
            int count = 0;
            List<Block> deleteList2 = new ArrayList<Block>();
            for (Block item : blocks){
                if (item.y == i) {
                    deleteList2.add(item);
                    count++;
                }
            }
            if (count == num_horizontal){
                deleteList.addAll(deleteList2);
                deleteIndex.add(i);
            }
        }
        for (Block item : deleteList){
            panels.get(getListIndex(item.x, item.y)).setBackground(Color.white);
        }
        if (deleteList.size() != 0){
            sleep(300);
        }
        for (Block item : deleteList){
            panels.get(getListIndex(item.x, item.y)).setBackground(blockColor);
            blocks.remove(item);
        }
        Collections.sort(deleteIndex, Collections.reverseOrder());
        List<Block> setColorList = new ArrayList<Block>();
        int addCount = 0;
        for (int i = 0; i < deleteIndex.size(); i++){

            for (Block item : blocks){
                if (item.y < deleteIndex.get(i) + addCount){
                    panels.get(getListIndex(item.x, item.y)).setBackground(blockColor);
                    item.y++;
                    setColorList.add(item);
                }
            }
            addCount++;
        }
        for (Block item : setColorList){
            panels.get(getListIndex(item.x, item.y)).setBackground(item.color);
        }
    }

    private void draw(){
        if (tetrimino == null) return;
        for (Block block : tetrimino.blocks){
            panels.get(getListIndex(block.x, block.y)).setBackground(block.color);;
        }
    }

    private void drawBefore(){
        if (tetrimino == null) return;
        for (Block block : tetrimino.blocks){
            panels.get(getListIndex(block.x, block.y)).setBackground(blockColor);;
        }
    }

    private int getListIndex(int x, int y){
        int i = y * num_horizontal + x;
        return i;
    }
}

class Tetrimino{
    List<Block> blocks = new ArrayList<Block>();
    Block axis = null;

    public int getCenter(int x){
        int center = x / 2;
        if (x % 2 == 0) center--;
        return center;
    }

    public void down(){
        for (Block item : blocks){
            item.y++;
        }
        if (axis != null) axis.y++;
    }

    public void move(int x){
        for (Block item : blocks){
            item.x += x;
        }
        if (axis != null) axis.x += x;
    }

    public void rotation(int r){
        if (axis == null) return;
        for (Block item : blocks){
            if (item.x == axis.x && item.y == axis.y) continue;
            int x = axis.x - item.x;
            int y = axis.y - item.y;
            if (r >= 0){
                item.x = axis.x + y;
                item.y = axis.y - x;
            }
            else{
                item.x = axis.x - y;
                item.y = axis.y + x;
            }
        }
    }

    public Tetrimino getClone(){
        Tetrimino tetrimino = new Tetrimino();
        if (blocks.size() != 0){
            for (Block block : blocks){
                tetrimino.blocks.add(block.getClone());
            }
        }
        if (axis != null) tetrimino.axis = axis.getClone();
        return tetrimino;
    }
}

class Block{
    public Block(){

    }

    public Block(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Block(int x, int y, Color color){
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int x;
    public int y;
    public Color color;

    public Block getClone(){
        return new Block(x, y, color);
    }
}