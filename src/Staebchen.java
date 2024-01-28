import java.awt.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Staebchen {
    private boolean aufgenommen;

    private boolean istLinkesStaebchen;

    Lock lock = new ReentrantLock();

    public synchronized void aufnehmen() {
        aufgenommen = true;
    }

    public synchronized void ablegen() {
        aufgenommen = false;
    }

    public synchronized boolean istAufgenommen() {
        return aufgenommen;
    }

    public void setIstLinkesStaebchen() {
        istLinkesStaebchen = true;
    }

    public void setIstRechtesStaebchen() {
        istLinkesStaebchen = false;
    }

    public void paint(Graphics g, int x, int y, int x2, int y2, int x3, int y3, double winkel2, double winkel3, double winkel4) {
        synchronized (lock) {
            int length = 40;
            int xanf, yanf, xend, yend;
            if (!aufgenommen) {
                xanf = (int) (x + 10 * Math.cos(winkel2));
                yanf = (int) (y + 10 * Math.sin(winkel2));
                xend = (int) (x + (length + 10) * Math.cos(winkel2));
                yend = (int) (y + (length + 10) * Math.sin(winkel2));
            } else {
                if (istLinkesStaebchen) {
                    xanf = (int) (x3 + 10 * Math.cos(winkel4));
                    yanf = (int) (y3 + 10 * Math.sin(winkel4));
                    xend = (int) (x3 + (length + 10) * Math.cos(winkel4));
                    yend = (int) (y3 + (length + 10) * Math.sin(winkel4));
                } else {
                    xanf = (int) (x2 + 10 * Math.cos(winkel3));
                    yanf = (int) (y2 + 10 * Math.sin(winkel3));
                    xend = (int) (x2 + (length + 10) * Math.cos(winkel3));
                    yend = (int) (y2 + (length + 10) * Math.sin(winkel3));
                }
            }
            g.setColor(Color.BLACK);
            g.drawLine(xanf, yanf, xend, yend);
        }

    }
}
