/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gov.nih.nlm.chqa.model.experiment;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author gayens
 */

@Entity
@Table(name = "USER_BASE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserBase implements Serializable{
    
    @Id
    private Long userID;
    private Integer qestionID;
    private String userName;
    private Integer status;   
            
}
