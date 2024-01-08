package Software;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * MemberReport contains the method to generate a full member report into a text document
 * 
 * @author Pearson Hunter
 */
public class MemberReport{

    /**
     * generateMemberReport is a public function that prints every members' report out into 1 text document
     * 
     * @param report the text doc being printed to
     * @param memberList the list of all members
     * @param serviceList the list of all recorded services
     * @param providerList the list of providers, used to grab provider names in recordServices
     * @param codeList the list of all service codes, used to grab the service name
     */
    public void generateMemberReport(FileWriter report, ModifyMemberDatabase memberList, RecordService serviceList, ModifyProviderDatabase providerList, ProviderController codeList){
        try {
        	//=============================================================
            //FileWriter report = new FileWriter(generate);
            ServiceRecordEntity record;
            ProviderEntity provider;
            String service;
            //=============================================================
            
        //write header
        report.write("================================================Member Reports================================================\n\n");
        Member currMember;
        for(int i = 0; i < memberList.members.size(); i++){
        	//=============================================================
        	//find the member entity
            String memberNum = memberList.members.get(i).memberNumber;
            currMember = memberList.retrieveMember(memberNum);
            //=============================================================
            
            
            //=============================================================
            //header of individual
            report.write("======================================Begin Member Report: " +currMember.memberName+ "======================================\n");
            report.write("Member Name: " +currMember.memberName+ "\n");
            report.write("Member Number: " +currMember.memberNumber+ "\n");
            report.write("Member Street Address: " +currMember.memberAddress+ "\n");
            report.write("Member City: " +currMember.memberCity+ "\n");
            report.write("Member State: " +currMember.memberState+ "\n");
            report.write("Member ZIP code: " +currMember.memberZIP+ "\n");
            //=============================================================
            
            
            //=============================================================
            report.write("---------------------Services Received---------------------\n");

            //record every service
            for(int j = 0; j < serviceList.serviceRecordList.size(); j++){
                //collect and check next record
                record = serviceList.getServiceRecord(j);
                String recordNum;
                recordNum = record.memberNumber;
                if(!(memberNum.equals(recordNum))) {continue;}
                
                //find provider name and service name
                provider = providerList.retrieveProvider(record.providerNumber);
                int serviceNum = Integer.parseInt(record.serviceCode);
                service = codeList.getService(serviceNum);

                //write out info
                report.write("Date: " +record.dateOfService+ "\n");
                report.write("Provider: " +provider.name+ "\n");
                report.write("Service Name: " +service+ "\n");
                report.write("\n");
            }
            //=============================================================
            //end of individual report
            report.write("======================================End Member Report: " +currMember.memberName+ " ======================================\n\n");
        } 

        //write ending
        report.write("================================================End Member Reports================================================\n\n");
        report.write("\n");
        }
        catch(IOException e) {System.out.println("an error occured");}
 
        return;
    }
}