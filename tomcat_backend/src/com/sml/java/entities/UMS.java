package com.sml.java.entities;
public class UMS {

	Integer id;
	String drugName;
	String drugDescription;
	String drugRoute;
	String drugDose;
	boolean breakfast;
	boolean lunch;
	boolean dinner;
	boolean bedtime;
	String text;

	public UMS(Integer id, String drugName, String drugDescription, String drugRoute, String drugDose, String text){
		this.id = id;
		this.drugName = drugName;
		this.drugDescription = drugDescription;
		this.drugRoute = drugRoute;
		this.drugDose = drugDose;
		parse(text);
	}
	
	public UMS(String text){
		parse(text);
	}
	
	private static final String	QD = "qd";		//once per day
	private static final String	Q1D = "q1d";	//once per day
	private static final String BID = "bid";	//twice per day
	private static final String TID = "tid";	//three times daily
	private static final String QID = "qid";	//4 times daily
	private static final String QHS = "qhs";	//before bed
	private static final String AC = "ac";		//before meals
	private static final String PC = "pc";		//after meals
	private static final String Q8H = "q8h";	//3 times daily
	private static final String Q12H = "q12h";  //twice daily
	private static final String Q6H = "q6h";	//4 times daily
	private static final String BED = "bed";
	private static final String SLEEP = "sleep";
	private static final String BREAKFAST = "breakfast";
	private static final String MORNING = "morning";
	private static final String AM = "am";
	private static final String NOON = "noon";
	private static final String LUNCH = "lunch";
	private static final String PM = "pm";
	private static final String NIGHT = "night";
	private static final String EVENING = "evening";
	private static final String DINNER = "dinner";
	
	private static final String[] THREE_TIMES = new String[]{AC, PC, Q8H, TID };
	private static final String[] FOUR_TIMES = new String[]{Q6H, QID};
	private static final String[] TWO_TIMES = new String[]{BID, Q12H};
	private static final String[] ONCE_NIGHTLY = new String[]{QHS, BED, SLEEP, NIGHT};
	private static final String[] ONCE_LUNCH = new String[]{NOON, LUNCH, PM};
	private static final String[] ONCE_DINNER = new String[]{EVENING, DINNER};
	private static final String[] ONCE_DAILY = new String[]{Q1D, QD, BREAKFAST, MORNING, AM};
	
	public void parse(String text){
		this.text = text;
		for (String str : ONCE_LUNCH){
			if (text.toLowerCase().contains(str.toLowerCase())){
				this.lunch = true;
				return;
			}
		}
		for (String str : ONCE_DINNER){
			if (text.toLowerCase().contains(str.toLowerCase())){
				this.dinner = true;
				return;
			}
		}
		for (String str : ONCE_NIGHTLY){
			if (text.toLowerCase().contains(str.toLowerCase())){
				this.bedtime = true;
				return;
			}
		}
		for (String str : ONCE_DAILY){
			if (text.toLowerCase().contains(str.toLowerCase())){
				this.breakfast = true;
				return;
			}
		}
		for (String str : TWO_TIMES){
			if (text.toLowerCase().contains(str.toLowerCase())){
				this.breakfast = true;
				this.dinner = true;
				return;
			}
		}
		for (String str : THREE_TIMES){
			if (text.toLowerCase().contains(str.toLowerCase())){
				this.breakfast = true;
				this.lunch = true;
				this.dinner = true;
				return;
			}
		}
		for (String str : FOUR_TIMES){
			if (text.toLowerCase().contains(str.toLowerCase())){
				this.breakfast = true;
				this.lunch = true;
				this.dinner = true;
				this.bedtime = true;
				return;
			}
		}
	}

	public boolean isBreakfast() {
		return breakfast;
	}

	public void setBreakfast(boolean breakfast) {
		this.breakfast = breakfast;
	}

	public boolean isLunch() {
		return lunch;
	}

	public void setLunch(boolean lunch) {
		this.lunch = lunch;
	}

	public boolean isDinner() {
		return dinner;
	}

	public void setDinner(boolean dinner) {
		this.dinner = dinner;
	}

	public boolean isBedtime() {
		return bedtime;
	}

	public void setBedtime(boolean bedtime) {
		this.bedtime = bedtime;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
