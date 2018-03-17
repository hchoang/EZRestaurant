package restaurant.model;

import java.io.Serializable;

public abstract class User implements Serializable {

    private String username;
    private String password;
    private String name;
    private int age;
    private boolean gender; //True is Male, False is Female
    private String email;
    private boolean active; // user's status 

    /**
     * Constructor with username, password and relevant info of the user
     *
     * @param username username to login the account
     * @param password password to login the account
     * @param name name of the user
     * @param age age of the user
     * @param gender true is male, false is Female
     * @param email email to contact the user of the account
     */
    public User(String username, String password, String name, int age, boolean gender, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.email = email;
        active=true;
    }

    /**
     * Accessors to the status of the user
     *
     * @return true if account is active, otherwise false
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Activate the user
     */
    public void activate() {
        active = true;
    }

    /**
     * Deactivate the user
     */
    public void deactivate() {
        active = false;
    }

    /**
     * Accessors to age
     *
     * @return the age of user
     */
    public int getAge() {
        return age;
    }

    /**
     * Accessors to name
     *
     * @return the name of user
     */
    public String getName() {
        return name;
    }

    /**
     * Mutator to age
     *
     * @param age the age of the user
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Mutator to name
     *
     * @param name the name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Mutator to username
     *
     * @param username the username of the account
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Accessors to password
     *
     * @return the password of account
     */
    public String getPassword() {
        return password;
    }

    /**
     * Accessors to username
     *
     * @return the username of account
     */
    public String getUsername() {
        return username;
    }

    /**
     * Mutator to password
     *
     * @param password the password of the account
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Accessors to email
     *
     * @return the email of user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Mutator to email
     *
     * @param email the email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Accessors to gender
     *
     * @return true if male, otherwise female
     */
    public boolean isGender() {
        return gender;
    }

    /**
     * Mutator to gender
     *
     * @param gender true if male, otherwise false
     */
    public void setGender(boolean gender) {
        this.gender = gender;
    }  

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", password=" + password + ", name=" + name + ", age=" + age + ", gender=" + gender + ", email=" + email + ", active=" + active + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if ((this.username == null) ? (other.username != null) : !this.username.equals(other.username)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + (this.username != null ? this.username.hashCode() : 0);
        return hash;
    }

}
