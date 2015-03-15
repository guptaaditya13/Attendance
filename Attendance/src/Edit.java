/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author aadi
 */
import java.util.*;
import java.io.*;
import javax.swing.*;
public final class Edit extends javax.swing.JFrame {

    /**
     * Creates new form Edit
     */
    Sub Subject;
    Home home;
    
    public Edit(Sub sub, Home home)
    {
        this.Subject = sub;
        this.home = home;
        home.setVisible(false);
        initComponents();
        initialize();
        markChecks();
    }
    public Edit() {
        initComponents();
    }
    void initialize()
    {
        this.setTitle(Subject.sub + " - Edit");
        this.Name.setText(Subject.sub);
        this.Percentage.setText(Subject.getPercent());
        this.Attend.setText(Subject.getAttend() + "");
        this.Total.setText(Subject.getTotal() + "");
    }

    void markChecks()
    {
        CBSun.setSelected(false);
        CBMon.setSelected(false);
        CBTue.setSelected(false);
        CBWed.setSelected(false);
        CBThu.setSelected(false);
        CBFri.setSelected(false);
        CBSat.setSelected(false);
        for(int i : Subject.day)
        {
            switch(i)
            {
                case 0:
                    CBSun.setSelected(true);break;
                case 1:
                    CBMon.setSelected(true);break;
                case 2:
                    CBTue.setSelected(true);break;
                case 3:
                    CBWed.setSelected(true);break;
                case 4:
                    CBThu.setSelected(true);break;
                case 5:
                    CBFri.setSelected(true);break;
                case 6:
                    CBSat.setSelected(true);break;
            }
        }
    }
    Integer[] getMarked()
    {
        ArrayList<Integer> marks = new ArrayList<>();
        
        if(CBSun.isSelected())
            marks.add(0);
        if(CBMon.isSelected())
            marks.add(1);
        if(CBTue.isSelected())
            marks.add(2);
        if(CBWed.isSelected())
            marks.add(3);
        if(CBThu.isSelected())
            marks.add(4);
        if(CBFri.isSelected())
            marks.add(5);
        if(CBSat.isSelected())
            marks.add(6);
        
        Integer mark[] = new Integer[marks.size()];
        mark = marks.toArray(mark);
        return mark;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        CBMon = new javax.swing.JCheckBox();
        CBTue = new javax.swing.JCheckBox();
        CBWed = new javax.swing.JCheckBox();
        CBThu = new javax.swing.JCheckBox();
        CBFri = new javax.swing.JCheckBox();
        CBSat = new javax.swing.JCheckBox();
        Name = new javax.swing.JTextField();
        Attend = new javax.swing.JTextField();
        Total = new javax.swing.JTextField();
        Percentage = new javax.swing.JTextField();
        Save = new javax.swing.JButton();
        Revert = new javax.swing.JButton();
        Delete = new javax.swing.JButton();
        CBSun = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jLabel1.setText("Name:");

        jLabel2.setText("Attended:");

        jLabel3.setText("Total:");

        jLabel4.setText("Percentage:");

        jLabel5.setText("Days :");

        CBMon.setText("Monday");

        CBTue.setText("Tuesday");

        CBWed.setText("Wednesday");

        CBThu.setText("Thursday");

        CBFri.setText("Friday");

        CBSat.setText("Saturday");

        Name.setFocusable(false);

        Percentage.setFocusable(false);

        Save.setText("Save");
        Save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveActionPerformed(evt);
            }
        });

        Revert.setText("Revert");
        Revert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RevertActionPerformed(evt);
            }
        });

        Delete.setText("Delete");
        Delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteActionPerformed(evt);
            }
        });

        CBSun.setText("Sunday");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Percentage)
                            .addComponent(Total)
                            .addComponent(Attend)
                            .addComponent(Name))
                        .addGap(60, 60, 60)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Revert, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Delete, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Save, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CBWed)
                            .addComponent(CBTue)
                            .addComponent(CBMon))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(CBThu)
                                    .addComponent(CBSat))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(CBFri)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(CBSun)))))
                .addGap(46, 46, 46))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(Save)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(Attend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(Revert)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(Total, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(Percentage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(Delete)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CBMon, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(CBThu, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(CBTue)
                        .addComponent(CBFri)
                        .addComponent(CBSun)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CBWed)
                    .addComponent(CBSat))
                .addGap(33, 33, 33))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        home.makeVisible();
    }//GEN-LAST:event_formWindowClosed

    private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
        
        int attend = Subject.getAttend();
        int total = Subject.getTotal();
        try{
            attend = Integer.parseInt(Attend.getText());
             total = Integer.parseInt(Total.getText());
        }
        catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, "Error in datatype of datafields", "Error", 0);
            Attend.setText(Subject.getAttend() + "");
            Total.setText(Subject.getTotal() + "");
            return;
        }
        
        if(attend >= 0 && total >= 0 && attend <= total)
        {
            Subject.setData(attend, total);
            Percentage.setText(Subject.getPercent());
            Integer m[] = getMarked();
            Subject.day.clear();
            for(int x : m)
            {
                Subject.day.add(x);
            }
            Subject.selfEval();
            home.saveData();
            JOptionPane.showMessageDialog(this, "Changes have been made!!", "Done!!", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Changes cannot be saved! Recheck data", "Data Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_SaveActionPerformed

    private void RevertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RevertActionPerformed
        this.Name.setText(Subject.sub);
        this.Percentage.setText(Subject.getPercent());
        this.Attend.setText(Subject.getAttend() + "");
        this.Total.setText(Subject.getTotal() + "");
        markChecks();
    }//GEN-LAST:event_RevertActionPerformed

    private void DeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteActionPerformed
        
        Subject.day.clear();
        Subject.selfEval();
        JOptionPane.showMessageDialog(this, "Changes have been made!!", "Done!!", JOptionPane.INFORMATION_MESSAGE);
        home.saveData();
        this.dispose();
        
    }//GEN-LAST:event_DeleteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Edit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Edit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Edit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Edit.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Edit().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Attend;
    private javax.swing.JCheckBox CBFri;
    private javax.swing.JCheckBox CBMon;
    private javax.swing.JCheckBox CBSat;
    private javax.swing.JCheckBox CBSun;
    private javax.swing.JCheckBox CBThu;
    private javax.swing.JCheckBox CBTue;
    private javax.swing.JCheckBox CBWed;
    private javax.swing.JButton Delete;
    private javax.swing.JTextField Name;
    private javax.swing.JTextField Percentage;
    private javax.swing.JButton Revert;
    private javax.swing.JButton Save;
    private javax.swing.JTextField Total;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}