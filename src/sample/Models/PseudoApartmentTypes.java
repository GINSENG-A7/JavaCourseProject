package sample.Models;

public class PseudoApartmentTypes {
    private static String[] admissibleTypesOfApartments;

    private PseudoApartmentTypes() {}

    public static String[] getTypesArray() {
        if (admissibleTypesOfApartments == null) {
            admissibleTypesOfApartments = new String[] {"Люкс", "Полулюкс", "Одноместный", "Двуместный", "Трёхместный", "Четырёхместный", "Пятиместный", "Шестиместный"};
        }
        return admissibleTypesOfApartments;
    }
}
