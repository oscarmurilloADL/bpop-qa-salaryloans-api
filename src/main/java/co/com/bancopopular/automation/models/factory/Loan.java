package co.com.bancopopular.automation.models.factory;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan{

	@JsonProperty("approvalAssigned")
	private String approvalAssigned;

	@JsonProperty("approvalCausal")
	private String approvalCausal;

	@JsonProperty("approvalLastChangeDate")
	private String approvalLastChangeDate;

	@JsonProperty("approvalResult")
	private String approvalResult;

	@JsonProperty("approvalToDo")
	private String approvalToDo;

	@JsonProperty("apprrovedTerm")
	private String apprrovedTerm;

	@JsonProperty("captureAssigned")
	private String captureAssigned;

	@JsonProperty("captureCausal")
	private String captureCausal;

	@JsonProperty("captureLastChangeDate")
	private String captureLastChangeDate;

	@JsonProperty("captureResult")
	private int captureResult;

	@JsonProperty("captureToDo")
	private String captureToDo;

	@JsonProperty("causalComment")
	private String causalComment;

	@JsonProperty("date")
	private String date;

	@JsonProperty("decisionDate")
	private String decisionDate;

	@JsonProperty("decisionType")
	private String decisionType;

	@JsonProperty("decisionTypeDescription")
	private String decisionTypeDescription;

	@JsonProperty("fechaHoraGeneracion")
	private String fechaHoraGeneracion;

	@JsonProperty("filename")
	private String filename;

	@JsonProperty("idApprovalCausal")
	private String idApprovalCausal;

	@JsonProperty("idApprovalResult")
	private String idApprovalResult;

	@JsonProperty("idCaptureCausal")
	private String idCaptureCausal;

	@JsonProperty("idCaptureResult")
	private String idCaptureResult;

	@JsonProperty("idImprovementCausal")
	private String idImprovementCausal;

	@JsonProperty("idImprovementResult")
	private String idImprovementResult;

	@JsonProperty("identificationNumber")
	private String identificationNumber;

	@JsonProperty("improvementAssigned")
	private String improvementAssigned;

	@JsonProperty("improvementCausal")
	private String improvementCausal;

	@JsonProperty("improvementLastChangeDate")
	private String improvementLastChangeDate;

	@JsonProperty("improvementResult")
	private String improvementResult;

	@JsonProperty("improvementToDo")
	private String improvementToDo;

	@JsonProperty("loanCode")
	private String loanCode;

	@JsonProperty("newFeeAmount")
	private String newFeeAmount;

	@JsonProperty("payerName")
	private String payerName;

	@JsonProperty("payrollPlatform")
	private String payrollPlatform;

	@JsonProperty("requestId")
	private int requestId;
}