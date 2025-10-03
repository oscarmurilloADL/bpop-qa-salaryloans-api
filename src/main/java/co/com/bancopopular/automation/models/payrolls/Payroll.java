package co.com.bancopopular.automation.models.payrolls;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Payroll {

    @JsonProperty("nit")
    private String nit;

    @JsonProperty("sector")
    private int sector;

    @JsonProperty("subsector")
    private int subsector;

    @JsonProperty("name")
    private String name;

    @JsonProperty("status")
    private int status;

    public Payroll(String nit, int sector, int subsector, String name, int status) {
        this.nit=nit;
        this.sector=sector;
        this.subsector=subsector;
        this.name=name;
        this.status=status;
    }

    public String toString(){
        return "nit: "+nit+" sector: "+sector+" subsector: "+subsector+" nombre: "+name+" status: "+status;
    }
}