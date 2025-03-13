/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.*;
/**
 *
 * @author admin
 */
public abstract class BaseModel {
    private int id;
    private Employee createdby;
    private Date createddate;

    @Override
    public String toString() {
        return "BaseModel{" + "id=" + id + ", createdby=" + createdby + ", createddate=" + createddate + '}';
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getCreatedby() {
        return createdby;
    }

    public void setCreatedby(Employee createdby) {
        this.createdby = createdby;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }
    
    
}
