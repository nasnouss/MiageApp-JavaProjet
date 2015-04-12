package Modele;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CSVList<E> extends ArrayList<E> implements ICSVList {

	private static final long serialVersionUID = 1L;

	public CSVList(){
		
	}
	/**
	 * Affiche le fichier au format csv 
	 */
	public String toCSV() throws IllegalArgumentException, IllegalAccessException {

		List<Field> csvFields = new ArrayList<Field>();
		StringBuilder csv = new StringBuilder();

		for (int i = 0; i < this.size(); i++) {

			E element = this.get(i);

			if (i == 0) {
				Field[] fields = element.getClass().getDeclaredFields();

				for (Field field : fields) {
					CSVColumnHeader csvColumnHeader = field.getAnnotation(CSVColumnHeader.class);
					if (csvColumnHeader == null) {
						continue;
					}
					csv.append(csvColumnHeader.name()).append(CSV_SEPARATOR);
					csvFields.add(field);
				}
				csv.append(CSV_LR);
			}

			for (Field field : csvFields) {
				field.setAccessible(true);
				csv.append(field.get(element)).append(CSV_SEPARATOR);
			}
			csv.append(CSV_LR);
		}
		return csv.toString();
	}
}
