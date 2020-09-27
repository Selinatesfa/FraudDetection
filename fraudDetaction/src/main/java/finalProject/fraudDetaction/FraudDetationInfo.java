package finalProject.fraudDetaction;

import java.io.Serializable;

public class FraudDetationInfo implements Serializable {

	private Integer step;
	
	private String type;
	
	private Double amount;
	
	private String numeOrig;
	
	private Double oldbalanceOrg;

	private Double newBalanceOrig;
	
	private String nameDest;
	
	private Double oldbalanceDest;
	
	private Double newbalanceDest;
	
	private Integer isFraud;
	
	private Integer isFlaggedFraud;
	
	public FraudDetationInfo() {
		// TODO Auto-generated constructor stub
	}

	public FraudDetationInfo(Integer step, String type, Double amount,
			String numeOrig, Double oldbalanceOrg, Double newBalanceOrig,
			String nameDest, Double oldbalanceDest, Double newbalanceDest,
			Integer isFraud, Integer isFlaggedFraud) {
		this.step = step;
		this.type = type;
		this.amount = amount;
		this.numeOrig = numeOrig;
		this.oldbalanceOrg = oldbalanceOrg;
		this.newBalanceOrig = newBalanceOrig;
		this.nameDest = nameDest;
		this.oldbalanceDest = oldbalanceDest;
		this.newbalanceDest = newbalanceDest;
		this.isFraud = isFraud;
		this.isFlaggedFraud = isFlaggedFraud;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getNumeOrig() {
		return numeOrig;
	}

	public void setNumeOrig(String numeOrig) {
		this.numeOrig = numeOrig;
	}

	public Double getOldbalanceOrg() {
		return oldbalanceOrg;
	}

	public void setOldbalanceOrg(Double oldbalanceOrg) {
		this.oldbalanceOrg = oldbalanceOrg;
	}

	public Double getNewBalanceOrig() {
		return newBalanceOrig;
	}

	public void setNewBalanceOrig(Double newBalanceOrig) {
		this.newBalanceOrig = newBalanceOrig;
	}

	public String getNameDest() {
		return nameDest;
	}

	public void setNameDest(String nameDest) {
		this.nameDest = nameDest;
	}

	public Double getOldbalanceDest() {
		return oldbalanceDest;
	}

	public void setOldbalanceDest(Double oldbalanceDest) {
		this.oldbalanceDest = oldbalanceDest;
	}

	public Double getNewbalanceDest() {
		return newbalanceDest;
	}

	public void setNewbalanceDest(Double newbalanceDest) {
		this.newbalanceDest = newbalanceDest;
	}

	public Integer getIsFraud() {
		return isFraud;
	}

	public void setIsFraud(Integer isFraud) {
		this.isFraud = isFraud;
	}

	public Integer getIsFlaggedFraud() {
		return isFlaggedFraud;
	}

	public void setIsFlaggedFraud(Integer isFlaggedFraud) {
		this.isFlaggedFraud = isFlaggedFraud;
	}

	@Override
	public String toString() {
		return step + ","
				+type+","
				+ amount + "," 
				+ numeOrig + ","
				+ oldbalanceOrg + ","
				+ newBalanceOrig + ","
				+ nameDest + "," 
				+ oldbalanceDest + ","
				+ newbalanceDest + "," 
				+ isFraud + ","
				+ isFlaggedFraud ;
	}
	
	
	
	
}
