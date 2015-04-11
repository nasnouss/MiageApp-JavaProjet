package Modele;

public interface ICSVList {

	String CSV_SEPARATOR = ";";
	
	String CSV_LR = "\r\n";

	String toCSV() throws IllegalArgumentException, IllegalAccessException;
}
