package co.com.bancopopular.automation.models.decrim.requestresponse;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RqDecrim{

	@JsonProperty("advisorId")
	private String advisorId;

	@JsonProperty("documentType")
	private String documentType;

	@JsonProperty("documentNumber")
	private String documentNumber;

	@JsonProperty("fileName1")
	private String fileName1;

	@JsonProperty("fullName")
	private String fullName;

	@JsonProperty("fileType1")
	private String fileType1;

	@JsonProperty("fileName2")
	private String fileName2;

	@JsonProperty("fileType2")
	private String fileType2;

	@JsonProperty("fileName3")
	private String fileName3;

	@JsonProperty("fileType3")
	private String fileType3;
}