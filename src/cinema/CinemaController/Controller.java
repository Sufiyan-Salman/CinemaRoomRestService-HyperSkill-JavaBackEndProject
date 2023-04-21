package cinema.CinemaController;

import cinema.Model.Cinema;
import cinema.Model.Seat;
import cinema.Model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@RestController
public class Controller {

    @Autowired
    Cinema cinema;


    @GetMapping("/seats")
//    public List<Cinema.seat> getSeats(){
    public Cinema getSeats(){
//    public ResponseEntity<Cinema> getSeats(){
        cinema.setAvailable_seats();
        System.out.println(cinema.getAvailable_seats().toString());
//        return ResponseEntity.ok().body(cinema);
//        return ResponseEntity.ok().body(cinema.getAvailable_seats());
        return cinema;
        //it is not returning object
//        return new ArrayList<>(List.of(cinema.getAvailable_seats().get(1)));
    }

    @GetMapping("/hi")
    public ResponseEntity<Object> Hello(){

        return ResponseEntity.ok().body("HI");
    }

//    @PostMapping("/purchase{row}{column}")
    @PostMapping("/purchase")

    public ResponseEntity purchase(@RequestBody Seat seat){
        //custom request handler was needed here , to send error in json
        //check if seat exists
        if(cinema.findSeat(seat.getRow(), seat.getColumn())==null  ||seat.getRow() > cinema.total_rows || seat.getColumn() > cinema.total_columns || seat.getRow()<0 || seat.getColumn()<0 /*| row==null || column==null*/){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cause description here");
//            ye upr wala triqa exception throw k sath respond krne k lie hai in json format
//            return ResponseEntity.status(HttpStatus.valueOf(400)).body("{error: "+"The number of a row or a column is out of bounds!}");
          //custom request handler was needed here , sirf value of kar k code dalen(as above) ya ,Badreq (as below) , ek hi bt hai
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error","The number of a row or a column is out of bounds!"));
//            return ResponseEntity.status(HttpStatus.valueOf(400)).build(); // agar ye likhenge Responseentity<Seat> b return kra skte hen
        }


        //check if seat available to purchase

        Seat result= cinema.Purchase(seat.getRow(), seat.getColumn());
        if (result != null) {
//            return ResponseEntity.ok(result);
            result.setTokenAssignedAgainstTicket(UUID.randomUUID().toString());
            return ResponseEntity.ok(Map.of("token", result.getTokenAssignedAgainstTicket(),"ticket",result));
              //ye wali return ham stage 3 me use krenge
            // iske sath token b return krwana hai hmen
        }
        else
//          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{error: "+"The ticket has been already purchased!}");
          //custom request handler was needed here
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error","The ticket has been already purchased!"));

    }

    @PostMapping("/return")
    public ResponseEntity refundTicket(@RequestBody Token token){
    //identify the token ,, match with seat
        Seat result= cinema.findSeat(token.getToken());
        if (result!=null) return ResponseEntity.ok(Map.of("returned_ticket",result));
        // if okk then change that seat purchased status and send return  Map.of("returned_ticket",seat)
        else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error","Wrong token!"));

    }
    @PostMapping("/stats{password}" )
    public ResponseEntity showStats(/*@RequestParam String password , yahan hamen value islie nai likha q k method ka parameter aur requeust ka parameter is same */ @RequestParam(value = "password", required = false) String password){
        //agar ham required ko false na krte to ye yahi pe error deta rehta k urle me pass chahiye aur hamen mila nai , and we wouldnt be able to handle it on our own
        System.out.println(password);
//        if (cinema.secret_key.equals(password)) return ResponseEntity.ok(Map.of("current_income",Cinema.current_income,
        if (password!=null) return ResponseEntity.ok(Map.of("current_income",Cinema.current_income,
                "number_of_available_seats",Cinema.number_of_available_seats,
                "number_of_purchased_tickets" , Cinema.number_of_purchased_tickets));
        // if okk then change that seat purchased status and send return  Map.of("returned_ticket",seat)
        else return ResponseEntity.status(HttpStatus.valueOf(401)).body(Map.of("error","The password is wrong!"));

    }

}
