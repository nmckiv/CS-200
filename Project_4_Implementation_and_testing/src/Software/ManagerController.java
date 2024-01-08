package Software;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Manager Controller is the class used to call main accounting procedure and to generate an individual report
 * 
 * @author Pearson Hunter
 */
public class ManagerController {
    /**
     * mainAccountingProcedure takes in a file along with each database and then files the file with every report: member, provider, and then manager
     * @param report the file being written to
     * @param memberList member database
     * @param providerList provider  database
     * @param serviceList recorded service database
     * @param codeList provider directory
     */
    public static Boolean mainAccountingProcedure(String path, ModifyMemberDatabase memberList, ModifyProviderDatabase providerList, RecordService serviceList, ProviderController codeList){
        
        //create variables
        MemberReport member = new MemberReport();
        ProviderReport provider = new ProviderReport();
        ManagerReport manager = new ManagerReport();
        
        //=============================================================
        //create file and begin report
        File generate = new File(path);  
        try{
        generate.createNewFile();
        FileWriter report = new FileWriter(generate);
        report.write("==========================================================Main Accounting Procedure==========================================================\n\n");
        //=============================================================
        
        //=============================================================
        //run each report generation
        member.generateMemberReport(report, memberList, serviceList, providerList, codeList);
        provider.generateProviderReport(report, providerList, memberList, serviceList);
        manager.generateManagerReport(report, providerList, serviceList);
        //=============================================================

        //=============================================================
        report.write("==========================================================Main Accounting Procedure Complete==========================================================\n");
        report.close();
        //=============================================================
        }
        catch(IOException e) {System.out.println("an error occured");}
        
        return true;
    }

    /**
     * generates an individual report for the manager based off of decision
     * @param decision either member, provider, or manager to decide what report is made
     * @param report the file writer to say where to write the report
     * @param memberList member database
     * @param providerList provider database
     * @param serviceList recorded service database
     * @param codeList provider directory
     */
    public static Boolean generateIndividualReport(String decision, String path, ModifyMemberDatabase memberList, ModifyProviderDatabase providerList, RecordService serviceList, ProviderController codeList){
        
        //make sure string is lower case
        decision.toLowerCase();
        
        //create file
        try {
        File generate = new File(path);
        generate.createNewFile();
        FileWriter report = new FileWriter(generate);
        
        //generate individual report type
        switch(decision){
            //create member report
            case("Member Report"):
                MemberReport member = new MemberReport();
                member.generateMemberReport(report, memberList, serviceList, providerList, codeList);
                break;
            //create provider report
            case("Provider Report"):
                ProviderReport provider = new ProviderReport();
                provider.generateProviderReport(report, providerList, memberList, serviceList);
                break;
            //create manager report
            case("Manager Report"):
                ManagerReport manager = new ManagerReport();
                manager.generateManagerReport(report, providerList, serviceList);
                break;
            default:
                System.out.println("this type of report does not exist\n");
                return false;
        }
        }
        catch(IOException e) {System.out.println("an error occured");}

        return true;
    }

}

