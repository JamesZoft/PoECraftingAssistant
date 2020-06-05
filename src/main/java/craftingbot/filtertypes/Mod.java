/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package craftingbot.filtertypes;

import craftingbot.Modifier;
import craftingbot.Utility;
import craftingbot.Item;
import java.io.Serializable;

/**
 *
 * @author charl
 */
public class Mod implements Serializable {
    public Modifier assocModifier;
    public String name; // Form of: #% increased movement speed
    public Id ID = new Id(); // Form of: min 25, max 35
    
//    public String converged;
    
    public Mod(Modifier assocModifier, String name, int... id)
    {
        this.assocModifier = assocModifier;
//        this.name = name.toLowerCase();
        this.name = name;
//        this.ids = new Id[id.length / 2];
               
        for (int i=0; i<id.length; i+=2)
            ID = new Id(id[i/2], id[i/2+1]);
        
        if (assocModifier != null && assocModifier.getModGenerationTypeID() == -1) // Pseudo mod. Default min should be 1
        {
            ID = new Id(1);
        }
    }
    
//    public void updateMin()
//    {
//        if (assocModifier.getModGenerationTypeID() == -1 && ID.min == -100000)
//        {
//            ID.min = 0;
//        }
//    }
    
    public Mod dupe()
    {   
        return new Mod(this.assocModifier, this.name, ID.toArr());
    }
    
    public double valueOn(Modifier psm)
    {
        System.out.println("'" + this.name + "'-'" + psm.getStr() + "'");
        if (this.name.equals(psm.getStr()))
        {
            return psm.rolls[0];
        }
        return 0;
    }
    
    public boolean hit(Modifier em)
    {
//        System.out.println("'" + this.name + "'-'" + em.getStr() + "'");
        if (this.name.equals(em.getStr()))
        {
            if (ID.valid(em.rolls))
                return true;
        }
        
        return false;
    }
    
    private double total(String input, String[] inputLines, String... mods)
    {
        double total = 0;
        for (String modName : mods)
        {
            if (input.contains(modName))
            {
                for (String s : inputLines)
                {
                    if (s.contains(modName))
                    {                

                        String[] inputLinesSep = s.split("[*]{1}");

                        s = inputLinesSep[0];
                        double value = Double.valueOf(inputLinesSep[1]);
                        total += value;
                    }
                }
            }
        }
        
        return total;
    }
    
    public void print()
    {
        System.out.println("        \"" + name + "\"");
        System.out.println("            ids: " + ID.min + ", " + ID.max);
    }
    
    public String view()
    {
        String str = name + "\n";
        str += ("               min: " + ID.min + ", max: " + ID.max);
        
        return str;
    }
}