
package pmedianas;

import javax.swing.JFrame;


public class Pmedianas {

    
    public static void main(String[] args) {
        int largura=1200, altura=700, pontos_demanda=500;
        int facilidades=3;
        
        //Gerando os pontos de demanda na tela
        //As facilidades poderão ser instaladas em qualquer ponto de demanda, sem restrição
        Pontos p = new Pontos(pontos_demanda,largura, altura);
        
        JFrame pto = new JFrame("Pontos de Demanda");
        pto.add(p); 
       
       pto.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       pto.setSize(largura,altura);
       pto.setLocationRelativeTo(null);
       pto.setVisible(true);
       
       
       BuscaLocal bl = new BuscaLocal(p.getDistancias(), pontos_demanda, facilidades);
    }
    
}
