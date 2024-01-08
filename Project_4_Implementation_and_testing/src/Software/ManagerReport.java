package Software;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


/**
* ManagerReport contains the methods to create a new manager report
*
* @author Pearson Hunter
*/
public class ManagerReport {

    /**
     * generateManagerReport is the method to lay out all of the providers, their consultations, their fees, and the combined number of consultations, fees, and providers who provided services
     * @param report
     * @param providerList
     * @param serviceList
     */
    public void generateManagerReport(FileWriter report, ModifyProviderDatabase providerList, RecordService serviceList){
        ProviderEntity currProvider;
        ServiceRecordEntity record;
        double providerFee = 0;
        int providerConsultations = 0;
        double totalFee = 0;
        int totalConsultations = 0;
        int totalProviders = 0;
        
        try{
        //write header
        report.write("================================================Manager Report================================================\n\n");
        
        for(int i = 0; i < providerList.providerDatabase.size(); i++){
            providerFee = 0;
            providerConsultations = 0;
            //find the provider entity
            String providerNum = providerList.providerDatabase.get(i).providerNum;
            currProvider = providerList.retrieveProvider(providerNum);

            //find total consultations and fees for each provider
            for(int j = 0; j < serviceList.serviceRecordList.size(); j++){

                //collect and check next record
                record = serviceList.getServiceRecord(j);
                if(!record.providerNumber.equals(providerNum)){
                    continue;
                }

                //count provider totals
                providerConsultations++;
                providerFee += record.serviceFee;
            }

            //write info
            report.write("Provider: " +currProvider.name+ "\n");
            report.write("Provider Consultations: " +providerConsultations+ "\n");
            report.write("Provider Fee: " +providerFee+ "\n");
            report.write("\n");
            
            totalConsultations += providerConsultations;
            totalFee += providerFee;
            if(providerConsultations != 0){
                totalProviders++;
            }
        }

        //write total numbers
        report.write("---------------------Totals---------------------\n\n");
        report.write("Total providers who provided services: " +totalProviders+ "\n");
        report.write("Total Consultations: " +totalConsultations+ "\n");
        report.write("Total Fee: " +totalFee+ "\n");
        report.write("================================================End Report================================================\n");
        report.write("\n");
        }
        catch(IOException e){ System.out.println("An error occured.");}
        
        return;
    }
}