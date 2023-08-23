package Gerenciador;

import java.util.List;

import Class.Contato;

public interface GerenciaContato {

    public void exportar(List<Contato> contatos);
    public List<Contato> importar();
}
