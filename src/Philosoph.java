
public class Philosoph extends Thread {

    private static int philosophCount;
    private boolean running = true;
    private boolean sitztGeradeText;
    private boolean sitztGeradePaint;
    private boolean isstGerade;
    private final int philosophId;
    private final Tisch tisch;
    private final Staebchen linkesStaebchen;
    private final Staebchen rechtesStaebchen;


    public Philosoph(Tisch tisch) {
        this.philosophId = philosophCount;
        this.tisch = tisch;
        philosophCount++;
        linkesStaebchen = tisch.getStaebchen(getPhilosophId());
        rechtesStaebchen = tisch.getStaebchen((getPhilosophId() + 1) % tisch.getPhilosophenAnzahl());

    }


    private void philosophieren() {
        if (sitztGeradeText) {
            tisch.writeText("Philosoph " + getPhilosophId() + " steht vom Tisch auf und philosophiert.\n");
            sitztGeradePaint = false;
            tisch.repaint();
        } else {
            tisch.writeText("Philosoph " + getPhilosophId() + " philosophiert.\n");
            sitztGeradeText = true;
        }
        tisch.getAufsicht().decrementAmTischCount();
        try {
            Thread.sleep(Math.round(2000 + (Math.random() * (4000 - 2000))));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    private void hungrigWerden() {
        try {
            synchronized (tisch.getAufsicht()) {
                tisch.writeText("Philosoph " + getPhilosophId() + " wird hungrig und möchte sich an den Tisch setzen.\n");
                while (tisch.getAufsicht().istTischVoll(this)) {
                    tisch.getAufsicht().wait();
                }
                tisch.getAufsicht().incrementAmTischCount();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        sitztGeradePaint = true;
        tisch.writeText("Philosoph " + getPhilosophId() + " setzt sich an den Tisch.\n");
        try {
            synchronized (linkesStaebchen) {
                while (linkesStaebchen.istAufgenommen()) {
                    linkesStaebchen.wait();
                }
                Thread.sleep(75);
                linkesStaebchen.aufnehmen();
                linkesStaebchen.setIstLinkesStaebchen();
                tisch.repaint();
            }

            tisch.writeText("Philosoph " + getPhilosophId() + " hat linkes Stäbchen aufgenommen.\n");
            Thread.sleep(Math.round(400 + (Math.random() * (600 - 400))));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        try {
            synchronized (rechtesStaebchen) {
                while (rechtesStaebchen.istAufgenommen()) {
                    rechtesStaebchen.wait();
                }
                Thread.sleep(75);
                rechtesStaebchen.aufnehmen();
                rechtesStaebchen.setIstRechtesStaebchen();
                tisch.repaint();
            }

            tisch.writeText("Philosoph " + getPhilosophId() + " hat rechtes Stäbchen aufgenommen.\n");
            Thread.sleep(Math.round(400 + (Math.random() * (600 - 400))));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

    }

    private void essen() {
        isstGerade = true;
        tisch.repaint();
        tisch.writeText("Philosoph " + getPhilosophId() + " isst.\n");
        try {
            Thread.sleep(Math.round(1500 + (Math.random() * (2500 - 1500))));
            synchronized (linkesStaebchen) {
                linkesStaebchen.ablegen();
                isstGerade = false;
                tisch.repaint();
                Thread.sleep(75);
                tisch.writeText("Philosoph " + getPhilosophId() + " hat linkes Stäbchen abgelegt.\n");
                linkesStaebchen.notifyAll();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        try {
            Thread.sleep(Math.round(800 + (Math.random() * (1200 - 800))));
            synchronized (rechtesStaebchen) {
                rechtesStaebchen.ablegen();
                tisch.repaint();
                Thread.sleep(75);
                tisch.writeText("Philosoph " + getPhilosophId() + " hat rechtes Stäbchen abgelegt.\n");
                rechtesStaebchen.notifyAll();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

        }
    }

    @Override
    public void run() {
        while (running) {
            philosophieren();
            hungrigWerden();
            essen();
        }
        sitztGeradePaint = false;
        tisch.repaint();
        tisch.writeText("Philosoph " + getPhilosophId() + " ist satt und geht nach Hause.\n");
        tisch.getAufsicht().decrementAmTischCount();
    }

    public int getPhilosophId() {
        return philosophId;
    }

    public synchronized boolean getSitztGerade() {
        return sitztGeradePaint;
    }

    public synchronized boolean isIsstGerade() {
        return isstGerade;
    }

    public void stopRunning() {
        running = false;
    }

}

