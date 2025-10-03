package co.com.bancopopular.automation.questions.admin;

import co.com.bancopopular.automation.abilities.ConnectToParameters;
import lombok.SneakyThrows;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReadNumberOfPages implements Question<Integer> {
    private static final String NUMBER_PAGES = "NumberPages";

    public static ReadNumberOfPages forGetLastPage() {
        return new ReadNumberOfPages();
    }

    @SneakyThrows
    @Override
    public Integer answeredBy(Actor actor) {
        var numberPages = 0.0;
        Statement stm = null;
        ResultSet rs = null;

        try (Connection conn = ConnectToParameters.as(actor).connect()) {
            stm = conn.createStatement();
            rs = stm.executeQuery(getQuery());
            if (rs.next()) {
                numberPages = rs.getDouble(NUMBER_PAGES);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }finally {
            try{
                if(rs!=null)
                    rs.close();
            }catch (SQLException e){
                JOptionPane.showMessageDialog(null, e);
            }
            try {
                if(stm!=null)
                    stm.close();
            }catch (SQLException e){
                JOptionPane.showMessageDialog(null, e);
            }
        }

        return (int) Math.ceil(numberPages / 50);
    }

    private String getQuery() {
        return "SELECT COUNT(*) as NumberPages\n" +
                "FROM parameters.factory_file ff\n" +
                "left join parameters.client cli on ff.id_client = cli.id\n" +
                "left join parameters.salary_loan_states sle_captura on ff.captura_resultado = sle_captura.id\n" +
                "left join parameters.salary_loan_states sle_aprobacion on ff.aprobacion_resultado = sle_aprobacion.id \n" +
                "left join parameters.salary_loan_states sle_perfeccionamiento on ff.prefeccionamiento_resultado = sle_perfeccionamiento.id\n" +
                "left join parameters.salary_loan_causal_state causal_captura on ff.captura_causal = causal_captura.id\n" +
                "left join parameters.salary_loan_causal_state causal_aprobacion on ff.aprobacion_causal = causal_aprobacion.id \n" +
                "left join parameters.salary_loan_causal_state causal_perfeccionamiento on ff.perfeccionamiento_causal = causal_perfeccionamiento.id\n" +
                " Where ff.capture_type <> 'En Espera Documentos Onbase'\n" +
                " Order by ff.last_update asc";
    }
}
