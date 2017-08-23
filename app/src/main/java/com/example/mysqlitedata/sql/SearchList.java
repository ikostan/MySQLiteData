package com.example.mysqlitedata.sql;

import android.util.Log;
import java.nio.charset.IllegalCharsetNameException;
import static java.lang.Character.isLetter;

/**
 * This class is responsible for the DB creation
 */

public class SearchList{

    private final String TAG = this.getClass().getSimpleName();
    //private Context context;

    private long id;
    private String first_name;
    private String last_name;
    private int tel_num;
    private String address;
    private int is_active; // 0 > for false, 1 > for true;
    private final String booleanError = "Illigal argument. Please enter 0 > false OR 1 > true";
    private final String nameError = "Name contains illegal characters. Please reenter";


    public SearchList(String first_name, String last_name, int tel_num, String address, int is_active) throws Exception {

        Log.i(TAG, "class called");
        this.first_name = setName(first_name);
        this.last_name = setName(last_name);
        this.tel_num = tel_num;
        this.address = setAddress(address);
        setIsActive(is_active);
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public int getTel_num() {
        return tel_num;
    }

    public String getAddress() {
        return address;
    }

    public int getIs_active() {
        return is_active;
    }

    //Set is_active as boolean
    private void setIsActive(int is_active) throws Exception{

        Log.i(TAG, "setIsActive method called");

        if(is_active > 1 || is_active < 0){
            Log.d(TAG, "ERROR: " + booleanError);
            throw new IllegalArgumentException(booleanError);
        }
        else{
            this.is_active = is_active;
        }
    }

    //Test is name valid
    private boolean isNameValid(String name){

        Log.i(TAG, "isNameValid method called");

        boolean isValid = true;

        if(name != null && !name.equals("")){

            for(int i = 0; i < name.length(); i++){
                if(!isLetter(name.charAt(i))){
                    isValid = false;
                    break;
                }
            }
        }
        else{

            isValid = false;
        }

        Log.d(TAG, "isNameValid: " + isValid);
        return isValid;
    }

    //Set name
    private String setName(String name) throws Exception{

        Log.i(TAG, "setName method called");

        if(!isNameValid(name)){

            Log.d(TAG, "ERROR: " + nameError);
            throw new IllegalCharsetNameException(nameError);
        }

        return name;
    }

    //Set address
    private String setAddress(String address) throws Exception{

        Log.i(TAG, "setAddress method called");

        if(address == null || address.equals("")){

            Log.d(TAG, "ERROR: " + "Address can not be empty.");
            throw new IllegalCharsetNameException("Address can not be empty.");
        }

        return address;
    }

    //Returns ID
    public long getId(){
        Log.i(TAG, "getId method called");
        return id;
    }

    //Set ID
    public void setId(long id){
        Log.i(TAG, "setId method called");
        this.id = id;
    }

}
