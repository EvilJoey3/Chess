package advanced.timers;

public class FrequentTimer extends Thread{

    private boolean breakpoint = false;
    private int frequent = 50;

    @Override
    public void run() {
        for (int i = 0; i < frequent; i++) {
            try {
                event();
                sleep(10);
                if(breakpoint) break;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void event(){}

    public void setBreakpoint(boolean breakpoint) {
        this.breakpoint = breakpoint;
    }
}
