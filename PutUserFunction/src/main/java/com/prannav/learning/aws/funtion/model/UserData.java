package com.prannav.learning.aws.funtion.model;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.io.Serializable;
import java.util.Objects;

@DynamoDbBean
public class UserData implements Serializable {
    private String userId;
    private String firstName;
    private String lastName;
    private Integer age;
    private Boolean isActive;

    @DynamoDbPartitionKey
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;
        UserData userData = (UserData) object;
        return java.util.Objects.equals(userId, userData.userId) && java.util.Objects.equals(firstName, userData.firstName) && java.util.Objects.equals(lastName, userData.lastName) && java.util.Objects.equals(age, userData.age) && java.util.Objects.equals(isActive, userData.isActive);
    }

    public int hashCode() {
        return java.util.Objects.hash(super.hashCode(), userId, firstName, lastName, age, isActive);
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "UserData{" +
                "userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", isActive=" + isActive +
                '}';
    }
}
