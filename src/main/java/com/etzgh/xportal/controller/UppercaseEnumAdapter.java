/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.etzgh.xportal.controller;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import org.apache.log4j.Logger;

/**
 *
 * @author sunkwa-arthur
 */
public class UppercaseEnumAdapter implements JsonDeserializer<Enum> {

    private static final Logger log = Logger.getLogger(RefreshToken.class.getName());

    @Override
    public Enum deserialize(JsonElement json, java.lang.reflect.Type type, JsonDeserializationContext context)
            throws JsonParseException {
        try {
            if (type instanceof Class && ((Class<?>) type).isEnum()) {
                return Enum.valueOf((Class<Enum>) type, json.getAsString().toUpperCase());
            }
            return null;
        } catch (Exception e) {
            log.error("ERROR", e);
            return null;
        }
    }
}
