import java.awt.*;

public class Tisch extends Canvas {

    private int philosophenAnzahl;

    private Staebchen[] staebchen;

    private MainFrame mainFrame;

    private Philosoph[] philosophen;

    private Aufsicht aufsicht;


    public Tisch(MainFrame mainFrame, int philosophenAnzahl) {
        this.mainFrame = mainFrame;
        this.philosophenAnzahl = philosophenAnzahl;
        staebchen = new Staebchen[this.philosophenAnzahl];
        for (int i = 0; i < this.philosophenAnzahl; i++) {
            staebchen[i] = new Staebchen();
        }
        philosophen = new Philosoph[this.philosophenAnzahl];
        for (int i = 0; i < this.philosophenAnzahl; i++) {
            philosophen[i] = new Philosoph(this);
            philosophen[i].start();
        }
        aufsicht = Aufsicht.getAufsicht(this);
    }

    @Override
    public synchronized void paint(Graphics g) {
        super.paint(g);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = 200;

        g.setColor(Color.BLACK);
        g.drawOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
        for (int i = 0; i < this.philosophenAnzahl; i++) {
            double winkel = 2 * Math.PI / this.philosophenAnzahl * i;
            double winkel2 = winkel - Math.PI / this.philosophenAnzahl;
            double winkel3 = 2 * Math.PI / this.philosophenAnzahl * (i - 0.9);
            double winkel4 = 2 * Math.PI / this.philosophenAnzahl * (i - 0.1);
            int x = (int) (centerX + (radius - 40) * Math.cos(winkel));
            int y = (int) (centerY + (radius - 40) * Math.sin(winkel));
            int x2 = (int) (centerX + (radius - 60) * Math.cos(winkel2));
            int y2 = (int) (centerY + (radius - 60) * Math.sin(winkel2));
            int x3 = (int) (centerX + (radius - 60) * Math.cos(winkel3));
            int y3 = (int) (centerY + (radius - 60) * Math.sin(winkel3));
            int x4 = (int) (centerX + (radius - 60) * Math.cos(winkel4));
            int y4 = (int) (centerY + (radius - 60) * Math.sin(winkel4));

            if (philosophen[i].getSitztGerade()) {
                g.setColor(Color.YELLOW);
                if (philosophen[i].isIsstGerade())
                    g.setColor(Color.GREEN);
            } else {
                g.setColor(Color.RED);
            }
            g.fillOval(x - 20, y - 20, 40, 40);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(i), x, y);
            staebchen[i].paint(g, x2, y2, x3, y3, x4, y4, winkel2, winkel3, winkel4);


        }
        double winkel = 2 * Math.PI / this.philosophenAnzahl * 0;
        double winkel2 = winkel - Math.PI / this.philosophenAnzahl;
        double winkel3 = 2 * Math.PI / this.philosophenAnzahl * (0 - 0.9);
        double winkel4 = 2 * Math.PI / this.philosophenAnzahl * (0 - 0.1);
        int x2 = (int) (centerX + (radius - 60) * Math.cos(winkel2));
        int y2 = (int) (centerY + (radius - 60) * Math.sin(winkel2));
        int x3 = (int) (centerX + (radius - 60) * Math.cos(winkel3));
        int y3 = (int) (centerY + (radius - 60) * Math.sin(winkel3));
        int x4 = (int) (centerX + (radius - 60) * Math.cos(winkel4));
        int y4 = (int) (centerY + (radius - 60) * Math.sin(winkel4));
        staebchen[0].paint(g, x2, y2, x3, y3, x4, y4, winkel2, winkel3, winkel4);

    }

    public synchronized void writeText(String text) {
        mainFrame.getTextArea().append(text);
    }


    public Staebchen getStaebchen(int index) {
        return staebchen[index];
    }

    public int getPhilosophenAnzahl() {
        return philosophenAnzahl;
    }


    public Aufsicht getAufsicht() {
        return aufsicht;
    }

    public Philosoph[] getPhilosophen() {
        return philosophen;
    }
}
