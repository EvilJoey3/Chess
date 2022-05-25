package advanced;

public class Timer extends Thread{
    protected int time;
    protected final int period;
    protected boolean breakPoint;

    public Timer(int period){
        breakPoint = false;
        this.period = period;
        time = period;
    }

    @Override
    public void run() {
        while (true){
            try {
                if(time == 0){
                    reset();
                    periodEvent();
                }
                sleep(1000);
                time--;
                secEvent();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if(breakPoint) break;
        }
    }

    public void reset(){
        time = period;
    }

    protected void secEvent(){}

    protected void periodEvent(){}


    //GS

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPeriod() {
        return period;
    }

    public boolean isBreakPoint() {
        return breakPoint;
    }

    public void setBreakPoint(boolean breakPoint) {
        this.breakPoint = breakPoint;
    }
}
