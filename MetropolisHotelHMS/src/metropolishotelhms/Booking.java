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
public class Booking {
    private String bookID;
    private String room_num;
    private String book_pay;
    private String pay_due;
    private String book_time;
    private String book_status;

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getRoom_num() {
        return room_num;
    }

    public void setRoom_num(String room_num) {
        this.room_num = room_num;
    }

    public String getBook_pay() {
        return book_pay;
    }

    public void setBook_pay(String book_pay) {
        this.book_pay = book_pay;
    }

    public String getPay_due() {
        return pay_due;
    }

    public void setPay_due(String pay_due) {
        this.pay_due = pay_due;
    }

    public String getBook_time() {
        return book_time;
    }

    public void setBook_time(String book_time) {
        this.book_time = book_time;
    }

    public String getBook_status() {
        return book_status;
    }

    public void setBook_status(String book_status) {
        this.book_status = book_status;
    }
    
    
    
}
