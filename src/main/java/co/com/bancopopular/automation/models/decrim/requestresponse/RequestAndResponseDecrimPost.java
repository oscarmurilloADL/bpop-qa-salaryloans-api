package co.com.bancopopular.automation.models.decrim.requestresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestAndResponseDecrimPost{

	@JsonProperty("officeName")
	private String officeName;

	@JsonProperty("vendorDocument")
	private String vendorDocument;

	@JsonProperty("administrativeOffice")
	private String administrativeOffice;

	@JsonProperty("rsValidateDecrim")
	private RsValidateDecrim rsValidateDecrim;

	@JsonProperty("customerDocType")
	private String customerDocType;

	@JsonProperty("officeCode")
	private String officeCode;

	@JsonProperty("administrativeOfficeName")
	private String administrativeOfficeName;

	@JsonProperty("salesModel")
	private String salesModel;

	@JsonProperty("rsCreateCase")
	private RsCreateCase rsCreateCase;

	@JsonProperty("payerUniqueName")
	private String payerUniqueName;

	@JsonProperty("advisorDocument")
	private String advisorDocument;

	@JsonProperty("customerDocument")
	private String customerDocument;

	@JsonProperty("obligationID")
	private String obligationID;

	@JsonProperty("rqDecrim")
	private RqDecrim rqDecrim;
}