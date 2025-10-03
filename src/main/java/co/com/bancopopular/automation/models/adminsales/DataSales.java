package co.com.bancopopular.automation.models.adminsales;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSales {

	@JsonProperty("date")
	private String date;

	@JsonProperty("fechaHoraGeneracion")
	private String fechaHoraGeneracion;

	@JsonProperty("improvementToDo")
	private String improvementToDo;

	@JsonProperty("captureAssigned")
	private Object captureAssigned;

	@JsonProperty("newFeeAmount")
	private String newFeeAmount;

	@JsonProperty("idCaptureResult")
	private int idCaptureResult;

	@JsonProperty("creditType")
	private String creditType;

	@JsonProperty("disbursementCausal")
	private Object disbursementCausal;

	@JsonProperty("improvementResult")
	private String improvementResult;

	@JsonProperty("approvalToDo")
	private String approvalToDo;

	@JsonProperty("payrollPlatform")
	private Object payrollPlatform;

	@JsonProperty("requestId")
	private int requestId;

	@JsonProperty("idImprovementCausal")
	private int idImprovementCausal;

	@JsonProperty("improvementAssigned")
	private int improvementAssigned;

	@JsonProperty("improvementLastChangeDate")
	private String improvementLastChangeDate;

	@JsonProperty("captureType")
	private String captureType;

	@JsonProperty("captureCausal")
	private String captureCausal;

	@JsonProperty("payerName")
	private String payerName;

	@JsonProperty("approvalLastChangeDate")
	private String approvalLastChangeDate;

	@JsonProperty("idImprovementResult")
	private int idImprovementResult;

	@JsonProperty("sendFabricDate")
	private Object sendFabricDate;

	@JsonProperty("captureResult")
	private String captureResult;

	@JsonProperty("idCaptureCausal")
	private int idCaptureCausal;

	@JsonProperty("approvalAssigned")
	private Object approvalAssigned;

	@JsonProperty("disbursementResult")
	private Object disbursementResult;

	@JsonProperty("improvementCausal")
	private String improvementCausal;

	@JsonProperty("approvalCausal")
	private String approvalCausal;

	@JsonProperty("apprrovedTerm")
	private String apprrovedTerm;

	@JsonProperty("captureLastChangeDate")
	private String captureLastChangeDate;

	@JsonProperty("idApprovalCausal")
	private int idApprovalCausal;

	@JsonProperty("causalComment")
	private Object causalComment;

	@JsonProperty("filename")
	private String filename;

	@JsonProperty("approvalResult")
	private String approvalResult;

	@JsonProperty("identificationNumber")
	private String identificationNumber;

	@JsonProperty("forDisbursementCausal")
	private Object forDisbursementCausal;

	@JsonProperty("captureToDo")
	private String captureToDo;

	@JsonProperty("loanCode")
	private String loanCode;

	@JsonProperty("idApprovalResult")
	private int idApprovalResult;

	@JsonProperty("forDisbursementResult")
	private String forDisbursementResult;
}