/*
This is the JavaBeans
 */

/**
 *
 * @author gregbug
 */
public class DashboardFrame extends javax.swing.JFrame {

    /**
     * Creates new form DashboardFrame
     */
    public DashboardFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        controller1 = new controller.controller();
        moistureSensor2 = new moisturesensor.MoistureSensor();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        controller1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                controller1PropertyChange(evt);
            }
        });

        moistureSensor2.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                moistureSensor2PropertyChange(evt);
                moistureSensor2PropertyChange1(evt);
                moistureSensor2PropertyChange2(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Humidity");

        jButton1.setText("Start");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Decreasing");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel1)
                        .addGap(70, 70, 70)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1)))
                .addContainerGap(177, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(155, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(89, 89, 89)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        moistureSensor2.start();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void moistureSensor2PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_moistureSensor2PropertyChange
        jLabel1.setText(String.valueOf("Humidity %: " + Integer.toString(moistureSensor2.getCurrentHumidity())));
    }//GEN-LAST:event_moistureSensor2PropertyChange

    private void moistureSensor2PropertyChange1(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_moistureSensor2PropertyChange1
        controller1.setLocalHumidity(moistureSensor2.getCurrentHumidity());
    }//GEN-LAST:event_moistureSensor2PropertyChange1

    private void moistureSensor2PropertyChange2(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_moistureSensor2PropertyChange2
        jLabel2.setText(String.valueOf("Decreasing: " + moistureSensor2.isDecreasing()));
    }//GEN-LAST:event_moistureSensor2PropertyChange2

    private void controller1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_controller1PropertyChange
        /* 
         When we are going to start irrigation system, consequently the localHumidity 
         will increase, so the value of 'on' variable will be 'true' and in the call of method
         if the setDecreasing  method will be called, consequently, means, that the irrigation is 'off'
        */
        moistureSensor2.setDecreasing(!controller1.isOn());
    }//GEN-LAST:event_controller1PropertyChange

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
            java.util.logging.Logger.getLogger(DashboardFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashboardFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashboardFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashboardFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashboardFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private controller.controller controller1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private moisturesensor.MoistureSensor moistureSensor2;
    // End of variables declaration//GEN-END:variables
}
