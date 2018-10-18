/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmedianas;

import java.util.Random;
import java.awt.Graphics;
import javax.swing.JPanel;


/**
 *
 * @author tarcisio
 */

public class Pontos extends JPanel{
    private int qtde, largura, altura;
    private int[][] coordenadas;
    private double[][] distancias;

    public double[][] getDistancias() {
        return distancias;
    }

    public int[][] getCoordenadas() {
        return coordenadas;
    }

    public Pontos(int qtde, int largura, int altura) {
        this.qtde = qtde;
        this.altura=altura;
        this.largura=largura;
        this.coordenadas= new int[qtde][2];
        this.distancias= new double[qtde][qtde];
        gerar_aleatorio();
        calcular_distancias();
    }
    private void calcular_distancias(){
        int x1, x2, y1, y2, c1,c2;
        double h;
        for (int i=0;i<this.qtde;i++){
            for (int j=0; j<this.qtde;j++){
                x1=this.coordenadas[i][0];
                y1=this.coordenadas[i][1];
                x2=this.coordenadas[j][0];
                y2=this.coordenadas[j][1];
                
                c1= x2-x1;
                c2= y2-y1;
                
                h=Math.sqrt(c1*c1 + c2*c2);
                this.distancias[i][j]=h;
            }
        }
    }
    
    private void gerar_aleatorio(){
        Random r = new Random();
        for (int i=0;i<this.qtde;i++){
            this.coordenadas[i][0]=r.nextInt(this.largura);
            this.coordenadas[i][1]=r.nextInt(this.altura);
        }
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int x, y;

        //Plotando todos os pontos gerados na tela
        for (int i=0;i<this.qtde;i++){
            x=this.coordenadas[i][0];
            y=this.coordenadas[i][1];
            g.fillOval(x, y, 7, 7);
            g.drawString("" + i, x, y);
        }
    }
    

}
