package cinema.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Cinema {

    public int total_rows;
    public int total_columns;
//    public Map<Integer,seat> available_seats;

    public ArrayList<Seat> available_seats;

    public static int current_income;
    public static int number_of_available_seats;
    public static int number_of_purchased_tickets;

    @JsonIgnore
    public final String secret_key="ADMIN";
//    public Seat seat;


//is inner class ki waje se serializer not found ki exception arahi thi , ise hal krne kl ie json import kr k serialize hame khudse krna prhta, jab k mene
    //model k package me ek seat ki class banadi and then us se sab sahi chal gaya
//   public class seat {
//        int row;
//        int column;
//
//        public seat(int row, int column) {
//            this.row = row;
//            this.column = column;
//        }
//
//        @Override
//        public String toString() {
//            return "{" +
//                    "row=" + row +
//                    ", column=" + column +
//                    '}';
//        }
//    }

    public Cinema() {
        current_income=0;
        number_of_available_seats= 81;
        number_of_purchased_tickets=0;
//       this.seat=new Seat(1,2);
    }


//    public Cinema(int total_rows, int total_columns, Map<Integer,seat> available_seats) {
    public Cinema(int total_rows, int total_columns, ArrayList<Seat> available_seats) {
        this.total_rows = total_rows;
        this.total_columns = total_columns;
        current_income=0;
        number_of_available_seats= 81;
        number_of_purchased_tickets=0;
//        this.available_seats = available_seats;
    }

    public int getTotal_rows() {
        return total_rows;
    }

    public void setTotal_rows(int total_rows) {
        this.total_rows = total_rows;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public void setTotal_columns(int total_columns) {
        this.total_columns = total_columns;
    }

//    public Map<Integer,seat> getAvailable_seats() {
    public ArrayList<Seat> getAvailable_seats() {
//        System.out.println(available_seats);
        return available_seats;
    }

//    public void setAvailable_seats(Map<Integer,seat> available_seats)
    public void setAvailable_seats(/*ArrayList< seat> available_seats*/) {

//        ArrayList avai
        total_rows=9;
        total_columns=9;
//        this.available_seats=null;
        this.available_seats=new ArrayList<>();
        for (int i = 1; i <=total_rows ; i++) {

            for (int j = 1; j <=total_columns ; j++) {
                 available_seats.add(new Seat(i,j))    ;

            }

        }
//        this.available_seats = available_seats; // jab paramet ko uncomment krenge , tab isko use krenge

    }

    @Override
    public String toString() {
        return "Cinema{" +
                "total_rows=" + total_rows +
                ", total_columns=" + total_columns +
//                ", available_seats=" + available_seats.toString() +
                '}';
    }

//    public Seat findSeat(Token token) { // for refund purpose
    public Seat findSeat(String token) { // for refund purpose
        Seat returnValue=null;
        for (   Seat storedSeat: getAvailable_seats() ) {

//            if(givenSeat.row == storedSeat.getRow() && givenSeat.column == storedSeat.getColumn()){
            if(storedSeat.tokenAssignedAgainstTicket!=null  && storedSeat.tokenAssignedAgainstTicket.equals(token)){
                   storedSeat.setTokenAssignedAgainstTicket(null);
                   storedSeat.setPurchased(false);
                   number_of_purchased_tickets--;
                   number_of_available_seats++;
                   current_income-=storedSeat.price;

                   returnValue=storedSeat;

            }

        }
        return returnValue;
    }

    public Seat findSeat(int row, int column) {
        Seat returnValue=null;
        for (   Seat storedSeat: getAvailable_seats() ) {

//            if(givenSeat.row == storedSeat.getRow() && givenSeat.column == storedSeat.getColumn()){
            if(row == storedSeat.getRow() && column == storedSeat.getColumn()){


                    returnValue=storedSeat;

            }

        }
        return returnValue;

    }

//    public boolean isSeatAvailable(Seat givenSeat){
    //purchase
    public Seat Purchase(int row , int col){
        Seat receivedSeat=findSeat(row,col);

        if (receivedSeat != null) {

            if(!receivedSeat.isPurchased){
                receivedSeat.setPurchased(true);
                number_of_purchased_tickets++;
                number_of_available_seats--;
                current_income+=receivedSeat.price;
            }else {
                receivedSeat=null;
            }

        }

        return receivedSeat;
    }
}
