package cinema.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class Seat {

    int row;
    int column;

    int price;

    // this field should be ignored , lekin ignoring k lie hamen get method pe ignore lagana parha
//    @JsonIgnore
    boolean isPurchased;

//    UUID tokenAssignedAgainstTicket;
//    Token tokenAssignedAgainstTicket;
    String tokenAssignedAgainstTicket;

    public Seat() {
    }

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.isPurchased=false;
        tokenAssignedAgainstTicket=null;
        if(row<=4){
            this.price=10;
        }else this.price=8;


    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @JsonIgnore
    public boolean isPurchased() {
        return isPurchased;
    }

    public void setPurchased(boolean purchased) {
        isPurchased = purchased;
    }

    @JsonIgnore
    public String getTokenAssignedAgainstTicket() {
        return tokenAssignedAgainstTicket;
    }

    public void setTokenAssignedAgainstTicket(String tokenAssignedAgainstTicket) {
        this.tokenAssignedAgainstTicket = tokenAssignedAgainstTicket;
    }
}
