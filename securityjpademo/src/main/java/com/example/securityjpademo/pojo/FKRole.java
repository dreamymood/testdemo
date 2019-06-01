package com.example.securityjpademo.pojo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_role")
public class FKRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "autority")
    private String autority;

    public FKRole() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAutority() {
        return autority;
    }

    public void setAutority(String autority) {
        this.autority = autority;
    }

    @Override
    public String toString() {
        return "FKRole [id="+id+",authority="+autority+"]";
    }
}
