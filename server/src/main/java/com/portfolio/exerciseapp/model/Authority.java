package com.portfolio.exerciseapp.model;

import java.util.Objects;

/**
 * Model class representing an Authority, or a user role within the application.
 *
 * Contains the name of the role. Note that this begins with "ROLE_" in all caps.
 */
public class Authority {

   // Constants representing the Authority objects for the user roles for this application.
   public static final Authority ADMIN_AUTHORITY = new Authority("ROLE_ADMIN");
   public static final Authority USER_AUTHORITY = new Authority("ROLE_USER");

   private String name;

   public Authority() {}

   public Authority(String name) {
      this.name = name;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   /*
    * Note that the `equals` method must be overridden in class to support checking object equality
    * based on the values of its properties - for example here, two Authority instances are
    * considered equal if they have the same `name` value. If this method is not overridden in a class,
    * the .equals method only returns true when comparing the same exact instance of a class to itself.
    *
    * Note that it is a best practice to ensure that `equals` and `hashcode` are consistent by overriding
    * both methods and having them use the same properties to check equality and generate the hash.
    */
   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (o == null || getClass() != o.getClass()) {
         return false;
      }
      Authority authority = (Authority) o;
      return name.equals(authority.name);
   }

   @Override
   public int hashCode() {
      return Objects.hash(name);
   }

   @Override
   public String toString() {
      return "Authority{" +
              "name=" + name +
              '}';
   }
}
