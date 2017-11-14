/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aston.team15.jumazy;

/**
 *
 * @author brade
 */
public class Weather {
    private int movementMod; 
    private String name; 
    
    public Weather(int mod, String name)
    {
        movementMod = mod;
        this.name = name; 
    }

    public int getMovementMod() {
        return movementMod;
    }
    
     public String getName() {
        return name;
    }
    
    
}
