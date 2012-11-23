/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.pranavprakash.peoplesoft;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Pranav
 */
@ManagedBean
@SessionScoped
public class AuthenticateUser {

    /**
     * Creates a new instance of AuthenticateUser
     */
    private String userid;
    private String pwd;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
    public String doLogin() {
       return "main";
    }
}
