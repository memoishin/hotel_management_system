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
public class Customer extends Registration{
    private String cus_code;
    private String cus_password;
    private String cus_email;

    public String getCus_code() {
        return cus_code;
    }

    public void setCus_code(String cus_code) {
        this.cus_code = cus_code;
    }

    public String getCus_password() {
        return cus_password;
    }

    public void setCus_password(String cus_password) {
        this.cus_password = cus_password;
    }

    public String getCus_email() {
        return cus_email;
    }

    public void setCus_email(String cus_email) {
        this.cus_email = cus_email;
    }
    
    
    
}
