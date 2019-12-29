package sk.softip;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Logger;

@Entity
@Table(name = "Property", uniqueConstraints = {
@UniqueConstraint(columnNames = "propertyId")})

public class Property implements Serializable {
    private static  final long serialVersionUID = 1L;
    @PersistenceContext
    private EntityManager entityManager;

    private final static Logger logger = Logger.getLogger(Property.class.getName());
    @Id
    @Column(name = "propertyId", unique = true, nullable = false)
    private int propertyId;
    @Column(name = "propertyName", unique = true, nullable = false)
    private  String propertyName;
    @Column(name = "propertyRoom")
    private  String propertyRoom;
    @Column(name = "propertyType", nullable = false)
    private int propertyType;
    @Column(name = "propertyPrice", nullable = false)
    private float propertyPrice;
    @Column(name = "propertyInDate", nullable = false)
    private LocalDate propertyInDate;
    @Column(name = "propertyOutDate", unique = true, nullable = true)
    private LocalDate propertyOutDate;
    @Column(name = "propertyState")
    private char propertyState;


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
         logger.info("Zaevidovanie majektu");
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

    /*public static void insertProperty (Property property, Connection connect){
        int id = 0;
        String SQL = "INSERT INTO PROPERTY VALUES(?,?,?,?,?,?,?,?)";
        System.out.println(java.sql.Date.valueOf(property.getPropertyInDate()));
        try {
            PreparedStatement pstmt = connect.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1,property.getPropertyId());
            pstmt.setString(2, property.getPropertyName());
            pstmt.setString(3, property.getPropertyRoom());
            pstmt.setInt(4,property.getPropertyType());
            pstmt.setFloat(5,property.getPropertyPrice());
            pstmt.setDate(6, java.sql.Date.valueOf(property.getPropertyInDate()));
            if (property.getPropertyOutDate() == null){
                pstmt.setNull(7,java.sql.Types.DATE);
            }
            else{
                pstmt.setDate(7, java.sql.Date.valueOf(property.getPropertyOutDate()));
            }
            pstmt.setString(8, String.valueOf(property.getPropertyState()));

            int affectedRows = pstmt.executeUpdate();
            // check the affected rows
            if (affectedRows > 0) {
                // get the ID back
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        id = rs.getInt(1);
                    }
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(id);
    }*/

    @Transactional
    public void insertWithQuery(Property property) {
        entityManager.createNativeQuery("INSERT INTO property VALUES (?,?,?,?,?,?,?,?)")
                .setParameter(1, property.getPropertyId())
                .setParameter(2, property.getPropertyName())
                .setParameter(3, property.getPropertyRoom())
                .setParameter(4, property.getPropertyType())
                .setParameter(5, property.getPropertyPrice())
                .setParameter(6, property.getPropertyInDate())
                .setParameter(7, property.getPropertyOutDate())
                .setParameter(8, property.getPropertyState())
                .executeUpdate();
    }
}
