/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.model;

import java.util.ArrayList;

/**
 *
 * @author sunkwa-arthur
 */
public class GhanaCardVerification {

    public String message;
    public String code;
    public String status;
    public PersonData personData;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PersonData getPersonData() {
        return personData;
    }

    public void setPersonData(PersonData personData) {
        this.personData = personData;
    }

    public class PersonData {

        public String nationalId;
        public String cardId;
        public String cardValidFrom;
        public String cardValidTo;
        public String surname;
        public String forenames;
        public String nationality;
        public String birthDate;
        public String birthCountry;
        public String birthDistrict;
        public String birthRegion;
        public String birthTown;
        public Object prevName;
        public String gender;
        public ArrayList<Address> addresses;
        public Contact contact;
        public ArrayList<Occupation> occupations;
        public BiometricFeed biometricFeed;
        public ArrayList<Binary> binaries;

        public String getNationalId() {
            return nationalId;
        }

        public void setNationalId(String nationalId) {
            this.nationalId = nationalId;
        }

        public String getCardId() {
            return cardId;
        }

        public void setCardId(String cardId) {
            this.cardId = cardId;
        }

        public String getCardValidFrom() {
            return cardValidFrom;
        }

        public void setCardValidFrom(String cardValidFrom) {
            this.cardValidFrom = cardValidFrom;
        }

        public String getCardValidTo() {
            return cardValidTo;
        }

        public void setCardValidTo(String cardValidTo) {
            this.cardValidTo = cardValidTo;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getForenames() {
            return forenames;
        }

        public void setForenames(String forenames) {
            this.forenames = forenames;
        }

        public String getNationality() {
            return nationality;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
        }

        public String getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(String birthDate) {
            this.birthDate = birthDate;
        }

        public String getBirthCountry() {
            return birthCountry;
        }

        public void setBirthCountry(String birthCountry) {
            this.birthCountry = birthCountry;
        }

        public String getBirthDistrict() {
            return birthDistrict;
        }

        public void setBirthDistrict(String birthDistrict) {
            this.birthDistrict = birthDistrict;
        }

        public String getBirthRegion() {
            return birthRegion;
        }

        public void setBirthRegion(String birthRegion) {
            this.birthRegion = birthRegion;
        }

        public String getBirthTown() {
            return birthTown;
        }

        public void setBirthTown(String birthTown) {
            this.birthTown = birthTown;
        }

        public Object getPrevName() {
            return prevName;
        }

        public void setPrevName(Object prevName) {
            this.prevName = prevName;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public ArrayList<Address> getAddresses() {
            return addresses;
        }

        public void setAddresses(ArrayList<Address> addresses) {
            this.addresses = addresses;
        }

        public Contact getContact() {
            return contact;
        }

        public void setContact(Contact contact) {
            this.contact = contact;
        }

        public ArrayList<Occupation> getOccupations() {
            return occupations;
        }

        public void setOccupations(ArrayList<Occupation> occupations) {
            this.occupations = occupations;
        }

        public BiometricFeed getBiometricFeed() {
            return biometricFeed;
        }

        public void setBiometricFeed(BiometricFeed biometricFeed) {
            this.biometricFeed = biometricFeed;
        }

        public ArrayList<Binary> getBinaries() {
            return binaries;
        }

        public void setBinaries(ArrayList<Binary> binaries) {
            this.binaries = binaries;
        }

        public class Address {

            public String type;
            public String community;
            public String postalCode;
            public String region;
            public GpsAddressDetails gpsAddressDetails;
            public String addressDigital;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getCommunity() {
                return community;
            }

            public void setCommunity(String community) {
                this.community = community;
            }

            public String getPostalCode() {
                return postalCode;
            }

            public void setPostalCode(String postalCode) {
                this.postalCode = postalCode;
            }

            public String getRegion() {
                return region;
            }

            public void setRegion(String region) {
                this.region = region;
            }

            public GpsAddressDetails getGpsAddressDetails() {
                return gpsAddressDetails;
            }

            public void setGpsAddressDetails(GpsAddressDetails gpsAddressDetails) {
                this.gpsAddressDetails = gpsAddressDetails;
            }

            public String getAddressDigital() {
                return addressDigital;
            }

            public void setAddressDigital(String addressDigital) {
                this.addressDigital = addressDigital;
            }

            public class GpsAddressDetails {

                public String gpsName;
                public String region;
                public String district;
                public String area;
                public String street;
                public String longitude;
                public String latitude;

                public String getGpsName() {
                    return gpsName;
                }

                public void setGpsName(String gpsName) {
                    this.gpsName = gpsName;
                }

                public String getRegion() {
                    return region;
                }

                public void setRegion(String region) {
                    this.region = region;
                }

                public String getDistrict() {
                    return district;
                }

                public void setDistrict(String district) {
                    this.district = district;
                }

                public String getArea() {
                    return area;
                }

                public void setArea(String area) {
                    this.area = area;
                }

                public String getStreet() {
                    return street;
                }

                public void setStreet(String street) {
                    this.street = street;
                }

                public String getLongitude() {
                    return longitude;
                }

                public void setLongitude(String longitude) {
                    this.longitude = longitude;
                }

                public String getLatitude() {
                    return latitude;
                }

                public void setLatitude(String latitude) {
                    this.latitude = latitude;
                }

            }
        }

        public class Contact {

            public String email;
            public ArrayList<PhoneNumber> phoneNumbers;

            public class PhoneNumber {

                public String type;
                public String phoneNumber;
                public String network;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getPhoneNumber() {
                    return phoneNumber;
                }

                public void setPhoneNumber(String phoneNumber) {
                    this.phoneNumber = phoneNumber;
                }

                public String getNetwork() {
                    return network;
                }

                public void setNetwork(String network) {
                    this.network = network;
                }

            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public ArrayList<PhoneNumber> getPhoneNumbers() {
                return phoneNumbers;
            }

            public void setPhoneNumbers(ArrayList<PhoneNumber> phoneNumbers) {
                this.phoneNumbers = phoneNumbers;
            }

        }

        public class Occupation {

            public String name;
            public Object validFrom;
            public Object validTo;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Object getValidFrom() {
                return validFrom;
            }

            public void setValidFrom(Object validFrom) {
                this.validFrom = validFrom;
            }

            public Object getValidTo() {
                return validTo;
            }

            public void setValidTo(Object validTo) {
                this.validTo = validTo;
            }

        }

        public class BiometricFeed {

            public Face face;
            public Object dataType;

            public Face getFace() {
                return face;
            }

            public void setFace(Face face) {
                this.face = face;
            }

            public Object getDataType() {
                return dataType;
            }

            public void setDataType(Object dataType) {
                this.dataType = dataType;
            }

            public class Face {

                public String dataType;
                public String data;

                public String getDataType() {
                    return dataType;
                }

                public void setDataType(String dataType) {
                    this.dataType = dataType;
                }

                public String getData() {
                    return data;
                }

                public void setData(String data) {
                    this.data = data;
                }

            }
        }

        public class Binary {

            public String type;
            public String dataType;
            public String data;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDataType() {
                return dataType;
            }

            public void setDataType(String dataType) {
                this.dataType = dataType;
            }

            public String getData() {
                return data;
            }

            public void setData(String data) {
                this.data = data;
            }

        }
    }
}
