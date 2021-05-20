package sample.Models;

import sample.DbHandler;

public class PseudoApartmentTypes {
    private String[] admissibleTypesOfApartments;
    private static PseudoApartmentTypes pseudoApartmentTypes;

    private PseudoApartmentTypes() {}

    public String[] getTypesArray() {
        if (admissibleTypesOfApartments == null) {
            admissibleTypesOfApartments = new String[] {"Люкс", "Полулюкс", "Одноместный", "Двуместный", "Трёхместный", "Четырёхместный", "Пятиместный", "Шестиместный"};
        }
        return admissibleTypesOfApartments;
    }

    public static PseudoApartmentTypes getApartmentsTypes() {
        if(pseudoApartmentTypes == null) {
            pseudoApartmentTypes = new PseudoApartmentTypes();
        }
        return pseudoApartmentTypes;
    }
}
