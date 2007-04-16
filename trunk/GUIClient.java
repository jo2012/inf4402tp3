import java.lang.Float;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Vector;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
/*
 * GUIClient.java
 *
 * Created on April 15, 2007, 4:01 PM
 */

/**
 *
 * @author  Yann
 */
public class GUIClient extends javax.swing.JFrame {
    
    private ImplClient monClient;
    private boolean isConnected;
    private Vector<Article> lesArticles;
    private javax.swing.table.DefaultTableModel monTableModel;
    private int curArticle= -1;
    
    /** Creates new form GUIClient */
    public GUIClient() {
        String monLogin = "";
        while(monLogin=="")
            monLogin = JOptionPane.showInputDialog("Entrez votre nom d'utilisateur");
        try {
            monClient = new ImplClient(monLogin);
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        initComponents();
        isConnected = false;
        lesArticles = new Vector<Article>();
        createjTable();    
     
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jToolBar1 = new javax.swing.JToolBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableArticles = new javax.swing.JTable();
        jToolBar2 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jTextFieldMiseCourante = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldVotreMise = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldMonCredit = new javax.swing.JTextField();
        jTextFieldTempsActuel = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemConnexionDeconnexion = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jTableArticles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(jTableArticles);

        jButton1.setText("Mise \u00e0 jour");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jToolBar2.add(jButton1);

        jLabel1.setText("Temps actuel : ");

        jButton2.setText("Effectuer une mise");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jTextFieldMiseCourante.setEditable(false);

        jLabel2.setText("Mise Courante :");

        jLabel3.setText("Votre mise :");

        jLabel4.setText("Cr\u00e9dit :");

        jTextFieldMonCredit.setEditable(false);

        jTextFieldTempsActuel.setEditable(false);

        jMenu1.setText("Menu");
        jMenuItemConnexionDeconnexion.setText("Connexion/D\u00e9connexion");
        jMenuItemConnexionDeconnexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConnexionDeconnexionActionPerformed(evt);
            }
        });

        jMenu1.add(jMenuItemConnexionDeconnexion);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 615, Short.MAX_VALUE))
                    .add(jToolBar2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jSeparator1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 625, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextFieldMiseCourante, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 55, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jLabel3)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextFieldVotreMise, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 55, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jButton2))
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextFieldTempsActuel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 183, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(30, 30, 30)
                        .add(jLabel4)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jTextFieldMonCredit, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 71, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jToolBar2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(jTextFieldMiseCourante, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3)
                    .add(jTextFieldVotreMise, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jButton2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jTextFieldMonCredit, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel4)
                    .add(jTextFieldTempsActuel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel1))
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
            lesArticles = monClient.getListArticle();
            String s = "";
            s+=monClient.getCredit();
            jTextFieldMonCredit.setText(s);
            updatejTable();
            if(lesArticles.elementAt(curArticle).IsTimeOut())
                JOptionPane.showMessageDialog(this, "La periode de mise est ecoule, "+lesArticles.elementAt(curArticle).getLeader()+" remporte les mises.",
                    "Message fin de ",JOptionPane.WARNING_MESSAGE);
                
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
// TODO add your handling code here:
        String s = jTextFieldVotreMise.getText();
        Float f = new Float(s);
        monClient.faireMise(f.floatValue());
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jMenuItemConnexionDeconnexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemConnexionDeconnexionActionPerformed
        //Connexion 
       if(!isConnected){
        GUIDefAdresse g = new GUIDefAdresse(this);
        g.setVisible(true);
        }
       else
       {
         monClient.deconnexionEbay();
         lesArticles.clear();
         updatejTable();
         isConnected = false;
       }
        //Deconnexion 
        //else            
    }//GEN-LAST:event_jMenuItemConnexionDeconnexionActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUIClient().setVisible(true);
            }
        });
    }
    
    public void updatejTable()
    {       
        while(monTableModel.getRowCount() > 0)
            monTableModel.removeRow(0);
        for(int i = 0; i<lesArticles.size(); i++)
                monTableModel.addRow(new Object[] {lesArticles.get(i).id,lesArticles.get(i).getNom(),lesArticles.get(i).getPrix(),lesArticles.get(i).bestMise, lesArticles.get(i).getDateFin()});
    }

    public void createjTable()
    {
            monTableModel = new javax.swing.table.DefaultTableModel();
            monTableModel.addColumn("ID#");
            monTableModel.addColumn("Nom de l'article");
            monTableModel.addColumn("Prix de base");
            monTableModel.addColumn("Mise courante");
            monTableModel.addColumn("Date de fin");
            for(int i = 0; i<lesArticles.size(); i++)
                monTableModel.addRow(new Object[] {lesArticles.get(i).id,lesArticles.get(i).getNom(),lesArticles.get(i).getPrix(),lesArticles.get(i).bestMise, lesArticles.get(i).getDateFin()});
            jTableArticles.setModel(monTableModel);
            ListSelectionModel rowSM = jTableArticles.getSelectionModel();
            rowSM.addListSelectionListener(new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    if (e.getValueIsAdjusting())
                        return;
                    else
                    {
                        ListSelectionModel rowSM = (ListSelectionModel)e.getSource();
                        int selectedIndex = rowSM.getMinSelectionIndex();
                        indexChangeTable(selectedIndex);
                    }
                }
            });
    }
    
    void connexion2(String adrEbay, String adrPpal) {
        if(monClient.connexionEbay(adrEbay,adrPpal)){
            lesArticles = monClient.getListArticle();
            updatejTable();
            String s = "";
            s+=monClient.getCredit();
            jTextFieldMonCredit.setText(s);
            isConnected = true;
        }
    }

    public void indexChangeTable(int i) {
        if(i>=0){
        float f = lesArticles.get(i).bestMise;
        curArticle = i;
        String s = "";
        s+=f;
        jTextFieldMiseCourante.setText(s);
        jTextFieldVotreMise.setText(s);
        monClient.setcurArticle(i);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemConnexionDeconnexion;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTableArticles;
    private javax.swing.JTextField jTextFieldMiseCourante;
    private javax.swing.JTextField jTextFieldMonCredit;
    private javax.swing.JTextField jTextFieldTempsActuel;
    private javax.swing.JTextField jTextFieldVotreMise;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    // End of variables declaration//GEN-END:variables
    private JList list;
}

class SharedListSelectionHandler implements ListSelectionListener {
    private GUIClient monGUI;
    public SharedListSelectionHandler(GUIClient g)
    {
        monGUI = g;
    }
    public void valueChanged(ListSelectionEvent e) {    
      ListSelectionModel rowSM = (ListSelectionModel)e.getSource();
      int selectedIndex = rowSM.getMinSelectionIndex();  
      System.out.println(selectedIndex);
      monGUI.indexChangeTable(selectedIndex);
    }
}
