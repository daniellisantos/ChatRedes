package chatredes;

import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Usuario {
    
    //private int porta = 6790; // porta do servidor
    private String nomeUsuario;
    private final Socket socketCliente;
    private ArrayList<String> listaArq;
    private String caminhoPasta;
    private String sala;
    private InetAddress salaChat = null;
    private MulticastSocket socketMulticast;

    public Usuario(Socket socketCliente){
        this.socketCliente=socketCliente;
    }

    
    // GETTERS E SETTERS
    public String getNomeUsuario() {
        return nomeUsuario;
    }
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    } 
    public Socket getSocketCliente() {
        return socketCliente;
    }
    public ArrayList<String> getListaArq() {
        return listaArq;
    }
    public void setListaArq(ArrayList<String> listaArq) {
        this.listaArq = listaArq;
    }
    public String getCaminhoPasta() {
        return caminhoPasta;
    }
    public void setCaminhoPasta(String caminhoPasta) {
        this.caminhoPasta = caminhoPasta;
    }
    public String getSala() {
        return sala;
    }
    public void setSala(String sala) {
        this.sala = sala;
    }
    public InetAddress getSalaChat() {
        return salaChat;
    }
    public void setSalaChat(InetAddress salaChat) {
        this.salaChat = salaChat;
    }
    public InetAddress getIPUsuario(){
        return socketCliente.getInetAddress();
    }
    public int getPortaUsuario(){
        return socketCliente.getPort();
    }
    public MulticastSocket getSocketMulticast() {
        return socketMulticast;
    }
    public void setSocketMulticast(MulticastSocket socketMulticast) {
        this.socketMulticast = socketMulticast;
    }

    


    //metodo teste para verificar se esta pegando os arquivos da pasta informada
    public void imprimirListaArq() {
        for (int i = 0; i < listaArq.size(); i++) {
            System.out.println(listaArq.get(i) + '\n');
        }
    }
}
