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

    public static long usedTime = 0;
    private ShuduCanvers panelCanvers;

    public static java.util.List logs = new ArrayList<>();
    public static JTextArea logArea = new JTextArea();

    public static int[][] nums = new int[9][9];



    public ShuduMainFrame() {
        init();
        addComponent();
        addCanvers();
        addLog("Ready");

    }


    private void addCanvers() {
        panelCanvers = new ShuduCanvers();
        panelCanvers.setBorder(new TitledBorder("SUDOKU"));
        panelCanvers.setBackground(Color.BLACK);
        this.add(panelCanvers, BorderLayout.CENTER);

    }


    private void addComponent() {
        JPanel panelComponent = new JPanel();
        addPanelMsg(panelComponent);
        this.add(panelComponent, BorderLayout.NORTH);

        addPanelTime();
    }

    private void addPanelTime() {
        JPanel panel = new JPanel();
        logArea.setSize(515, 200);
        panel.add(logArea);
        panel.setBorder(new TitledBorder("System Log"));
        panel.setBackground(Color.GREEN);
        panel.setPreferredSize(new Dimension(515, 200));
        this.add(panel, BorderLayout.SOUTH);


    }


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
                addLog("Working...");
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
                        panelCanvers.cells[i][j].setBackground(Color.YELLOW);
                    }
                }
                addLog("Finish");
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
                        addLog("Loading "+file.getName());
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

        this.setSize(515, 800);
        this.setLocation(500, 50);
        this.setTitle("SUDOKU");
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void addLog(String log){
        if(logs.size() >= 10){
            logs.remove(0);
        }
        logs.add(log);
        logArea.setText(String.join("\n",logs));
    }
}
