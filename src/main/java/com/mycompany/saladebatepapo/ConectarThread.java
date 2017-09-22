/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.saladebatepapo;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 *
 * @author aluno
 */
public class ConectarThread extends Thread{
    int porta;
    String nome;
    BatePapo sala;
    String grupo;
    byte[] buffer;
    DatagramPacket msgIn;
    public ConectarThread(int porta, String nome, BatePapo sala, String grupo){
        this.porta = porta;
        this.nome = nome;
        this.sala = sala;
        this.grupo = grupo;
    }
    public void enviarMensage(){
        
    }
    
    public void run() {
        try{
            InetAddress group = InetAddress.getByName(grupo);
            MulticastSocket socket = new MulticastSocket(porta);
            socket.joinGroup(group);
            buffer = new byte[1000];
            msgIn = new DatagramPacket(buffer, buffer.length);                 
            String msg = nome + " Entrou na sala!";
            byte[] data = msg.getBytes();
            DatagramPacket msgOut = new DatagramPacket(data, data.length, group, porta);
            System.out.println("aqui "+new String(data));
            socket.send(msgOut);
            
                while (true) { 
                    buffer = new byte[1000];
                   msgIn = new DatagramPacket(buffer, buffer.length); 
                    socket.receive(msgIn);
                    sala.inserirTexto(new String(msgIn.getData()));
                    msgIn = new DatagramPacket(buffer, buffer.length); 
                    
            }
            
        }
        catch (Exception e) {
            System.out.println("Deu pal no ConectarThread :"+ e);
        }
    }
}
