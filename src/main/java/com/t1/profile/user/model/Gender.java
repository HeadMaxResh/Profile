package com.t1.profile.user.model;

            public enum Gender {
              MALE, FEMALE;

              @Override
              public String toString() {
                return name().toLowerCase();
              }

              public static Gender fromString(String gender) {
                  try {
                      return valueOf(gender.toUpperCase());
                  } catch (IllegalArgumentException | NullPointerException e) {
                      //TODO Сделать свой тип исключения, для возвращения понятной ошибки
                      throw new IllegalArgumentException("Invalid gender value: " + gender);
                  }
              }
            }
