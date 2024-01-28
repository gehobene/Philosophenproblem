
public class Aufsicht {
    private static Aufsicht aufsicht = null;
    private Tisch tisch;
    private int amTischCount;

    private Aufsicht(Tisch tisch) {
        this.tisch = tisch;

    }

    protected static Aufsicht getAufsicht(Tisch tisch) {
        if (aufsicht == null || aufsicht.tisch != tisch) {
            aufsicht = new Aufsicht(tisch);
        }
        return aufsicht;
    }


    public synchronized int getAmTischCount() {
        return amTischCount;
    }

    public synchronized boolean istTischVoll(Philosoph philosoph) {
        if (getAmTischCount() == tisch.getPhilosophenAnzahl() - 1) {
            tisch.writeText("Die Aufsicht verbietet Philosoph " + philosoph.getPhilosophId() + " sich an den Tisch zu setzen da dieser voll ist\n");
            return true;
        } else return false;
    }

    public synchronized void incrementAmTischCount() {
        amTischCount++;
    }

    public synchronized void decrementAmTischCount() {
        if (!(amTischCount == 0)) {
            amTischCount--;
            tisch.writeText("Aufsicht: es ist ein Platz frei geworden es gibt jetzt " + (tisch.getPhilosophenAnzahl() - 1 - amTischCount) + " freie Plätze.\n");
        }
        notifyAll();
    }

}
