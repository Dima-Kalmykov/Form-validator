package tests.form_examples;

import solution.annotations.*;

import java.util.List;
import java.util.Map;

@Constrained
public class BookingForm {

    @NotNull
    @Size(min = 1, max = 5)
    private List<@NotNull GuestForm> guests;
    @NotNull
    private List<@AnyOf({"TV", "Kitchen", "Toilet", "room"}) String> amenities;

    @NotNull
    @AnyOf({"House", "Hostel"})
    private String propertyType;

    @Positive
    private int roomNumber;

    private Map<@NotNull Integer, @InRange(min=1, max=3) Integer> peopleInRoom;

    public BookingForm(List<GuestForm> guests, List<String> amenities, String propertyType,
                       int roomNumber, Map<Integer, Integer> peopleInRoom) {
        this.guests = guests;
        this.amenities = amenities;
        this.propertyType = propertyType;
        this.roomNumber = roomNumber;
        this.peopleInRoom = peopleInRoom;
    }
}
