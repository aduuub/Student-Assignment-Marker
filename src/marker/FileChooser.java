package marker;

import javax.swing.*;

/**
 * A basic file chooser window
 * @Author Adam
 */
public class FileChooser extends JDialog{

    private static JFileChooser chooser = new JFileChooser(".");

    private FileChooser(){
        // Cannot construct
    }

    /**
     * Open the JFileChooser with the title {@code title}
     * @param title
     * @return
     */
    public static String open(String title){
        chooser.setDialogTitle(title);
        String ans = open();
        chooser.setDialogTitle("");
        return ans;
    }


    /**
     * Open the JFileChooser with no title
     * @return
     */
    public static String open(){
        int returnVal =  chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION)
            return (chooser.getSelectedFile().getPath());
        else
            return null;
    }

}
