package co.com.bancopopular.automation.data;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:${environment}.properties"})

public interface Environment extends Config {

    String validVendor();

    String invalidVendor();

    String inactiveVendor();
    String baseUrlFactor();
    String baseUrlToken();
    String baseUrl();

    String castlemockUrl();
    String baseSecurityUrl();

    String baseExtractUrl();
    String baseUrlDocuments();

    String redis();

    String baseCipherUrl();

    String client();

    String advisor();

    String noClient();

    String clientNoPhone();

    String clientWriteOffPortFolio();

    String clientSeizureAccount();

    String clientArrearsMoreThanNinetyDays();

    String clientNotConditionSameFee();

    String validFactoryAdmin();

    String clientBureauTrailOfficePres();

    String clientBureauTrailVendorPres();

    String clientBureauTrailLoyaltyPres();

    String clientBureauTrailExpressPres();

    String clientBureauTrailFVEPres();

    String clientBureauTrailSchedulingPres();

    String clientBureauTrailOfficeTel();

    String clientBureauTrailLoyaltyTel();

    String clientBureauTrailExpressTel();

    String clientBureauTrailFVETel();

    String clientBureauTrailSchedulingTel();

    String clientWithoutPaymentByPayroll();

    String clientNegativeDisbursement();

    String clientPunishedPayroll();

    String clientPaymentNotIncluded();

    String clientCanceledAndDisbursedPayroll();

    String clientPensioner69BankInsurance12();

    String clientPensioner69BankInsurance48();

    String clientPensioner70BankInsurance12();

    String clientPensioner70BankInsurance48();

    String clientEducational40BankInsurance12();

    String clientEducational56BankInsurance48();

    String clientSeizureAccountBP();

    String clientSimNotValidSameFee();

    String clientSimNotValidAnyFee();

    String clientInvalidODM();

    String bankInsuranceCoverage70Old12();

    String bankInsuranceCoverage70Old48();

    String bankInsuranceCoverageEducation();

    String bankInsuranceCoverage69Old12();

    String bankInsuranceCoverage69Old48();

    String bankInsuranceCoverage56Old48();

    String maritalStatusValues();

    String clientSygnus();

    String clientUnauthorizedSygnus();

    String clientWithoutOfferSygnus();

    String togglesInformation();

    String clientPhoneModel();

    String clientAnyFeeInvalidHolder();

    String clientLowDiscountCapacity();

    String clientNotSubjectToPayroll();

    String clientExpiredPayrollCheck();

    String clientOver81CNC();

    String client70to81CNC();

    String bankInsuranceCoverage70to81CNC();

    String clientBelow69CNC();

    String clientWithoutPaymentsInProgress();

    String fullListPayrolls();

    String clientWithCNCSalePendingCapture();

    String clientWithCNCSalePendingApproval();

    String clientWithCNCSalePendingPerfecting();

    String clientWithOnlyCancelledPayroll();

    String clientWithActivePayroll();

    String clientWithPayrollToDisbursement();

    String clientFailSygnusMinus2();

    String clientFailSygnusMinus9();

    String clientFailCrm();

    String clientWithoutDisburmentAmt();

    String clientNoGrowthPolicy();

    String clientWithOnlyNewAccount();

    String clientWithoutLoansActive();

    String clientWithoutLoans();

    String clientWithPunishedPayroll();

    String clientWithDisabledAccount();

    String clientWithOutAuthenticationMethod();

    String clientWithAbnormalCondition();

    String clientWhitValidationCrmAndSimSucces();

    String clientWithSuccessfulSimValidationAndPermanenceDateLessThan90Days();

    String clientWhitReportByBadBehavior();

    String clientMinimumGrowthAmount();

    String clientOfi0();

    String client68Years();

    String clientNotViableToProcessThroughThisChannel();

    String clientWithUnreadableDocument();

    String clientWithDiscoutValueGreaterEqualsCurrentFee();

    String clientWithoutBankininsurance();

    String clientNoRecalculationOrdinaryAndWithoutBRMS();

    String clientNoRecalculationOrdinaryButWithBRMS();

    String clientSygnusWithFailedRenewal();

    String bankInsuranceCoverage40Old12();

    String clientWithInvalidCodeInColpensionesDoc();

    String clientWithNegativeValueInSygnus();

    String clientNoRecalculationOrdinaryAndWithoutBRMSInSygnus();

    String clientWithInvalidRemovableDoc();

    String clientSameQuota();

    String clientWithSaleCasur();

    String unsuccessfulReserveInRenewal();

    String clientWithResponseRemoveOnTap();

    String clientWithpaymentHabitReport();

    String clientWithValidationDecrimFailed();

    String toSignDecrimCreateCase();

    String toSignDecrimValidation();

    String getSha256();

    String getTokenMDM();

    String getUpdateMDM();

    String getDataMDM();

    String clientWithInternalHistoricalMora30();

    String clientWithInternalHistoricalMora60();

    String clientWithInternalHistoricalMora90();

    String clientWithInternalHistoricalMora120();

    String clientWithInternalCurrentMoraPayrollcheck();

    String clientWithIternalTDC();

    String clientWithInternalFreeInvestment();

    String clientWithInternalMutipleQuota();

    String clientWithInternalMicrocredit();

    String clientWithInternalCommercial();

    String clientWithInternalLivingPlace();

    String clientWithInternalOverdraft();

    String clientWithInternalOtherProduct();

    String clientWithInternalQualificationPrevious();

    String clientWithInternalFootprints();

    String clientWithInternalCoSigner();

    String clientWithInternalRepealing();

    String clientWithExternalHistoricalMora30();

    String clientWithExternalHistoricalMora60();

    String clientWithExternalHistoricalMora90();

    String clientWithExternalHistoricalMora120();

    String clientWithExternalCurrentMoraPayrollcheck();

    String clientWithExternalTDC();

    String clientWithExternalFreeInvestment();

    String clientWithExternalMutipleQuota();

    String clientWithExternalCommercial();

    String clientWithExternalLivingPlace();

    String clientWithExternalOverdraft();

    String clientWithExternalOtherProduct();

    String clientWithExternalCoSigner();

    String clientWithExternalInvalidViability();

    String clientWithManyInternalCausal();

    String clientWithManyExternalCausal();

    String clientWithInternalCreditPolliicy();

    String clientWithUnexpectedExternalCauses();

    String clientWithExternalRepealing();

    String clientWithSubcausalListPeaceSafe();

    String clientWithExternalSubcausalListWithoutPeaceSafe();

    String clientWithInternalSubcausalListWithoutPeaceSafe();

    String factorSeguro00073();

    String factorSeguro0112();
    String factorSeguro00245();
    String factorSeguro0075();
    String factorSeguro0125();

    String clientWithCNC101CodeCertification();

    String clientWithCNC102CodeCertification();

    String clientWithCNC103CodeCertification();

    String clientWithCNC104CodeCertification();

    String clientWithCNC105CodeCertification();

    String clientWithCNC106CodeCertification();

    String clientWithCNCAllCodeCertification();

    String clientWithExpiredCertificate();

    String clientWithSuccessfullDocuments();

    String clientCNCEducational();

    String clientAntioquiaCNCEducational();

    String clientCNClOtherHeadLine();

    String clientCNCActiveDiscount();

    String clientCNCUnreadableFile();

    String clientCNCExpiredFile();

    String clientWithAgeLess70();

    String clientWithAgeMore70();

    String clientWithOutAuthenticationMethodMDM();

    String clientNoPhoneMDM();

    String pensionerClientWithBCExternalQualificationPrevious();

    String educationalClientWithBCExternalQualificationPrevious();

    String publicClientWithBCExternalQualificationPrevious();

    String pensionerClientWithDEExternalQualificationPrevious();

    String educationalClientWithDEExternalQualificationPrevious();

    String publicClientWithDEExternalQualificationPrevious();

    String pensionerClientWithBCInternalQualificationPrevious();

    String educationalClientWithBCInternalQualificationPrevious();

    String publicClientWithBCInternalQualificationPrevious();

    String pensionerClientWithDEInternalQualificationPrevious();

    String educationalClientWithDEInternalQualificationPrevious();

    String publicClientWithDEInternalQualificationPrevious();

    String optimization();

    String clientWithCode2ErrorFirstCallSimulation();

    String clientWithCode105ErrorFirstCallSimulation();

    String clientWithCode111ErrorFirstCallSimulation();

    String clientWithCode114ErrorFirstCallSimulation();

    String clientWithCode117ErrorFirstCallSimulation();

    String clientWithCode122ErrorFirstCallSimulation();

    String clientWithCode125ErrorFirstCallSimulation();

    String clientWithCode141ErrorFirstCallSimulation();

    String clientWithCode145ErrorFirstCallSimulation();

    String clientWithCode147ErrorFirstCallSimulation();

    String clientWithTimeOutPayroll();

    String clientWithCertificatePreLoaded();

    String clientWithPayrollPreLoaded();

    String clientBureauTrailVendorTel();

    String clientWithoutContactInfoUniqueComponentID();

    String clientWithoutContactInfoTwoComponentID();

    String clientWithContactInfoOnlyTwoComponentID();

    String toSingDocumentsPayers();

    String clientWithExternalInvalidDocument();

    String clientWithExternalDerStaLiabilityProd();

    String clientWithInternalSubcausaListOverflow();

    String clientWithInternalAccountCancel();

    String clientWithExternalAccountCancel();

    String clientWithInternalGarnishedAccount();

    String clientWithExternalGarnishedAccount();

    String clientWithCausalListNonExistent();

    String clientWithExtenalRevolving();

    String clientWithExternalOtherReal();

    String clientWithInternalRatingModelBehavior();

    String clientWithExternalOriModelQuafilication();

    String clientReportedODM();

    String clientWithFlexcubeAccount500();

    String clientOnlyWithFlexcubeAccountsMigrated();

    String technicalFailureODM();

    String incorrectNamePromissory();

    String clientWithReleaseNotApproved();

    String clientWithCertihuellaMC();

    String clientWithCertihuellaTC();

    String clientBRMSRecipring();

    String clientRepricingWithoutPaymentEnabled();

    String clientRepricingInactiveObligation();

    String clientIsNotInRepricingTable();

    String clientRepricingDisableAccount();

    String clientRepricingWithoutActiveObligation();

    String clientRepricingFlexcubeSeizedAccount();

    String clientFlexcubeSeizedAccount();

    String clientFlexcubeBloquedAccount();

    String clientFlexcubeDualOwnnershipAccount();

    String clientWithValidAccountAndSeizedBloquedAndDualOwnershipAccounts();

    String clientWithSeizedBloquedAndDualOwnershipAccounts();

    String clientWithValidAccountWithDualOwnershipNull();

    String clientWithoutFlexcubeAccounts();

    String clientWithUnstructuredAccount();

    String clientWithUnstructuredAccountFailed();

    String clientTechnicalErrorOwnership();

    String clientNotInCRMMDM();

    String clientReportedAMLRiskv2();

    String clientBureauTrailNoExtractorOfficePres();

    String clientBureauTrailNoExtractorOfficeTel();

    String clientBureauTrailNoExtractorLoyaltyPres();

    String clientBureauTrailNoExtractorLoyaltyTel();

    String clientWithPaymentEnabledAndDisabled();

    String clientWithValidPayment();

    String clientWithExternalInstalamentos();

    String clientWithExternalMicroCredit();

    String clientWithColpensionesExtraction();

    String clientPreselectNotResponse();

    String clientWithFopepExtraction();

    String clientWithFiduExtraction();

    String clientWithFerroExtraction();

    String clientWithPositivaExtraction();

    String clientFinancialSector();

    String clientCurrentPayment();

    String clientWithColpensionesExtractExt();

    String clientWithFiduprevisoraExtractExt();

    String clientWithFerrocarrilesExtractExt();

    String clientWithPositivaExtractExt();

    String clientWithFopepExtractExt();

    String extractClientDate1();
    String extractClientDate2();
    String extractClientDate3();
    String extractClientDate4();

    String clientWithProvisionalLevel();
    String clientWithChargeLevel();
    String clientWithTestLevel();
    String clientWithVoidLevel();
    String clientWithunknownLevel();

}