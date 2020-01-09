package sk.softip;
import org.apache.log4j.Logger;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "property")
public class Property implements Serializable {
    private static  final long serialVersionUID = 1L;

    @Id
    @Column(name = "property_ID", unique = true, nullable = false)
    private int propertyId;
    @Column(name = "property_Name", unique = true, nullable = false)
    private  String propertyName;

    @Column(name = "property_Room")
    private  String propertyRoom;
    @Column(name = "property_Type", nullable = false)
    private int propertyType;
    @Column(name = "property_Price", nullable = false)
    private float propertyPrice;
    @Column(name = "property_In_date", nullable = false)
    private LocalDate propertyInDate;
    @Column(name = "property_Out_date", unique = true)
    private LocalDate propertyOutDate;
    @Column(name = "property_State")
    private char propertyState;

    public Property(){

    }


    public Property(String[] parameters)  {
        if  (parameters[6].length() > 1) {
                      this.propertyOutDate = LocalDate.parse(parameters[6].substring(0, 4) + "-" + parameters[6].substring(4, 6) + "-" + parameters[6].substring(6, 8));
        }
        else {
            this.propertyOutDate = null;
        }

        this.propertyId = Integer.parseInt(parameters[0]);
        this.propertyName = parameters[1];
        this.propertyRoom = parameters[2];
        this.propertyType = Integer.parseInt(parameters[3]);
        this.propertyPrice = Float.parseFloat(parameters[4].substring(0, parameters[4].length() - 3).replace(",","."));
        this.propertyInDate =LocalDate.parse(parameters[5].substring(0,4)+"-" + parameters[5].substring(4,6)+"-"+ parameters[5].substring(6,8));
        this.propertyState = parameters[7].charAt(0);
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyRoom() {
        return propertyRoom;
    }

    public void setPropertyRoom(String propertyRoom) {
        this.propertyRoom = propertyRoom;
    }

    public int getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(int propertyType) {
        this.propertyType = propertyType;
    }

    public float getPropertyPrice() {
        return propertyPrice;
    }

    public void setPropertyPrice(float propertyPrice) {
        this.propertyPrice = propertyPrice;
    }

    public LocalDate getPropertyInDate() {
        return propertyInDate;
    }

    public void setPropertyInDate(LocalDate propertyInDate) {
        this.propertyInDate = propertyInDate;
    }

    public LocalDate getPropertyOutDate() {
        return propertyOutDate;
    }

    public void setPropertyOutDate(LocalDate propertyOutDate) {
        this.propertyOutDate = propertyOutDate;
    }

    public char getPropertyState() {
        return propertyState;
    }

    public void setPropertyState(char propertyState) {
        this.propertyState = propertyState;
    }

    @Override
    public String toString() {
        return
                propertyId + " " +
                propertyName +  " " +
                propertyRoom +  " " +
                propertyType + " " +
                propertyPrice + " " +
                propertyInDate + " " +
                propertyOutDate + " " +
                propertyState ;
    }

    public static List<Property> getProperties(EntityManager em) {

        String strQuery = "SELECT p FROM Property p WHERE p.id IS NOT NULL";

        // Issue the query and get a matching Customer
        TypedQuery<Property> tq = em.createQuery(strQuery, Property.class);
        List<Property> properties = null;
        try {
            // Get matching customer object and output
            properties = tq.getResultList();
        }
        catch(NoResultException ex) {
            ex.printStackTrace();
        }
        return properties;
    }

/*    public static boolean propertyValidation(Property p){
        if (p.getPropertyType() != 0 && p.getPropertyType() != 1){
            System.out.println("zla hodnota typu");
            return false;
        }

        if (p.getPropertyState() != 'V' && p.getPropertyState() != 'M' && p.getPropertyState() != 'O'){
            System.out.println("zla hodnota state");
            return false;
        }
        else return true;

    }

    public static boolean typeValidation(Property p){
        if (p.getPropertyType() != 0 && p.getPropertyType() != 1){
            //System.out.println("zla hodnota typu");
            return false;
        }
        else
            return true;

    }

    public static boolean stateValidation(Property p){
        if (p.getPropertyState() != 'V' && p.getPropertyState() != 'M' && p.getPropertyState() != 'O'){
            System.out.println("zla hodnota state");
            return false;
        }
        else return true;
    }
*/

}
