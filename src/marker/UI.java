package marker;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.text.*;


/**
 * The entry point of the program and displays a window for choosing the master and student jar as well as printing the
 * differences between the two out.
 *
 * @Author Adam Wareing
 */
public final class UI {

    private String masterFileName;
    private String studentFile;
    private Marker marker;
    private File directory;

    private String text;

    // UI
    private JEditorPane textPane;

    public UI() {
        buildAndDisplayGui();
    }


    /**
     * Run the app yo
     *
     * @throws IOException
     */
    private void run() throws IOException {
        // create marker and run
        this.marker = new Marker();

        // create marking directory
        this.directory = new File(marker.dir);

        // attempt to cleanup previous students data
        System.out.println("Deleting previous files:");
        marker.deleteFile(directory);

        //create new marking directory
        directory.mkdir();
    }

    /**
     * Re-run the program
     */
    private void rerun() {
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        marker.unzipMasterJar(masterFileName);
        String studentFile = FileChooser.open("Choose student jar");
        runComparison(studentFile);
    }


    /**
     * Builds the JFrame window
     */
    private void buildAndDisplayGui() {
        JFrame frame = new JFrame("Marking");
        buildContent(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(1000, 1000);
    }


    /**
     * Builds the content for the frame
     *
     * @param aFrame
     */
    private void buildContent(JFrame aFrame) {
        // Build panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create load original button
        JButton loadMasterButton = new JButton("Load Master");
        loadMasterButton.addActionListener(new LoadMasterListener());

        // Create load student button
        JButton loadStudentButton = new JButton("Load Student");
        loadStudentButton.addActionListener(new LoadStudentListener(aFrame));

        // Create button panel
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout());
        buttonPane.add(loadMasterButton);
        buttonPane.add(loadStudentButton);

        // Add button pane to panel and add panel to frame
        panel.add(buttonPane, BorderLayout.NORTH);
        aFrame.getContentPane().add(panel);

        // Create the text pane
        textPane = new JEditorPane();
        textPane.setText(text);
        textPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, Boolean.TRUE);
        textPane.setFont(new Font("COURIER", Font.PLAIN, 12));

        panel.add(new JScrollPane(textPane), BorderLayout.CENTER);
    }


    /**
     * Sets the text of the content pane
     *
     * @param text
     */
    private void setText(String text) {
        textPane.setText(text);
        Document document = textPane.getDocument();

        try {
            String find = "ADDITIONS";
            java.util.List<String> wordsToHighlight = Arrays.asList("ADDITIONS", "DELETIONS");
            for (int index = 0; index + find.length() < document.getLength(); index++) {
                String match = document.getText(index, find.length());
                if (wordsToHighlight.contains(match)) {
                    javax.swing.text.DefaultHighlighter.DefaultHighlightPainter highlightPainter =
                            new javax.swing.text.DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
                    textPane.getHighlighter().addHighlight(index, index + find.length(),
                            highlightPainter);
                }
            }
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Run the comparison between the master and student jar
     *
     * @param studentFile
     */
    private void runComparison(String studentFile) {
        marker.unzipJar(studentFile, marker.dir);
        // diffing files
        String diff = marker.diffFiles();
        //marker.cleanup();
        System.out.println("Detecting additional files...:");

        try {
            diff += "\n" + marker.findExtras(directory);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.text = diff;
        new WorkerThread().execute();
        System.out.println("Finished detecting additional files");
        System.out.println("====================");
        marker.getFormattedTextOfChangedFiles();
    }


    /**
     * When action is performed it shows a window to choose the target master jar
     */
    private final class LoadMasterListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent aEvent) {
            masterFileName = FileChooser.open("Choose master jar");
            marker.unzipMasterJar(masterFileName);
        }
    }


    /**
     * When action is performed it shows a window to choose the target student jar
     */
    private final class LoadStudentListener implements ActionListener {
        private JFrame fFrame;

        LoadStudentListener(JFrame aFrame) {
            fFrame = aFrame;
        }

        @Override
        public void actionPerformed(ActionEvent aEvent) {
            if (masterFileName == null) {
                JOptionPane.showMessageDialog(fFrame, "Please choose master file first");
            } else {
                if (studentFile != null) {
                    rerun();
                } else {
                    studentFile = FileChooser.open("Choose student jar");
                    runComparison(studentFile);
                }
            }

        }

    }


    /**
     * Sets the text in the background.
     * Used as the listeners cannot perform this task.
     */
    public class WorkerThread extends SwingWorker<Void, Void> {

        @Override
        public Void doInBackground() {
            try {
                textPane.getDocument().remove(textPane.getSelectionStart(), textPane.getSelectionEnd());
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
            setText(text);
            return null;
        }
    }


    /**
     * Entry point right here yo
     *
     * @param args
     * @throws IOException
     */
    public static void main(String args[]) throws IOException {
        new UI().run();
    }

}