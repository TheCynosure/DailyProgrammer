package Day1;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Created by jack on 10/24/16.
 */
public class Main {

    private static String[] types = new String[] {
        "normal",
        "fire",
        "water",
        "electric",
        "grass",
        "ice",
        "fighting",
        "poison",
        "ground",
        "flying",
        "psychic",
        "bug",
        "rock",
        "ghost",
        "dragon",
        "dark",
        "steel",
        "fairy"
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What is the Attack type: ");
        String attackType = "";
        while(!contains(types, attackType))
            attackType = scanner.next();
        System.out.println("What is the Defense type: ");
        String defenseType  = "";
        while(!contains(types, defenseType))
            defenseType = scanner.next();
        System.out.println("Loading PokeChart");
        JSONParser parser = new JSONParser();
        try {
            JSONArray array = (JSONArray) parser.parse(new FileReader("DailyProgrammer/src/main/java/Day1/types.json"));
            Iterator iterator = array.iterator();
            while(iterator.hasNext()) {
                JSONObject type = (JSONObject) iterator.next();
                //Checking if this is the type that the user said was the attack type.
                if(type.get("name").toString().toLowerCase().equals(attackType.toLowerCase())) {
                    System.out.println(getMultiplier(type, defenseType.toLowerCase()));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean contains(String[] arr, String message) {
        for(String string: arr) {
            if(string.equals(message.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private static float getMultiplier(JSONObject typeList, String defenseType) {
        JSONArray typeLists[] = new JSONArray[]{ (JSONArray) typeList.get("immunes"), (JSONArray) typeList.get("strengths"), (JSONArray) typeList.get("weaknesses")};
        for(int i = 0; i < typeLists.length; i++) {
            if(typeLists[i].contains(defenseType.substring(0,1).toUpperCase() + defenseType.substring(1))) {
                switch(i) {
                    case 0:
                        return 0;
                    case 1:
                        return 2;
                    case 2:
                        return 0.5f;
                    default:
                        return 1;
                }
            }
        }
        return 1;
    }
}
