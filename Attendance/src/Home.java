/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author aadi
 */
import java.awt.Color;
import java.util.*;
import javax.swing.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.table.*;


class HB extends Thread
{
    Home home;
    int index[];
    String value[][];
    boolean stop;
    HB(Home home)
    {
        this.home = home;
        index = home.getBM();
        value = home.RminH(index);
        home.SminH(value, index);
        stop = true;
    }
    
    public void run()
    {
        while(stop)
        {
            home.RminH(index);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                stop = false;
                home.setToggleFalse();
                return;
            }
            home.SminH(value, index);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                stop = false;
                home.setToggleFalse();
                return;
            }
        }
    }
}
final class Sub
{
    final String sub;
    ArrayList<Integer> day = new ArrayList<>();
    private int attend;
    private int total;
    
    Sub(String sub, int ... x)
    {
        this.sub = sub;
        for(int p : x)
        {
            day.add(p);
        }
        this.attend = 0;
        this.total = 0;
        updateDetails();
    }
    
    Sub(String ... x)
    {
        sub = x[0];

        attend = Integer.parseInt(x[1]);

        total = Integer.parseInt(x[2]);

        for(int i = 3; i < x.length ; i++)
        {
            day.add(Integer.parseInt(x[i]));

        }

        updateDetails();
    }
    
    void selfEval()
    {
        updateDetails();
        if(day.size() == 0)
        {
            Home.subject.remove(this);
        }
    }
    int getAttend()
    {
        return attend;
    }
    int getTotal()
    {
        return total;
    }
    void incPre ()
    {
        attend = attend + 1;
        total = total + 1;
        
    }
    void incAbs()
    {
        total = total + 1;
    }
    
    void incA()
    {
        if(attend< total)
        {
            attend = attend +1;
        }
       
    }
    void decA()
    {
        if(attend > 0)
        {
            attend = attend - 1;
        }
       
    }
    
    void incT()
    {
        total = total + 1;
    }
    
    void decT()
    {
        if(total > attend)
        {
            total = total - 1;
        }
    }
    void setData(int attend, int total)
    {
        this.attend = attend;
        this.total = total;
    }
    
    String getPercent()
    {
        String m = "NA";
        if(total != 0 )
        {
            double x = (attend * 1.0)/(total * 1.0) * 100;
            
            m = (int)x + ".0";
        }
        System.out.println(m);
        return m;
        
    }
    int getPercent(int p)
    {
        int m = -1;
        if(total != 0 )
        {
            double x = (attend * 1.0)/(total * 1.0) * 100;
            
            m = (int)x ;
        }
        System.out.println(m);
        return m;
        
    }
    String[] getData()
    {
//        int del = 0;
        String attend =  "" + this.attend;
        String total =  "" + this.total;
        String percent = getPercent();
        return new String[]{sub,attend,total,percent};
    }
    
    String saveData()
    {
        String m = sub + " " + attend + " " + total ;
        for(int p : day)
        {
            m = m + " " + p;
        }
        return m;
    }
    
    void updateDetails()
    {
        deleteDetails();
        for(int m : day)
        {
            Home.schd[m].add(this);
        }
    }
    
    void deleteDetails()
    {
        for(int m = 0; m < 7; m++)
        {
            if(Home.schd[m].contains(this))
            {
                Home.schd[m].remove(this);
            }
        }
    }
    

}
public final class Home extends javax.swing.JFrame {

    /**
     * Creates new form Home
     */
    DefaultTableModel model;
    Scanner sc = null;
    FileInputStream fis = null;
    FileOutputStream fos = null;
    PrintWriter pw = null;
    HB hb;
    Calendar cal;
//    DefaultListModel model;
    static ArrayList schd[] = new ArrayList[7];;
    ArrayList<Sub> day0;
    ArrayList<Sub> day1;
    ArrayList<Sub> day2;
    ArrayList<Sub> day3;
    ArrayList<Sub> day4;
    ArrayList<Sub> day5;
    ArrayList<Sub> day6;
    
    static ArrayList<Sub> subject = new ArrayList<>();;
    int edits;
    int minimum = 70;
    
    private Home() {
    	day0 = new ArrayList<>();
    	day1 = new ArrayList<>();
    	day2 = new ArrayList<>();
    	day3 = new ArrayList<>();
    	day4 = new ArrayList<>();
    	day5 = new ArrayList<>();
    	day6 = new ArrayList<>();
        schd[0] = day0;
        schd[1] = day1;
        schd[2] = day2;
        schd[3] = day3;
        schd[4] = day4;
        schd[5] =day5;
        schd[6] = day6; 
//        model = new DefaultListModel();

        initComponents();
        
        model = (DefaultTableModel)tDisplayTable.getModel();
//        taDisplayTable.setModel(model);
        generateAL();
        initialize();
        TableColumn column0 = tDisplayTable.getColumnModel().getColumn(0);
        column0.setPreferredWidth(tDisplayTable.getWidth()/20);
        TableColumn column1 = tDisplayTable.getColumnModel().getColumn(1);
        column1.setPreferredWidth(tDisplayTable.getWidth()/3);
        for(int i = 0; i < 5; i++)
        {
            TableColumn cx = tDisplayTable.getColumnModel().getColumn(i);
            cx.setResizable(false);
            
        }
    }
    void initialize()
    {
        cal = Calendar.getInstance();
        int m = cal.get(Calendar.DAY_OF_WEEK) - 1;
        
        switch(m)
        {
            case 0: Sun.doClick();break;
            case 1: Mon.doClick();break;
            case 2: Tue.doClick();break;
            case 3: Wed.doClick();break;
            case 4: Thu.doClick();break;
            case 5: Fri.doClick();break;
            case 6: Sat.doClick();break;
        }
    }
    String[] getData()
    {
     
        try{
            
            fis = new FileInputStream("ProgramData!!DoNotModify.txt");
        } catch (FileNotFoundException ex) {
            
            int m = JOptionPane.showConfirmDialog(null, "Program File Not Found : \nProgramData!!DoNotModify.txt\nCreate new File? All data will be lost!", "Data Lost", JOptionPane.YES_NO_OPTION, 0);
            if(m == 1)
            {
                JOptionPane.showMessageDialog(null, "Try to Find your Data File then", "Exiting", JOptionPane.INFORMATION_MESSAGE);
                System.exit(1);
            }
            
            if(m == 0)
            {
                try{
                    fos = new FileOutputStream("ProgramData!!DoNotModify.txt");
                    fos.close();
                    fis = new FileInputStream("ProgramData!!DoNotModify.txt");
                }
                catch (FileNotFoundException x)
                {
                    JOptionPane.showMessageDialog(null, "Program was unable to create new file!\nCreate it manually!!", "Exiting", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(2);
                }
                catch (IOException x)
                {
                    JOptionPane.showMessageDialog(null, "IOException occured after file creation!\nTry Later", "Exiting", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(3);
                }
            }
        }
                sc = new Scanner(fis);
                sc.useDelimiter("[\n]");
                ArrayList<String> data = new ArrayList<>();
                while(sc.hasNext())
                {
                    data.add(sc.nextLine());
                }
                System.out.println("Done reading");
                String dat[] = new String[data.size()];
                dat = data.toArray(dat);
                for(String p : dat)
                {
                    System.out.println(p);
                }
                try
                {
                   fis.close(); 
                }
                catch(IOException e)
                {
                    
                }
                
                return dat;
    }
    void generateAL()
    {
        String data[] = getData();
        for(String x : data)
        {
            String details[] = x.split("\\s");
            subject.add(new Sub(details));
        }
    }

    String[][] RminH(int ...x)
    {
    
        String xx[][] = new String[x.length][5];
        for(int i = 0; i < x.length ; i++)
        {
            xx[i][0] = (String)model.getValueAt(x[i], 0);
            xx[i][1] = (String)model.getValueAt(x[i], 1);
            xx[i][2] = (String)model.getValueAt(x[i], 2);
            xx[i][3] = (String)model.getValueAt(x[i], 3);
            xx[i][4] = (String)model.getValueAt(x[i], 4);
                    
        }
        
        for(int m : x)
        {
            model.setValueAt("", m, 0);
            model.setValueAt("", m, 1);
            model.setValueAt("", m, 2);
            model.setValueAt("", m, 3);
            model.setValueAt("", m, 4);
        }
            
        
        return xx;
    }
    void SminH(String[][] xx,int ...x)
    {
        //DefaultTableModel model = (DefaultTableModel)tDisplayTable.getModel();
        for(int i = 0; i < x.length ; i++)
        {
            model.setValueAt(xx[i][0], x[i], 0);
            model.setValueAt(xx[i][1], x[i], 1);
            model.setValueAt(xx[i][2], x[i], 2);
            model.setValueAt(xx[i][3], x[i], 3);
            model.setValueAt(xx[i][4], x[i], 4);
        }
    }
    int[] getBM()
    {
        int m = getPressed();
        ArrayList<Integer> below = new ArrayList<>();
        for(int i = 0; i < schd[m].size(); i++)
        {
            
            Sub y = (Sub)schd[m].get(i);
            if(y.getPercent(5) == -1)
                continue;
            else if(y.getPercent(5) < minimum)
                below.add(i);
                    
        }
        if(below.isEmpty())
            return null;
        else
        {
            Integer[] mm = new Integer[below.size()];
            mm = below.toArray(mm);
            int mmm[] = new int[mm.length];
            for(int i =0; i < mm.length ; i++)
                mmm[i] = mm[i];

            return mmm;
        }
    }
    void makeVisible()
    {
        edits = edits - 1;
        if(edits == 0)
        {
            this.setVisible(true);
            this.DoPressing(getPressed());
        }
    }
    void saveData()
    {
        File oldName = null ;
        System.out.println("Save Data Called");
//        File f;
        String data[] = new String[subject.size()];
        for(int i = 0; i < data.length ; i++)
        {
            data[i] = subject.get(i).saveData();
        }
        try{
            oldName = new File("xyz.txt");
            pw = new PrintWriter(new FileWriter("xyz.txt"));
//            f = new File("ProgramData!!DoNotModify.txt");
//            f.delete();
            System.out.println("Deletion of file called");
        }
        catch(FileNotFoundException e)
        {
            JOptionPane.showMessageDialog(this, "An Internal Error Occured in saving data!", "Trying to recover", JOptionPane.INFORMATION_MESSAGE);
            saveData();
        }
        catch(IOException e)
        {
            JOptionPane.showMessageDialog(this, "An Internal Error Occured in saving data!", "Trying to recover", JOptionPane.INFORMATION_MESSAGE);
            saveData();
        }
        
        for(int i = 0; i < data.length; i++)
        {
            pw.println(data[i]);
            System.out.println(data[i]);
        }
        pw.flush();
        
        
        
        File newName = new File("ProgramData!!DoNotModify.txt");
        oldName.renameTo(newName);
        newName.setReadOnly();
        System.out.println("xyz.txt renamed");
        
            
            pw.close();    
        
        
        
    }
    void DoPressing(int m)
    {
        switch(m)
        {
            case 0:
                Sun.doClick();
                Sun.doClick();
                break;
            case 1:
                Mon.doClick();
                Mon.doClick();
                break;
            case 2:
                Tue.doClick();
                Tue.doClick();
                break;
            case 3:
                Wed.doClick();
                Wed.doClick();
                break;
            case 4:
                Thu.doClick();
                Thu.doClick();
                break;
            case 5:
                Fri.doClick();
                Fri.doClick();
                break;
            case 6: 
                Sat.doClick();
                Sat.doClick();
                break;
        }
    }
    int getPressed()
    {
        int m = 0;
        if(Mon.isSelected())
            m = 1;
        if(Tue.isSelected())
            m = 2;
        if(Wed.isSelected())
            m = 3;
        if(Fri.isSelected())
            m = 5;
        if(Sat.isSelected())
            m = 6;
        if(Sun.isSelected())
            m = 0;
        if(Thu.isSelected())
            m = 4;
        return m;
    }
    public void finalize()
    {
        saveData();
    }
    void setToggleFalse()
    {
        tbHBMinimum.setSelected(false);
        System.out.println("Toggle set to false");
    }
    String toAttend(Sub x)
    {
        String m = "";
        int newatt = (int)(minimum * 0.01) * x.getTotal();
        int l = cal.get(Calendar.DAY_OF_WEEK)-1;
        int day[] = new int[7];
        for(int i = l; x.getAttend() < newatt; i++)
        {
            if(i==7)
                i = 0;
            if(x.day.contains(i))
            {
                day[i] = day[i] + 1;
            }
        }
        int max = day[0];
        for(int i = 0; i < 7; i++)
        {
            if(day[i] > max)
                max = day[i];
        }
        
        m = "To achieve " + minimum + "% attendance u need to attend classes for " + max +" weeks.\n ";
        if(day[0] != 0)
            m = m + day[0] + " Sundays. ";
        if(day[1] != 0)
            m = m + day[1] + " Mondays. ";
        if(day[2] != 0)
            m = m + day[2] + " Tuesdays. ";
        if(day[3] != 0)
            m = m + day[3] + " Wednesdays. ";
        if(day[4] != 0)
            m = m + day[4] + " Thursdays. ";
        if(day[5] != 0)
            m = m + day[5] + " Fridays. ";
        if(day[6] != 0)
            m = m + day[6] + " Saturdays. ";
        
        return m;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Mon = new javax.swing.JToggleButton();
        Sun = new javax.swing.JToggleButton();
        Sat = new javax.swing.JToggleButton();
        Fri = new javax.swing.JToggleButton();
        Thu = new javax.swing.JToggleButton();
        Wed = new javax.swing.JToggleButton();
        Tue = new javax.swing.JToggleButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        tbHBMinimum = new javax.swing.JToggleButton();
        cbMarkingSelection = new javax.swing.JComboBox();
        jButton7 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tDisplayTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Attendance");
        setBounds(new java.awt.Rectangle(300, 100, 0, 0));
        setResizable(false);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        Mon.setBackground(new java.awt.Color(48, 146, 40));
        Mon.setText("Monday");
        Mon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MonActionPerformed(evt);
            }
        });

        Sun.setBackground(new java.awt.Color(48, 146, 40));
        Sun.setText("Sunday");
        Sun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SunActionPerformed(evt);
            }
        });

        Sat.setBackground(new java.awt.Color(48, 146, 40));
        Sat.setText("Saturday");
        Sat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SatActionPerformed(evt);
            }
        });

        Fri.setBackground(new java.awt.Color(48, 146, 40));
        Fri.setText("Friday");
        Fri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FriActionPerformed(evt);
            }
        });

        Thu.setBackground(new java.awt.Color(48, 146, 40));
        Thu.setText("Thursday");
        Thu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ThuActionPerformed(evt);
            }
        });

        Wed.setBackground(new java.awt.Color(48, 146, 40));
        Wed.setText("Wednesday");
        Wed.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                WedActionPerformed(evt);
            }
        });

        Tue.setBackground(new java.awt.Color(48, 146, 40));
        Tue.setText("Tuesday");
        Tue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TueActionPerformed(evt);
            }
        });

        jButton1.setText("Edit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("+");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextField1.setText("S.No.   | Subject Name                                                  Attended       Total           Percentage");
        jTextField1.setFocusable(false);
        jTextField1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jTextField1MouseMoved(evt);
            }
        });
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton3.setText("+");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("+");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("-");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("-");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel1.setText("Mark :");

        jButton11.setText("Add Subject");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setText("Set Minimum");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        tbHBMinimum.setText("Highlight below minimum");
        tbHBMinimum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbHBMinimumActionPerformed(evt);
            }
        });

        cbMarkingSelection.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Present", "Absent", "Present(All)", "Absent(All)" }));

        jButton7.setText("Mark");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        tDisplayTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.No.", "Subject Name", "Attended", "Total", "Percentage"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tDisplayTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbMarkingSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton7))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(92, 92, 92)
                                .addComponent(jButton2)
                                .addGap(37, 37, 37)
                                .addComponent(jButton1)))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton6)
                                .addGap(29, 29, 29)
                                .addComponent(jButton4))
                            .addComponent(tbHBMinimum, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addComponent(jButton5))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jButton11)
                        .addGap(18, 18, 18)
                        .addComponent(jButton12)))
                .addContainerGap(160, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Mon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Tue)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Wed)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Thu)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Fri)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Sat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Sun)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Mon)
                    .addComponent(Tue)
                    .addComponent(Wed)
                    .addComponent(Thu)
                    .addComponent(Fri)
                    .addComponent(Sat)
                    .addComponent(Sun))
                .addGap(18, 18, 18)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(cbMarkingSelection, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton3)
                            .addComponent(jButton6)
                            .addComponent(jButton4)
                            .addComponent(jButton5))
                        .addGap(18, 18, 18)
                        .addComponent(tbHBMinimum)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton11)
                    .addComponent(jButton12))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MonActionPerformed
        //DefaultTableModel model = (DefaultTableModel)tDisplayTable.getModel();
        if(Mon.isSelected())
        {
            
//            if(Mon.isSelected())
//            {
//                Mon.setBackground(Color.cyan);
//            }
            if(Tue.isSelected())
            {
                Tue.setBackground(Color.cyan);
                
            }
            if(Wed.isSelected())
            {
                Wed.setBackground(Color.cyan);
                
            }
            if(Thu.isSelected())
            {
                Thu.setBackground(Color.cyan);
            }
            if(Fri.isSelected())
            {
                Fri.setBackground(Color.cyan);
            }
            if(Sat.isSelected())
            {
                Sat.setBackground(Color.cyan);
            }
            if(Sun.isSelected())
            {
                Sun.setBackground(Color.cyan);
            }
            
            Tue.setSelected(false);
            Wed.setSelected(false);
            Thu.setSelected(false);
            Fri.setSelected(false);
            Sat.setSelected(false);
            Sun.setSelected(false);
            
            for(;tDisplayTable.getRowCount() != 0;)
            {
                model.removeRow(0);
            }
            Mon.setBackground(Color.yellow);
            int i = 1;
            for(Sub x : day1)
            {
                String[] xx = x.getData();
                
                    model.addRow(new String[]{i+"",xx[0],xx[1],xx[2],xx[3]});
                    System.out.println("added : = *******" + i+"" + xx[0] + xx[1] + xx[2] + xx[3]);
                
            }
        }
        else{
            Mon.setBackground(Color.cyan);
            for(;tDisplayTable.getRowCount() != 0;)
            {
                model.removeRow(0);
            }
        }
    }//GEN-LAST:event_MonActionPerformed

    private void SunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SunActionPerformed
        //DefaultTableModel model = (DefaultTableModel)tDisplayTable.getModel();
        if(Sun.isSelected())
        {
            if(Mon.isSelected())
            {
                Mon.setBackground(Color.cyan);
            }
            if(Tue.isSelected())
            {
                Tue.setBackground(Color.cyan);
            }
            if(Wed.isSelected())
            {
                Wed.setBackground(Color.cyan);
            }
            if(Thu.isSelected())
            {
                Thu.setBackground(Color.cyan);
            }
            if(Fri.isSelected())
            {
                Fri.setBackground(Color.cyan);
            }
            if(Sat.isSelected())
            {
                Sat.setBackground(Color.cyan);
            }
//            if(Sun.isSelected())
//            {
//                Sun.setBackground(Color.cyan);
//            }
            Tue.setSelected(false);
            Wed.setSelected(false);
            Thu.setSelected(false);
            Fri.setSelected(false);
            Sat.setSelected(false);
            Mon.setSelected(false);
            for(;tDisplayTable.getRowCount() != 0;)
            {
                model.removeRow(0);
            }
            Sun.setBackground(Color.yellow);
            int i = 1;
            for(Sub x : day0)
            {
                String[] xx = x.getData();
                
                    model.addRow(new String[]{i+"",xx[0],xx[1],xx[2],xx[3]});
                    System.out.println("added : = *******" + i+"" + xx[0] + xx[1] + xx[2] + xx[3]);
                    i=i+1;
            }
        }
        else{
            Sun.setBackground(Color.cyan);
            for(;tDisplayTable.getRowCount() != 0;)
            {
                model.removeRow(0);
            }
        }
    }//GEN-LAST:event_SunActionPerformed

    private void SatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SatActionPerformed
        //DefaultTableModel model = (DefaultTableModel)tDisplayTable.getModel();
        if(Sat.isSelected())
        {
            if(Mon.isSelected())
            {
                Mon.setBackground(Color.cyan);
            }
            if(Tue.isSelected())
            {
                Tue.setBackground(Color.cyan);
            }
            if(Wed.isSelected())
            {
                Wed.setBackground(Color.cyan);
            }
            if(Thu.isSelected())
            {
                Thu.setBackground(Color.cyan);
            }
            if(Fri.isSelected())
            {
                Fri.setBackground(Color.cyan);
            }
//            if(Sat.isSelected())
//            {
//                Sat.setBackground(Color.cyan);
//            }
            if(Sun.isSelected())
            {
                Sun.setBackground(Color.cyan);
            }
            Tue.setSelected(false);
            Wed.setSelected(false);
            Thu.setSelected(false);
            Fri.setSelected(false);
            Sun.setSelected(false);
            Mon.setSelected(false);
            for(;tDisplayTable.getRowCount() != 0;)
            {
                model.removeRow(0);
            }
            Sat.setBackground(Color.yellow);
            int i = 1;
            for(Sub x : day6)
            {
                String[] xx = x.getData();
                
                    model.addRow(new String[]{i+"",xx[0],xx[1],xx[2],xx[3]});
                    System.out.println("added : = *******" + i+"" + xx[0] + xx[1] + xx[2] + xx[3]);
                i=i+1;
            }
        }
        else{
            Sat.setBackground(Color.cyan);
            for(;tDisplayTable.getRowCount() != 0;)
            {
                model.removeRow(0);
            }
        }
    }//GEN-LAST:event_SatActionPerformed

    private void FriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FriActionPerformed
        //DefaultTableModel model = (DefaultTableModel)tDisplayTable.getModel();
        if(Fri.isSelected())
        {
            if(Mon.isSelected())
            {
                Mon.setBackground(Color.cyan);
            }
            if(Tue.isSelected())
            {
                Tue.setBackground(Color.cyan);
            }
            if(Wed.isSelected())
            {
                Wed.setBackground(Color.cyan);
            }
            if(Thu.isSelected())
            {
                Thu.setBackground(Color.cyan);
            }
//            if(Fri.isSelected())
//            {
//                Fri.setBackground(Color.cyan);
//            }
            if(Sat.isSelected())
            {
                Sat.setBackground(Color.cyan);
            }
            if(Sun.isSelected())
            {
                Sun.setBackground(Color.cyan);
            }
            Tue.setSelected(false);
            Wed.setSelected(false);
            Thu.setSelected(false);
            Sun.setSelected(false);
            Sat.setSelected(false);
            Mon.setSelected(false);
            for(;tDisplayTable.getRowCount() != 0;)
            {
                model.removeRow(0);
            }
            Fri.setBackground(Color.yellow);
            int i = 1;
            for(Sub x : day5)
            {
                String[] xx = x.getData();
                
                    model.addRow(new String[]{i+"",xx[0],xx[1],xx[2],xx[3]});
                    System.out.println("added : = *******" + i+"" + xx[0] + xx[1] + xx[2] + xx[3]);
                i=i+1;
            }
        }
        else{
            Fri.setBackground(Color.cyan);
            for(;tDisplayTable.getRowCount() != 0;)
            {
                model.removeRow(0);
            }
        }
    }//GEN-LAST:event_FriActionPerformed

    private void ThuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ThuActionPerformed
        //DefaultTableModel model = (DefaultTableModel)tDisplayTable.getModel();
        if(Thu.isSelected())
        {
            if(Mon.isSelected())
            {
                Mon.setBackground(Color.cyan);
            }
            if(Tue.isSelected())
            {
                Tue.setBackground(Color.cyan);
            }
            if(Wed.isSelected())
            {
                Wed.setBackground(Color.cyan);
            }
//            if(Thu.isSelected())
//            {
//                Thu.setBackground(Color.cyan);
//            }
            if(Fri.isSelected())
            {
                Fri.setBackground(Color.cyan);
            }
            if(Sat.isSelected())
            {
                Sat.setBackground(Color.cyan);
            }
            if(Sun.isSelected())
            {
                Sun.setBackground(Color.cyan);
            }
            Tue.setSelected(false);
            Wed.setSelected(false);
            Fri.setSelected(false);
            Sun.setSelected(false);
            Sat.setSelected(false);
            Mon.setSelected(false);
            for(;tDisplayTable.getRowCount() != 0;)
            {
                model.removeRow(0);
            }
            Thu.setBackground(Color.yellow);
            int i = 1;
            for(Sub x : day4)
            {
                String[] xx = x.getData();
                
                    model.addRow(new String[]{i+"",xx[0],xx[1],xx[2],xx[3]});
                    System.out.println("added : = *******" + i+"" + xx[0] + xx[1] + xx[2] + xx[3]);
                i=i+1;
            }
        }
        else{
            Thu.setBackground(Color.cyan);
            for(;tDisplayTable.getRowCount() != 0;)
            {
                model.removeRow(0);
            }
        }
    }//GEN-LAST:event_ThuActionPerformed

    private void WedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_WedActionPerformed
        //DefaultTableModel model = (DefaultTableModel)tDisplayTable.getModel();
        if(Wed.isSelected())
        {
            if(Mon.isSelected())
            {
                Mon.setBackground(Color.cyan);
            }
            if(Tue.isSelected())
            {
                Tue.setBackground(Color.cyan);
            }
//            if(Wed.isSelected())
//            {
//                Wed.setBackground(Color.cyan);
//            }
            if(Thu.isSelected())
            {
                Thu.setBackground(Color.cyan);
            }
            if(Fri.isSelected())
            {
                Fri.setBackground(Color.cyan);
            }
            if(Sat.isSelected())
            {
                Sat.setBackground(Color.cyan);
            }
            if(Sun.isSelected())
            {
                Sun.setBackground(Color.cyan);
            }
            Tue.setSelected(false);
            Thu.setSelected(false);
            Fri.setSelected(false);
            Sun.setSelected(false);
            Sat.setSelected(false);
            Mon.setSelected(false);
            for(;tDisplayTable.getRowCount() != 0;)
            {
                model.removeRow(0);
            }
            Wed.setBackground(Color.yellow);
            int i = 1;
            for(Sub x : day3)
            {
                String[] xx = x.getData();
                
                    model.addRow(new String[]{i+"",xx[0],xx[1],xx[2],xx[3]});
                    System.out.println("added : = *******" + i+"" + xx[0] + xx[1] + xx[2] + xx[3]);
                i=i+1;
            }
        }
        else{
            Wed.setBackground(Color.cyan);
            for(;tDisplayTable.getRowCount() != 0;)
            {
                model.removeRow(0);
            }
        }
    }//GEN-LAST:event_WedActionPerformed

    private void TueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TueActionPerformed
        //DefaultTableModel model = (DefaultTableModel)tDisplayTable.getModel();
        if(Tue.isSelected())
        {
            if(Mon.isSelected())
            {
                Mon.setBackground(Color.cyan);
            }
//            if(Tue.isSelected())
//            {
//                Tue.setBackground(Color.cyan);
//            }
            if(Wed.isSelected())
            {
                Wed.setBackground(Color.cyan);
            }
            if(Thu.isSelected())
            {
                Thu.setBackground(Color.cyan);
            }
            if(Fri.isSelected())
            {
                Fri.setBackground(Color.cyan);
            }
            if(Sat.isSelected())
            {
                Sat.setBackground(Color.cyan);
            }
            if(Sun.isSelected())
            {
                Sun.setBackground(Color.cyan);
            }
            Wed.setSelected(false);
            Thu.setSelected(false);
            Fri.setSelected(false);
            Sun.setSelected(false);
            Sat.setSelected(false);
            Mon.setSelected(false);
            for(;tDisplayTable.getRowCount() != 0;)
            {
                model.removeRow(0);
            }
            Tue.setBackground(Color.yellow);
            int i = 1;
            for(Sub x : day2)
            {
                String[] xx = x.getData();
                
                    model.addRow(new String[]{i+"",xx[0],xx[1],xx[2],xx[3]});
                    System.out.println("added : = *******" + i+"" + xx[0] + xx[1] + xx[2] + xx[3]);
                    i=i+1;
            }
        }
        else{
            Tue.setBackground(Color.cyan);
            for(;tDisplayTable.getRowCount() != 0;)
            {
                model.removeRow(0);
            }
        }
    }//GEN-LAST:event_TueActionPerformed

    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        edits = 1;
        AddSubject a = new AddSubject(this);
        int x = this.getX();
        int y = this.getY();
        a.setLocation(x+20, y+20);
        int m = getPressed();
        a.mark(m);
        a.setVisible(true);
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int m = getPressed();
        int p[] = tDisplayTable.getSelectedRows();
        int dec = 0;
        edits = p.length;
        for(int i = 0; i < p.length ; i++)
        {
            Edit edi = new Edit(((Sub)schd[m].get(p[i])),this);
            int x = this.getX();
            int y = this.getY();
            edi.setLocation(x+dec, y+dec);
            dec = dec + 20;
            edi.setVisible(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int i[] = tDisplayTable.getSelectedRows();
        int m = getPressed();
        
        
        for(int x : i)
        {
            System.out.println(x);
            ((Sub)schd[m].get(x)).incA();   
        }
        
        DoPressing(m);
        tDisplayTable.clearSelection();
        for(int x : i)
        {
            tDisplayTable.setRowSelectionInterval(x, x);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        int i[] = tDisplayTable.getSelectedRows();
        int m = getPressed();
        
        for(int x : i)
        {
            ((Sub)schd[m].get(x)).decA();
            
        }
        DoPressing(m);
        tDisplayTable.clearSelection();
        for(int x : i)
        {
            tDisplayTable.setRowSelectionInterval(x, x);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int i[] = tDisplayTable.getSelectedRows();
        int m = getPressed();
        
        for(int x : i)
        {
            ((Sub)schd[m].get(x)).incT();
            
        }
        DoPressing(m);
        tDisplayTable.clearSelection();
        for(int x : i)
        {
            tDisplayTable.setRowSelectionInterval(x, x);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        int i[] = tDisplayTable.getSelectedRows();
        int m = getPressed();
        
        for(int x : i)
        {
            ((Sub)schd[m].get(x)).decT();
            
        }
        
        DoPressing(m);
        tDisplayTable.clearSelection();
        for(int x : i)
        {
            tDisplayTable.setRowSelectionInterval(x, x);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed

        int operation = cbMarkingSelection.getSelectedIndex();
        int c = getPressed();
        if(schd[c].isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Operation cannot be performed!0", "Error",0);
            return;
        }
        if(operation == 0 || operation == 1)
        {
            if(tDisplayTable.getSelectedRowCount()==0)
            {
                JOptionPane.showMessageDialog(this, "No items selected", "Error",0);
                return;
            }
            else
            {
                int k[] = tDisplayTable.getSelectedRows();
                
                for(int m : k)
                {
                    if(operation == 1)
                    {
                        ((Sub)schd[c].get(m)).incPre();
                    }
                    else
                    {
                        ((Sub)schd[c].get(m)).incAbs();
                    }
                }
            }
        }
        else
        {
            if(operation == 2)
            {
                ArrayList<Sub> p = schd[c];
                for(Sub m : p)
                {
                    m.incPre();
                } 
            }
            else
            {
                ArrayList<Sub> p = schd[c];
                for(Sub m : p)
                {
                    m.incAbs();
                }
            }
            
                
        }
        DoPressing(c);
            
    }//GEN-LAST:event_jButton7ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        saveData();
//        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_formWindowClosed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        edits = 1;
        AddSubject a = new AddSubject(this);
        int x = this.getX();
        int y = this.getY();
        a.setLocation(x+20, y+20);
        a.setVisible(true);
        
    }//GEN-LAST:event_jButton11ActionPerformed

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        
    }//GEN-LAST:event_formMouseMoved

    private void tbHBMinimumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbHBMinimumActionPerformed
        if(tbHBMinimum.isSelected())
        {
            int min[] = getBM();
            if(min == null)
            {
                JOptionPane.showMessageDialog(this, "All are Above " + minimum + " %", "Congrats", JOptionPane.INFORMATION_MESSAGE);
                tbHBMinimum.setSelected(false);
                return;
            }
            else
            {
                hb = new HB(this);
                hb.start();
            }
        
        }
        else
        {
            if(hb == null)
                return;
            else
            {
                hb.stop = false;
                hb = null;
            }
        }
    }//GEN-LAST:event_tbHBMinimumActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        int p  = 0;
        try{
            p = Integer.parseInt(JOptionPane.showInputDialog("Enter the minimum attendance", "" + minimum ));
            if(p<1 || p>99)
                throw (new NumberFormatException());
        }
        catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, "Invalid Input!!\nMinimum attendance not changed", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        minimum = p;
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseMoved
        int x = evt.getX();
        if(x>52 && x <342)
        {
            jButton2.setBackground(Color.pink);
            jButton1.setBackground(Color.pink);
            jButton3.setBackground(new Color(250,250,250));
            jButton6.setBackground(new Color(250,250,250));
            jButton4.setBackground(new Color(250,250,250));
            jButton5.setBackground(new Color(250,250,250));
        }
        else if(x>341 && x<402)
        {
            jButton3.setBackground(Color.pink);
            jButton6.setBackground(Color.pink);
            jButton1.setBackground(new Color(250,250,250));
            jButton2.setBackground(new Color(250,250,250));
            jButton4.setBackground(new Color(250,250,250));
            jButton5.setBackground(new Color(250,250,250));

        }
        else if(x>401 && x<507)
        {
            jButton4.setBackground(Color.pink);
            jButton5.setBackground(Color.pink);
            jButton3.setBackground(new Color(250,250,250));
            jButton6.setBackground(new Color(250,250,250));
            jButton2.setBackground(new Color(250,250,250));
            jButton1.setBackground(new Color(250,250,250));
        }
        else
        {
            jButton4.setBackground(new Color(250,250,250));
            jButton5.setBackground(new Color(250,250,250));
            jButton3.setBackground(new Color(250,250,250));
            jButton6.setBackground(new Color(250,250,250));
            jButton2.setBackground(new Color(250,250,250));
            jButton1.setBackground(new Color(250,250,250));

        }
    }//GEN-LAST:event_jTextField1MouseMoved

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
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton Fri;
    private javax.swing.JToggleButton Mon;
    private javax.swing.JToggleButton Sat;
    private javax.swing.JToggleButton Sun;
    private javax.swing.JToggleButton Thu;
    private javax.swing.JToggleButton Tue;
    private javax.swing.JToggleButton Wed;
    private javax.swing.JComboBox cbMarkingSelection;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tDisplayTable;
    private javax.swing.JToggleButton tbHBMinimum;
    // End of variables declaration//GEN-END:variables
}
