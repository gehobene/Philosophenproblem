import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends Frame {
    private Tisch tisch;

    private int philosophenAnzahl;

    private TextArea textArea;

    public MainFrame() {
        final int[] anzahl = new int[1];
        textArea = new TextArea();
        textArea.setPreferredSize(new Dimension(this.getWidth(), 500));
        textArea.setEditable(false);
        Dialog dialog = new Dialog(this, "Wie viele Philosophen?", true);
        dialog.setLocationRelativeTo(null);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                anzahl[0] = 5;
                dialog.setVisible(false);
            }
        });
        dialog.setLayout(new FlowLayout());
        dialog.add(new Label("Bitte eine Zahl zwischen 5 und 20 eingeben."));
        TextField t = new TextField();
        t.setPreferredSize(new Dimension(100, 25));
        dialog.add(t, FlowLayout.CENTER);
        Button b = new Button("OK");
        dialog.add(b);
        b.addActionListener(e -> {
            try {
                anzahl[0] = Integer.parseInt(t.getText());
                if (anzahl[0] < 5 || anzahl[0] > 20) {
                    throw new NumberFormatException();
                }
                dialog.setVisible(false);
            } catch (NumberFormatException ex) {
                dialog.setTitle("ERROR: Bitte eine Zahl zwischen 5 und 20 eingeben");
            }
        });
        dialog.pack();
        dialog.setVisible(true);
        philosophenAnzahl = anzahl[0];
        setSize(1014, 1024);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(textArea, BorderLayout.NORTH);
        tisch = new Tisch(this, philosophenAnzahl);
        add(tisch, BorderLayout.CENTER);
        setTitle("Philosophenproblem mit " + philosophenAnzahl + " Philosophen.");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);
    }

    public Tisch getTisch() {
        return tisch;
    }

    public TextArea getTextArea() {
        return textArea;
    }

}
