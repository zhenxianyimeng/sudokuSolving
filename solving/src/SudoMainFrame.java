import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

/**
 *
 * @date 2018/1/13.
 */
public class SudoMainFrame extends JFrame{

    public static long usedTime = 0;
    private static SudoCanvers panelCanvers;

    public static java.util.List logs = new ArrayList<>();
    public static JTextArea logArea = new JTextArea();

    public static int[][] nums = new int[9][9];

    private SoveThread soveThread;

    public SudoMainFrame() {
        init();
        addComponent();
        addCanvers();
        addLog("Ready");

    }


    private void addCanvers() {
        panelCanvers = new SudoCanvers();
        panelCanvers.setBorder(new TitledBorder("SUDOKU"));
        panelCanvers.setBackground(Color.BLACK);
        this.add(panelCanvers, BorderLayout.CENTER);

    }


    private void addComponent() {
        JPanel panelComponent = new JPanel();
        addPanelMsg(panelComponent);
        this.add(panelComponent, BorderLayout.NORTH);

        addPanelLog();
    }

    /**
     * log panel
     */
    private void addPanelLog() {
        JPanel panel = new JPanel();
        logArea.setSize(515, 200);
        panel.add(logArea);
        panel.setBorder(new TitledBorder("System Log"));
        panel.setBackground(Color.GREEN);
        panel.setPreferredSize(new Dimension(515, 200));
        this.add(panel, BorderLayout.SOUTH);


    }

    /**
     * button panel
     * @param panelComponent
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
        interruptListerner(interruptButton);
        JButton clearButton = new JButton("CLEAR");
        clearSudukuListerner(clearButton);

        JButton quitButton = new JButton("QUIT");
        quitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
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

    private void cleanAllCellsValue(){
        for (int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                panelCanvers.cells[j][i].setText("");
                panelCanvers.cells[j][i].setBackground(Color.WHITE);
                nums[j][i] = 0;
            }
        }
    }

    /**
     * clear cells number
     * @param jButton
     */
    private void clearSudukuListerner(JButton jButton){
        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cleanAllCellsValue();
            }
        });
    }

    /**
     * run sudoku
     * @param jButton
     */
    private void runSudukuListerner(JButton jButton){
        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Boolean isFinish = false;
                soveThread = new SoveThread();
                soveThread.setFinish(isFinish);
                soveThread.setPanelCanvers(panelCanvers);
                soveThread.start();
                StuckThread stuckThread = new StuckThread();
                stuckThread.setFinish(isFinish);
                stuckThread.start();
            }
        });
    }

    /**
     * interrupt
     * @param jButton
     */
    private void interruptListerner(JButton jButton){
        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                addLog("interrupt");
                for(int i=0; i<9; i++){
                    for(int j=0; j<9; j++){
                        panelCanvers.cells[i][j].addMouseListener(panelCanvers);
                    }
                }
                soveThread.stop();
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
            public void mousePressed(MouseEvent e) {
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
                                    nums[l][i] = chs[i] - '0';

                                }
                            }
                        }


                    } catch (FileNotFoundException e1) {
                        addLog("File not found");
                        e1.printStackTrace();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        addLog("IOException");
                    } catch (Exception e3){
                        addLog("file line or row is less than 9, reinput or by hand");
                        cleanAllCellsValue();
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

    public static SudoCanvers getPanelCanvers() {
        return panelCanvers;
    }

    public static void setPanelCanvers(SudoCanvers panelCanvers) {
        panelCanvers = panelCanvers;
    }
}
