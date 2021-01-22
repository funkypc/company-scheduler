package com.charlesplett.companyscheduler;

import static java.lang.Integer.parseInt;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.Appointment;
import model.Item;


/** 
 * Class of shared methods. 
 * @author Charles Plett
 */
public final class SharedUtility {

    /** Opens a child window. 
    The value of all cells in column will be displayed in the currency. 
    @param root The root from FXMLLoader. 
    @param title The title for the child window. 
    @param x The width of the child window, in pixels. 
    @param y The height of the child window, in pixels
    @param wait Wait until window is closed before returning
    */    
    public static void showChildWindow(Parent root, String title, int x, int y, boolean wait){
        Stage stage = new Stage();
        stage.getIcons().add(new Image(App.class.getResourceAsStream("/CS256.png")));
        stage.setTitle(title);
        Scene scene = new Scene(root, x, y);
        stage.setScene(scene);
        if(wait){
            stage.showAndWait();
            return;
        }
        stage.show();
    }
    
    /** Displays Error Alert. 
    @param title A String containing the title for the Alert. 
    @param msg A String containing the message for the Alert. 
    */    
    public static void displayErrorAlert(String title, String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(msg);
        alert.show();
    }
    
    /**
     * Static Class used to instantiate LocalTime Spinner. 
     */
    public static class TimeSpinnerValueFactory extends SpinnerValueFactory<LocalTime> {

        private LocalTime defaultTime() {
            return LocalTime.now().truncatedTo(ChronoUnit.HOURS).plusHours(1);
        }

        /**
         * Decrements time in spinner by 15 minute steps. 
         * @param steps Default value
         */
        @Override
        public void decrement(int steps) {
            LocalTime value = getValue();
            setValue(value == null ? defaultTime() : value.minusMinutes(steps*15));
        }

        /**
         * Increments time in spinner by 15 minute steps.
         * @param steps Default value
         */
        @Override
        public void increment(int steps) {
            LocalTime value = getValue();
            setValue(value == null ? defaultTime() : value.plusMinutes(steps*15));
        }
    }
    
    /**
     * Static Class to Override StringConverter for ObservableList(Item). Especially useful in ComboBox. 
     * <p><strong>A lambda expression is used in this method.</strong></p>
     * <p>
     * The Stream API is used with the lambda expression in order to achieve 
     * higher efficiency through parallel execution.
     * </p>
     * @param items The ObservableList to Override
     * @return Returns new StringConverter. Overrides toString and fromString.
     */
    public static StringConverter itemStringConverter(ObservableList<? extends Item> items){
        // https://stackoverflow.com/questions/35744227/javafx-8-combobox-and-observablelist
        StringConverter<Item> converter = new StringConverter<>() {
            @Override
            public String toString(Item item) {
                String name;
                try {
                    name = item.getName();
                } catch (NullPointerException e) { // Work around for bug: https://bugs.openjdk.java.net/browse/JDK-8150178
                    name = ""; 
                }
                return name;
            }

            @Override
            public Item fromString(String id) {
                return items.stream().filter(item -> item.getId() == Integer.parseInt(id))
                        .collect(Collectors.toList()).get(0);
            }
        };
        return converter;
    }
    
    /**
     * Static Class to Override StringConverter for ObservableList(Appointment). Returns String value of Type. 
     * <p><strong>A lambda expression is used in this method.</strong></p>
     * <p>
     * The Stream API is used with the lambda expression in order to achieve 
     * higher efficiency through parallel execution.
     * </p>
     * @param items The ObservableList to override.
     * @return Returns new StringConverter. Overrides toString and fromString.
     */
    public static StringConverter appointmentTypeStringConverter(ObservableList<Appointment> items){
        // https://stackoverflow.com/questions/35744227/javafx-8-combobox-and-observablelist
        StringConverter<Appointment> converter = new StringConverter<>() {
            @Override
            public String toString(Appointment item) {
                String name;
                try {
                    name = item.getType();
                } catch (NullPointerException e) { // Work around for bug: https://bugs.openjdk.java.net/browse/JDK-8150178
                    name = ""; 
                }
                return name;
            }

            @Override
            public Appointment fromString(String id) {
                return items.stream().filter(item -> item.getId() == Integer.parseInt(id))
                        .collect(Collectors.toList()).get(0);
            }
        };
        return converter;
    }
    
    /**
     * Static Class to convert Instant to Timestamp. 
     * @param instant Instant to convert to Timestamp 
     * @return Returns Timestamp.
     */
    public static Timestamp instantToTimestamp(Instant instant){
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.ofInstant(instant, ZoneOffset.UTC));
        return timestamp;
    }
    
    /**
     * Static Class to convert Timestamp to Instant. 
     * @param timestamp Timestamp to convert to Instant 
     * @return Returns Instant.
     */
    public static Instant timestampToInstant(Timestamp timestamp){
        Instant instant = timestamp.toInstant(); // For MySql 5.7
        // Instant instant = timestamp.toLocalDateTime().atOffset(ZoneOffset.UTC).toInstant(); // Workaround for MySql 8
        return instant;
    }
    
    /** Parses String to int. 
    Returns -1 if String does not exclusively contain an integer.
    @param strNumber The String to convert to Double
    @return Returns Int if successful. Returns -1 on Failure.
    */
    public static int myParseInt(String strNumber) {
        if (strNumber != null && strNumber.length() > 0) {
            try {
                return parseInt(strNumber);
            } catch(Exception e) {
                return -1;
            }
        }
        else return -1;
    }
    
    /** Formats TableColumn containing Instant to display in Local Time. 
     * <p><strong>A lambda expression is used in this method.</strong></p>
     * <p>
     * A lambda expression is used in this method to simplify and cleanup the code.
     * </p>
     * The value of all cells in column will be displayed in local Time.
     * @param column The TableColumn to be displayed in Local Time.
     */
    public static void toLocalDateTime(TableColumn <Object,Object> column){
        column.setCellFactory(c -> new TableCell<Object, Object>() {
            @Override
            protected void updateItem(Object time, boolean isEmpty) {
                super.updateItem(time, isEmpty);
                if (isEmpty) {
                    setText(null);
                } else {
                    LocalDateTime localStartDateTime = LocalDateTime.from(( (Instant) time).atZone(ZoneId.systemDefault()));
                    setText(localStartDateTime.toString());
                }
            }
        });
    }
}
