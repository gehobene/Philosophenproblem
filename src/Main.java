
public class Main {
    public static void main(String[] args) throws InterruptedException {
        MainFrame mainFrame = new MainFrame();
        Tisch tisch = mainFrame.getTisch();
        Philosoph[] philosophen = tisch.getPhilosophen();
        Thread.sleep(10000);
        for (Philosoph philosoph : philosophen) {
            philosoph.stopRunning();
            while (philosoph.isAlive()) {
            }
            Thread.sleep(Math.round(Math.random() * 10000));
        }

    }


}