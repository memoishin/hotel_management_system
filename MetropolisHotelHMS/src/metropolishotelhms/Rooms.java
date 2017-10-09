/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metropolishotelhms;

/**
 *
 * @author Moishin
 */
public class Rooms {
    
    private String code;
    private String type;
    private double price;
    private String description;
    

    Rooms(String rcode, String rtype, double rprice, String rdescription){
        this.code = rcode;
        this.type = rtype;
        this.price = rprice;
        this.description = rdescription;
        
    }
    
    Rooms(String rcode){
        this.code = rcode;    
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    
}
