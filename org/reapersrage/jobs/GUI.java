package org.reapersrage.reaperscbsmithy.jobs;

import org.reapersrage.reaperscbsmithy.resources.Constants;
import org.reapersrage.reaperscbsmithy.resources.Variables;
import org.reapersrage.reaperscbsmithy.script.ReapersCBSmithy;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUI extends JFrame {
    public JPanel contentPane;

    private static final long serialVersionUID = -2916298284461536693L;
    private final ButtonGroup btnGrpSmithingLocations = new ButtonGroup();
    private final ButtonGroup btnGrpSmithingType = new ButtonGroup();

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI frame = new GUI();
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
    public GUI() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 364, 197);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("Reaper's Rage\u2122 Cannonball Smithy v1.06");
        lblNewLabel.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 17));
        lblNewLabel.setBounds(28, 11, 291, 20);
        contentPane.add(lblNewLabel);

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Smithing Locations", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setBounds(38, 42, 150, 98);
        contentPane.add(panel);
        panel.setLayout(null);

        final JRadioButton rdbtnEdgeville = new JRadioButton("Edgeville");
        rdbtnEdgeville.setSelected(true);
        rdbtnEdgeville.setBounds(6, 16, 109, 23);
        panel.add(rdbtnEdgeville);
        btnGrpSmithingLocations.add(rdbtnEdgeville);

        final JRadioButton rdbtnAlKharid = new JRadioButton("Al Kharid");
        rdbtnAlKharid.setBounds(6, 42, 109, 23);
        panel.add(rdbtnAlKharid);
        btnGrpSmithingLocations.add(rdbtnAlKharid);

        final JRadioButton rdbtnPortP = new JRadioButton("<html>Port Phastymas<p>\r\n(Amulet Reccomended)</html>");
        rdbtnPortP.setToolTipText("");
        rdbtnPortP.setBounds(6, 68, 138, 23);
        panel.add(rdbtnPortP);
        btnGrpSmithingLocations.add(rdbtnPortP);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Smithing Type", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.setBounds(198, 42, 121, 85);
        contentPane.add(panel_1);
        panel_1.setLayout(null);

        final JRadioButton rdbtnBars = new JRadioButton("Smith from bars");
        rdbtnBars.setSelected(true);
        rdbtnBars.setBounds(6, 16, 109, 23);
        panel_1.add(rdbtnBars);
        btnGrpSmithingType.add(rdbtnBars);

        final JRadioButton rdbtnOres = new JRadioButton("<html>Smith from ores<p>\r\nMight be glitchy");
        rdbtnOres.setBounds(6, 42, 109, 23);
        panel_1.add(rdbtnOres);
        btnGrpSmithingType.add(rdbtnOres);

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
        btnStart.setBounds(257, 135, 89, 23);
        contentPane.add(btnStart);
    }
}
