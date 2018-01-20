/**
 *
 * @date 2018/1/13.
 */
public class StuckThread extends Thread{

    private Boolean isFinish;

    @Override
    public void run() {
        try {
            Thread.sleep(15000);
            if(isFinish == false){
                SudoMainFrame.addLog("Stuck");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Boolean getFinish() {
        return isFinish;
    }

    public void setFinish(Boolean finish) {
        isFinish = finish;
    }
}
