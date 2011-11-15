package com.exigen.ipb.selenium.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Available options to choose for Broad Line Of Business
 * @author mulevicius
 * @since 3.9
 */
public enum LOB {
	
	Agriculture_Applicant_Information_AL1_only("Agriculture Applicant Information (AL1 only)"),
	Accident_and_Health("Accident and Health"),
	Agriculture_Liability("Agriculture Liability"),
	Agriculture_Scheduled_and_Unscheduled_Personal_Property("Agriculture Scheduled and Unscheduled Personal Property"),
	Agriculture_Property("Agriculture Property"),
	Aircraft("Aircraft"),
	Airport_Fixed_Base_Operator("Airport & Fixed Base Operator"),
	Agriculture_Package("Agriculture Package"),
	Aircraft_Products_Liability("Aircraft Products Liability"),
	Accounts_ReceivableValuable_Papers("Accounts Receivable/Valuable Papers"),
	Automobile("Automobile"),
	Automobile_Business("Automobile - Business"),
	Automobile_Personal("Automobile - Personal"),
	Aviation_Package("Aviation Package"),
	Boiler_and_Machinery("Boiler and Machinery"),
	Not_Applicable_Blank("Not Applicable (Blank)"),
	Miscellaneous_Bond("Miscellaneous Bond"),
	Watercraft_small_boat("Watercraft (small boat)"),
	Business_Owners_Policy("Business Owners Policy"),
	Business_Owners_Policy_Liability("Business Owners Policy Liability"),
	Business_Owners_Policy_Property("Business Owners Policy Property"),
	Commercial_Aviation("Commercial Aviation"),
	Contractors_Equipment_Floater("Contractor's Equipment Floater"),
	Commercial_Fire("Commercial Fire"),
	Farm_Owners("Farm Owners"),
	General_Liability("General Liability"),
	Commercial_Articles("Commercial Articles"),
	Ocean_Marine("Ocean Marine"),
	Contract("Contract"),
	Commercial_Package("Commercial Package"),
	Commercial_Multi_Peril("Commercial Multi Peril"),
	Crime("Crime"),
	Crime_includes("Crime (includes"),
	Dwelling_Fire("Dwelling Fire"),
	Disability("Disability"),
	Directors_And_Officers("Directors And Officers"),
	Computers("Computers"),
	Employers_Liability("Employers Liability"),
	Errors_and_Omissions("Errors and Omissions"),
	Employment_Practices_Liability_Insurance("Employment Practices Liability Insurance"),
	Earthquake("Earthquake"),
	Equipment_Floater("Equipment Floater"),
	Excess_Liability("Excess Liability"),
	Fidelity("Fidelity"),
	Fiduciary("Fiduciary"),
	Fine_Arts("Fine Arts"),
	Flood("Flood"),
	Garage_and_Dealers("Garage and Dealers"),
	Glass("Glass"),
	Private_Hangar("Private Hangar"),
	Home_Based_Business("Home Based Business"),
	Homeowners("Homeowners"),
	Installation_Builders_Risk("Installation/Builders Risk"),
	Inland_Marine("Inland Marine"),
	Inland_Marine_commercial("Inland Marine (commercial)"),
	Inland_Marine_personal_lines("Inland Marine (personal lines)"),
	Internet_Liability("Internet Liability"),
	Judicial("Judicial"),
	Kidnap_and_Ransom("Kidnap and Ransom"),
	License_and_Permit("License and Permit"),
	Lost_Instrument("Lost Instrument"),
	Mobile_Homeowners("Mobile Homeowners"),
	Medical_Malpractice("Medical Malpractice"),
	Motor_Truck_Cargo("Motor Truck Cargo"),
	Motorcycle("Motorcycle"),
	New_Financial_Guarantee("New Financial Guarantee"),
	Other_Liability("Other Liability"),
	Valuable_papers("Valuable papers"),
	Physicians_and_Surgeons("Physicians and Surgeons"),
	Package("Package"),
	Professional_Liability("Professional Liability"),
	Miscellaneous_Professional_Liability("Miscellaneous Professional Liability"),
	Personal_Package("Personal Package"),
	Property_includes("Property (includes"),
	Commercial_Property("Commercial Property"),
	Public_Official("Public Official"),
	Recreational_Vehicles("Recreational Vehicles"),
	Small_Commercial_Package("Small Commercial Package"),
	Scheduled_Property("Scheduled Property"),
	Small_FarmRanch("Small Farm/Ranch"),
	Signs("Signs"),
	Special_MultiPeril("Special Multi-Peril"),
	Surety("Surety"),
	Transportation("Transportation"),
	Truckers("Truckers"),
	Umbrella_Commercial("Umbrella - Commercial"),
	Umbrella("Umbrella"),
	Umbrella_Personal_excess_indemnity("Umbrella - Personal (excess indemnity)"),
	Unknown("Unknown"),
	Workers_Comp_Marine("Workers Comp Marine"),
	Wind_Policy("Wind Policy"),
	Workers_Compensation("Worker's Compensation"),
	Workers_Compensation_Participating("Workers Compensation Participating"),
	Workplace_Violence("Workplace Violence");

	public String getName() {
		return name;
	}
	
	private LOB(String name) {
		this.name = name;
	}
	
	private final String name;

	@Override
	public String toString() {
		return name;
	}
}
