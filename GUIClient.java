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
        lesArticles.add(a);
        lesArticles.add(b);
        updatejTable();    
     
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
        jToolBar2.add(jButton1);

        jLabel1.setText("Temps actuel : ");

        jButton2.setText("Effectuer une mise");

        jLabel2.setText("Mise Courante :");

        jLabel3.setText("Votre mise :");

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
                    .add(jLabel1)
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
                        .add(jButton2)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jToolBar2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
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
                .add(jLabel1))
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemConnexionDeconnexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemConnexionDeconnexionActionPerformed
     
        //Connexion 
       if(!isConnected){
        GUIDefAdresse g = new GUIDefAdresse(this);
        g.setVisible(true);
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
            monTableModel = new javax.swing.table.DefaultTableModel();
            monTableModel.addColumn("ID#");
            monTableModel.addColumn("Nom de l'article");
            monTableModel.addColumn("Prix de base");
            monTableModel.addColumn("Mise courante");
            monTableModel.addColumn("Date de fin");
            for(int i = 0; i<lesArticles.size(); i++)
                monTableModel.addRow(new Object[] {lesArticles.get(i).id,lesArticles.get(i).getNom(),lesArticles.get(i).getPrix(),lesArticles.get(i).bestMise, lesArticles.get(i).getDateFin()});
            jTableArticles.setModel(monTableModel);
            list = new JList();
            list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            list.setDragEnabled(false);
            ListSelectionModel listSelectionModel = list.getSelectionModel();
            listSelectionModel.addListSelectionListener(
                            new SharedListSelectionHandler(this));
            jTableArticles.setSelectionModel(listSelectionModel);
        
            /*jTableArticles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
            },
            new String [] {
                "ID#", "Nom de l'article", "Prix de base", "Mise courante", "Date de fin"
            }
        ));*/
        
    }

    void connexion2(String adrEbay, String adrPpal) {
        if(monClient.connexionEbay(adrEbay,adrPpal)){
            lesArticles = monClient.getListArticle();
            updatejTable();
            isConnected = true;
        }
    }

    public void indexChangeTable(int i) {
        Article art_tmp = lesArticles.get(i);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemConnexionDeconnexion;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTableArticles;
    private javax.swing.JTextField jTextFieldMiseCourante;
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
      monGUI.indexChangeTable(e.getLastIndex());
    }
}
