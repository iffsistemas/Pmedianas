/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pmedianas;

import java.util.Random;

/**
 *
 * @author tarcisio
 */

public class BuscaLocal {
    private int[] solucao;
    private double [][]distancias;
    private int pontos_demanda;
    private int facilidades;
       

    public BuscaLocal(double[][] distancias, int pontos_demanda, int facilidades) {
        this.distancias = distancias;
        this.pontos_demanda = pontos_demanda;
        this.facilidades = facilidades;
        this.solucao =  new int [facilidades];
                
        gerar_solucao_inicial_aleatoria();
                
        for (int i=0;i<this.qtde;i++){
            System.out.print(""+this.rota[i]+"-");
        }
        
        System.out.println("\nCusto Total = " + custo_rota(this.rota));
        System.arraycopy(this.rota, 0, this.rotinha, 0, qtde);
        //Melhorando o trajeto do caxeiro viajente
        AllPairs();
        
         for (int i=0;i<this.qtde;i++){
            System.out.print(""+this.rota[i]+"-");
         }
         System.out.println("\nCusto Total = " + custo_rota(this.rota));
         
         
         
    }

 
    
    
    
    private void AllPairs(){
        int aux_rota[] = new int [this.qtde], troca;
        double distancia_total, aux_distancia_total;
        
        //Primeiro copia-se a rota inicial gerada aleatoriamente
        System.arraycopy(this.rota,0, aux_rota,0,this.qtde);
        distancia_total=custo_rota(this.rota);
        boolean melhorou=true;
        
        
        //Iniciando a busca App Pairs
        while(melhorou) {
            melhorou=false;
        for(int i=0;i<this.qtde-1;i++){
            for(int j=i+1; j<this.qtde;j++){
                troca=aux_rota[i];
                aux_rota[i]=aux_rota[j];
                aux_rota[j]=troca;
                
                //Verificando o custo da nova rota
                aux_distancia_total=custo_rota(aux_rota);
                
                //Melhorou?
                    if (aux_distancia_total<distancia_total){
                        melhorou=true;
                        distancia_total=aux_distancia_total;
                        
                        //Atualizando a rota que foi melhorada
                        System.arraycopy(aux_rota,0,this.rota,0,this.qtde);
                    }
                    //Voltando com a configuração original para testar as outras combinações
                    
                    troca=aux_rota[j];
                    aux_rota[j]=aux_rota[i];
                    aux_rota[j]=troca;
            }//Neste Local encerram-se os ciclos de troca
            
            //Atualizando com a melhor rota
            System.arraycopy(this.rota, 0, aux_rota, 0, this.qtde);
        }
       
      }    
        
    }
    
    
    private double custo_rota(int[] rota_testada){
        double custo =0.0;
        int i, de , para;
        
        for (i=0;  i<this.qtde-1; i++){
            de=rota_testada[i];
            para=rota_testada[i+1];
            
            custo+= this.distancias[de][para];
                                 
        }
        //Voltando para a cidade de origem
        
        custo+=this.distancias[i][0];
        return custo;
                
    }
    
    
    private void gerar_solucao_inicial_aleatoria(){
        //Esvaziando o vetor
        for (int i=0;i<this.qtde;i++){
            this.rota[i]=-1;
        }
        
        Random r = new Random();
        int aux=0, i=0;
        while (i<this.qtde){
            aux=r.nextInt(this.qtde);
            if (!existe(aux)){
                this.rota[i]=aux;
                i++;
            }
        }
    }
    private boolean existe(int cidade){
        boolean retorno=false;
        for (int i=0;i<this.qtde;i++){
            if (cidade==this.rota[i]){
                retorno=true;
                break;
            }
        }
        return retorno;
    }
    
}
