package Software;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
* ProviderReport contains the method to generate a full provider report into a text document
*
* @author Pearson Hunter
*/
public class ProviderReport {
    
    /**
     * recordProvider is a private method to help simplify the code for generateProviderReport
     * @param report is the file being written to
     * @param currProvider is a provider entity containing all of the information that needs to be printed to file here
     */
    private void recordProvider(FileWriter report, ProviderEntity currProvider){
    	try{  
        //print provider data to file
        report.write("Provider Name: " +currProvider.name+ "\n");
        report.write("Provider Number: " +currProvider.providerNum+ " \n");
        report.write("Provider Street Address: " +currProvider.address+ "\n");
        report.write("Provider City: " +currProvider.city+ "\n");
        report.write("Provider State: " +currProvider.state+ "\n");
        report.write("Provider ZIP Code: " +currProvider.zip+ "\n");
        }
        catch(IOException e){ System.out.println("An error occured");}

        return;
    }
    
    /**
     * generateProviderReport prints all of the individual provider reports into 1 file
     * @param report the file being written to
     * @param providerList list of all providers, looped through to create the individual reports
     * @param memberList list of all members, used to find member names from member numbers
     * @param serviceList list of all recorded services, used to write down service information and track total fees owed to providers
     */
    public void generateProviderReport(FileWriter report, ModifyProviderDatabase providerList, ModifyMemberDatabase memberList, RecordService serviceList){
        
        //create variables
        int totalConsultations = 0;
        double totalFees = 0;
        ProviderEntity currProvider;
        ServiceRecordEntity record;
        Member member;


        try{
        report.write("================================================Provider Reports================================================\n\n");
        
        //create all of the reports in 1 file
        for(int i = 0; i < providerList.providerDatabase.size(); i++){

            //initialize totals
            totalConsultations = 0;
            totalFees = 0;

            //find provider entity
            String providerNum = providerList.providerDatabase.get(i).providerNum;
            currProvider = providerList.retrieveProvider(providerNum);

            //header of individual
            report.write("======================================Begin Provider Report: " +currProvider.name+ "======================================\n");

            //write out provider info
            recordProvider(report, currProvider);

            //write out every service
            report.write("---------------------Services Given---------------------\n");
            for(int j = 0; j < serviceList.serviceRecordList.size(); j++){

                //collect and check next record
                record = serviceList.getServiceRecord(j);
                if(!record.providerNumber.equals(providerNum)){
                    continue;
                }

                //find member name
                member = memberList.retrieveMember(record.memberNumber);

                //record info
                report.write("Date of Service: " +record.dateOfService+ "\n");
                report.write("Date and Time data were received: " +record.dateOfRecord+ "\n");
                report.write("Member Name: " +member.memberName+ "\n");
                report.write("Service Code: " +record.serviceCode+ "\n");
                report.write("Fee to be paid: " +record.serviceFee+ "\n");
                report.write("\n");

                //count totals
                totalFees += record.serviceFee;
                totalConsultations++;
            }

            //finish individual report
            report.write("---------------------Totals---------------------\n");
            report.write("Total Consultations: " +totalConsultations+ "\n");
            report.write("Total fees: " +totalFees+ "\n"); 
            report.write("======================================End Provider Report: " +currProvider.name+"======================================\n\n");
        }

        //write ending
        report.write("================================================End Provider Reports================================================\n\n");
        report.write("\n");
        }
        catch(IOException e){ System.out.println("An error occured");}

        return;
    }
}