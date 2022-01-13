package gr.hua.petregistry;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreatePetDTO {

    private String name;

    private String type;

    private String sex;

    private String birthday;

    @JsonProperty("microchip_number")
    private int microchipNumber;

    private String breed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getMicrochipNumber() {
        return microchipNumber;
    }

    public void setMicrochipNumber(int microchipNumber) {
        this.microchipNumber = microchipNumber;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
}
