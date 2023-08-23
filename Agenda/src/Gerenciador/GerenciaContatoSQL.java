package Gerenciador;

import java.util.ArrayList;
import java.util.List;
import Class.Contato;
import DB.ContatoDAO;

public class GerenciaContatoSQL implements GerenciaContato{


    @Override
    public void exportar(List<Contato> contatos) {
        ContatoDAO contatoDAO = new ContatoDAO();
        for (Contato contato: contatos) {
            contatoDAO.insert(contato);
        }
    }

    @Override
    public List<Contato> importar() {
        List<Contato> contatos = new ArrayList<Contato>();
        ContatoDAO contatoDAO = new ContatoDAO();
        contatos = contatoDAO.selectAll();
        return contatos;
    }
}
