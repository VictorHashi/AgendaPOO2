package DB;

import Class.Contato;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ContatoDAO {
    final String TABLE = "contato";
    public boolean insert(Contato contato) {
        try {
            Connection connection = ConectDB.connect();
            String sql = "INSERT INTO " + TABLE + " (Nome,Nascimento,Telefone,Email) VALUES (?,?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, contato.getNome());
            preparedStatement.setDate(2, new Date(contato.getNascimento().getTime()));
            preparedStatement.setString(3, contato.getTelefone());
            preparedStatement.setString(4, contato.getEmail());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Contato> selectAll() {
        try {
            Connection connection = ConectDB.connect();
            String sql = "SELECT * FROM " + TABLE + ";";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Contato> contatos = montarLista(resultSet);
            return contatos;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Contato> montarLista(ResultSet resultSet) {
        List<Contato> contatos = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Contato contato = resultSetToContato(resultSet);
                contatos.add(contato);
            }
            return contatos;
        } catch (Exception e) {
            return null;
        }
    }

    private Contato resultSetToContato(ResultSet resultSet) throws Exception {
        Contato contato = new Contato();

        Field[] fields = Contato.class.getDeclaredFields();
        for (Field field : fields) {
            String nomeCampo = field.getName();
            Object value = resultSet.getObject(nomeCampo);

            if (value != null) {
                String setterMethodName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
                Method setterMethod = Contato.class.getMethod(setterMethodName, field.getType());
                setterMethod.invoke(contato, value);
            }
        }

        return contato;
    }

}