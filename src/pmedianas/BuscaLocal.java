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
    private int atendido_por; //Este vetor informa qual a facilidade (UPA) que esta mais proxima do bairro
    private int pontos_demanda;
    private int facilidades;
    
       

    public BuscaLocal(double[][] distancias, int pontos_demanda, int facilidades) {
        this.distancias = distancias;
        this.pontos_demanda = pontos_demanda;
        this.facilidades = facilidades;
        this.solucao =  new int [facilidades];
        this.atendido_por = new int[pontos_demanda];        
       
        
        gerar_solucao_inicial_aleatoria();
               
        //Mostrando a solucao inicial
        
        for (int i=0; i<this.facilidades; i++){
            System.out.print(this.solucao[i]+"-");        }
        
        //Melhorando o trajeto do caxeiro viajente
        AllPairs();
        
           }

    private void atendido_por_quem(int [] vet){
        //Vet corresponde a um vetor que contém as facilidades que estão sendo usadas em um dado momento
        
        
    }

    
    
    private void AllPairs(){
        int fu[] = new int [this.facilidades], troca; //Facilidades Usada
        int fnu[] = new int [this.pontos_demanda-this.facilidades]; //Facilidades Nao Usada
                
        double distancia_total, aux_distancia_total;
        boolean melhorou=true;
        
        //Primeiro copia-se a solucao inicial gerada aleatoriamente
        System.arraycopy(this.solucao,0, fu,0,this.facilidades);
        
        //Preenchendo o vetor facilidades nao usadas
        int k=0, i=0;
        for(k=0;k<this.pontos_demanda;k++){
            if(!existe(k)){
                fnu[i]=k;
                i++;
            }
        }
        
        distancia_total=custo_solucao(this.solucao);
        aux_distancia_total=distancia_total;
        
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
    
    
    private double custo_solucao(int[] rota_testada){
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
        for (int i=0;i<this.facilidades;i++){
            this.solucao[i]=-1; //zero é um numero valido.. o -1 e para ir antes do zero.
        }
        
        Random r = new Random();
        int aux=0, i=0;
        while (i<this.facilidades){
            aux=r.nextInt(this.pontos_demanda);
            if (!existe(aux)){ //existe se o ponto ja não foi chamado
                this.solucao[i]=aux;
                i++;
            }
        }
    }
    private boolean existe(int ponto){
        boolean retorno=false;
        for (int i=0;i<this.facilidades;i++){
            if (ponto==this.solucao[i]){
                retorno=true;
                break;
            }
        }
        return retorno;
    }
    
}
