package org.reapersrage.reaperscbsmithy.jobs;

import org.reapersrage.reaperscbsmithy.resources.Constants;
import org.reapersrage.reaperscbsmithy.resources.Variables;
import org.reapersrage.reaperscbsmithy.script.ReapersCBSmithy;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MACGUI extends JFrame {
    private static final long serialVersionUID = -2916298284461536693L;
    private JPanel contentPane;
    private final ButtonGroup btnGrpSmithingLocations = new ButtonGroup();
    private final ButtonGroup btnGrpSmithingType = new ButtonGroup();


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MACGUI frame = new MACGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MACGUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 403, 219);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Reaper's Rage\u2122 Cannonball Smithy");
        lblNewLabel.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 17));
        lblNewLabel.setBounds(28, 11, 291, 20);
        contentPane.add(lblNewLabel);

        final JRadioButton rdbtnPortP = new JRadioButton("<html>Port Phastymas<p>\r\n(Amulet Reccomended)</html>");
        rdbtnPortP.setBounds(54, 125, 138, 23);
        contentPane.add(rdbtnPortP);
        rdbtnPortP.setToolTipText("");
        btnGrpSmithingLocations.add(rdbtnPortP);

        final JRadioButton rdbtnAlKharid = new JRadioButton("Al Kharid");
        rdbtnAlKharid.setBounds(50, 93, 109, 23);
        contentPane.add(rdbtnAlKharid);
        btnGrpSmithingLocations.add(rdbtnAlKharid);

        final JRadioButton rdbtnEdgeville = new JRadioButton("Edgeville");
        rdbtnEdgeville.setBounds(50, 67, 109, 23);
        contentPane.add(rdbtnEdgeville);
        rdbtnEdgeville.setSelected(true);
        btnGrpSmithingLocations.add(rdbtnEdgeville);

        JLabel lblSmithingLocations = new JLabel("Smithing Locations");
        lblSmithingLocations.setBounds(54, 53, 88, 14);
        contentPane.add(lblSmithingLocations);

        final JRadioButton rdbtnBars = new JRadioButton("Smith from bars");
        rdbtnBars.setBounds(203, 67, 109, 23);
        contentPane.add(rdbtnBars);
        rdbtnBars.setSelected(true);
        btnGrpSmithingType.add(rdbtnBars);

        final JRadioButton rdbtnOres = new JRadioButton("Smith from ores");
        rdbtnOres.setBounds(203, 93, 109, 23);
        contentPane.add(rdbtnOres);
        btnGrpSmithingType.add(rdbtnOres);

        JLabel lblSmithingType = new JLabel("Smithing Type");
        lblSmithingType.setBounds(203, 53, 67, 14);
        contentPane.add(lblSmithingType);

        JButton btnStart = new JButton("Start!");
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                /**
                 * Set Location
                 */
                if (rdbtnPortP.isSelected()) {
                    Variables.bank = Constants.BANK.PORTP;
                } else if (rdbtnEdgeville.isSelected()) {
                    Variables.bank = Constants.BANK.EVBANK;
                } else if (rdbtnAlKharid.isSelected()) {
                    Variables.bank = Constants.BANK.AKBANK;
                }

                /**
                 * Set how to smith
                 */
                if (rdbtnBars.isSelected()) {
                    Variables.Bools.isUsingBars = true;
                } else if (rdbtnOres.isSelected()) {
                    Variables.Bools.isUsingBars = false;
                }
                ReapersCBSmithy.setStartTime(System.currentTimeMillis());
                Variables.Bools.isGuiFinished = true;
                dispose();
            }
        });
        btnStart.setBounds(230, 125, 89, 23);
        contentPane.add(btnStart);
    }
}
