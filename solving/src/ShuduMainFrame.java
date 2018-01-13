import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * @author zjb
 * @date 2018/1/13.
 */
public class ShuduMainFrame extends JFrame{
    public static int pass = 1; // 关卡
    public static JLabel lbPass; // 显示关卡的lable
    public static long usedTime = 0; // 玩家用时
    private ShuduCanvers panelCanvers; // 主游戏区
    public static Timer userTimeAction;

    public static java.util.List logs = new ArrayList<>();
    public static JTextArea logArea = new JTextArea();

    /*
     * 默认构造函数
     */
    public ShuduMainFrame() {
        // 初始化方法
        init();
        // 添加组件
        addComponent();
        // 添加主游戏区
        addCanvers();

    }

    /*
     * 添加主游戏区
     */
    private void addCanvers() {
        panelCanvers = new ShuduCanvers();
        panelCanvers.setBorder(new TitledBorder("SUDOKU"));
        panelCanvers.setBackground(Color.BLACK);

        // 将主游戏区添加到窗体中
        this.add(panelCanvers, BorderLayout.CENTER);

    }

    /*
     * 添加组件区
     */
    private void addComponent() {
        JPanel panelComponent = new JPanel();
        // 添加消息区
        addPanelMsg(panelComponent);
        // 添加时间区
        //addPanelTime(panelComponent);

        // 将组件添加到窗体顶部
        this.add(panelComponent, BorderLayout.NORTH);

        addPanelTime();
    }

    private void addPanelTime() {
        JPanel panel = new JPanel();
        panel.add(logArea);
        panel.setBorder(new TitledBorder("System Log"));
        panel.setBackground(Color.GREEN);
        this.add(panel, BorderLayout.SOUTH);
//        panelTime.setLayout(new GridLayout(2, 1));
//
//        final JLabel lbSysTime = new JLabel();
//        final JLabel lbUserTime = new JLabel();
//
//        panelTime.add(lbSysTime, BorderLayout.NORTH);
//        panelTime.add(lbUserTime, BorderLayout.SOUTH);
//
//        // 设置系统时间定时器
//        Timer sysTimeAction = new Timer(500, new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                long timeMillis = System.currentTimeMillis();
//                SimpleDateFormat df = new SimpleDateFormat(
//                        "yyyy-MM-dd HH:mm:ss");
//                lbSysTime.setText("    系统时间：  " + df.format(timeMillis));
//            }
//        });
//        sysTimeAction.start();
//        userTimeAction = new Timer(1000, new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                lbUserTime.setText("    您已用时：  " + (++usedTime)+ " sec.");
//            }
//        });
//        userTimeAction.start();

       // panelComponent.add(panelTime, BorderLayout.SOUTH);

    }

    /*
     * 添加消息区
     */
    private void addPanelMsg(JPanel panelComponent) {
        panelComponent.setLayout(new GridLayout(1, 3));

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder("Button"));

        JButton loadButton = new JButton("Load");
        loadFileListener(loadButton);
        JButton runButton = new JButton("RUN");
        runSudukuListerner(runButton);

        JButton interruptButton = new JButton("INTERRUPT");
        JButton clearButton = new JButton("CLEAR");
        clearSudukuListerner(clearButton);

        JButton quitButton = new JButton("QUIT");
        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        panel.add(loadButton);
        panel.add(runButton);
        panel.add(interruptButton);
        panel.add(clearButton);
        panel.add(quitButton);
        panelComponent.add(panel, BorderLayout.CENTER);

    }


    private void clearSudukuListerner(JButton jButton){
        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                for (int i=0; i<9; i++){
                    for(int j=0; j<9; j++){
                        panelCanvers.cells[j][i].setText("");
                        panelCanvers.cells[j][i].setBackground(Color.WHITE);

                    }
                }
            }
        });
    }

    private void runSudukuListerner(JButton jButton){
        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                boolean isInit = true;
                int[][] init ={
                    {0,2,0,0,0,9,0,1,0,0},
                    {5,0,6,0,0,0,3,0,9,0},
                    {0,8,0,5,0,2,0,6,0,0},
                    {0,0,5,0,7,0,1,0,0,0},
                    {0,0,0,2,0,8,0,0,0,0},
                    {0,0,4,0,1,0,8,0,0,0},
                    {0,5,0,8,0,7,0,3,0,0},
                    {7,0,2,3,0,0,4,0,5,0},
                    {0,4,0,0,0,0,0,7,0,0},
                };
                int[][] nums = new int[9][9];
                for (int i=0; i<9; i++){
                    for(int j=0; j<9; j++){
                        String str = panelCanvers.cells[j][i].getText();
                        if(str.length()==1) {
                            isInit = false;
                            nums[i][j] = Integer.parseInt(str);
                        }else {
                            nums[i][j] = 0;
                        }
                    }
                }
                if(isInit){
                    nums = init;
                }

                Solve solve = new Solve();
                solve.setNums(nums);
                solve.function(0,0);
                nums = solve.result();
                for (int i=0; i<9; i++){
                    for(int j=0; j<9; j++){
                        panelCanvers.cells[i][j].setText(nums[j][i]+"");
                    }
                }
            }
        });
    }

    /**
     * loadFile
     * @param jButton
     */
    private void loadFileListener(JButton jButton){
        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser jfc = new JFileChooser(this.getClass().getResource("/").getPath());
                jfc.setDialogTitle("Choose a sudoku file");
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

                int returnValue = jfc.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File file = jfc.getSelectedFile();
                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(new FileReader(file));
                        for(int l=0; l<9; l++){
                            String line = br.readLine();
                            char[] chs = line.toCharArray();
                            for(int i=0; i<9; i++){
                                if(chs[i]>='0' && chs[i]<='9'){
                                    panelCanvers.cells[i][l].setText(chs[i]+"");
                                    panelCanvers.cells[i][l].setBackground(Color.YELLOW);
                                    panelCanvers.cells[i][l].setOpaque(true);
                                }
                            }
                        }
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    } catch (Exception e3){
                        e3.printStackTrace();
                    }
                    finally {
                        if(br != null){
                            try {
                                br.close();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }

                }
            }
        });
    }

    /**
     * frame init
     */
    private void init() {

        // 设置窗口初始大小
        this.setSize(515, 620);
        // 设置窗口初始位置
        this.setLocation(500, 50);
        // 设置窗口标题
        this.setTitle("SUDOKU");
        // 设置窗体不允许改变大小
        this.setResizable(false);
        // 设置默认关闭操作
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
