package Software;

/**
 * ServiceRecordEntity contains all of the necessary data to create a servivce record
 * 
 * @author John Byrd
 */


public class ServiceRecordEntity {
	public int servRecordNum;
	public String dateOfRecord;
	public String dateOfService;
	public String memberNumber;
	public String providerNumber;
	public String serviceCode;
	public Double serviceFee;
	public String inComments;
	
	public ServiceRecordEntity(int currrecNum, String dateRec, String dateServ, String memNum, String provNum, String servCode, Double servFee, String comments) {
		servRecordNum = currrecNum;
		dateOfRecord = dateRec;
		dateOfService = dateServ;
		memberNumber = memNum;
		providerNumber = provNum;
		serviceCode = servCode;
		serviceFee = servFee;
		inComments = comments;
	}
}   