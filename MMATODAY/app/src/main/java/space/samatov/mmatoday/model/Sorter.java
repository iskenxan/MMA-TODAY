package space.samatov.mmatoday.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Sorter {
    public static final String FLYWEIGHT = "Flyweight";
    public static final String BANTAMWEIGHT = "Bantamweight";
    public static final String FEATHERWEIGHT = "Featherweight";
    public static final String LIGHTWEIGHT = "Lightweight";
    public static final String WELTERWEIGHT = "Welterweight";
    public static final String MIDDLEWEIGHT = "Middleweight";
    public static final String LIGHT_HEAVYWEIGHT = "Light Heavyweight";
    public static final String HEAVYWEIGHT = "Heavyweight";
    public static final String W_STRAWWEIGHT = "Women Strawweight";
    public static final String W_BANTAMWEIGHT = "Women Bantamweight";
    public static final String GENDER_MEN = "Male";
    public static final String GENDER_WOMEN = "Female";

    //return the new array of the fighters from the division specified by devision key
    public static ArrayList<ListItem> getDivision(String divisionKey, ArrayList<Fighter> fighters) {
        ArrayList<ListItem> division = new ArrayList<ListItem>();
        for (Fighter fighter : fighters) {
            if (fighter.getmWeightClass().equals(divisionKey))
                division.add(fighter);
        }
        return division;
    }

    public static ArrayList<Fighter> getGender(String gender, ArrayList<Fighter> fighters) {
        ArrayList<Fighter> list = new ArrayList<Fighter>();
        if (gender == GENDER_WOMEN)
            for (Fighter fighter : fighters) {
                if (fighter.getmWeightClass().equals(W_STRAWWEIGHT) || fighter.getmWeightClass().equals(W_BANTAMWEIGHT))
                    list.add(fighter);
            }
        else if (gender == GENDER_MEN) {
            for (Fighter fighter : fighters) {
                if (!fighter.getmWeightClass().equals(W_STRAWWEIGHT) && !fighter.getmWeightClass().equals(W_BANTAMWEIGHT))
                    list.add(fighter);
            }
        }
        return list;
    }

    //special method used in the List fragment for men division for sorting the list results based on the selected spinner item
    public static  ArrayList<ListItem> getSpinnerSelectedDivisionMen(int spinnerPosition,ArrayList<Fighter> fighters){
        ArrayList<ListItem> list=new ArrayList<ListItem>();
        switch (spinnerPosition){
            case 0:
                list=getDivision(FLYWEIGHT,fighters);
                break;
            case 1:
                list=getDivision(BANTAMWEIGHT,fighters);
                break;
            case 2:
                list=getDivision(FEATHERWEIGHT,fighters);
                break;
            case 3:
                list=getDivision(LIGHTWEIGHT,fighters);
                break;
            case 4:
                list=getDivision(WELTERWEIGHT,fighters);
                break;
            case 5:
                list=getDivision(MIDDLEWEIGHT,fighters);
                break;
            case 6:
                list=getDivision(LIGHT_HEAVYWEIGHT,fighters);
                break;
            case 7:
                list=getDivision(HEAVYWEIGHT,fighters);
                break;
        }
        return list;
    }

    public static ArrayList<ListItem> getSpinnerSelectedDivionWomen(int spinnerPosition,ArrayList<Fighter> fighters){
        ArrayList<ListItem> list=new ArrayList<ListItem>();
        switch (spinnerPosition){
            case 0:
                list=getDivision(W_STRAWWEIGHT,fighters);
                break;
            case 1:
                list=getDivision(W_BANTAMWEIGHT,fighters);
                break;
        }
        return list;
    }

    public static  ArrayList<ListItem> sortAlphabetically(ArrayList<ListItem> fighters){
        ArrayList<ListItem> list=fighters;
         Comparator<ListItem> ALPHABETICAL_ORDER = new Comparator<ListItem>() {
             @Override
             public int compare(ListItem listItem, ListItem t1) {
                 Fighter f1= (Fighter) listItem;
                 Fighter f2=(Fighter) t1;
                 int res = String.CASE_INSENSITIVE_ORDER.compare(f1.getLastName(), f2.getLastName());
                 if (res == 0) {
                     res = f1.getLastName().compareTo(f2.getLastName());
                 }
                 return res;
             }

        };

        Collections.sort(list, ALPHABETICAL_ORDER);
        ArrayList<ListItem> listItems=new ArrayList<>();
        Fighter fighter1= (Fighter) list.get(0);
        Fighter champ=new Fighter();
        ListGroupHeaders groupName=new ListGroupHeaders(fighter1.getLastName().charAt(0)+"");
        listItems.add(groupName);
        for (ListItem item:fighters){
            Fighter fighter= (Fighter) item;
            String groupChar= fighter.getLastName().charAt(0)+"";
            if(!groupName.getmGroupName().equals(groupChar)){
                groupName=new ListGroupHeaders(fighter.getLastName().charAt(0)+"");
                listItems.add(groupName);
            }
            if(fighter.isTitleHolder())
                champ=fighter;
            else
            listItems.add(fighter);
        }
        listItems.add(0,champ);
        return listItems;
    }


}
